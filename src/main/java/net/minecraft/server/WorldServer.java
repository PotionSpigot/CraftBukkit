package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.util.gnu.trove.iterator.TLongShortIterator;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.WorldTimingsHandler;
import org.bukkit.craftbukkit.v1_7_R4.generator.InternalChunkGenerator;
import org.bukkit.craftbukkit.v1_7_R4.util.HashTreeSet;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.github.paperspigot.PaperSpigotWorldConfig;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.SpigotWorldConfig;

public class WorldServer extends World
{
  private static final org.apache.logging.log4j.Logger a = ;
  private final MinecraftServer server;
  public EntityTracker tracker;
  private final PlayerChunkMap manager;
  private HashTreeSet<NextTickListEntry> N;
  public ChunkProviderServer chunkProviderServer;
  public boolean savingDisabled;
  private boolean O;
  private int emptyTime;
  private final PortalTravelAgent Q;
  private final SpawnerCreature R = new SpawnerCreature();
  private BlockActionDataList[] S = { new BlockActionDataList((BananaAPI)null), new BlockActionDataList((BananaAPI)null) };
  private int T;
  private static final StructurePieceTreasure[] U = { new StructurePieceTreasure(Items.STICK, 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.WOOD), 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG), 0, 1, 3, 10), new StructurePieceTreasure(Items.STONE_AXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOOD_AXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.STONE_PICKAXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOOD_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.APPLE, 0, 2, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 2, 3, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG2), 0, 1, 3, 10) };
  private List V = new ArrayList();
  
  private IntHashMap entitiesById;
  
  public final int dimension;
  
  public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, MethodProfiler methodprofiler, World.Environment env, ChunkGenerator gen)
  {
    super(idatamanager, s, worldsettings, WorldProvider.byDimension(env.getId()), methodprofiler, gen, env);
    this.dimension = i;
    this.pvpMode = minecraftserver.getPvP();
    
    this.server = minecraftserver;
    this.tracker = new EntityTracker(this);
    this.manager = new PlayerChunkMap(this, this.spigotConfig.viewDistance);
    if (this.entitiesById == null) {
      this.entitiesById = new IntHashMap();
    }
    
    if (this.N == null) {
      this.N = new HashTreeSet();
    }
    
    this.Q = new org.bukkit.craftbukkit.v1_7_R4.CraftTravelAgent(this);
    this.scoreboard = new ScoreboardServer(minecraftserver);
    PersistentScoreboard persistentscoreboard = (PersistentScoreboard)this.worldMaps.get(PersistentScoreboard.class, "scoreboard");
    
    if (persistentscoreboard == null) {
      persistentscoreboard = new PersistentScoreboard();
      this.worldMaps.a("scoreboard", persistentscoreboard);
    }
    
    persistentscoreboard.a(this.scoreboard);
    ((ScoreboardServer)this.scoreboard).a(persistentscoreboard);
  }
  

  public TileEntity getTileEntity(int i, int j, int k)
  {
    TileEntity result = super.getTileEntity(i, j, k);
    Block type = getType(i, j, k);
    
    if ((type == Blocks.CHEST) || (type == Blocks.TRAPPED_CHEST)) {
      if (!(result instanceof TileEntityChest)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.FURNACE) {
      if (!(result instanceof TileEntityFurnace)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.DROPPER) {
      if (!(result instanceof TileEntityDropper)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.DISPENSER) {
      if (!(result instanceof TileEntityDispenser)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.JUKEBOX) {
      if (!(result instanceof TileEntityRecordPlayer)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.NOTE_BLOCK) {
      if (!(result instanceof TileEntityNote)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.MOB_SPAWNER) {
      if (!(result instanceof TileEntityMobSpawner)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if ((type == Blocks.SIGN_POST) || (type == Blocks.WALL_SIGN)) {
      if (!(result instanceof TileEntitySign)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.ENDER_CHEST) {
      if (!(result instanceof TileEntityEnderChest)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.BREWING_STAND) {
      if (!(result instanceof TileEntityBrewingStand)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Blocks.BEACON) {
      if (!(result instanceof TileEntityBeacon)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if ((type == Blocks.HOPPER) && 
      (!(result instanceof TileEntityHopper))) {
      result = fixTileEntity(i, j, k, type, result);
    }
    

    return result;
  }
  
  private TileEntity fixTileEntity(int x, int y, int z, Block type, TileEntity found) {
    getServer().getLogger().severe("Block at " + x + "," + y + "," + z + " is " + org.bukkit.Material.getMaterial(Block.getId(type)).toString() + " but has " + found + ". Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.");
    

    if ((type instanceof IContainer)) {
      TileEntity replacement = ((IContainer)type).a(this, getData(x, y, z));
      replacement.world = this;
      setTileEntity(x, y, z, replacement);
      return replacement;
    }
    getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
    return found;
  }
  
  private boolean canSpawn(int x, int z)
  {
    if (this.generator != null) {
      return this.generator.canSpawn(getWorld(), x, z);
    }
    return this.worldProvider.canSpawn(x, z);
  }
  

  public void doTick()
  {
    super.doTick();
    if ((getWorldData().isHardcore()) && (this.difficulty != EnumDifficulty.HARD)) {
      this.difficulty = EnumDifficulty.HARD;
    }
    
    this.worldProvider.e.b();
    if (everyoneDeeplySleeping()) {
      if (getGameRules().getBoolean("doDaylightCycle")) {
        long i = this.worldData.getDayTime() + 24000L;
        
        this.worldData.setDayTime(i - i % 24000L);
      }
      
      d();
    }
    
    this.methodProfiler.a("mobSpawner");
    
    long time = this.worldData.getTime();
    if ((getGameRules().getBoolean("doMobSpawning")) && ((this.allowMonsters) || (this.allowAnimals)) && ((this instanceof WorldServer)) && (this.players.size() > 0)) {
      this.timings.mobSpawn.startTiming();
      this.R.spawnEntities(this, (this.allowMonsters) && (this.ticksPerMonsterSpawns != 0L) && (time % this.ticksPerMonsterSpawns == 0L), (this.allowAnimals) && (this.ticksPerAnimalSpawns != 0L) && (time % this.ticksPerAnimalSpawns == 0L), this.worldData.getTime() % 400L == 0L);
      this.timings.mobSpawn.stopTiming();
    }
    

    this.timings.doChunkUnload.startTiming();
    this.methodProfiler.c("chunkSource");
    this.chunkProvider.unloadChunks();
    int j = a(1.0F);
    
    if (j != this.j) {
      this.j = j;
    }
    
    this.worldData.setTime(this.worldData.getTime() + 1L);
    if (getGameRules().getBoolean("doDaylightCycle")) {
      this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
    }
    
    this.timings.doChunkUnload.stopTiming();
    this.methodProfiler.c("tickPending");
    this.timings.doTickPending.startTiming();
    a(false);
    this.timings.doTickPending.stopTiming();
    this.methodProfiler.c("tickBlocks");
    this.timings.doTickTiles.startTiming();
    g();
    this.timings.doTickTiles.stopTiming();
    this.spigotConfig.antiXrayInstance.flushUpdates(this);
    this.methodProfiler.c("chunkMap");
    this.timings.doChunkMap.startTiming();
    this.manager.flush();
    this.timings.doChunkMap.stopTiming();
    this.methodProfiler.c("village");
    this.timings.doVillages.startTiming();
    this.villages.tick();
    this.siegeManager.a();
    this.timings.doVillages.stopTiming();
    this.methodProfiler.c("portalForcer");
    this.timings.doPortalForcer.startTiming();
    this.Q.a(getTime());
    this.timings.doPortalForcer.stopTiming();
    this.methodProfiler.b();
    this.timings.doSounds.startTiming();
    Z();
    this.timings.doSounds.stopTiming();
    
    this.timings.doChunkGC.startTiming();
    getWorld().processChunkGC();
    this.timings.doChunkGC.stopTiming();
  }
  
  public BiomeMeta a(EnumCreatureType enumcreaturetype, int i, int j, int k) {
    List list = L().getMobsFor(enumcreaturetype, i, j, k);
    
    return (list != null) && (!list.isEmpty()) ? (BiomeMeta)WeightedRandom.a(this.random, list) : null;
  }
  
  public void everyoneSleeping() {
    this.O = (!this.players.isEmpty());
    Iterator iterator = this.players.iterator();
    
    while (iterator.hasNext()) {
      EntityHuman entityhuman = (EntityHuman)iterator.next();
      
      if ((!entityhuman.isSleeping()) && (!entityhuman.fauxSleeping)) {
        this.O = false;
        break;
      }
    }
  }
  
  protected void d() {
    this.O = false;
    Iterator iterator = this.players.iterator();
    
    while (iterator.hasNext()) {
      EntityHuman entityhuman = (EntityHuman)iterator.next();
      
      if (entityhuman.isSleeping()) {
        entityhuman.a(false, false, true);
      }
    }
    
    Y();
  }
  
  private void Y()
  {
    WeatherChangeEvent weather = new WeatherChangeEvent(getWorld(), false);
    getServer().getPluginManager().callEvent(weather);
    
    ThunderChangeEvent thunder = new ThunderChangeEvent(getWorld(), false);
    getServer().getPluginManager().callEvent(thunder);
    if (!weather.isCancelled()) {
      this.worldData.setWeatherDuration(0);
      this.worldData.setStorm(false);
    }
    if (!thunder.isCancelled()) {
      this.worldData.setThunderDuration(0);
      this.worldData.setThundering(false);
    }
  }
  
  public boolean everyoneDeeplySleeping()
  {
    if ((this.O) && (!this.isStatic)) {
      Iterator iterator = this.players.iterator();
      

      boolean foundActualSleepers = false;
      
      EntityHuman entityhuman;
      do
      {
        if (!iterator.hasNext()) {
          return foundActualSleepers;
        }
        
        entityhuman = (EntityHuman)iterator.next();
        
        if (entityhuman.isDeeplySleeping()) {
          foundActualSleepers = true;
        }
      } while ((entityhuman.isDeeplySleeping()) || (entityhuman.fauxSleeping));
      

      return false;
    }
    return false;
  }
  
  protected void g()
  {
    super.g();
    int i = 0;
    int j = 0;
    



    for (TLongShortIterator iter = this.chunkTickList.iterator(); iter.hasNext();) {
      iter.advance();
      long chunkCoord = iter.key();
      int chunkX = World.keyToX(chunkCoord);
      int chunkZ = World.keyToZ(chunkCoord);
      
      if ((!isChunkLoaded(chunkX, chunkZ)) || (this.chunkProviderServer.unloadQueue.contains(chunkX, chunkZ)))
      {
        iter.remove();

      }
      else
      {
        int k = chunkX * 16;
        int l = chunkZ * 16;
        
        this.methodProfiler.a("getChunk");
        Chunk chunk = getChunkAt(chunkX, chunkZ);
        

        a(k, l, chunk);
        this.methodProfiler.c("tickChunk");
        chunk.b(false);
        this.methodProfiler.c("thunder");
        




        if ((!this.paperSpigotConfig.disableThunder) && (this.random.nextInt(100000) == 0) && (Q()) && (P())) {
          this.k = (this.k * 3 + 1013904223);
          int i1 = this.k >> 2;
          int j1 = k + (i1 & 0xF);
          int k1 = l + (i1 >> 8 & 0xF);
          int l1 = h(j1, k1);
          if (isRainingAt(j1, l1, k1)) {
            strikeLightning(new EntityLightning(this, j1, l1, k1));
          }
        }
        
        this.methodProfiler.c("iceandsnow");
        if ((!this.paperSpigotConfig.disableIceAndSnow) && (this.random.nextInt(16) == 0)) {
          this.k = (this.k * 3 + 1013904223);
          int i1 = this.k >> 2;
          int j1 = i1 & 0xF;
          int k1 = i1 >> 8 & 0xF;
          int l1 = h(j1 + k, k1 + l);
          if (s(j1 + k, l1 - 1, k1 + l))
          {
            BlockState blockState = getWorld().getBlockAt(j1 + k, l1 - 1, k1 + l).getState();
            blockState.setTypeId(Block.getId(Blocks.ICE));
            
            BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
            getServer().getPluginManager().callEvent(iceBlockForm);
            if (!iceBlockForm.isCancelled()) {
              blockState.update(true);
            }
          }
          

          if ((Q()) && (e(j1 + k, l1, k1 + l, true)))
          {
            BlockState blockState = getWorld().getBlockAt(j1 + k, l1, k1 + l).getState();
            blockState.setTypeId(Block.getId(Blocks.SNOW));
            
            BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
            getServer().getPluginManager().callEvent(snow);
            if (!snow.isCancelled()) {
              blockState.update(true);
            }
          }
          

          if (Q()) {
            BiomeBase biomebase = getBiome(j1 + k, k1 + l);
            
            if (biomebase.e()) {
              getType(j1 + k, l1 - 1, k1 + l).l(this, j1 + k, l1 - 1, k1 + l);
            }
          }
        }
        
        this.methodProfiler.c("tickBlocks");
        ChunkSection[] achunksection = chunk.getSections();
        
        int j1 = achunksection.length;
        
        for (int k1 = 0; k1 < j1; k1++) {
          ChunkSection chunksection = achunksection[k1];
          
          if ((chunksection != null) && (chunksection.shouldTick())) {
            for (int i2 = 0; i2 < 3; i2++) {
              this.k = (this.k * 3 + 1013904223);
              int j2 = this.k >> 2;
              int k2 = j2 & 0xF;
              int l2 = j2 >> 8 & 0xF;
              int i3 = j2 >> 16 & 0xF;
              
              j++;
              Block block = chunksection.getTypeId(k2, i3, l2);
              
              if (block.isTicking()) {
                i++;
                this.growthOdds = (iter.value() < 1 ? this.modifiedOdds : 100.0F);
                block.a(this, k2 + k, i3 + chunksection.getYPosition(), l2 + l, this.random);
              }
            }
          }
        }
        
        this.methodProfiler.b();
      }
    }
    if (this.spigotConfig.clearChunksOnTick)
    {
      this.chunkTickList.clear();
    }
  }
  
  public boolean a(int i, int j, int k, Block block)
  {
    NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
    
    return this.V.contains(nextticklistentry);
  }
  
  public void a(int i, int j, int k, Block block, int l) {
    a(i, j, k, block, l, 0);
  }
  
  public void a(int i, int j, int k, Block block, int l, int i1) {
    NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
    byte b0 = 0;
    
    if ((this.d) && (block.getMaterial() != Material.AIR)) {
      if (block.L()) {
        b0 = 8;
        if (b(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
          Block block1 = getType(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
          
          if ((block1.getMaterial() != Material.AIR) && (block1 == nextticklistentry.a())) {
            block1.a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
          }
        }
        
        return;
      }
      
      l = 1;
    }
    
    if (b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
      if (block.getMaterial() != Material.AIR) {
        nextticklistentry.a(l + this.worldData.getTime());
        nextticklistentry.a(i1);
      }
      
      if (!this.N.contains(nextticklistentry)) {
        this.N.add(nextticklistentry);
      }
    }
  }
  
  public void b(int i, int j, int k, Block block, int l, int i1) {
    NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
    
    nextticklistentry.a(i1);
    if (block.getMaterial() != Material.AIR) {
      nextticklistentry.a(l + this.worldData.getTime());
    }
    
    if (!this.N.contains(nextticklistentry)) {
      this.N.add(nextticklistentry);
    }
  }
  




  public void tickEntities()
  {
    i();
    

    super.tickEntities();
    this.spigotConfig.currentPrimedTnt = 0;
  }
  
  public void i() {
    this.emptyTime = 0;
  }
  
  public boolean a(boolean flag) {
    int i = this.N.size();
    














    if (i > this.paperSpigotConfig.tickNextTickListCap) {
      i = this.paperSpigotConfig.tickNextTickListCap;
    }
    

    this.methodProfiler.a("cleaning");
    


    for (int j = 0; j < i; j++) {
      NextTickListEntry nextticklistentry = (NextTickListEntry)this.N.first();
      if ((!flag) && (nextticklistentry.d > this.worldData.getTime())) {
        break;
      }
      
      this.N.remove(nextticklistentry);
      this.V.add(nextticklistentry);
    }
    

    if (this.paperSpigotConfig.tickNextTickListCapIgnoresRedstone) {
      Iterator<NextTickListEntry> iterator = this.N.iterator();
      while (iterator.hasNext()) {
        NextTickListEntry next = (NextTickListEntry)iterator.next();
        if ((!flag) && (next.d > this.worldData.getTime())) {
          break;
        }
        
        if ((next.a().isPowerSource()) || ((next.a() instanceof IContainer))) {
          iterator.remove();
          this.V.add(next);
        }
      }
    }
    

    this.methodProfiler.b();
    this.methodProfiler.a("ticking");
    Iterator iterator = this.V.iterator();
    
    while (iterator.hasNext()) {
      NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
      iterator.remove();
      byte b0 = 0;
      
      if (b(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
        Block block = getType(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
        
        if ((block.getMaterial() != Material.AIR) && (Block.a(block, nextticklistentry.a()))) {
          try {
            block.a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
          } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
            
            int k;
            try
            {
              k = getData(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
            } catch (Throwable throwable1) { int k;
              k = -1;
            }
            
            CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, block, k);
            throw new ReportedException(crashreport);
          }
        }
      } else {
        a(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, nextticklistentry.a(), 0);
      }
    }
    
    this.methodProfiler.b();
    this.V.clear();
    return !this.N.isEmpty();
  }
  
  public List a(Chunk chunk, boolean flag)
  {
    ArrayList arraylist = null;
    ChunkCoordIntPair chunkcoordintpair = chunk.l();
    int i = (chunkcoordintpair.x << 4) - 2;
    int j = i + 16 + 2;
    int k = (chunkcoordintpair.z << 4) - 2;
    int l = k + 16 + 2;
    
    for (int i1 = 0; i1 < 2; i1++) {
      Iterator iterator;
      Iterator iterator;
      if (i1 == 0) {
        iterator = this.N.iterator();
      } else {
        iterator = this.V.iterator();
        if (!this.V.isEmpty()) {
          a.debug("toBeTicked = " + this.V.size());
        }
      }
      
      while (iterator.hasNext()) {
        NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
        
        if ((nextticklistentry.a >= i) && (nextticklistentry.a < j) && (nextticklistentry.c >= k) && (nextticklistentry.c < l)) {
          if (flag) {
            iterator.remove();
          }
          
          if (arraylist == null) {
            arraylist = new ArrayList();
          }
          
          arraylist.add(nextticklistentry);
        }
      }
    }
    
    return arraylist;
  }
  













  protected IChunkProvider j()
  {
    IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
    
    InternalChunkGenerator gen;
    
    InternalChunkGenerator gen;
    if (this.generator != null) {
      gen = new org.bukkit.craftbukkit.v1_7_R4.generator.CustomChunkGenerator(this, getSeed(), this.generator); } else { InternalChunkGenerator gen;
      if ((this.worldProvider instanceof WorldProviderHell)) {
        gen = new org.bukkit.craftbukkit.v1_7_R4.generator.NetherChunkGenerator(this, getSeed()); } else { InternalChunkGenerator gen;
        if ((this.worldProvider instanceof WorldProviderTheEnd)) {
          gen = new org.bukkit.craftbukkit.v1_7_R4.generator.SkyLandsChunkGenerator(this, getSeed());
        } else
          gen = new org.bukkit.craftbukkit.v1_7_R4.generator.NormalChunkGenerator(this, getSeed());
      }
    }
    this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, gen);
    

    return this.chunkProviderServer;
  }
  
  public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
    ArrayList arraylist = new ArrayList();
    

    for (int chunkX = i >> 4; chunkX <= l - 1 >> 4; chunkX++) {
      for (int chunkZ = k >> 4; chunkZ <= j1 - 1 >> 4; chunkZ++) {
        Chunk chunk = getChunkAt(chunkX, chunkZ);
        if (chunk != null)
        {


          for (Object te : chunk.tileEntities.values()) {
            TileEntity tileentity = (TileEntity)te;
            if ((tileentity.x >= i) && (tileentity.y >= j) && (tileentity.z >= k) && (tileentity.x < l) && (tileentity.y < i1) && (tileentity.z < j1)) {
              arraylist.add(tileentity);
            }
          }
        }
      }
    }
    
    return arraylist;
  }
  
  public boolean a(EntityHuman entityhuman, int i, int j, int k) {
    return !this.server.a(this, i, j, k, entityhuman);
  }
  
  protected void a(WorldSettings worldsettings) {
    if (this.entitiesById == null) {
      this.entitiesById = new IntHashMap();
    }
    
    if (this.N == null) {
      this.N = new HashTreeSet();
    }
    
    b(worldsettings);
    super.a(worldsettings);
  }
  
  protected void b(WorldSettings worldsettings) {
    if (!this.worldProvider.e()) {
      this.worldData.setSpawn(0, this.worldProvider.getSeaLevel(), 0);
    } else {
      this.isLoading = true;
      WorldChunkManager worldchunkmanager = this.worldProvider.e;
      List list = worldchunkmanager.a();
      Random random = new Random(getSeed());
      ChunkPosition chunkposition = worldchunkmanager.a(0, 0, 256, list, random);
      int i = 0;
      int j = this.worldProvider.getSeaLevel();
      int k = 0;
      

      if (this.generator != null) {
        Random rand = new Random(getSeed());
        Location spawn = this.generator.getFixedSpawnLocation(getWorld(), rand);
        
        if (spawn != null) {
          if (spawn.getWorld() != getWorld()) {
            throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + spawn.getWorld().getName() + ")");
          }
          this.worldData.setSpawn(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
          this.isLoading = false;
          return;
        }
      }
      


      if (chunkposition != null) {
        i = chunkposition.x;
        k = chunkposition.z;
      } else {
        a.warn("Unable to find spawn biome");
      }
      
      int l = 0;
      
      while (!canSpawn(i, k)) {
        i += random.nextInt(64) - random.nextInt(64);
        k += random.nextInt(64) - random.nextInt(64);
        l++;
        if (l == 1000) {
          break;
        }
      }
      
      this.worldData.setSpawn(i, j, k);
      this.isLoading = false;
      if (worldsettings.c()) {
        k();
      }
    }
  }
  
  protected void k() {
    WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(U, 10);
    
    for (int i = 0; i < 10; i++) {
      int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
      int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
      int l = i(j, k) + 1;
      
      if (worldgenbonuschest.generate(this, this.random, j, l, k)) {
        break;
      }
    }
  }
  
  public ChunkCoordinates getDimensionSpawn() {
    return this.worldProvider.h();
  }
  
  public void save(boolean flag, IProgressUpdate iprogressupdate) throws ExceptionWorldConflict {
    if (this.chunkProvider.canSave()) {
      if (iprogressupdate != null) {
        iprogressupdate.a("Saving level");
      }
      
      a();
      if (iprogressupdate != null) {
        iprogressupdate.c("Saving chunks");
      }
      
      this.chunkProvider.saveChunks(flag, iprogressupdate);
      
      Collection arraylist = this.chunkProviderServer.a();
      Iterator iterator = arraylist.iterator();
      
      while (iterator.hasNext()) {
        Chunk chunk = (Chunk)iterator.next();
        
        if ((chunk != null) && (!this.manager.a(chunk.locX, chunk.locZ))) {
          this.chunkProviderServer.queueUnload(chunk.locX, chunk.locZ);
        }
      }
    }
  }
  
  public void flushSave() {
    if (this.chunkProvider.canSave()) {
      this.chunkProvider.c();
    }
  }
  
  protected void a() throws ExceptionWorldConflict {
    G();
    this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().t());
    
    if (!(this instanceof SecondaryWorldServer)) {
      this.worldMaps.a();
    }
  }
  
  protected void a(Entity entity)
  {
    super.a(entity);
    this.entitiesById.a(entity.getId(), entity);
    Entity[] aentity = entity.at();
    
    if (aentity != null) {
      for (int i = 0; i < aentity.length; i++) {
        this.entitiesById.a(aentity[i].getId(), aentity[i]);
      }
    }
  }
  
  protected void b(Entity entity) {
    super.b(entity);
    this.entitiesById.d(entity.getId());
    Entity[] aentity = entity.at();
    
    if (aentity != null) {
      for (int i = 0; i < aentity.length; i++) {
        this.entitiesById.d(aentity[i].getId());
      }
    }
  }
  
  public Entity getEntity(int i) {
    return (Entity)this.entitiesById.get(i);
  }
  
  public boolean strikeLightning(Entity entity)
  {
    LightningStrikeEvent lightning = new LightningStrikeEvent(getWorld(), (org.bukkit.entity.LightningStrike)entity.getBukkitEntity());
    getServer().getPluginManager().callEvent(lightning);
    
    if (lightning.isCancelled()) {
      return false;
    }
    
    if (super.strikeLightning(entity)) {
      this.server.getPlayerList().sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new PacketPlayOutSpawnEntityWeather(entity));
      
      return true;
    }
    return false;
  }
  
  public void broadcastEntityEffect(Entity entity, byte b0)
  {
    getTracker().sendPacketToEntity(entity, new PacketPlayOutEntityStatus(entity, b0));
  }
  
  public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1)
  {
    Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, flag1);
    
    if (explosion.wasCanceled) {
      return explosion;
    }
    








    if (!flag1) {
      explosion.blocks.clear();
    }
    
    Iterator iterator = this.players.iterator();
    
    while (iterator.hasNext()) {
      EntityHuman entityhuman = (EntityHuman)iterator.next();
      
      if (entityhuman.e(d0, d1, d2) < 4096.0D) {
        ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.blocks, (Vec3D)explosion.b().get(entityhuman)));
      }
    }
    
    return explosion;
  }
  
  public void playBlockAction(int i, int j, int k, Block block, int l, int i1) {
    BlockActionData blockactiondata = new BlockActionData(i, j, k, block, l, i1);
    Iterator iterator = this.S[this.T].iterator();
    
    BlockActionData blockactiondata1;
    do
    {
      if (!iterator.hasNext()) {
        this.S[this.T].add(blockactiondata);
        return;
      }
      
      blockactiondata1 = (BlockActionData)iterator.next();
    } while (!blockactiondata1.equals(blockactiondata));
  }
  
  private void Z()
  {
    while (!this.S[this.T].isEmpty()) {
      int i = this.T;
      
      this.T ^= 0x1;
      Iterator iterator = this.S[i].iterator();
      
      while (iterator.hasNext()) {
        BlockActionData blockactiondata = (BlockActionData)iterator.next();
        
        if (a(blockactiondata))
        {
          this.server.getPlayerList().sendPacketNearby(blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), 64.0D, this.dimension, new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), blockactiondata.f(), blockactiondata.d(), blockactiondata.e()));
        }
      }
      
      this.S[i].clear();
    }
  }
  
  private boolean a(BlockActionData blockactiondata) {
    Block block = getType(blockactiondata.a(), blockactiondata.b(), blockactiondata.c());
    
    return block == blockactiondata.f() ? block.a(this, blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), blockactiondata.d(), blockactiondata.e()) : false;
  }
  
  public void saveLevel() {
    this.dataManager.a();
  }
  
  protected void o() {
    boolean flag = Q();
    
    super.o();
    



















    if (flag != Q())
    {
      for (int i = 0; i < this.players.size(); i++) {
        if (((EntityPlayer)this.players.get(i)).world == this) {
          ((EntityPlayer)this.players.get(i)).setPlayerWeather(!flag ? org.bukkit.WeatherType.DOWNFALL : org.bukkit.WeatherType.CLEAR, false);
        }
      }
    }
  }
  
  protected int p()
  {
    return this.server.getPlayerList().s();
  }
  
  public MinecraftServer getMinecraftServer() {
    return this.server;
  }
  
  public EntityTracker getTracker() {
    return this.tracker;
  }
  
  public PlayerChunkMap getPlayerChunkMap() {
    return this.manager;
  }
  
  public PortalTravelAgent getTravelAgent() {
    return this.Q;
  }
  
  public void a(String s, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
    PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(s, (float)d0, (float)d1, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, i);
    
    for (int j = 0; j < this.players.size(); j++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
      ChunkCoordinates chunkcoordinates = entityplayer.getChunkCoordinates();
      double d7 = d0 - chunkcoordinates.x;
      double d8 = d1 - chunkcoordinates.y;
      double d9 = d2 - chunkcoordinates.z;
      double d10 = d7 * d7 + d8 * d8 + d9 * d9;
      
      if (d10 <= 256.0D) {
        entityplayer.playerConnection.sendPacket(packetplayoutworldparticles);
      }
    }
  }
  
  public int getTypeId(int x, int y, int z)
  {
    return Block.getId(getType(x, y, z));
  }
}
