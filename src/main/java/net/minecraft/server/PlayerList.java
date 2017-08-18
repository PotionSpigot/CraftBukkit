package net.minecraft.server;

import java.io.File;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import net.minecraft.util.com.google.common.collect.Lists;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.TravelAgent;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;
import org.spigotmc.SpigotConfig;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public abstract class PlayerList
{
  public static final File a = new File("banned-players.json");
  public static final File b = new File("banned-ips.json");
  public static final File c = new File("ops.json");
  public static final File d = new File("whitelist.json");
  private static final Logger g = org.apache.logging.log4j.LogManager.getLogger();
  private static final SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
  private final MinecraftServer server;
  public final List players = new java.util.concurrent.CopyOnWriteArrayList();
  
  public final Map<String, EntityPlayer> playerMap = new HashMap()
  {
    public EntityPlayer put(String key, EntityPlayer value) {
      return (EntityPlayer)super.put(key.toLowerCase(), value);
    }
    

    public EntityPlayer get(Object key)
    {
      EntityPlayer player = (EntityPlayer)super.get((key instanceof String) ? ((String)key).toLowerCase() : key);
      return (player != null) && (player.playerConnection != null) ? player : null;
    }
    
    public boolean containsKey(Object key)
    {
      return get(key) != null;
    }
    
    public EntityPlayer remove(Object key)
    {
      return (EntityPlayer)super.remove((key instanceof String) ? ((String)key).toLowerCase() : key);
    }
  };
  public final Map<UUID, EntityPlayer> uuidMap = new HashMap()
  {
    public EntityPlayer get(Object key)
    {
      EntityPlayer player = (EntityPlayer)super.get((key instanceof String) ? ((String)key).toLowerCase() : key);
      return (player != null) && (player.playerConnection != null) ? player : null;
    }
  };
  
  private final GameProfileBanList j;
  
  private final IpBanList k;
  private final OpList operators;
  private final WhiteList whitelist;
  private final Map n;
  public IPlayerFileData playerFileData;
  public boolean hasWhitelist;
  protected int maxPlayers;
  private int q;
  private EnumGamemode r;
  private boolean s;
  private int t;
  private CraftServer cserver;
  
  public PlayerList(MinecraftServer minecraftserver)
  {
    minecraftserver.server = new CraftServer(minecraftserver, this);
    minecraftserver.console = org.bukkit.craftbukkit.v1_7_R4.command.ColouredConsoleSender.getInstance();
    minecraftserver.reader.addCompleter(new org.bukkit.craftbukkit.v1_7_R4.command.ConsoleCommandCompleter(minecraftserver.server));
    this.cserver = minecraftserver.server;
    

    this.j = new GameProfileBanList(a);
    this.k = new IpBanList(b);
    this.operators = new OpList(c);
    this.whitelist = new WhiteList(d);
    this.n = net.minecraft.util.com.google.common.collect.Maps.newHashMap();
    this.server = minecraftserver;
    this.j.a(false);
    this.k.a(false);
    this.maxPlayers = 8;
  }
  
  public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
    GameProfile gameprofile = entityplayer.getProfile();
    UserCache usercache = this.server.getUserCache();
    GameProfile gameprofile1 = usercache.a(gameprofile.getId());
    String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
    
    usercache.a(gameprofile);
    NBTTagCompound nbttagcompound = a(entityplayer);
    
    entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
    entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
    String s1 = "local";
    
    if (networkmanager.getSocketAddress() != null) {
      s1 = networkmanager.getSocketAddress().toString();
    }
    

    Player bukkitPlayer = entityplayer.getBukkitEntity();
    PlayerSpawnLocationEvent ev = new PlayerSpawnLocationEvent(bukkitPlayer, bukkitPlayer.getLocation());
    Bukkit.getPluginManager().callEvent(ev);
    
    Location loc = ev.getSpawnLocation();
    WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
    
    entityplayer.spawnIn(world);
    entityplayer.setPosition(loc.getX(), loc.getY(), loc.getZ());
    entityplayer.b(loc.getYaw(), loc.getPitch());
    



    WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
    ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
    
    a(entityplayer, (EntityPlayer)null, worldserver);
    PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);
    

    int maxPlayers = getMaxPlayers();
    if (maxPlayers > 60) {
      maxPlayers = 60;
    }
    playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worldserver.getWorldData().isHardcore(), worldserver.worldProvider.dimension, worldserver.difficulty, maxPlayers, worldserver.getWorldData().getType()));
    entityplayer.getBukkitEntity().sendSupportedChannels();
    
    playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", getServer().getServerModName().getBytes(net.minecraft.util.com.google.common.base.Charsets.UTF_8)));
    playerconnection.sendPacket(new PacketPlayOutSpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
    playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
    playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
    entityplayer.getStatisticManager().d();
    entityplayer.getStatisticManager().updateStatistics(entityplayer);
    sendScoreboard((ScoreboardServer)worldserver.getScoreboard(), entityplayer);
    this.server.az();
    











    c(entityplayer);
    worldserver = this.server.getWorldServer(entityplayer.dimension);
    playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
    b(entityplayer, worldserver);
    if (this.server.getResourcePack().length() > 0) {
      entityplayer.setResourcePack(this.server.getResourcePack());
    }
    
    Iterator iterator = entityplayer.getEffects().iterator();
    
    while (iterator.hasNext()) {
      MobEffect mobeffect = (MobEffect)iterator.next();
      
      playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
    }
    
    entityplayer.syncInventory();
    if ((nbttagcompound != null) && (nbttagcompound.hasKeyOfType("Riding", 10))) {
      Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), worldserver);
      
      if (entity != null) {
        entity.attachedToPlayer = true;
        worldserver.addEntity(entity);
        entityplayer.mount(entity);
        entity.attachedToPlayer = false;
      }
    }
    

    g.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "] " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
  }
  
  public void sendScoreboard(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
    HashSet hashset = new HashSet();
    Iterator iterator = scoreboardserver.getTeams().iterator();
    
    while (iterator.hasNext()) {
      ScoreboardTeam scoreboardteam = (ScoreboardTeam)iterator.next();
      
      entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
    }
    
    for (int i = 0; i < 3; i++) {
      ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);
      
      if ((scoreboardobjective != null) && (!hashset.contains(scoreboardobjective))) {
        List list = scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective);
        Iterator iterator1 = list.iterator();
        
        while (iterator1.hasNext()) {
          Packet packet = (Packet)iterator1.next();
          
          entityplayer.playerConnection.sendPacket(packet);
        }
        
        hashset.add(scoreboardobjective);
      }
    }
  }
  
  public void setPlayerFileData(WorldServer[] aworldserver) {
    if (this.playerFileData != null) return;
    this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
  }
  
  public void a(EntityPlayer entityplayer, WorldServer worldserver) {
    WorldServer worldserver1 = entityplayer.r();
    
    if (worldserver != null) {
      worldserver.getPlayerChunkMap().removePlayer(entityplayer);
    }
    
    worldserver1.getPlayerChunkMap().addPlayer(entityplayer);
    worldserver1.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
  }
  
  public int d() {
    return PlayerChunkMap.getFurthestViewableBlock(s());
  }
  
  public NBTTagCompound a(EntityPlayer entityplayer)
  {
    NBTTagCompound nbttagcompound = ((WorldServer)this.server.worlds.get(0)).getWorldData().i();
    
    NBTTagCompound nbttagcompound1;
    if ((entityplayer.getName().equals(this.server.M())) && (nbttagcompound != null)) {
      entityplayer.f(nbttagcompound);
      NBTTagCompound nbttagcompound1 = nbttagcompound;
      g.debug("loading single player");
    } else {
      nbttagcompound1 = this.playerFileData.load(entityplayer);
    }
    
    return nbttagcompound1;
  }
  
  protected void b(EntityPlayer entityplayer) {
    this.playerFileData.save(entityplayer);
    ServerStatisticManager serverstatisticmanager = (ServerStatisticManager)this.n.get(entityplayer.getUniqueID());
    
    if (serverstatisticmanager != null) {
      serverstatisticmanager.b();
    }
  }
  
  public void c(EntityPlayer entityplayer) {
    this.cserver.detectListNameConflict(entityplayer);
    
    this.players.add(entityplayer);
    this.playerMap.put(entityplayer.getName(), entityplayer);
    this.uuidMap.put(entityplayer.getUniqueID(), entityplayer);
    WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
    

    PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.getName() + " joined the game.");
    this.cserver.getPluginManager().callEvent(playerJoinEvent);
    
    String joinMessage = playerJoinEvent.getJoinMessage();
    
    if ((joinMessage != null) && (joinMessage.length() > 0)) {
      for (IChatBaseComponent line : org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage.fromString(joinMessage)) {
        this.server.getPlayerList().sendAll(new PacketPlayOutChat(line));
      }
    }
    this.cserver.onPlayerJoin(playerJoinEvent.getPlayer());
    
    ChunkIOExecutor.adjustPoolSize(getPlayerCount());
    


    if ((entityplayer.world == worldserver) && (!worldserver.players.contains(entityplayer))) {
      worldserver.addEntity(entityplayer);
      a(entityplayer, (WorldServer)null);
    }
    


    PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.addPlayer(entityplayer);
    PacketPlayOutPlayerInfo displayPacket = PacketPlayOutPlayerInfo.updateDisplayName(entityplayer);
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      
      if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
        entityplayer1.playerConnection.sendPacket(packet);
        
        if ((!entityplayer.getName().equals(entityplayer.listName)) && (entityplayer1.playerConnection.networkManager.getVersion() > 28))
        {
          entityplayer1.playerConnection.sendPacket(displayPacket);
        }
      }
    }
    


    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      

      if (entityplayer.getBukkitEntity().canSee(entityplayer1.getBukkitEntity()))
      {


        entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.addPlayer(entityplayer1));
        
        if ((!entityplayer.getName().equals(entityplayer.listName)) && (entityplayer.playerConnection.networkManager.getVersion() > 28))
        {
          entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.updateDisplayName(entityplayer1));
        }
      }
    }
  }
  
  public void d(EntityPlayer entityplayer)
  {
    entityplayer.r().getPlayerChunkMap().movePlayer(entityplayer);
  }
  
  public String disconnect(EntityPlayer entityplayer) {
    entityplayer.a(StatisticList.f);
    

    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleInventoryCloseEvent(entityplayer);
    
    PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.getName() + " left the game.");
    this.cserver.getPluginManager().callEvent(playerQuitEvent);
    entityplayer.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
    

    b(entityplayer);
    WorldServer worldserver = entityplayer.r();
    
    if ((entityplayer.vehicle != null) && (!(entityplayer.vehicle instanceof EntityPlayer))) {
      worldserver.removeEntity(entityplayer.vehicle);
      g.debug("removing player mount");
    }
    
    worldserver.kill(entityplayer);
    worldserver.getPlayerChunkMap().removePlayer(entityplayer);
    this.players.remove(entityplayer);
    this.uuidMap.remove(entityplayer.getUniqueID());
    this.playerMap.remove(entityplayer.getName());
    this.n.remove(entityplayer.getUniqueID());
    ChunkIOExecutor.adjustPoolSize(getPlayerCount());
    


    PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.removePlayer(entityplayer);
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      
      if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
        entityplayer1.playerConnection.sendPacket(packet);
      } else {
        entityplayer1.getBukkitEntity().removeDisconnectingPlayer(entityplayer.getBukkitEntity());
      }
    }
    
    this.cserver.getScoreboardManager().removePlayer(entityplayer.getBukkitEntity());
    
    return playerQuitEvent.getQuitMessage();
  }
  




  public EntityPlayer attemptLogin(LoginListener loginlistener, GameProfile gameprofile, String hostname)
  {
    java.net.SocketAddress socketaddress = loginlistener.networkManager.getSocketAddress();
    
    EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), gameprofile, new PlayerInteractManager(this.server.getWorldServer(0)));
    Player player = entity.getBukkitEntity();
    PlayerLoginEvent event = new PlayerLoginEvent(player, hostname, ((InetSocketAddress)socketaddress).getAddress(), ((InetSocketAddress)loginlistener.networkManager.getRawAddress()).getAddress());
    

    if ((this.j.isBanned(gameprofile)) && (!this.j.get(gameprofile).hasExpired())) {
      GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry)this.j.get(gameprofile);
      
      String s = "You are banned from this server!\nReason: " + gameprofilebanentry.getReason();
      if (gameprofilebanentry.getExpires() != null) {
        s = s + "\nYour ban will be removed on " + h.format(gameprofilebanentry.getExpires());
      }
      

      if (!gameprofilebanentry.hasExpired()) event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s);
    } else if (!isWhitelisted(gameprofile))
    {
      event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
    } else if ((this.k.isBanned(socketaddress)) && (!this.k.get(socketaddress).hasExpired())) {
      IpBanEntry ipbanentry = this.k.get(socketaddress);
      
      String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getReason();
      if (ipbanentry.getExpires() != null) {
        s = s + "\nYour ban will be removed on " + h.format(ipbanentry.getExpires());
      }
      

      event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s);

    }
    else if (this.players.size() >= this.maxPlayers) {
      event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
    }
    

    this.cserver.getPluginManager().callEvent(event);
    if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
      loginlistener.disconnect(event.getKickMessage());
      return null;
    }
    
    return entity;
  }
  
  public EntityPlayer processLogin(GameProfile gameprofile, EntityPlayer player)
  {
    UUID uuid = EntityHuman.a(gameprofile);
    ArrayList arraylist = Lists.newArrayList();
    







    EntityPlayer entityplayer;
    






    if ((entityplayer = (EntityPlayer)this.uuidMap.get(uuid)) != null)
    {
      entityplayer.playerConnection.disconnect("You logged in from another location");
    }
    











    return player;
  }
  

  public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag)
  {
    return moveToWorld(entityplayer, i, flag, null, true);
  }
  
  public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation)
  {
    entityplayer.r().getTracker().untrackPlayer(entityplayer);
    
    entityplayer.r().getPlayerChunkMap().removePlayer(entityplayer);
    this.players.remove(entityplayer);
    this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
    ChunkCoordinates chunkcoordinates = entityplayer.getBed();
    boolean flag1 = entityplayer.isRespawnForced();
    












    EntityPlayer entityplayer1 = entityplayer;
    org.bukkit.World fromWorld = entityplayer1.getBukkitEntity().getWorld();
    entityplayer1.viewingCredits = false;
    

    entityplayer1.playerConnection = entityplayer.playerConnection;
    entityplayer1.copyTo(entityplayer, flag);
    entityplayer1.d(entityplayer.getId());
    





    if (location == null) {
      boolean isBedSpawn = false;
      CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
      if ((cworld != null) && (chunkcoordinates != null)) {
        ChunkCoordinates chunkcoordinates1 = EntityHuman.getBed(cworld.getHandle(), chunkcoordinates, flag1);
        if (chunkcoordinates1 != null) {
          isBedSpawn = true;
          location = new Location(cworld, chunkcoordinates1.x + 0.5D, chunkcoordinates1.y, chunkcoordinates1.z + 0.5D);
        } else {
          entityplayer1.setRespawnPosition(null, true);
          entityplayer1.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
        }
      }
      
      if (location == null) {
        cworld = (CraftWorld)this.server.server.getWorlds().get(0);
        chunkcoordinates = cworld.getHandle().getSpawn();
        location = new Location(cworld, chunkcoordinates.x + 0.5D, chunkcoordinates.y, chunkcoordinates.z + 0.5D);
      }
      
      Player respawnPlayer = this.cserver.getPlayer(entityplayer1);
      PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
      this.cserver.getPluginManager().callEvent(respawnEvent);
      
      if (entityplayer.playerConnection.isDisconnected()) {
        return entityplayer;
      }
      

      location = respawnEvent.getRespawnLocation();
      entityplayer.reset();
    } else {
      location.setWorld(this.server.getWorldServer(i).getWorld());
    }
    WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
    entityplayer1.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    

    worldserver.chunkProviderServer.getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4);
    
    while ((avoidSuffocation) && (!worldserver.getCubes(entityplayer1, entityplayer1.boundingBox).isEmpty())) {
      entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
    }
    

    byte actualDimension = (byte)worldserver.getWorld().getEnvironment().getId();
    
    entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn((byte)(actualDimension >= 0 ? -1 : 0), worldserver.difficulty, worldserver.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
    entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn(actualDimension, worldserver.difficulty, worldserver.getWorldData().getType(), entityplayer1.playerInteractManager.getGameMode()));
    entityplayer1.spawnIn(worldserver);
    entityplayer1.dead = false;
    entityplayer1.playerConnection.teleport(new Location(worldserver.getWorld(), entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch));
    entityplayer1.setSneaking(false);
    ChunkCoordinates chunkcoordinates1 = worldserver.getSpawn();
    

    entityplayer1.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(chunkcoordinates1.x, chunkcoordinates1.y, chunkcoordinates1.z));
    entityplayer1.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer1.exp, entityplayer1.expTotal, entityplayer1.expLevel));
    b(entityplayer1, worldserver);
    

    if (!entityplayer.playerConnection.isDisconnected()) {
      worldserver.getPlayerChunkMap().addPlayer(entityplayer1);
      worldserver.addEntity(entityplayer1);
      this.players.add(entityplayer1);
    }
    
    updateClient(entityplayer1);
    entityplayer1.updateAbilities();
    Iterator iterator = entityplayer1.getEffects().iterator();
    
    while (iterator.hasNext()) {
      MobEffect mobeffect = (MobEffect)iterator.next();
      
      entityplayer1.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer1.getId(), mobeffect));
    }
    

    entityplayer1.setHealth(entityplayer1.getHealth());
    


    if (fromWorld != location.getWorld()) {
      PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(entityplayer1.getBukkitEntity(), fromWorld);
      Bukkit.getServer().getPluginManager().callEvent(event);
    }
    

    if (entityplayer.playerConnection.isDisconnected()) {
      b(entityplayer1);
    }
    

    return entityplayer1;
  }
  
  public void changeDimension(EntityPlayer entityplayer, int i, PlayerTeleportEvent.TeleportCause cause)
  {
    WorldServer exitWorld = null;
    if (entityplayer.dimension < 10)
    {
      for (WorldServer world : this.server.worlds) {
        if (world.dimension == i) {
          exitWorld = world;
        }
      }
    }
    
    Location enter = entityplayer.getBukkitEntity().getLocation();
    Location exit = null;
    boolean useTravelAgent = false;
    if (exitWorld != null) {
      if ((cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) && (i == 0))
      {
        exit = entityplayer.getBukkitEntity().getBedSpawnLocation();
        if ((exit == null) || (((CraftWorld)exit.getWorld()).getHandle().dimension != 0)) {
          exit = exitWorld.getWorld().getSpawnLocation();
        }
      }
      else {
        exit = calculateTarget(enter, exitWorld);
        useTravelAgent = true;
      }
    }
    
    TravelAgent agent = exit != null ? (TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : org.bukkit.craftbukkit.v1_7_R4.CraftTravelAgent.DEFAULT;
    agent.setCanCreatePortal(cause != PlayerTeleportEvent.TeleportCause.END_PORTAL);
    
    PlayerPortalEvent event = new PlayerPortalEvent(entityplayer.getBukkitEntity(), enter, exit, agent, cause);
    event.useTravelAgent(useTravelAgent);
    Bukkit.getServer().getPluginManager().callEvent(event);
    if ((event.isCancelled()) || (event.getTo() == null)) {
      return;
    }
    

    exit = (cause != PlayerTeleportEvent.TeleportCause.END_PORTAL) && (event.useTravelAgent()) ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
    if (exit == null) {
      return;
    }
    exitWorld = ((CraftWorld)exit.getWorld()).getHandle();
    
    Vector velocity = entityplayer.getBukkitEntity().getVelocity();
    boolean before = exitWorld.chunkProviderServer.forceChunkLoad;
    exitWorld.chunkProviderServer.forceChunkLoad = true;
    exitWorld.getTravelAgent().adjustExit(entityplayer, exit, velocity);
    exitWorld.chunkProviderServer.forceChunkLoad = before;
    
    moveToWorld(entityplayer, exitWorld.dimension, true, exit, false);
    if ((entityplayer.motX != velocity.getX()) || (entityplayer.motY != velocity.getY()) || (entityplayer.motZ != velocity.getZ())) {
      entityplayer.getBukkitEntity().setVelocity(velocity);
    }
  }
  

  public void a(Entity entity, int i, WorldServer worldserver, WorldServer worldserver1)
  {
    Location exit = calculateTarget(entity.getBukkitEntity().getLocation(), worldserver1);
    repositionEntity(entity, exit, true);
  }
  
  public Location calculateTarget(Location enter, World target)
  {
    WorldServer worldserver = ((CraftWorld)enter.getWorld()).getHandle();
    WorldServer worldserver1 = target.getWorld().getHandle();
    int i = worldserver.dimension;
    
    double y = enter.getY();
    float yaw = enter.getYaw();
    float pitch = enter.getPitch();
    double d0 = enter.getX();
    double d1 = enter.getZ();
    double d2 = 8.0D;
    







    if (worldserver1.dimension == -1) {
      d0 /= d2;
      d1 /= d2;





    }
    else if (worldserver1.dimension == 0) {
      d0 *= d2;
      d1 *= d2;
    }
    else
    {
      ChunkCoordinates chunkcoordinates;
      

      ChunkCoordinates chunkcoordinates;
      

      if (i == 1)
      {
        worldserver1 = (WorldServer)this.server.worlds.get(0);
        chunkcoordinates = worldserver1.getSpawn();
      } else {
        chunkcoordinates = worldserver1.getDimensionSpawn();
      }
      
      d0 = chunkcoordinates.x;
      y = chunkcoordinates.y;
      d1 = chunkcoordinates.z;
      yaw = 90.0F;
      pitch = 0.0F;
    }
    







    if (i != 1)
    {
      d0 = MathHelper.a((int)d0, -29999872, 29999872);
      d1 = MathHelper.a((int)d1, -29999872, 29999872);
    }
    











    return new Location(worldserver1.getWorld(), d0, y, d1, yaw, pitch);
  }
  
  public void repositionEntity(Entity entity, Location exit, boolean portal)
  {
    int i = entity.dimension;
    WorldServer worldserver = (WorldServer)entity.world;
    WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
    









    worldserver.methodProfiler.a("moving");
    entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
    if (entity.isAlive()) {
      worldserver.entityJoinedWorld(entity, false);
    }
    

































    worldserver.methodProfiler.b();
    if (i != 1) {
      worldserver.methodProfiler.a("placing");
      



      if (entity.isAlive())
      {

        if (portal) {
          Vector velocity = entity.getBukkitEntity().getVelocity();
          worldserver1.getTravelAgent().adjustExit(entity, exit, velocity);
          entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
          if ((entity.motX != velocity.getX()) || (entity.motY != velocity.getY()) || (entity.motZ != velocity.getZ())) {
            entity.getBukkitEntity().setVelocity(velocity);
          }
        }
        worldserver1.addEntity(entity);
        worldserver1.entityJoinedWorld(entity, false);
      }
      
      worldserver.methodProfiler.b();
    }
    
    entity.spawnIn(worldserver1);
  }
  

  private int currentPing = 0;
  
  public void tick() {
    if (++this.t > 600) {
      this.t = 0;
    }
    








    try
    {
      if (!this.players.isEmpty())
      {
        this.currentPing = ((this.currentPing + 1) % this.players.size());
        EntityPlayer player = (EntityPlayer)this.players.get(this.currentPing);
        if ((player.lastPing == -1) || (Math.abs(player.ping - player.lastPing) > 20))
        {
          Packet packet = PacketPlayOutPlayerInfo.updatePing(player);
          for (EntityPlayer splayer : this.players)
          {
            if (splayer.getBukkitEntity().canSee(player.getBukkitEntity()))
            {
              splayer.playerConnection.sendPacket(packet);
            }
          }
          player.lastPing = player.ping;
        }
      }
    }
    catch (Exception localException) {}
  }
  

  public void sendAll(Packet packet)
  {
    for (int i = 0; i < this.players.size(); i++) {
      ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet);
    }
  }
  
  public void a(Packet packet, int i) {
    for (int j = 0; j < this.players.size(); j++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
      
      if (entityplayer.dimension == i) {
        entityplayer.playerConnection.sendPacket(packet);
      }
    }
  }
  
  public String b(boolean flag) {
    String s = "";
    ArrayList arraylist = Lists.newArrayList(this.players);
    
    for (int i = 0; i < arraylist.size(); i++) {
      if (i > 0) {
        s = s + ", ";
      }
      
      s = s + ((EntityPlayer)arraylist.get(i)).getName();
      if (flag) {
        s = s + " (" + ((EntityPlayer)arraylist.get(i)).getUniqueID().toString() + ")";
      }
    }
    
    return s;
  }
  
  public String[] f() {
    String[] astring = new String[this.players.size()];
    
    for (int i = 0; i < this.players.size(); i++) {
      astring[i] = ((EntityPlayer)this.players.get(i)).getName();
    }
    
    return astring;
  }
  
  public GameProfile[] g() {
    GameProfile[] agameprofile = new GameProfile[this.players.size()];
    
    for (int i = 0; i < this.players.size(); i++) {
      agameprofile[i] = ((EntityPlayer)this.players.get(i)).getProfile();
    }
    
    return agameprofile;
  }
  
  public GameProfileBanList getProfileBans() {
    return this.j;
  }
  
  public IpBanList getIPBans() {
    return this.k;
  }
  
  public void addOp(GameProfile gameprofile) {
    this.operators.add(new OpListEntry(gameprofile, this.server.l()));
    

    Player player = this.server.server.getPlayer(gameprofile.getId());
    if (player != null) {
      player.recalculatePermissions();
    }
  }
  
  public void removeOp(GameProfile gameprofile)
  {
    this.operators.remove(gameprofile);
    

    Player player = this.server.server.getPlayer(gameprofile.getId());
    if (player != null) {
      player.recalculatePermissions();
    }
  }
  
  public boolean isWhitelisted(GameProfile gameprofile)
  {
    return (!this.hasWhitelist) || (this.operators.d(gameprofile)) || (this.whitelist.d(gameprofile));
  }
  
  public boolean isOp(GameProfile gameprofile)
  {
    return (this.operators.d(gameprofile)) || ((this.server.N()) && (((WorldServer)this.server.worlds.get(0)).getWorldData().allowCommands()) && (this.server.M().equalsIgnoreCase(gameprofile.getName()))) || (this.s);
  }
  
  public EntityPlayer getPlayer(String s) {
    return (EntityPlayer)this.playerMap.get(s);
  }
  












  public List a(ChunkCoordinates chunkcoordinates, int i, int j, int k, int l, int i1, int j1, Map map, String s, String s1, World world)
  {
    if (this.players.isEmpty()) {
      return Collections.emptyList();
    }
    Object object = new ArrayList();
    boolean flag = k < 0;
    boolean flag1 = (s != null) && (s.startsWith("!"));
    boolean flag2 = (s1 != null) && (s1.startsWith("!"));
    int k1 = i * i;
    int l1 = j * j;
    
    k = MathHelper.a(k);
    if (flag1) {
      s = s.substring(1);
    }
    
    if (flag2) {
      s1 = s1.substring(1);
    }
    
    for (int i2 = 0; i2 < this.players.size(); i2++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(i2);
      
      if (((world == null) || (entityplayer.world == world)) && ((s == null) || (flag1 != s.equalsIgnoreCase(entityplayer.getName())))) {
        if (s1 != null) {
          ScoreboardTeamBase scoreboardteambase = entityplayer.getScoreboardTeam();
          String s2 = scoreboardteambase == null ? "" : scoreboardteambase.getName();
          
          if (flag2 == s1.equalsIgnoreCase(s2)) {}



        }
        else if ((chunkcoordinates != null) && ((i > 0) || (j > 0))) {
          float f = chunkcoordinates.e(entityplayer.getChunkCoordinates());
          
          if (((i > 0) && (f < k1)) || ((j > 0) && (f > l1))) {}



        }
        else if ((a(entityplayer, map)) && ((l == EnumGamemode.NONE.getId()) || (l == entityplayer.playerInteractManager.getGameMode().getId())) && ((i1 <= 0) || (entityplayer.expLevel >= i1)) && (entityplayer.expLevel <= j1)) {
          ((List)object).add(entityplayer);
        }
      }
    }
    
    if (chunkcoordinates != null) {
      Collections.sort((List)object, new PlayerDistanceComparator(chunkcoordinates));
    }
    
    if (flag) {
      Collections.reverse((List)object);
    }
    
    if (k > 0) {
      object = ((List)object).subList(0, Math.min(k, ((List)object).size()));
    }
    
    return (List)object;
  }
  
  private boolean a(EntityHuman entityhuman, Map map)
  {
    if ((map != null) && (map.size() != 0)) {
      Iterator iterator = map.entrySet().iterator();
      
      Entry entry;
      boolean flag;
      int i;
      do
      {
        if (!iterator.hasNext()) {
          return true;
        }
        
        entry = (Entry)iterator.next();
        String s = (String)entry.getKey();
        
        flag = false;
        if ((s.endsWith("_min")) && (s.length() > 4)) {
          flag = true;
          s = s.substring(0, s.length() - 4);
        }
        
        Scoreboard scoreboard = entityhuman.getScoreboard();
        ScoreboardObjective scoreboardobjective = scoreboard.getObjective(s);
        
        if (scoreboardobjective == null) {
          return false;
        }
        
        ScoreboardScore scoreboardscore = entityhuman.getScoreboard().getPlayerScoreForObjective(entityhuman.getName(), scoreboardobjective);
        
        i = scoreboardscore.getScore();
        if ((i < ((Integer)entry.getValue()).intValue()) && (flag)) {
          return false;
        }
      } while ((i <= ((Integer)entry.getValue()).intValue()) || (flag));
      
      return false;
    }
    return true;
  }
  
  public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet)
  {
    sendPacketNearby((EntityHuman)null, d0, d1, d2, d3, i, packet);
  }
  
  public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
    EntityPlayer entityhp = (EntityPlayer)entityhuman;
    
    for (int j = 0; j < this.players.size(); j++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
      
      if ((packet == null) || (!(packet instanceof PacketPlayOutNamedSoundEffect)) || 
        (entityhp != entityplayer))
      {

        if ((entityhp == null) || (entityhp.getShowEntities().contains(entityplayer.getUniqueID())))
        {

          if ((entityhuman == null) || (!(entityhuman instanceof EntityPlayer)) || (entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())))
          {



            if (entityplayer.dimension == i) {
              double d4 = d0 - entityplayer.locX;
              double d5 = d1 - entityplayer.locY;
              double d6 = d2 - entityplayer.locZ;
              
              if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3)
                entityplayer.playerConnection.sendPacket(packet);
            } } }
      }
    }
  }
  
  public void savePlayers() {
    for (int i = 0; i < this.players.size(); i++) {
      b((EntityPlayer)this.players.get(i));
    }
  }
  
  public void addWhitelist(GameProfile gameprofile) {
    this.whitelist.add(new WhiteListEntry(gameprofile));
  }
  
  public void removeWhitelist(GameProfile gameprofile) {
    this.whitelist.remove(gameprofile);
  }
  
  public WhiteList getWhitelist() {
    return this.whitelist;
  }
  
  public String[] getWhitelisted() {
    return this.whitelist.getEntries();
  }
  
  public OpList getOPs() {
    return this.operators;
  }
  
  public String[] n() {
    return this.operators.getEntries();
  }
  
  public void reloadWhitelist() {}
  
  public void b(EntityPlayer entityplayer, WorldServer worldserver) {
    entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
    if (worldserver.Q())
    {



      entityplayer.setPlayerWeather(org.bukkit.WeatherType.DOWNFALL, false);
    }
  }
  
  public void updateClient(EntityPlayer entityplayer)
  {
    entityplayer.updateInventory(entityplayer.defaultContainer);
    entityplayer.getBukkitEntity().updateScaledHealth();
    entityplayer.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
  }
  
  public int getPlayerCount() {
    return this.players.size();
  }
  
  public int getMaxPlayers() {
    return this.maxPlayers;
  }
  
  public String[] getSeenPlayers()
  {
    return ((WorldServer)this.server.worlds.get(0)).getDataManager().getPlayerFileData().getSeenPlayers();
  }
  
  public boolean getHasWhitelist() {
    return this.hasWhitelist;
  }
  
  public void setHasWhitelist(boolean flag) {
    this.hasWhitelist = flag;
  }
  
  public List b(String s) {
    ArrayList arraylist = new ArrayList();
    Iterator iterator = this.players.iterator();
    
    while (iterator.hasNext()) {
      EntityPlayer entityplayer = (EntityPlayer)iterator.next();
      
      if (entityplayer.s().equals(s)) {
        arraylist.add(entityplayer);
      }
    }
    
    return arraylist;
  }
  
  public int s() {
    return this.q;
  }
  
  public MinecraftServer getServer() {
    return this.server;
  }
  
  public NBTTagCompound t() {
    return null;
  }
  
  private void a(EntityPlayer entityplayer, EntityPlayer entityplayer1, World world) {
    if (entityplayer1 != null) {
      entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
    } else if (this.r != null) {
      entityplayer.playerInteractManager.setGameMode(this.r);
    }
    
    entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
  }
  
  public void u() {
    while (!this.players.isEmpty())
    {
      EntityPlayer p = (EntityPlayer)this.players.get(0);
      p.playerConnection.disconnect(this.server.server.getShutdownMessage());
      if ((!this.players.isEmpty()) && (this.players.get(0) == p))
      {
        this.players.remove(0);
      }
    }
  }
  

  public void sendMessage(IChatBaseComponent[] ichatbasecomponent)
  {
    for (IChatBaseComponent component : ichatbasecomponent) {
      sendMessage(component, true);
    }
  }
  
  public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag)
  {
    this.server.sendMessage(ichatbasecomponent);
    sendAll(new PacketPlayOutChat(ichatbasecomponent, flag));
  }
  
  public void sendMessage(IChatBaseComponent ichatbasecomponent) {
    sendMessage(ichatbasecomponent, true);
  }
  
  public ServerStatisticManager a(EntityHuman entityhuman) {
    UUID uuid = entityhuman.getUniqueID();
    ServerStatisticManager serverstatisticmanager = uuid == null ? null : (ServerStatisticManager)this.n.get(uuid);
    
    if (serverstatisticmanager == null) {
      File file1 = new File(this.server.getWorldServer(0).getDataManager().getDirectory(), "stats");
      File file2 = new File(file1, uuid.toString() + ".json");
      
      if (!file2.exists()) {
        File file3 = new File(file1, entityhuman.getName() + ".json");
        
        if ((file3.exists()) && (file3.isFile())) {
          file3.renameTo(file2);
        }
      }
      
      serverstatisticmanager = new ServerStatisticManager(this.server, file2);
      serverstatisticmanager.a();
      this.n.put(uuid, serverstatisticmanager);
    }
    
    return serverstatisticmanager;
  }
  
  public void a(int i) {
    this.q = i;
    if (this.server.worldServer != null) {
      WorldServer[] aworldserver = this.server.worldServer;
      int j = aworldserver.length;
      
      for (int k = 0; k < j; k++) {
        WorldServer worldserver = aworldserver[k];
        
        if (worldserver != null) {
          worldserver.getPlayerChunkMap().a(i);
        }
      }
    }
  }
}
