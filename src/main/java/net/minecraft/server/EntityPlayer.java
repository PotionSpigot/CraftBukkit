package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryView;
import org.github.paperspigot.PaperSpigotWorldConfig;
import org.spigotmc.SpigotWorldConfig;

public class EntityPlayer extends EntityHuman implements ICrafting
{
  private static final Logger bL = ;
  public String locale = "en_US";
  public PlayerConnection playerConnection;
  public final MinecraftServer server;
  public final PlayerInteractManager playerInteractManager;
  public double d;
  public double e;
  public final List chunkCoordIntPairQueue = new LinkedList();
  public final List removeQueue = new LinkedList();
  private final ServerStatisticManager bO;
  private float bP = Float.MIN_VALUE;
  private float bQ = -1.0E8F;
  private int bR = -99999999;
  private boolean bS = true;
  public int lastSentExp = -99999999;
  public int invulnerableTicks = 60;
  private EnumChatVisibility bV;
  private boolean bW = true;
  private long bX = System.currentTimeMillis();
  
  private int containerCounter;
  public boolean g;
  public int ping;
  public boolean viewingCredits;
  public List<UUID> showEntities;
  public String displayName;
  public String listName;
  public org.bukkit.Location compassTarget;
  public int newExp = 0;
  public int newLevel = 0;
  public int newTotalExp = 0;
  public boolean keepLevel = false;
  public double maxHealthCache;
  public boolean joining = true;
  public int lastPing = -1;
  

  public boolean collidesWithEntities = true;
  
  public int viewDistance;
  private int containerUpdateDelay;
  
  public boolean R()
  {
    return (this.collidesWithEntities) && (super.R());
  }
  

  public boolean S()
  {
    return (this.collidesWithEntities) && (super.S());
  }
  
  public EntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager)
  {
    super(worldserver, gameprofile);
    this.showEntities = new ArrayList();
    
    this.viewDistance = this.world.spigotConfig.viewDistance;
    playerinteractmanager.player = this;
    this.playerInteractManager = playerinteractmanager;
    ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
    int i = chunkcoordinates.x;
    int j = chunkcoordinates.z;
    int k = chunkcoordinates.y;
    
    if ((!worldserver.worldProvider.g) && (worldserver.getWorldData().getGameType() != EnumGamemode.ADVENTURE)) {
      int l = Math.max(5, minecraftserver.getSpawnProtection() - 6);
      
      i += this.random.nextInt(l * 2) - l;
      j += this.random.nextInt(l * 2) - l;
      k = worldserver.i(i, j);
    }
    
    this.server = minecraftserver;
    this.bO = minecraftserver.getPlayerList().a(this);
    this.W = 0.0F;
    this.height = 0.0F;
    setPositionRotation(i + 0.5D, k, j + 0.5D, 0.0F, 0.0F);
    
    while (!worldserver.getCubes(this, this.boundingBox).isEmpty()) {
      setPosition(this.locX, this.locY + 1.0D, this.locZ);
    }
    

    this.displayName = getName();
    this.listName = getName();
    
    this.maxHealthCache = getMaxHealth();
  }
  
  public List<UUID> getShowEntities()
  {
    return this.showEntities;
  }
  
  public void setShowEntities(List<UUID> list) {
    this.showEntities = list;
  }
  
  public void addShowEntities(UUID uuid) {
    this.showEntities.add(uuid);
  }
  
  public void removeShowEntities(UUID uuid) {
    this.showEntities.remove(uuid);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    if (nbttagcompound.hasKeyOfType("playerGameType", 99)) {
      if (MinecraftServer.getServer().getForceGamemode()) {
        this.playerInteractManager.setGameMode(MinecraftServer.getServer().getGamemode());
      } else {
        this.playerInteractManager.setGameMode(EnumGamemode.getById(nbttagcompound.getInt("playerGameType")));
      }
    }
    getBukkitEntity().readExtraData(nbttagcompound);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setInt("playerGameType", this.playerInteractManager.getGameMode().getId());
    getBukkitEntity().setExtraData(nbttagcompound);
  }
  
  public void spawnIn(World world)
  {
    super.spawnIn(world);
    if (world == null) {
      this.dead = false;
      ChunkCoordinates position = null;
      if ((this.spawnWorld != null) && (!this.spawnWorld.equals(""))) {
        CraftWorld cworld = (CraftWorld)Bukkit.getServer().getWorld(this.spawnWorld);
        if ((cworld != null) && (getBed() != null)) {
          world = cworld.getHandle();
          position = EntityHuman.getBed(cworld.getHandle(), getBed(), false);
        }
      }
      if ((world == null) || (position == null)) {
        world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
        position = world.getSpawn();
      }
      this.world = world;
      setPosition(position.x + 0.5D, position.y, position.z + 0.5D);
    }
    this.dimension = ((WorldServer)this.world).dimension;
    this.playerInteractManager.a((WorldServer)world);
  }
  
  public void levelDown(int i)
  {
    super.levelDown(i);
    this.lastSentExp = -1;
  }
  
  public void syncInventory() {
    this.activeContainer.addSlotListener(this);
  }
  
  protected void e_() {
    this.height = 0.0F;
  }
  
  public float getHeadHeight() {
    return 1.62F;
  }
  
  public void h()
  {
    if (this.joining) {
      this.joining = false;
    }
    

    this.playerInteractManager.a();
    this.invulnerableTicks -= 1;
    if (this.noDamageTicks > 0) {
      this.noDamageTicks -= 1;
    }
    

    if (--this.containerUpdateDelay <= 0) {
      this.activeContainer.b();
      this.containerUpdateDelay = this.world.paperSpigotConfig.containerUpdateTickRate;
    }
    
    if ((!this.world.isStatic) && (!this.activeContainer.a(this))) {
      closeInventory();
      this.activeContainer = this.defaultContainer;
    }
    
    while (!this.removeQueue.isEmpty()) {
      int i = Math.min(this.removeQueue.size(), 127);
      int[] aint = new int[i];
      Iterator iterator = this.removeQueue.iterator();
      int j = 0;
      
      while ((iterator.hasNext()) && (j < i)) {
        aint[(j++)] = ((Integer)iterator.next()).intValue();
        iterator.remove();
      }
      
      this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(aint));
    }
    
    if (!this.chunkCoordIntPairQueue.isEmpty()) {
      ArrayList arraylist = new ArrayList();
      Iterator iterator1 = this.chunkCoordIntPairQueue.iterator();
      ArrayList arraylist1 = new ArrayList();
      


      while ((iterator1.hasNext()) && (arraylist.size() < this.world.spigotConfig.maxBulkChunk)) {
        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator1.next();
        
        if (chunkcoordintpair != null) {
          if (this.world.isLoaded(chunkcoordintpair.x << 4, 0, chunkcoordintpair.z << 4)) {
            Chunk chunk = this.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
            if (chunk.isReady()) {
              arraylist.add(chunk);
              arraylist1.addAll(chunk.tileEntities.values());
              iterator1.remove();
            }
          }
        } else {
          iterator1.remove();
        }
      }
      
      if (!arraylist.isEmpty()) {
        this.playerConnection.sendPacket(new PacketPlayOutMapChunkBulk(arraylist, this.playerConnection.networkManager.getVersion()));
        Iterator iterator2 = arraylist1.iterator();
        
        while (iterator2.hasNext()) {
          TileEntity tileentity = (TileEntity)iterator2.next();
          
          b(tileentity);
        }
        
        iterator2 = arraylist.iterator();
        
        while (iterator2.hasNext()) {
          Chunk chunk = (Chunk)iterator2.next();
          r().getTracker().a(this, chunk);
        }
      }
    }
  }
  
  public void i() {
    try {
      super.h();
      
      for (int i = 0; i < this.inventory.getSize(); i++) {
        ItemStack itemstack = this.inventory.getItem(i);
        
        if ((itemstack != null) && (itemstack.getItem().h())) {
          Packet packet = ((ItemWorldMapBase)itemstack.getItem()).c(itemstack, this.world, this);
          
          if (packet != null) {
            this.playerConnection.sendPacket(packet);
          }
        }
      }
      

      if ((getHealth() == this.bQ) && (this.bR == this.foodData.getFoodLevel())) { if ((this.foodData.getSaturationLevel() == 0.0F) == this.bS) {}
      } else { this.playerConnection.sendPacket(new PacketPlayOutUpdateHealth(getBukkitEntity().getScaledHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
        this.bQ = getHealth();
        this.bR = this.foodData.getFoodLevel();
        this.bS = (this.foodData.getSaturationLevel() == 0.0F);
      }
      
      if (getHealth() + getAbsorptionHearts() != this.bP) {
        this.bP = (getHealth() + getAbsorptionHearts());
        
        this.world.getServer().getScoreboardManager().updateAllScoresForList(IScoreboardCriteria.f, getName(), com.google.common.collect.ImmutableList.of(this));
      }
      

      if (this.maxHealthCache != getMaxHealth()) {
        getBukkitEntity().updateScaledHealth();
      }
      

      if (this.expTotal != this.lastSentExp) {
        this.lastSentExp = this.expTotal;
        this.playerConnection.sendPacket(new PacketPlayOutExperience(this.exp, this.expTotal, this.expLevel));
      }
      
      if ((this.ticksLived % 20 * 5 == 0) && (!getStatisticManager().hasAchievement(AchievementList.L))) {
        j();
      }
      

      if (this.oldLevel == -1) {
        this.oldLevel = this.expLevel;
      }
      
      if (this.oldLevel != this.expLevel) {
        CraftEventFactory.callPlayerLevelChangeEvent(this.world.getServer().getPlayer(this), this.oldLevel, this.expLevel);
        this.oldLevel = this.expLevel;
      }
    }
    catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");
      
      a(crashreportsystemdetails);
      throw new ReportedException(crashreport);
    }
  }
  
  protected void j() {
    BiomeBase biomebase = this.world.getBiome(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
    
    if (biomebase != null) {
      String s = biomebase.af;
      AchievementSet achievementset = (AchievementSet)getStatisticManager().b(AchievementList.L);
      
      if (achievementset == null) {
        achievementset = (AchievementSet)getStatisticManager().a(AchievementList.L, new AchievementSet());
      }
      
      achievementset.add(s);
      if ((getStatisticManager().b(AchievementList.L)) && (achievementset.size() == BiomeBase.n.size())) {
        HashSet hashset = net.minecraft.util.com.google.common.collect.Sets.newHashSet(BiomeBase.n);
        Iterator iterator = achievementset.iterator();
        
        while (iterator.hasNext()) {
          String s1 = (String)iterator.next();
          Iterator iterator1 = hashset.iterator();
          
          while (iterator1.hasNext()) {
            BiomeBase biomebase1 = (BiomeBase)iterator1.next();
            
            if (biomebase1.af.equals(s1)) {
              iterator1.remove();
            }
          }
          
          if (hashset.isEmpty()) {
            break;
          }
        }
        
        if (hashset.isEmpty()) {
          a(AchievementList.L);
        }
      }
    }
  }
  
  public void die(DamageSource damagesource)
  {
    if (this.dead) {
      return;
    }
    
    List<org.bukkit.inventory.ItemStack> loot = new ArrayList();
    boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
    
    if (!keepInventory) {
      for (int i = 0; i < this.inventory.items.length; i++) {
        if (this.inventory.items[i] != null) {
          loot.add(CraftItemStack.asCraftMirror(this.inventory.items[i]));
        }
      }
      
      for (int i = 0; i < this.inventory.armor.length; i++) {
        if (this.inventory.armor[i] != null) {
          loot.add(CraftItemStack.asCraftMirror(this.inventory.armor[i]));
        }
      }
    }
    
    IChatBaseComponent chatmessage = aW().b();
    
    String deathmessage = chatmessage.c();
    PlayerDeathEvent event = CraftEventFactory.callPlayerDeathEvent(this, loot, deathmessage, keepInventory);
    
    String deathMessage = event.getDeathMessage();
    
    if ((deathMessage != null) && (deathMessage.length() > 0)) {
      if (deathMessage.equals(deathmessage)) {
        this.server.getPlayerList().sendMessage(chatmessage);
      } else {
        this.server.getPlayerList().sendMessage(org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage.fromString(deathMessage));
      }
    }
    

    if (!event.getKeepInventory()) {
      for (int i = 0; i < this.inventory.items.length; i++) {
        this.inventory.setItem(i, null);
      }
      
      for (int i = 0; i < this.inventory.armor.length; i++) {
        this.inventory.player.setEquipment(i, null);
      }
    }
    
    closeInventory();
    


    Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.c, getName(), new ArrayList());
    Iterator iterator = collection.iterator();
    
    while (iterator.hasNext()) {
      ScoreboardScore scoreboardscore = (ScoreboardScore)iterator.next();
      
      scoreboardscore.incrementScore();
    }
    
    EntityLiving entityliving = aX();
    
    if (entityliving != null) {
      int i = EntityTypes.a(entityliving);
      MonsterEggInfo monsteregginfo = (MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(i));
      
      if (monsteregginfo != null) {
        a(monsteregginfo.e, 1);
      }
      
      entityliving.b(this, this.ba);
    }
    
    a(StatisticList.v, 1);
    aW().g();
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    
    boolean flag = (this.server.X()) && (this.world.pvpMode) && ("fall".equals(damagesource.translationIndex));
    
    if ((!flag) && (this.invulnerableTicks > 0) && (damagesource != DamageSource.OUT_OF_WORLD)) {
      return false;
    }
    if ((damagesource instanceof EntityDamageSource)) {
      Entity entity = damagesource.getEntity();
      
      if (((entity instanceof EntityHuman)) && (!a((EntityHuman)entity))) {
        return false;
      }
      
      if ((entity instanceof EntityArrow)) {
        EntityArrow entityarrow = (EntityArrow)entity;
        
        if (((entityarrow.shooter instanceof EntityHuman)) && (!a((EntityHuman)entityarrow.shooter))) {
          return false;
        }
      }
    }
    
    return super.damageEntity(damagesource, f);
  }
  


  public boolean a(EntityHuman entityhuman)
  {
    return !this.world.pvpMode ? false : super.a(entityhuman);
  }
  
  public void b(int i)
  {
    boolean endPortal = (this.dimension == 1) && (i == 1);
    if (endPortal) {
      a(AchievementList.D);
      if (!this.world.paperSpigotConfig.disableEndCredits) {
        this.world.kill(this);
        this.viewingCredits = true;
        this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 0.0F));
      }
      
    }
    else if ((this.dimension == 0) && (i == 1)) {
      a(AchievementList.C);





    }
    else
    {




      a(AchievementList.y);
    }
    


    if ((!endPortal) || (this.world.paperSpigotConfig.disableEndCredits))
    {
      PlayerTeleportEvent.TeleportCause cause = (endPortal) || (this.dimension == 1) || (i == 1) ? PlayerTeleportEvent.TeleportCause.END_PORTAL : PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
      this.server.getPlayerList().changeDimension(this, i, cause);
      
      this.lastSentExp = -1;
      this.bQ = -1.0F;
      this.bR = -1;
    }
  }
  
  private void b(TileEntity tileentity)
  {
    if (tileentity != null) {
      Packet packet = tileentity.getUpdatePacket();
      
      if (packet != null) {
        this.playerConnection.sendPacket(packet);
      }
    }
  }
  
  public void receive(Entity entity, int i) {
    super.receive(entity, i);
    this.activeContainer.b();
  }
  
  public EnumBedResult a(int i, int j, int k) {
    EnumBedResult enumbedresult = super.a(i, j, k);
    
    if (enumbedresult == EnumBedResult.OK) {
      PacketPlayOutBed packetplayoutbed = new PacketPlayOutBed(this, i, j, k);
      
      r().getTracker().a(this, packetplayoutbed);
      this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      this.playerConnection.sendPacket(packetplayoutbed);
    }
    
    return enumbedresult;
  }
  
  public void a(boolean flag, boolean flag1, boolean flag2) {
    if (!this.sleeping) { return;
    }
    if (isSleeping()) {
      r().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 2));
    }
    
    super.a(flag, flag1, flag2);
    if (this.playerConnection != null) {
      this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }
  }
  
  public void mount(Entity entity)
  {
    setPassengerOf(entity);
  }
  

  public void setPassengerOf(Entity entity)
  {
    Entity currentVehicle = this.vehicle;
    
    super.setPassengerOf(entity);
    

    if (currentVehicle != this.vehicle) {
      this.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this, this.vehicle));
      this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }
  }
  
  protected void a(double d0, boolean flag) {}
  
  public void b(double d0, boolean flag)
  {
    super.a(d0, flag);
  }
  
  public void a(TileEntity tileentity) {
    if ((tileentity instanceof TileEntitySign)) {
      ((TileEntitySign)tileentity).a(this);
      this.playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(tileentity.x, tileentity.y, tileentity.z));
    }
  }
  
  public int nextContainerCounter() {
    this.containerCounter = (this.containerCounter % 100 + 1);
    return this.containerCounter;
  }
  
  public void startCrafting(int i, int j, int k)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerWorkbench(this.inventory, this.world, i, j, k));
    if (container == null) {
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 1, "Crafting", 0, true));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void startEnchanting(int i, int j, int k, String s)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerEnchantTable(this.inventory, this.world, i, j, k));
    if (container == null) {
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 4, s == null ? "" : s, 0, s != null));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openAnvil(int i, int j, int k)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerAnvil(this.inventory, this.world, i, j, k, this));
    if (container == null) {
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 8, "Repairing", 0, true));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openContainer(IInventory iinventory) {
    if (this.activeContainer != this.defaultContainer) {
      closeInventory();
    }
    

    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerChest(this.inventory, iinventory));
    if (container == null) {
      iinventory.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 0, iinventory.getInventoryName(), iinventory.getSize(), iinventory.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openHopper(TileEntityHopper tileentityhopper)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHopper(this.inventory, tileentityhopper));
    if (container == null) {
      tileentityhopper.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 9, tileentityhopper.getInventoryName(), tileentityhopper.getSize(), tileentityhopper.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openMinecartHopper(EntityMinecartHopper entityminecarthopper)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHopper(this.inventory, entityminecarthopper));
    if (container == null) {
      entityminecarthopper.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 9, entityminecarthopper.getInventoryName(), entityminecarthopper.getSize(), entityminecarthopper.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openFurnace(TileEntityFurnace tileentityfurnace)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerFurnace(this.inventory, tileentityfurnace));
    if (container == null) {
      tileentityfurnace.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 2, tileentityfurnace.getInventoryName(), tileentityfurnace.getSize(), tileentityfurnace.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openDispenser(TileEntityDispenser tileentitydispenser)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerDispenser(this.inventory, tileentitydispenser));
    if (container == null) {
      tileentitydispenser.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, (tileentitydispenser instanceof TileEntityDropper) ? 10 : 3, tileentitydispenser.getInventoryName(), tileentitydispenser.getSize(), tileentitydispenser.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openBrewingStand(TileEntityBrewingStand tileentitybrewingstand)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerBrewingStand(this.inventory, tileentitybrewingstand));
    if (container == null) {
      tileentitybrewingstand.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 5, tileentitybrewingstand.getInventoryName(), tileentitybrewingstand.getSize(), tileentitybrewingstand.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openBeacon(TileEntityBeacon tileentitybeacon)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerBeacon(this.inventory, tileentitybeacon));
    if (container == null) {
      tileentitybeacon.closeContainer();
      return;
    }
    

    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 7, tileentitybeacon.getInventoryName(), tileentitybeacon.getSize(), tileentitybeacon.k_()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void openTrade(IMerchant imerchant, String s)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerMerchant(this.inventory, imerchant, this.world));
    if (container == null) {
      return;
    }
    

    nextContainerCounter();
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
    InventoryMerchant inventorymerchant = ((ContainerMerchant)this.activeContainer).getMerchantInventory();
    
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 6, s == null ? "" : s, inventorymerchant.getSize(), s != null));
    MerchantRecipeList merchantrecipelist = imerchant.getOffers(this);
    
    if (merchantrecipelist != null) {
      PacketDataSerializer packetdataserializer = new PacketDataSerializer(net.minecraft.util.io.netty.buffer.Unpooled.buffer(), this.playerConnection.networkManager.getVersion());
      try
      {
        packetdataserializer.writeInt(this.containerCounter);
        merchantrecipelist.a(packetdataserializer);
        this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", packetdataserializer));
      } catch (Exception ioexception) {
        bL.error("Couldn't send trade list", ioexception);
      } finally {
        packetdataserializer.release();
      }
    }
  }
  
  public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory)
  {
    Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHorse(this.inventory, iinventory, entityhorse));
    if (container == null) {
      iinventory.closeContainer();
      return;
    }
    

    if (this.activeContainer != this.defaultContainer) {
      closeInventory();
    }
    
    nextContainerCounter();
    this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, 11, iinventory.getInventoryName(), iinventory.getSize(), iinventory.k_(), entityhorse.getId()));
    this.activeContainer = container;
    this.activeContainer.windowId = this.containerCounter;
    this.activeContainer.addSlotListener(this);
  }
  
  public void a(Container container, int i, ItemStack itemstack) {
    if ((!(container.getSlot(i) instanceof SlotResult)) && 
      (!this.g)) {
      this.playerConnection.sendPacket(new PacketPlayOutSetSlot(container.windowId, i, itemstack));
    }
  }
  
  public void updateInventory(Container container)
  {
    a(container, container.a());
  }
  
  public void a(Container container, List list) {
    this.playerConnection.sendPacket(new PacketPlayOutWindowItems(container.windowId, list));
    this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
    
    if (EnumSet.of(InventoryType.CRAFTING, InventoryType.WORKBENCH).contains(container.getBukkitView().getType())) {
      this.playerConnection.sendPacket(new PacketPlayOutSetSlot(container.windowId, 0, container.getSlot(0).getItem()));
    }
  }
  

  public void setContainerData(Container container, int i, int j)
  {
    if (((container instanceof ContainerFurnace)) && (this.playerConnection.networkManager.getVersion() >= 47))
    {
      switch (i) {
      case 0: 
        i = 2;
        this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, 3, 200));
        break;
      case 1: 
        i = 0;
        break;
      case 2: 
        i = 1;
      }
      
    }
    this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, i, j));
  }
  
  public void closeInventory() {
    CraftEventFactory.handleInventoryCloseEvent(this);
    this.playerConnection.sendPacket(new PacketPlayOutCloseWindow(this.activeContainer.windowId));
    m();
  }
  
  public void broadcastCarriedItem() {
    if (!this.g) {
      this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
    }
  }
  
  public void m() {
    this.activeContainer.b(this);
    this.activeContainer = this.defaultContainer;
  }
  
  public void a(float f, float f1, boolean flag, boolean flag1) {
    if (this.vehicle != null) {
      if ((f >= -1.0F) && (f <= 1.0F)) {
        this.bd = f;
      }
      
      if ((f1 >= -1.0F) && (f1 <= 1.0F)) {
        this.be = f1;
      }
      
      this.bc = flag;
      setSneaking(flag1);
    }
  }
  
  public void a(Statistic statistic, int i) {
    if (statistic != null) {
      this.bO.b(this, statistic, i);
      Iterator iterator = getScoreboard().getObjectivesForCriteria(statistic.k()).iterator();
      
      while (iterator.hasNext()) {
        ScoreboardObjective scoreboardobjective = (ScoreboardObjective)iterator.next();
        
        getScoreboard().getPlayerScoreForObjective(getName(), scoreboardobjective).incrementScore();
      }
      
      if (this.bO.e()) {
        this.bO.a(this);
      }
    }
  }
  
  public void n() {
    if (this.passenger != null) {
      this.passenger.mount(this);
    }
    
    if (this.sleeping) {
      a(true, false, false);
    }
  }
  
  public void triggerHealthUpdate() {
    this.bQ = -1.0E8F;
    this.lastSentExp = -1;
  }
  
  public void b(IChatBaseComponent ichatbasecomponent) {
    this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
  }
  
  protected void p() {
    this.playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte)9));
    super.p();
  }
  
  public void a(ItemStack itemstack, int i) {
    super.a(itemstack, i);
    if ((itemstack != null) && (itemstack.getItem() != null) && (itemstack.getItem().d(itemstack) == EnumAnimation.EAT)) {
      r().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 3));
    }
  }
  
  public void copyTo(EntityHuman entityhuman, boolean flag) {
    super.copyTo(entityhuman, flag);
    this.lastSentExp = -1;
    this.bQ = -1.0F;
    this.bR = -1;
    this.removeQueue.addAll(((EntityPlayer)entityhuman).removeQueue);
  }
  
  protected void a(MobEffect mobeffect) {
    super.a(mobeffect);
    this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(getId(), mobeffect));
  }
  
  protected void a(MobEffect mobeffect, boolean flag) {
    super.a(mobeffect, flag);
    this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(getId(), mobeffect));
  }
  
  protected void b(MobEffect mobeffect) {
    super.b(mobeffect);
    this.playerConnection.sendPacket(new PacketPlayOutRemoveEntityEffect(getId(), mobeffect));
  }
  
  public void enderTeleportTo(double d0, double d1, double d2) {
    this.playerConnection.a(d0, d1, d2, this.yaw, this.pitch);
  }
  
  public void b(Entity entity) {
    r().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 4));
  }
  
  public void c(Entity entity) {
    r().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 5));
  }
  
  public void updateAbilities() {
    if (this.playerConnection != null) {
      this.playerConnection.sendPacket(new PacketPlayOutAbilities(this.abilities));
    }
  }
  
  public WorldServer r() {
    return (WorldServer)this.world;
  }
  
  public void a(EnumGamemode enumgamemode) {
    this.playerInteractManager.setGameMode(enumgamemode);
    this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(3, enumgamemode.getId()));
  }
  
  public void sendMessage(IChatBaseComponent[] ichatbasecomponent)
  {
    for (IChatBaseComponent component : ichatbasecomponent) {
      sendMessage(component);
    }
  }
  
  public void sendMessage(IChatBaseComponent ichatbasecomponent)
  {
    this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
  }
  
  public boolean a(int i, String s) {
    if (("seed".equals(s)) && (!this.server.X()))
      return true;
    if ((!"tell".equals(s)) && (!"help".equals(s)) && (!"me".equals(s))) {
      if (this.server.getPlayerList().isOp(getProfile())) {
        OpListEntry oplistentry = (OpListEntry)this.server.getPlayerList().getOPs().get(getProfile());
        
        return oplistentry.a() >= i;
      }
      return false;
    }
    
    return true;
  }
  
  public String s()
  {
    String s = this.playerConnection.networkManager.getSocketAddress().toString();
    
    s = s.substring(s.indexOf("/") + 1);
    s = s.substring(0, s.indexOf(":"));
    return s;
  }
  
  public void a(PacketPlayInSettings packetplayinsettings) {
    this.locale = packetplayinsettings.c();
    int i = 256 >> packetplayinsettings.d();
    
    if ((i > 3) && (i < 20)) {}
    


    this.bV = packetplayinsettings.e();
    this.bW = packetplayinsettings.f();
    if ((this.server.N()) && (this.server.M().equals(getName()))) {
      this.server.a(packetplayinsettings.g());
    }
    

    if (packetplayinsettings.version < 16)
    {
      b(1, !packetplayinsettings.h(), packetplayinsettings.version);
    }
    else {
      b(1, false, packetplayinsettings.version);
      this.datawatcher.watch(10, new org.spigotmc.ProtocolData.HiddenByte((byte)packetplayinsettings.flags));
    }
  }
  
  public EnumChatVisibility getChatFlags()
  {
    return this.bV;
  }
  
  public void setResourcePack(String s) {
    this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|RPack", s.getBytes(net.minecraft.util.org.apache.commons.io.Charsets.UTF_8)));
    
    if (this.playerConnection.networkManager.getVersion() >= 36)
    {
      this.playerConnection.sendPacket(new org.spigotmc.ProtocolInjector.PacketPlayResourcePackSend(s, "thinkislazy"));
    }
  }
  
  public ChunkCoordinates getChunkCoordinates()
  {
    return new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY + 0.5D), MathHelper.floor(this.locZ));
  }
  
  public void v() {
    this.bX = MinecraftServer.ar();
  }
  
  public ServerStatisticManager getStatisticManager() {
    return this.bO;
  }
  
  public void d(Entity entity) {
    if ((entity instanceof EntityHuman)) {
      this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { entity.getId() }));
    } else {
      this.removeQueue.add(Integer.valueOf(entity.getId()));
    }
  }
  
  public long x() {
    return this.bX;
  }
  

  public long timeOffset = 0L;
  public boolean relativeTime = true;
  
  public long getPlayerTime() {
    if (this.relativeTime)
    {
      return this.world.getDayTime() + this.timeOffset;
    }
    
    return this.world.getDayTime() - this.world.getDayTime() % 24000L + this.timeOffset;
  }
  

  public WeatherType weather = null;
  
  public WeatherType getPlayerWeather() {
    return this.weather;
  }
  
  public void setPlayerWeather(WeatherType type, boolean plugin) {
    if ((!plugin) && (this.weather != null)) {
      return;
    }
    
    if (plugin) {
      this.weather = type;
    }
    
    if (type == WeatherType.DOWNFALL) {
      this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0.0F));
    }
    else
    {
      this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0.0F));
    }
  }
  
  public void resetPlayerWeather() {
    this.weather = null;
    setPlayerWeather(this.world.getWorldData().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
  }
  
  public String toString()
  {
    return super.toString() + "(" + getName() + " at " + this.locX + "," + this.locY + "," + this.locZ + ")";
  }
  
  public void reset() {
    float exp = 0.0F;
    boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
    
    if ((this.keepLevel) || (keepInventory)) {
      exp = this.exp;
      this.newTotalExp = this.expTotal;
      this.newLevel = this.expLevel;
    }
    
    setHealth(getMaxHealth());
    this.fireTicks = 0;
    this.fallDistance = 0.0F;
    this.foodData = new FoodMetaData(this);
    this.expLevel = this.newLevel;
    this.expTotal = this.newTotalExp;
    this.exp = 0.0F;
    this.deathTicks = 0;
    removeAllEffects();
    this.updateEffects = true;
    this.activeContainer = this.defaultContainer;
    this.killer = null;
    this.lastDamager = null;
    this.combatTracker = new CombatTracker(this);
    this.lastSentExp = -1;
    if ((this.keepLevel) || (keepInventory)) {
      this.exp = exp;
    } else {
      giveExp(this.newExp);
    }
    this.keepLevel = false;
  }
  
  public CraftPlayer getBukkitEntity()
  {
    return (CraftPlayer)super.getBukkitEntity();
  }
}
