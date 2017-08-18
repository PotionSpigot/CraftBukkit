package net.minecraft.server;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.util.gnu.trove.map.hash.TLongShortHashMap;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.WorldTimingsHandler;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.github.paperspigot.PaperSpigotWorldConfig;
import org.spigotmc.ActivationRange;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.SpigotWorldConfig;

public abstract class World implements IBlockAccess
{
  public boolean d;
  public List entityList = new ArrayList()
  {

    public Object remove(int index)
    {
      guard();
      return super.remove(index);
    }
    

    public boolean remove(Object o)
    {
      guard();
      return super.remove(o);
    }
    
    private void guard()
    {
      if (World.this.guardEntityList)
      {
        throw new java.util.ConcurrentModificationException();
      }
    }
  };
  
  protected List f = new ArrayList();
  public Set tileEntityList = new org.spigotmc.WorldTileEntityList(this);
  private List a = new ArrayList();
  private List b = new ArrayList();
  public List players = new ArrayList();
  public List i = new ArrayList();
  private long c = 16777215L;
  public int j;
  protected int k = new Random().nextInt();
  protected final int l = 1013904223;
  protected float m;
  protected float n;
  protected float o;
  protected float p;
  public int q;
  public EnumDifficulty difficulty;
  public Random random = new Random();
  public WorldProvider worldProvider;
  protected List u = new ArrayList();
  public IChunkProvider chunkProvider;
  protected final IDataManager dataManager;
  public WorldData worldData;
  public boolean isLoading;
  public PersistentCollection worldMaps;
  public final PersistentVillage villages;
  protected final VillageSiege siegeManager = new VillageSiege(this);
  public final MethodProfiler methodProfiler;
  private final Calendar J = Calendar.getInstance();
  public Scoreboard scoreboard = new Scoreboard();
  
  public boolean isStatic;
  
  private int K;
  
  public boolean allowMonsters;
  public boolean allowAnimals;
  public boolean captureBlockStates = false;
  public boolean captureTreeGeneration = false;
  public ArrayList<BlockState> capturedBlockStates = new ArrayList();
  
  public long ticksPerAnimalSpawns;
  
  public long ticksPerMonsterSpawns;
  
  public boolean populating;
  private int tickPosition;
  private ArrayList L;
  private boolean M;
  int[] I;
  private boolean guardEntityList;
  protected final TLongShortHashMap chunkTickList;
  protected float growthOdds = 100.0F;
  protected float modifiedOdds = 100.0F;
  private final byte chunkTickRadius;
  public static boolean haveWeSilencedAPhysicsCrash;
  public static String blockLocation;
  public List<TileEntity> triggerHoppersList = new ArrayList();
  public ExecutorService lightingExecutor = java.util.concurrent.Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("PaperSpigot - Lighting Thread").build());
  public final Map<Explosion.CacheKey, Float> explosionDensityCache = new java.util.HashMap();
  private final CraftWorld world;
  public boolean pvpMode;
  
  public static long chunkToKey(int x, int z) { long k = (x & 0xFFFF0000) << 16 | (x & 0xFFFF) << 0;
    k |= (z & 0xFFFF0000) << 32 | (z & 0xFFFF) << 16;
    return k;
  }
  
  public static int keyToX(long k)
  {
    return (int)(k >> 16 & 0xFFFFFFFFFFFF0000 | k & 0xFFFF);
  }
  
  public static int keyToZ(long k)
  {
    return (int)(k >> 32 & 0xFFFF0000 | k >> 16 & 0xFFFF);
  }
  
  private void initializeHoppers()
  {
    if (this.spigotConfig.altHopperTicking) {
      for (TileEntity o : this.triggerHoppersList) {
        o.scheduleTicks();
        if ((o instanceof TileEntityHopper)) {
          ((TileEntityHopper)o).convertToScheduling();
          ((TileEntityHopper)o).scheduleHopperTick();
        }
      }
    }
    this.triggerHoppersList.clear();
  }
  

  public void updateChestAndHoppers(int a, int b, int c)
  {
    Block block = getType(a, b, c);
    if ((block instanceof BlockChest)) {
      TileEntity tile = getTileEntity(a, b, c);
      if ((tile instanceof TileEntityChest)) {
        tile.scheduleTicks();
      }
      for (int i = 2; i < 6; i++)
      {
        if (getType(a + Facing.b[i], b, c + Facing.d[i]) == block) {
          tile = getTileEntity(a + Facing.b[i], b, c + Facing.d[i]);
          if (!(tile instanceof TileEntityChest)) break;
          tile.scheduleTicks(); break;
        }
      }
    }
  }
  


  public BiomeBase getBiome(int i, int j)
  {
    if (isLoaded(i, 0, j)) {
      Chunk chunk = getChunkAtWorldCoords(i, j);
      try
      {
        return chunk.getBiome(i & 0xF, j & 0xF, this.worldProvider.e);
      } catch (Throwable throwable) {
        CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Coordinates of biome request");
        
        crashreportsystemdetails.a("Location", new CrashReportWorldLocation(this, i, j));
        throw new ReportedException(crashreport);
      }
    }
    return this.worldProvider.e.getBiome(i, j);
  }
  
  public WorldChunkManager getWorldChunkManager()
  {
    return this.worldProvider.e;
  }
  



  public boolean keepSpawnInMemory = true;
  public ChunkGenerator generator;
  public final SpigotWorldConfig spigotConfig;
  public final PaperSpigotWorldConfig paperSpigotConfig;
  public final SpigotTimings.WorldTimingsHandler timings;
  
  public CraftWorld getWorld()
  {
    return this.world;
  }
  
  public CraftServer getServer() {
    return (CraftServer)Bukkit.getServer();
  }
  
  public Chunk getChunkIfLoaded(int x, int z) {
    return ((ChunkProviderServer)this.chunkProvider).getChunkIfLoaded(x, z);
  }
  
  public World(IDataManager idatamanager, String s, WorldSettings worldsettings, WorldProvider worldprovider, MethodProfiler methodprofiler, ChunkGenerator gen, World.Environment env)
  {
    this.spigotConfig = new SpigotWorldConfig(s);
    this.paperSpigotConfig = new PaperSpigotWorldConfig(s);
    this.generator = gen;
    this.world = new CraftWorld((WorldServer)this, gen, env);
    this.ticksPerAnimalSpawns = getServer().getTicksPerAnimalSpawns();
    this.ticksPerMonsterSpawns = getServer().getTicksPerMonsterSpawns();
    
    this.keepSpawnInMemory = this.paperSpigotConfig.keepSpawnInMemory;
    
    this.chunkTickRadius = ((byte)(getServer().getViewDistance() < 7 ? getServer().getViewDistance() : 7));
    this.chunkTickList = new TLongShortHashMap(this.spigotConfig.chunksPerTick * 5, 0.7F, Long.MIN_VALUE, (short)Short.MIN_VALUE);
    this.chunkTickList.setAutoCompactionFactor(0.0F);
    

    this.K = this.random.nextInt(12000);
    this.allowMonsters = true;
    this.allowAnimals = true;
    this.L = new ArrayList();
    this.I = new int[32768];
    this.dataManager = idatamanager;
    this.methodProfiler = methodprofiler;
    this.worldMaps = new PersistentCollection(idatamanager);
    this.worldData = idatamanager.getWorldData();
    if (worldprovider != null) {
      this.worldProvider = worldprovider;
    } else if ((this.worldData != null) && (this.worldData.j() != 0)) {
      this.worldProvider = WorldProvider.byDimension(this.worldData.j());
    } else {
      this.worldProvider = WorldProvider.byDimension(0);
    }
    
    if (this.worldData == null) {
      this.worldData = new WorldData(worldsettings, s);
    } else {
      this.worldData.setName(s);
    }
    
    this.worldProvider.a(this);
    this.chunkProvider = j();
    this.timings = new SpigotTimings.WorldTimingsHandler(this);
    if (!this.worldData.isInitialized()) {
      try {
        a(worldsettings);
      } catch (Throwable throwable) {
        CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");
        try
        {
          a(crashreport);
        }
        catch (Throwable localThrowable1) {}
        

        throw new ReportedException(crashreport);
      }
      
      this.worldData.d(true);
    }
    
    PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, "villages");
    
    if (persistentvillage == null) {
      this.villages = new PersistentVillage(this);
      this.worldMaps.a("villages", this.villages);
    } else {
      this.villages = persistentvillage;
      this.villages.a(this);
    }
    
    B();
    a();
    
    getServer().addWorld(this.world);
  }
  
  protected abstract IChunkProvider j();
  
  protected void a(WorldSettings worldsettings) {
    this.worldData.d(true);
  }
  

  public Block b(int i, int j)
  {
    for (int k = 63; !isEmpty(i, k + 1, j); k++) {}
    


    return getType(i, k, j);
  }
  

  public Block getType(int i, int j, int k)
  {
    return getType(i, j, k, true);
  }
  
  public Block getType(int i, int j, int k, boolean useCaptured)
  {
    if ((this.captureTreeGeneration) && (useCaptured))
    {
      Iterator<BlockState> it = this.capturedBlockStates.iterator();
      while (it.hasNext()) {
        BlockState previous = (BlockState)it.next();
        if ((previous.getX() == i) && (previous.getY() == j) && (previous.getZ() == k)) {
          return CraftMagicNumbers.getBlock(previous.getTypeId());
        }
      }
    }
    
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000) && (j >= 0) && (j < 256)) {
      Chunk chunk = null;
      try
      {
        chunk = getChunkAt(i >> 4, k >> 4);
        return chunk.getType(i & 0xF, j, k & 0xF);
      } catch (Throwable throwable) {
        CrashReport crashreport = CrashReport.a(throwable, "Exception getting block type in world");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Requested block coordinates");
        
        crashreportsystemdetails.a("Found chunk", Boolean.valueOf(chunk == null));
        crashreportsystemdetails.a("Location", CrashReportSystemDetails.a(i, j, k));
        throw new ReportedException(crashreport);
      }
    }
    return Blocks.AIR;
  }
  
  public boolean isEmpty(int i, int j, int k)
  {
    return getType(i, j, k).getMaterial() == Material.AIR;
  }
  
  public boolean isLoaded(int i, int j, int k) {
    return (j >= 0) && (j < 256) ? isChunkLoaded(i >> 4, k >> 4) : false;
  }
  
  public boolean areChunksLoaded(int i, int j, int k, int l) {
    return b(i - l, j - l, k - l, i + l, j + l, k + l);
  }
  
  public boolean b(int i, int j, int k, int l, int i1, int j1) {
    if ((i1 >= 0) && (j < 256)) {
      i >>= 4;
      k >>= 4;
      l >>= 4;
      j1 >>= 4;
      
      for (int k1 = i; k1 <= l; k1++) {
        for (int l1 = k; l1 <= j1; l1++) {
          if (!isChunkLoaded(k1, l1)) {
            return false;
          }
        }
      }
      
      return true;
    }
    return false;
  }
  
  protected boolean isChunkLoaded(int i, int j)
  {
    return this.chunkProvider.isChunkLoaded(i, j);
  }
  
  public Chunk getChunkAtWorldCoords(int i, int j) {
    return getChunkAt(i >> 4, j >> 4);
  }
  
  public Chunk getChunkAt(int i, int j) {
    return this.chunkProvider.getOrCreateChunk(i, j);
  }
  
  public boolean setTypeAndData(int i, int j, int k, Block block, int l, int i1)
  {
    if (this.captureTreeGeneration) {
      BlockState blockstate = null;
      Iterator<BlockState> it = this.capturedBlockStates.iterator();
      while (it.hasNext()) {
        BlockState previous = (BlockState)it.next();
        if ((previous.getX() == i) && (previous.getY() == j) && (previous.getZ() == k)) {
          blockstate = previous;
          it.remove();
          break;
        }
      }
      if (blockstate == null) {
        blockstate = CraftBlockState.getBlockState(this, i, j, k, i1);
      }
      blockstate.setTypeId(CraftMagicNumbers.getId(block));
      blockstate.setRawData((byte)l);
      this.capturedBlockStates.add(blockstate);
      return true;
    }
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      if (j < 0)
        return false;
      if (j >= 256) {
        return false;
      }
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      Block block1 = null;
      
      if ((i1 & 0x1) != 0) {
        block1 = chunk.getType(i & 0xF, j, k & 0xF);
      }
      

      BlockState blockstate = null;
      if (this.captureBlockStates) {
        blockstate = CraftBlockState.getBlockState(this, i, j, k, i1);
        this.capturedBlockStates.add(blockstate);
      }
      

      boolean flag = chunk.a(i & 0xF, j, k & 0xF, block, l);
      

      if ((!flag) && (this.captureBlockStates)) {
        this.capturedBlockStates.remove(blockstate);
      }
      

      this.methodProfiler.a("checkLight");
      t(i, j, k);
      this.methodProfiler.b();
      
      if ((flag) && (!this.captureBlockStates))
      {
        notifyAndUpdatePhysics(i, j, k, chunk, block1, block, i1);
      }
      




      if ((this.spigotConfig.altHopperTicking) && (block1 != null) && (block1.r()) && (!block.r())) {
        updateChestAndHoppers(i, j - 1, k);
      }
      

      return flag;
    }
    
    return false;
  }
  



  public void notifyAndUpdatePhysics(int i, int j, int k, Chunk chunk, Block oldBlock, Block newBlock, int flag)
  {
    if (((flag & 0x2) != 0) && ((chunk == null) || (chunk.isReady()))) {
      notify(i, j, k);
    }
    
    if ((flag & 0x1) != 0) {
      update(i, j, k, oldBlock);
      if (newBlock.isComplexRedstone()) {
        updateAdjacentComparators(i, j, k, newBlock);
      }
    }
  }
  

  public int getData(int i, int j, int k)
  {
    if (this.captureTreeGeneration) {
      Iterator<BlockState> it = this.capturedBlockStates.iterator();
      while (it.hasNext()) {
        BlockState previous = (BlockState)it.next();
        if ((previous.getX() == i) && (previous.getY() == j) && (previous.getZ() == k)) {
          return previous.getRawData();
        }
      }
    }
    
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      if (j < 0)
        return 0;
      if (j >= 256) {
        return 0;
      }
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      
      i &= 0xF;
      k &= 0xF;
      return chunk.getData(i, j, k);
    }
    
    return 0;
  }
  

  public boolean setData(int i, int j, int k, int l, int i1)
  {
    if (this.captureTreeGeneration) {
      BlockState blockstate = null;
      Iterator<BlockState> it = this.capturedBlockStates.iterator();
      while (it.hasNext()) {
        BlockState previous = (BlockState)it.next();
        if ((previous.getX() == i) && (previous.getY() == j) && (previous.getZ() == k)) {
          blockstate = previous;
          it.remove();
          break;
        }
      }
      if (blockstate == null) {
        blockstate = CraftBlockState.getBlockState(this, i, j, k, i1);
      }
      blockstate.setRawData((byte)l);
      this.capturedBlockStates.add(blockstate);
      return true;
    }
    
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      if (j < 0)
        return false;
      if (j >= 256) {
        return false;
      }
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      int j1 = i & 0xF;
      int k1 = k & 0xF;
      boolean flag = chunk.a(j1, j, k1, l);
      
      if (flag) {
        Block block = chunk.getType(j1, j, k1);
        
        if (((i1 & 0x2) != 0) && ((!this.isStatic) || ((i1 & 0x4) == 0)) && (chunk.isReady())) {
          notify(i, j, k);
        }
        
        if ((!this.isStatic) && ((i1 & 0x1) != 0)) {
          update(i, j, k, block);
          if (block.isComplexRedstone()) {
            updateAdjacentComparators(i, j, k, block);
          }
        }
      }
      
      return flag;
    }
    
    return false;
  }
  
  public boolean setAir(int i, int j, int k)
  {
    return setTypeAndData(i, j, k, Blocks.AIR, 0, 3);
  }
  
  public boolean setAir(int i, int j, int k, boolean flag) {
    Block block = getType(i, j, k);
    
    if (block.getMaterial() == Material.AIR) {
      return false;
    }
    int l = getData(i, j, k);
    
    triggerEffect(2001, i, j, k, Block.getId(block) + (l << 12));
    if (flag) {
      block.b(this, i, j, k, l, 0);
    }
    
    return setTypeAndData(i, j, k, Blocks.AIR, 0, 3);
  }
  
  public boolean setTypeUpdate(int i, int j, int k, Block block)
  {
    return setTypeAndData(i, j, k, block, 0, 3);
  }
  
  public void notify(int i, int j, int k) {
    for (int l = 0; l < this.u.size(); l++) {
      ((IWorldAccess)this.u.get(l)).a(i, j, k);
    }
  }
  
  public void update(int i, int j, int k, Block block)
  {
    if (this.populating) {
      return;
    }
    
    applyPhysics(i, j, k, block);
  }
  

  public void b(int i, int j, int k, int l)
  {
    if (k > l) {
      int i1 = l;
      l = k;
      k = i1;
    }
    
    if (!this.worldProvider.g) {
      for (int i1 = k; i1 <= l; i1++) {
        updateLight(EnumSkyBlock.SKY, i, i1, j);
      }
    }
    
    c(i, k, j, i, l, j);
  }
  
  public void c(int i, int j, int k, int l, int i1, int j1) {
    for (int k1 = 0; k1 < this.u.size(); k1++) {
      ((IWorldAccess)this.u.get(k1)).a(i, j, k, l, i1, j1);
    }
  }
  
  public void applyPhysics(int i, int j, int k, Block block) {
    e(i - 1, j, k, block);
    e(i + 1, j, k, block);
    e(i, j - 1, k, block);
    e(i, j + 1, k, block);
    e(i, j, k - 1, block);
    e(i, j, k + 1, block);
    this.spigotConfig.antiXrayInstance.updateNearbyBlocks(this, i, j, k);
  }
  
  public void b(int i, int j, int k, Block block, int l) {
    if (l != 4) {
      e(i - 1, j, k, block);
    }
    
    if (l != 5) {
      e(i + 1, j, k, block);
    }
    
    if (l != 0) {
      e(i, j - 1, k, block);
    }
    
    if (l != 1) {
      e(i, j + 1, k, block);
    }
    
    if (l != 2) {
      e(i, j, k - 1, block);
    }
    
    if (l != 3) {
      e(i, j, k + 1, block);
    }
  }
  
  public void e(int i, int j, int k, Block block) {
    if (!this.isStatic) {
      Block block1 = getType(i, j, k);
      
      try
      {
        CraftWorld world = ((WorldServer)this).getWorld();
        if (world != null) {
          BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(i, j, k), CraftMagicNumbers.getId(block));
          getServer().getPluginManager().callEvent(event);
          
          if (event.isCancelled()) {
            return;
          }
        }
        

        block1.doPhysics(this, i, j, k, block);
      } catch (StackOverflowError stackoverflowerror) {
        haveWeSilencedAPhysicsCrash = true;
        blockLocation = i + ", " + j + ", " + k;
      } catch (Throwable throwable) {
        CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
        
        int l;
        try
        {
          l = getData(i, j, k);
        } catch (Throwable throwable1) { int l;
          l = -1;
        }
        
        crashreportsystemdetails.a("Source block type", new CrashReportSourceBlockType(this, block));
        CrashReportSystemDetails.a(crashreportsystemdetails, i, j, k, block1, l);
        throw new ReportedException(crashreport);
      }
    }
  }
  
  public boolean a(int i, int j, int k, Block block) {
    return false;
  }
  
  public boolean i(int i, int j, int k) {
    return getChunkAt(i >> 4, k >> 4).d(i & 0xF, j, k & 0xF);
  }
  
  public int j(int i, int j, int k) {
    if (j < 0) {
      return 0;
    }
    if (j >= 256) {
      j = 255;
    }
    
    return getChunkAt(i >> 4, k >> 4).b(i & 0xF, j, k & 0xF, 0);
  }
  
  public int getLightLevel(int i, int j, int k)
  {
    return b(i, j, k, true);
  }
  
  public int b(int i, int j, int k, boolean flag) {
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      if ((flag) && (getType(i, j, k).n())) {
        int l = b(i, j + 1, k, false);
        int i1 = b(i + 1, j, k, false);
        int j1 = b(i - 1, j, k, false);
        int k1 = b(i, j, k + 1, false);
        int l1 = b(i, j, k - 1, false);
        
        if (i1 > l) {
          l = i1;
        }
        
        if (j1 > l) {
          l = j1;
        }
        
        if (k1 > l) {
          l = k1;
        }
        
        if (l1 > l) {
          l = l1;
        }
        
        return l; }
      if (j < 0) {
        return 0;
      }
      if (j >= 256) {
        j = 255;
      }
      
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      
      i &= 0xF;
      k &= 0xF;
      return chunk.b(i, j, k, this.j);
    }
    
    return 15;
  }
  
  public int getHighestBlockYAt(int i, int j)
  {
    if ((i >= -30000000) && (j >= -30000000) && (i < 30000000) && (j < 30000000)) {
      if (!isChunkLoaded(i >> 4, j >> 4)) {
        return 0;
      }
      Chunk chunk = getChunkAt(i >> 4, j >> 4);
      
      return chunk.b(i & 0xF, j & 0xF);
    }
    
    return 64;
  }
  
  public int g(int i, int j)
  {
    if ((i >= -30000000) && (j >= -30000000) && (i < 30000000) && (j < 30000000)) {
      if (!isChunkLoaded(i >> 4, j >> 4)) {
        return 0;
      }
      Chunk chunk = getChunkAt(i >> 4, j >> 4);
      
      return chunk.r;
    }
    
    return 64;
  }
  
  public int b(EnumSkyBlock enumskyblock, int i, int j, int k)
  {
    if (j < 0) {
      j = 0;
    }
    
    if (j >= 256) {
      j = 255;
    }
    
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      int l = i >> 4;
      int i1 = k >> 4;
      
      if (!isChunkLoaded(l, i1)) {
        return enumskyblock.c;
      }
      Chunk chunk = getChunkAt(l, i1);
      
      return chunk.getBrightness(enumskyblock, i & 0xF, j, k & 0xF);
    }
    
    return enumskyblock.c;
  }
  
  public void b(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
  {
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000) && 
      (j >= 0) && 
      (j < 256) && 
      (isChunkLoaded(i >> 4, k >> 4))) {
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      
      chunk.a(enumskyblock, i & 0xF, j, k & 0xF, l);
      
      for (int i1 = 0; i1 < this.u.size(); i1++) {
        ((IWorldAccess)this.u.get(i1)).b(i, j, k);
      }
    }
  }
  


  public void m(int i, int j, int k)
  {
    for (int l = 0; l < this.u.size(); l++) {
      ((IWorldAccess)this.u.get(l)).b(i, j, k);
    }
  }
  
  public float n(int i, int j, int k) {
    return this.worldProvider.h[getLightLevel(i, j, k)];
  }
  
  public boolean w() {
    return this.j < 4;
  }
  
  public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1) {
    return rayTrace(vec3d, vec3d1, false, false, false);
  }
  
  public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag) {
    return rayTrace(vec3d, vec3d1, flag, false, false);
  }
  
  public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag, boolean flag1, boolean flag2) {
    if ((!Double.isNaN(vec3d.a)) && (!Double.isNaN(vec3d.b)) && (!Double.isNaN(vec3d.c))) {
      if ((!Double.isNaN(vec3d1.a)) && (!Double.isNaN(vec3d1.b)) && (!Double.isNaN(vec3d1.c))) {
        int i = MathHelper.floor(vec3d1.a);
        int j = MathHelper.floor(vec3d1.b);
        int k = MathHelper.floor(vec3d1.c);
        int l = MathHelper.floor(vec3d.a);
        int i1 = MathHelper.floor(vec3d.b);
        int j1 = MathHelper.floor(vec3d.c);
        Block block = getType(l, i1, j1);
        int k1 = getData(l, i1, j1);
        
        if (((!flag1) || (block.a(this, l, i1, j1) != null)) && (block.a(k1, flag))) {
          MovingObjectPosition movingobjectposition = block.a(this, l, i1, j1, vec3d, vec3d1);
          
          if (movingobjectposition != null) {
            return movingobjectposition;
          }
        }
        
        MovingObjectPosition movingobjectposition1 = null;
        
        k1 = 200;
        
        while (k1-- >= 0) {
          if ((Double.isNaN(vec3d.a)) || (Double.isNaN(vec3d.b)) || (Double.isNaN(vec3d.c))) {
            return null;
          }
          
          if ((l == i) && (i1 == j) && (j1 == k)) {
            return flag2 ? movingobjectposition1 : null;
          }
          
          boolean flag3 = true;
          boolean flag4 = true;
          boolean flag5 = true;
          double d0 = 999.0D;
          double d1 = 999.0D;
          double d2 = 999.0D;
          
          if (i > l) {
            d0 = l + 1.0D;
          } else if (i < l) {
            d0 = l + 0.0D;
          } else {
            flag3 = false;
          }
          
          if (j > i1) {
            d1 = i1 + 1.0D;
          } else if (j < i1) {
            d1 = i1 + 0.0D;
          } else {
            flag4 = false;
          }
          
          if (k > j1) {
            d2 = j1 + 1.0D;
          } else if (k < j1) {
            d2 = j1 + 0.0D;
          } else {
            flag5 = false;
          }
          
          double d3 = 999.0D;
          double d4 = 999.0D;
          double d5 = 999.0D;
          double d6 = vec3d1.a - vec3d.a;
          double d7 = vec3d1.b - vec3d.b;
          double d8 = vec3d1.c - vec3d.c;
          
          if (flag3) {
            d3 = (d0 - vec3d.a) / d6;
          }
          
          if (flag4) {
            d4 = (d1 - vec3d.b) / d7;
          }
          
          if (flag5) {
            d5 = (d2 - vec3d.c) / d8;
          }
          
          boolean flag6 = false;
          
          byte b0;
          if ((d3 < d4) && (d3 < d5)) { byte b0;
            byte b0; if (i > l) {
              b0 = 4;
            } else {
              b0 = 5;
            }
            
            vec3d.a = d0;
            vec3d.b += d7 * d3;
            vec3d.c += d8 * d3;
          } else if (d4 < d5) { byte b0;
            byte b0; if (j > i1) {
              b0 = 0;
            } else {
              b0 = 1;
            }
            
            vec3d.a += d6 * d4;
            vec3d.b = d1;
            vec3d.c += d8 * d4;
          } else { byte b0;
            if (k > j1) {
              b0 = 2;
            } else {
              b0 = 3;
            }
            
            vec3d.a += d6 * d5;
            vec3d.b += d7 * d5;
            vec3d.c = d2;
          }
          
          Vec3D vec3d2 = Vec3D.a(vec3d.a, vec3d.b, vec3d.c);
          
          l = (int)(vec3d2.a = MathHelper.floor(vec3d.a));
          if (b0 == 5) {
            l--;
            vec3d2.a += 1.0D;
          }
          
          i1 = (int)(vec3d2.b = MathHelper.floor(vec3d.b));
          if (b0 == 1) {
            i1--;
            vec3d2.b += 1.0D;
          }
          
          j1 = (int)(vec3d2.c = MathHelper.floor(vec3d.c));
          if (b0 == 3) {
            j1--;
            vec3d2.c += 1.0D;
          }
          
          Block block1 = getType(l, i1, j1);
          int l1 = getData(l, i1, j1);
          
          if ((!flag1) || (block1.a(this, l, i1, j1) != null)) {
            if (block1.a(l1, flag)) {
              MovingObjectPosition movingobjectposition2 = block1.a(this, l, i1, j1, vec3d, vec3d1);
              
              if (movingobjectposition2 != null) {
                return movingobjectposition2;
              }
            } else {
              movingobjectposition1 = new MovingObjectPosition(l, i1, j1, b0, vec3d, false);
            }
          }
        }
        
        return flag2 ? movingobjectposition1 : null;
      }
      return null;
    }
    
    return null;
  }
  
  public void makeSound(Entity entity, String s, float f, float f1)
  {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).a(s, entity.locX, entity.locY - entity.height, entity.locZ, f, f1);
    }
  }
  
  public void a(EntityHuman entityhuman, String s, float f, float f1) {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).a(entityhuman, s, entityhuman.locX, entityhuman.locY - entityhuman.height, entityhuman.locZ, f, f1);
    }
  }
  
  public void makeSound(double d0, double d1, double d2, String s, float f, float f1) {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, f, f1);
    }
  }
  
  public void a(double d0, double d1, double d2, String s, float f, float f1, boolean flag) {}
  
  public void a(String s, int i, int j, int k) {
    for (int l = 0; l < this.u.size(); l++) {
      ((IWorldAccess)this.u.get(l)).a(s, i, j, k);
    }
  }
  
  public void addParticle(String s, double d0, double d1, double d2, double d3, double d4, double d5) {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, d3, d4, d5);
    }
  }
  
  public boolean strikeLightning(Entity entity) {
    this.i.add(entity);
    return true;
  }
  
  public boolean addEntity(Entity entity)
  {
    return addEntity(entity, CreatureSpawnEvent.SpawnReason.DEFAULT);
  }
  
  public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
    AsyncCatcher.catchOp("entity add");
    if (entity == null) { return false;
    }
    
    int i = MathHelper.floor(entity.locX / 16.0D);
    int j = MathHelper.floor(entity.locZ / 16.0D);
    boolean flag = entity.attachedToPlayer;
    
    if ((entity instanceof EntityHuman)) {
      flag = true;
    }
    

    Cancellable event = null;
    EntityExperienceOrb xp; if (((entity instanceof EntityLiving)) && (!(entity instanceof EntityPlayer))) {
      boolean isAnimal = ((entity instanceof EntityAnimal)) || ((entity instanceof EntityWaterAnimal)) || ((entity instanceof EntityGolem));
      boolean isMonster = ((entity instanceof EntityMonster)) || ((entity instanceof EntityGhast)) || ((entity instanceof EntitySlime));
      
      if ((spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM) && (
        ((isAnimal) && (!this.allowAnimals)) || ((isMonster) && (!this.allowMonsters)))) {
        entity.dead = true;
        return false;
      }
      

      event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
    } else if ((entity instanceof EntityItem)) {
      event = CraftEventFactory.callItemSpawnEvent((EntityItem)entity);
    } else if ((entity.getBukkitEntity() instanceof org.bukkit.entity.Projectile))
    {
      event = CraftEventFactory.callProjectileLaunchEvent(entity);

    }
    else if ((entity instanceof EntityExperienceOrb)) {
      xp = (EntityExperienceOrb)entity;
      double radius = this.spigotConfig.expMerge;
      if (radius > 0.0D) {
        List<Entity> entities = getEntities(entity, entity.boundingBox.grow(radius, radius, radius));
        for (Entity e : entities) {
          if ((e instanceof EntityExperienceOrb)) {
            EntityExperienceOrb loopItem = (EntityExperienceOrb)e;
            if (!loopItem.dead) {
              xp.value += loopItem.value;
              loopItem.die();
            }
          }
        }
      }
    }
    
    if ((event != null) && ((event.isCancelled()) || (entity.dead))) {
      entity.dead = true;
      return false;
    }
    

    if ((!flag) && (!isChunkLoaded(i, j))) {
      entity.dead = true;
      return false;
    }
    if ((entity instanceof EntityHuman)) {
      EntityHuman entityhuman = (EntityHuman)entity;
      
      this.players.add(entityhuman);
      everyoneSleeping();
      b(entity);
    }
    
    getChunkAt(i, j).a(entity);
    this.entityList.add(entity);
    a(entity);
    return true;
  }
  
  protected void a(Entity entity)
  {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).a(entity);
    }
    
    entity.valid = true;
  }
  
  protected void b(Entity entity) {
    for (int i = 0; i < this.u.size(); i++) {
      ((IWorldAccess)this.u.get(i)).b(entity);
    }
    
    entity.valid = false;
  }
  
  public void kill(Entity entity) {
    if (entity.passenger != null) {
      entity.passenger.mount((Entity)null);
    }
    
    if (entity.vehicle != null) {
      entity.mount((Entity)null);
    }
    
    entity.die();
    if ((entity instanceof EntityHuman)) {
      this.players.remove(entity);
      
      for (Object o : this.worldMaps.c)
      {
        if ((o instanceof WorldMap))
        {
          WorldMap map = (WorldMap)o;
          map.i.remove(entity);
          for (iter = map.f.iterator(); iter.hasNext();)
          {
            if (((WorldMapHumanTracker)iter.next()).trackee == entity)
            {
              iter.remove();
            }
          }
        }
      }
      Iterator<WorldMapHumanTracker> iter;
      everyoneSleeping();
    }
  }
  
  public void removeEntity(Entity entity) {
    AsyncCatcher.catchOp("entity remove");
    entity.die();
    if ((entity instanceof EntityHuman)) {
      this.players.remove(entity);
      everyoneSleeping();
    }
    
    if (!this.guardEntityList) {
      int i = entity.ah;
      int j = entity.aj;
      if ((entity.ag) && (isChunkLoaded(i, j))) {
        getChunkAt(i, j).b(entity);
      }
      
      int index = this.entityList.indexOf(entity);
      if (index != -1) {
        if (index <= this.tickPosition) {
          this.tickPosition -= 1;
        }
        this.entityList.remove(index);
      }
    }
    


    b(entity);
  }
  
  public void addIWorldAccess(IWorldAccess iworldaccess) {
    this.u.add(iworldaccess);
  }
  
  public List getCubes(Entity entity, AxisAlignedBB axisalignedbb) {
    this.L.clear();
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    

    int ystart = k - 1 < 0 ? 0 : k - 1;
    for (int chunkx = i >> 4; chunkx <= j - 1 >> 4; chunkx++)
    {
      int cx = chunkx << 4;
      for (int chunkz = i1 >> 4; chunkz <= j1 - 1 >> 4; chunkz++)
      {
        Chunk chunk = getChunkIfLoaded(chunkx, chunkz);
        if (chunk == null)
        {

          if (entity.loadChunks) {
            chunk = entity.world.chunkProvider.getChunkAt(chunkx, chunkz);
          } else {
            entity.inUnloadedChunk = true;
            continue;
          }
        }
        
        int cz = chunkz << 4;
        
        int xstart = i < cx ? cx : i;
        int xend = j < cx + 16 ? j : cx + 16;
        int zstart = i1 < cz ? cz : i1;
        int zend = j1 < cz + 16 ? j1 : cz + 16;
        
        for (int x = xstart; x < xend; x++)
        {
          for (int z = zstart; z < zend; z++)
          {
            for (int y = ystart; y < l; y++)
            {
              Block block = chunk.getType(x - cx, y, z - cz);
              if (block != null)
              {

                if ((entity.world.paperSpigotConfig.fallingBlocksCollideWithSigns) && (((entity instanceof EntityTNTPrimed)) || ((entity instanceof EntityFallingBlock))) && (((block instanceof BlockSign)) || ((block instanceof BlockFenceGate)) || ((block instanceof BlockTorch)) || ((block instanceof BlockButtonAbstract)) || ((block instanceof BlockLever)) || ((block instanceof BlockTripwireHook)) || ((block instanceof BlockTripwire))))
                {
                  AxisAlignedBB aabb = AxisAlignedBB.a(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
                  if (axisalignedbb.b(aabb)) this.L.add(aabb);
                } else {
                  block.a(this, x, y, z, axisalignedbb, this.L, entity);
                }
              }
            }
          }
        }
      }
    }
    


    if ((entity instanceof EntityItem)) { return this.L;
    }
    double d0 = 0.25D;
    List list = getEntities(entity, axisalignedbb.grow(d0, d0, d0));
    
    for (int j2 = 0; j2 < list.size(); j2++) {
      AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).J();
      
      if ((axisalignedbb1 != null) && (axisalignedbb1.b(axisalignedbb))) {
        this.L.add(axisalignedbb1);
      }
      
      axisalignedbb1 = entity.h((Entity)list.get(j2));
      if ((axisalignedbb1 != null) && (axisalignedbb1.b(axisalignedbb))) {
        this.L.add(axisalignedbb1);
      }
    }
    
    return this.L;
  }
  
  public List a(AxisAlignedBB axisalignedbb) {
    this.L.clear();
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = i1; l1 < j1; l1++) {
        if (isLoaded(k1, 64, l1)) {
          for (int i2 = k - 1; i2 < l; i2++) {
            Block block;
            Block block;
            if ((k1 >= -30000000) && (k1 < 30000000) && (l1 >= -30000000) && (l1 < 30000000)) {
              block = getType(k1, i2, l1);
            } else {
              block = Blocks.BEDROCK;
            }
            
            block.a(this, k1, i2, l1, axisalignedbb, this.L, (Entity)null);
          }
        }
      }
    }
    
    return this.L;
  }
  
  public int a(float f) {
    float f1 = c(f);
    float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.5F);
    
    if (f2 < 0.0F) {
      f2 = 0.0F;
    }
    
    if (f2 > 1.0F) {
      f2 = 1.0F;
    }
    
    f2 = 1.0F - f2;
    f2 = (float)(f2 * (1.0D - j(f) * 5.0F / 16.0D));
    f2 = (float)(f2 * (1.0D - h(f) * 5.0F / 16.0D));
    f2 = 1.0F - f2;
    return (int)(f2 * 11.0F);
  }
  
  public float c(float f) {
    return this.worldProvider.a(this.worldData.getDayTime(), f);
  }
  
  public float y() {
    return WorldProvider.a[this.worldProvider.a(this.worldData.getDayTime())];
  }
  
  public float d(float f) {
    float f1 = c(f);
    
    return f1 * 3.1415927F * 2.0F;
  }
  
  public int h(int i, int j) {
    return getChunkAtWorldCoords(i, j).d(i & 0xF, j & 0xF);
  }
  
  public int i(int i, int j) {
    Chunk chunk = getChunkAtWorldCoords(i, j);
    int k = chunk.h() + 15;
    
    i &= 0xF;
    
    for (j &= 0xF; k > 0; k--) {
      Block block = chunk.getType(i, k, j);
      
      if ((block.getMaterial().isSolid()) && (block.getMaterial() != Material.LEAVES)) {
        return k + 1;
      }
    }
    
    return -1;
  }
  
  public void a(int i, int j, int k, Block block, int l) {}
  
  public void a(int i, int j, int k, Block block, int l, int i1) {}
  
  public void b(int i, int j, int k, Block block, int l, int i1) {}
  
  public void tickEntities() {
    this.methodProfiler.a("entities");
    this.methodProfiler.a("global");
    





    for (int i = 0; i < this.i.size(); i++) {
      Entity entity = (Entity)this.i.get(i);
      
      if (entity != null)
      {

        try
        {

          entity.ticksLived += 1;
          entity.h();
        } catch (Throwable throwable) {
          CrashReport crashreport = CrashReport.a(throwable, "Ticking entity");
          CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
          if (entity == null) {
            crashreportsystemdetails.a("Entity", "~~NULL~~");
          } else {
            entity.a(crashreportsystemdetails);
          }
          
          throw new ReportedException(crashreport);
        }
        
        if (entity.dead) {
          this.i.remove(i--);
        }
      }
    }
    this.methodProfiler.c("remove");
    this.entityList.removeAll(this.f);
    



    for (i = 0; i < this.f.size(); i++) {
      Entity entity = (Entity)this.f.get(i);
      int j = entity.ah;
      int k = entity.aj;
      if ((entity.ag) && (isChunkLoaded(j, k))) {
        getChunkAt(j, k).b(entity);
      }
    }
    
    for (i = 0; i < this.f.size(); i++) {
      b((Entity)this.f.get(i));
    }
    
    this.f.clear();
    this.methodProfiler.c("regular");
    
    ActivationRange.activateEntities(this);
    this.timings.entityTick.startTiming();
    this.guardEntityList = true;
    
    for (this.tickPosition = 0; this.tickPosition < this.entityList.size(); this.tickPosition += 1) {
      Entity entity = (Entity)this.entityList.get(this.tickPosition);
      if (entity.vehicle != null) {
        if ((entity.vehicle.dead) || (entity.vehicle.passenger != entity))
        {


          entity.vehicle.passenger = null;
          entity.vehicle = null;
        }
      } else {
        this.methodProfiler.a("tick");
        if (!entity.dead) {
          try {
            SpigotTimings.tickEntityTimer.startTiming();
            playerJoinedWorld(entity);
            SpigotTimings.tickEntityTimer.stopTiming();
          }
          catch (Throwable throwable1) {
            SpigotTimings.tickEntityTimer.stopTiming();
            System.err.println("Entity threw exception at " + entity.world.getWorld().getName() + ":" + entity.locX + "," + entity.locY + "," + entity.locZ);
            throwable1.printStackTrace();
            entity.dead = true;
            continue;
          }
        }
        







        this.methodProfiler.b();
        this.methodProfiler.a("remove");
        if (entity.dead) {
          int j = entity.ah;
          int k = entity.aj;
          if ((entity.ag) && (isChunkLoaded(j, k))) {
            getChunkAt(j, k).b(entity);
          }
          
          this.guardEntityList = false;
          this.entityList.remove(this.tickPosition--);
          this.guardEntityList = true;
          b(entity);
        }
        
        this.methodProfiler.b();
      } }
    this.guardEntityList = false;
    
    this.timings.entityTick.stopTiming();
    this.methodProfiler.c("blockEntities");
    this.timings.tileEntityTick.startTiming();
    this.M = true;
    
    if (!this.b.isEmpty()) {
      this.tileEntityList.removeAll(this.b);
      this.b.clear();
    }
    

    initializeHoppers();
    Iterator iterator = this.tileEntityList.iterator();
    
    while (iterator.hasNext()) {
      TileEntity tileentity = (TileEntity)iterator.next();
      
      if (tileentity == null) {
        getServer().getLogger().severe("Spigot has detected a null entity and has removed it, preventing a crash");
        iterator.remove();

      }
      else
      {
        if ((!tileentity.r()) && (tileentity.o()) && (isLoaded(tileentity.x, tileentity.y, tileentity.z))) {
          try {
            tileentity.tickTimer.startTiming();
            tileentity.h();
            tileentity.tickTimer.stopTiming();
          }
          catch (Throwable throwable2) {
            tileentity.tickTimer.stopTiming();
            System.err.println("TileEntity threw exception at " + tileentity.world.getWorld().getName() + ":" + tileentity.x + "," + tileentity.y + "," + tileentity.z);
            throwable2.printStackTrace();
            iterator.remove(); }
          continue;
        }
        








        if (tileentity.r()) {
          iterator.remove();
          if (isChunkLoaded(tileentity.x >> 4, tileentity.z >> 4)) {
            Chunk chunk = getChunkAt(tileentity.x >> 4, tileentity.z >> 4);
            
            if (chunk != null) {
              chunk.f(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
            }
          }
        }
      }
    }
    this.timings.tileEntityTick.stopTiming();
    this.timings.tileEntityPending.startTiming();
    this.M = false;
    






    this.methodProfiler.c("pendingBlockEntities");
    if (!this.a.isEmpty()) {
      for (int l = 0; l < this.a.size(); l++) {
        TileEntity tileentity1 = (TileEntity)this.a.get(l);
        
        if (!tileentity1.r())
        {





          if (isChunkLoaded(tileentity1.x >> 4, tileentity1.z >> 4)) {
            Chunk chunk1 = getChunkAt(tileentity1.x >> 4, tileentity1.z >> 4);
            
            if (chunk1 != null) {
              chunk1.a(tileentity1.x & 0xF, tileentity1.y, tileentity1.z & 0xF, tileentity1);
              
              if (!this.tileEntityList.contains(tileentity1)) {
                this.tileEntityList.add(tileentity1);
              }
            }
          }
          

          notify(tileentity1.x, tileentity1.y, tileentity1.z);
        }
      }
      
      this.a.clear();
    }
    
    this.timings.tileEntityPending.stopTiming();
    this.methodProfiler.b();
    this.methodProfiler.b();
  }
  
  public void a(Collection collection) {
    if (this.M) {
      this.a.addAll(collection);
    } else {
      this.tileEntityList.addAll(collection);
    }
  }
  
  public void playerJoinedWorld(Entity entity) {
    entityJoinedWorld(entity, true);
  }
  
  public void entityJoinedWorld(Entity entity, boolean flag) {
    int i = MathHelper.floor(entity.locX);
    int j = MathHelper.floor(entity.locZ);
    byte b0 = 32;
    

    if (!ActivationRange.checkIfActive(entity)) {
      entity.ticksLived += 1;
      entity.inactiveTick();
      
      if ((!isChunkLoaded(i, j)) && ((((entity instanceof EntityEnderPearl)) && (this.paperSpigotConfig.removeUnloadedEnderPearls)) || (((entity instanceof EntityFallingBlock)) && (this.paperSpigotConfig.removeUnloadedFallingBlocks)) || (((entity instanceof EntityTNTPrimed)) && (this.paperSpigotConfig.removeUnloadedTNTEntities))))
      {

        entity.inUnloadedChunk = true;
        entity.die();
      }
    }
    else {
      entity.tickTimer.startTiming();
      
      entity.S = entity.locX;
      entity.T = entity.locY;
      entity.U = entity.locZ;
      entity.lastYaw = entity.yaw;
      entity.lastPitch = entity.pitch;
      if ((flag) && (entity.ag)) {
        entity.ticksLived += 1;
        if (entity.vehicle != null) {
          entity.ab();
        } else {
          entity.h();
        }
      }
      
      this.methodProfiler.a("chunkCheck");
      if ((Double.isNaN(entity.locX)) || (Double.isInfinite(entity.locX))) {
        entity.locX = entity.S;
      }
      
      if ((Double.isNaN(entity.locY)) || (Double.isInfinite(entity.locY))) {
        entity.locY = entity.T;
      }
      
      if ((Double.isNaN(entity.locZ)) || (Double.isInfinite(entity.locZ))) {
        entity.locZ = entity.U;
      }
      
      if ((Double.isNaN(entity.pitch)) || (Double.isInfinite(entity.pitch))) {
        entity.pitch = entity.lastPitch;
      }
      
      if ((Double.isNaN(entity.yaw)) || (Double.isInfinite(entity.yaw))) {
        entity.yaw = entity.lastYaw;
      }
      
      int k = MathHelper.floor(entity.locX / 16.0D);
      int l = MathHelper.floor(entity.locY / 16.0D);
      int i1 = MathHelper.floor(entity.locZ / 16.0D);
      
      if ((!entity.ag) || (entity.ah != k) || (entity.ai != l) || (entity.aj != i1)) {
        if (entity.loadChunks) entity.loadChunks();
        if ((entity.ag) && (isChunkLoaded(entity.ah, entity.aj))) {
          getChunkAt(entity.ah, entity.aj).a(entity, entity.ai);
        }
        
        if (isChunkLoaded(k, i1)) {
          entity.ag = true;
          getChunkAt(k, i1).a(entity);
        } else {
          entity.ag = false;
        }
      }
      
      this.methodProfiler.b();
      if ((flag) && (entity.ag) && (entity.passenger != null)) {
        if ((!entity.passenger.dead) && (entity.passenger.vehicle == entity)) {
          playerJoinedWorld(entity.passenger);
        } else {
          entity.passenger.vehicle = null;
          entity.passenger = null;
        }
      }
      entity.tickTimer.stopTiming();
    }
  }
  
  public boolean b(AxisAlignedBB axisalignedbb) {
    return a(axisalignedbb, (Entity)null);
  }
  
  public boolean a(AxisAlignedBB axisalignedbb, Entity entity) {
    List list = getEntities((Entity)null, axisalignedbb);
    
    for (int i = 0; i < list.size(); i++) {
      Entity entity1 = (Entity)list.get(i);
      
      if ((!(entity instanceof EntityPlayer)) || (!(entity1 instanceof EntityPlayer)) || 
        (((EntityPlayer)entity).getBukkitEntity().canSee(((EntityPlayer)entity1).getBukkitEntity())))
      {




        if ((!entity1.dead) && (entity1.k) && (entity1 != entity)) {
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean c(AxisAlignedBB axisalignedbb) {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    if (axisalignedbb.a < 0.0D) {
      i--;
    }
    
    if (axisalignedbb.b < 0.0D) {
      k--;
    }
    
    if (axisalignedbb.c < 0.0D) {
      i1--;
    }
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = k; l1 < l; l1++) {
        for (int i2 = i1; i2 < j1; i2++) {
          Block block = getType(k1, l1, i2);
          
          if (block.getMaterial() != Material.AIR) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  public boolean containsLiquid(AxisAlignedBB axisalignedbb) {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    if (axisalignedbb.a < 0.0D) {
      i--;
    }
    
    if (axisalignedbb.b < 0.0D) {
      k--;
    }
    
    if (axisalignedbb.c < 0.0D) {
      i1--;
    }
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = k; l1 < l; l1++) {
        for (int i2 = i1; i2 < j1; i2++) {
          Block block = getType(k1, l1, i2);
          
          if (block.getMaterial().isLiquid()) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  public boolean e(AxisAlignedBB axisalignedbb) {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    if (b(i, k, i1, j, l, j1)) {
      for (int k1 = i; k1 < j; k1++) {
        for (int l1 = k; l1 < l; l1++) {
          for (int i2 = i1; i2 < j1; i2++) {
            Block block = getType(k1, l1, i2);
            
            if ((block == Blocks.FIRE) || (block == Blocks.LAVA) || (block == Blocks.STATIONARY_LAVA)) {
              return true;
            }
          }
        }
      }
    }
    
    return false;
  }
  
  public boolean a(AxisAlignedBB axisalignedbb, Material material, Entity entity) {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    if (!b(i, k, i1, j, l, j1)) {
      return false;
    }
    boolean flag = false;
    Vec3D vec3d = Vec3D.a(0.0D, 0.0D, 0.0D);
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = k; l1 < l; l1++) {
        for (int i2 = i1; i2 < j1; i2++) {
          Block block = getType(k1, l1, i2);
          
          if (block.getMaterial() == material) {
            double d0 = l1 + 1 - BlockFluids.b(getData(k1, l1, i2));
            
            if (l >= d0) {
              flag = true;
              block.a(this, k1, l1, i2, entity, vec3d);
            }
          }
        }
      }
    }
    
    if ((vec3d.b() > 0.0D) && (entity.aC())) {
      vec3d = vec3d.a();
      double d1 = 0.014D;
      
      entity.motX += vec3d.a * d1;
      entity.motY += vec3d.b * d1;
      entity.motZ += vec3d.c * d1;
    }
    
    return flag;
  }
  
  public boolean a(AxisAlignedBB axisalignedbb, Material material)
  {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = k; l1 < l; l1++) {
        for (int i2 = i1; i2 < j1; i2++) {
          if (getType(k1, l1, i2).getMaterial() == material) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  public boolean b(AxisAlignedBB axisalignedbb, Material material) {
    int i = MathHelper.floor(axisalignedbb.a);
    int j = MathHelper.floor(axisalignedbb.d + 1.0D);
    int k = MathHelper.floor(axisalignedbb.b);
    int l = MathHelper.floor(axisalignedbb.e + 1.0D);
    int i1 = MathHelper.floor(axisalignedbb.c);
    int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
    
    for (int k1 = i; k1 < j; k1++) {
      for (int l1 = k; l1 < l; l1++) {
        for (int i2 = i1; i2 < j1; i2++) {
          Block block = getType(k1, l1, i2);
          
          if (block.getMaterial() == material) {
            int j2 = getData(k1, l1, i2);
            double d0 = l1 + 1;
            
            if (j2 < 8) {
              d0 = l1 + 1 - j2 / 8.0D;
            }
            
            if (d0 >= axisalignedbb.b) {
              return true;
            }
          }
        }
      }
    }
    
    return false;
  }
  
  public Explosion explode(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
    return createExplosion(entity, d0, d1, d2, f, false, flag);
  }
  
  public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
    Explosion explosion = new Explosion(this, entity, d0, d1, d2, f);
    
    explosion.a = flag;
    explosion.b = flag1;
    explosion.a();
    explosion.a(true);
    return explosion;
  }
  
  public float a(Vec3D vec3d, AxisAlignedBB axisalignedbb) {
    double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
    double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
    double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
    


    double xOffset = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
    double zOffset = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
    

    if ((d0 >= 0.0D) && (d1 >= 0.0D) && (d2 >= 0.0D)) {
      int i = 0;
      int j = 0;
      
      Vec3D vec3d2 = Vec3D.a(0.0D, 0.0D, 0.0D);
      for (float f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
        for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
          for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
            double d3 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
            double d4 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f1;
            double d5 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f2;
            
            if (a(vec3d2.b(xOffset + d3, d4, zOffset + d5), vec3d) == null) {
              i++;
            }
            
            j++;
          }
        }
      }
      
      return i / j;
    }
    return 0.0F;
  }
  
  public boolean douseFire(EntityHuman entityhuman, int i, int j, int k, int l)
  {
    if (l == 0) {
      j--;
    }
    
    if (l == 1) {
      j++;
    }
    
    if (l == 2) {
      k--;
    }
    
    if (l == 3) {
      k++;
    }
    
    if (l == 4) {
      i--;
    }
    
    if (l == 5) {
      i++;
    }
    
    if (getType(i, j, k) == Blocks.FIRE) {
      a(entityhuman, 1004, i, j, k, 0);
      setAir(i, j, k);
      return true;
    }
    return false;
  }
  
  public TileEntity getTileEntity(int i, int j, int k)
  {
    if ((j >= 0) && (j < 256)) {
      TileEntity tileentity = null;
      


      if (this.M) {
        for (int l = 0; l < this.a.size(); l++) {
          TileEntity tileentity1 = (TileEntity)this.a.get(l);
          if ((!tileentity1.r()) && (tileentity1.x == i) && (tileentity1.y == j) && (tileentity1.z == k)) {
            tileentity = tileentity1;
            break;
          }
        }
      }
      
      if (tileentity == null) {
        Chunk chunk = getChunkAt(i >> 4, k >> 4);
        
        if (chunk != null) {
          tileentity = chunk.e(i & 0xF, j, k & 0xF);
        }
      }
      
      if (tileentity == null) {
        for (int l = 0; l < this.a.size(); l++) {
          TileEntity tileentity1 = (TileEntity)this.a.get(l);
          if ((!tileentity1.r()) && (tileentity1.x == i) && (tileentity1.y == j) && (tileentity1.z == k)) {
            tileentity = tileentity1;
            break;
          }
        }
      }
      
      return tileentity;
    }
    return null;
  }
  
  public void setTileEntity(int i, int j, int k, TileEntity tileentity)
  {
    if ((tileentity != null) && (!tileentity.r())) {
      if (this.M) {
        tileentity.x = i;
        tileentity.y = j;
        tileentity.z = k;
        Iterator iterator = this.a.iterator();
        
        while (iterator.hasNext()) {
          TileEntity tileentity1 = (TileEntity)iterator.next();
          
          if ((tileentity1.x == i) && (tileentity1.y == j) && (tileentity1.z == k)) {
            tileentity1.s();
            iterator.remove();
          }
        }
        
        tileentity.a(this);
        this.a.add(tileentity);
      } else {
        this.tileEntityList.add(tileentity);
        Chunk chunk = getChunkAt(i >> 4, k >> 4);
        
        if (chunk != null) {
          chunk.a(i & 0xF, j, k & 0xF, tileentity);
        }
      }
    }
  }
  
  public void p(int i, int j, int k) {
    TileEntity tileentity = getTileEntity(i, j, k);
    
    if ((tileentity != null) && (this.M)) {
      tileentity.s();
      this.a.remove(tileentity);
    } else {
      if (tileentity != null) {
        this.a.remove(tileentity);
        this.tileEntityList.remove(tileentity);
      }
      
      Chunk chunk = getChunkAt(i >> 4, k >> 4);
      
      if (chunk != null) {
        chunk.f(i & 0xF, j, k & 0xF);
      }
    }
  }
  
  public void a(TileEntity tileentity) {
    this.b.add(tileentity);
  }
  
  public boolean q(int i, int j, int k) {
    AxisAlignedBB axisalignedbb = getType(i, j, k).a(this, i, j, k);
    
    return (axisalignedbb != null) && (axisalignedbb.a() >= 1.0D);
  }
  
  public static boolean a(IBlockAccess iblockaccess, int i, int j, int k) {
    Block block = iblockaccess.getType(i, j, k);
    int l = iblockaccess.getData(i, j, k);
    
    return (block.getMaterial().k()) && (block.d());
  }
  
  public boolean c(int i, int j, int k, boolean flag) {
    if ((i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000)) {
      Chunk chunk = this.chunkProvider.getOrCreateChunk(i >> 4, k >> 4);
      
      if ((chunk != null) && (!chunk.isEmpty())) {
        Block block = getType(i, j, k);
        
        return (block.getMaterial().k()) && (block.d());
      }
      return flag;
    }
    
    return flag;
  }
  
  public void B()
  {
    int i = a(1.0F);
    
    if (i != this.j) {
      this.j = i;
    }
  }
  
  public void setSpawnFlags(boolean flag, boolean flag1) {
    this.allowMonsters = flag;
    this.allowAnimals = flag1;
  }
  
  public void doTick() {
    o();
  }
  
  private void a() {
    if (this.worldData.hasStorm()) {
      this.n = 1.0F;
      if (this.worldData.isThundering()) {
        this.p = 1.0F;
      }
    }
  }
  
  protected void o() {
    if ((!this.worldProvider.g) && 
      (!this.isStatic)) {
      int i = this.worldData.getThunderDuration();
      
      if (i <= 0) {
        if (this.worldData.isThundering()) {
          this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
        } else {
          this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
        }
      } else {
        i--;
        this.worldData.setThunderDuration(i);
        if (i <= 0)
        {
          ThunderChangeEvent thunder = new ThunderChangeEvent(getWorld(), !this.worldData.isThundering());
          getServer().getPluginManager().callEvent(thunder);
          if (!thunder.isCancelled()) {
            this.worldData.setThundering(!this.worldData.isThundering());
          }
        }
      }
      

      this.o = this.p;
      if (this.worldData.isThundering()) {
        this.p = ((float)(this.p + 0.01D));
      } else {
        this.p = ((float)(this.p - 0.01D));
      }
      
      this.p = MathHelper.a(this.p, 0.0F, 1.0F);
      int j = this.worldData.getWeatherDuration();
      
      if (j <= 0) {
        if (this.worldData.hasStorm()) {
          this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
        } else {
          this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
        }
      } else {
        j--;
        this.worldData.setWeatherDuration(j);
        if (j <= 0)
        {
          WeatherChangeEvent weather = new WeatherChangeEvent(getWorld(), !this.worldData.hasStorm());
          getServer().getPluginManager().callEvent(weather);
          
          if (!weather.isCancelled()) {
            this.worldData.setStorm(!this.worldData.hasStorm());
          }
        }
      }
      

      this.m = this.n;
      if (this.worldData.hasStorm()) {
        this.n = ((float)(this.n + 0.01D));
      } else {
        this.n = ((float)(this.n - 0.01D));
      }
      
      this.n = MathHelper.a(this.n, 0.0F, 1.0F);
    }
  }
  

  protected void C()
  {
    this.methodProfiler.a("buildList");
    







    int optimalChunks = this.spigotConfig.chunksPerTick;
    
    if ((optimalChunks <= 0) || (this.players.isEmpty()))
    {
      return;
    }
    
    int chunksPerPlayer = Math.min(200, Math.max(1, (int)((optimalChunks - this.players.size()) / this.players.size() + 0.5D)));
    int randRange = 3 + chunksPerPlayer / 30;
    
    randRange = randRange > this.chunkTickRadius ? this.chunkTickRadius : randRange;
    
    this.growthOdds = (this.modifiedOdds = Math.max(35.0F, Math.min(100.0F, (chunksPerPlayer + 1) * 100.0F / 15.0F)));
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman entityhuman = (EntityHuman)this.players.get(i);
      int j = MathHelper.floor(entityhuman.locX / 16.0D);
      int k = MathHelper.floor(entityhuman.locZ / 16.0D);
      int l = p();
      

      long key = chunkToKey(j, k);
      int existingPlayers = Math.max(0, this.chunkTickList.get(key));
      this.chunkTickList.put(key, (short)(existingPlayers + 1));
      

      for (int chunk = 0; chunk < chunksPerPlayer; chunk++)
      {
        int dx = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
        int dz = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
        long hash = chunkToKey(dx + j, dz + k);
        if ((!this.chunkTickList.contains(hash)) && (isChunkLoaded(dx + j, dz + k)))
        {
          this.chunkTickList.put(hash, (short)-1);
        }
      }
    }
    

    this.methodProfiler.b();
    if (this.K > 0) {
      this.K -= 1;
    }
    
    this.methodProfiler.a("playerCheckLight");
    if ((this.spigotConfig.randomLightUpdates) && (!this.players.isEmpty())) {
      i = this.random.nextInt(this.players.size());
      EntityHuman entityhuman = (EntityHuman)this.players.get(i);
      int j = MathHelper.floor(entityhuman.locX) + this.random.nextInt(11) - 5;
      int k = MathHelper.floor(entityhuman.locY) + this.random.nextInt(11) - 5;
      int l = MathHelper.floor(entityhuman.locZ) + this.random.nextInt(11) - 5;
      t(j, k, l);
    }
    
    this.methodProfiler.b();
  }
  
  protected abstract int p();
  
  protected void a(int i, int j, Chunk chunk) {
    this.methodProfiler.c("moodSound");
    if ((!this.paperSpigotConfig.disableMoodSounds) && (this.K == 0) && (!this.isStatic)) {
      this.k = (this.k * 3 + 1013904223);
      int k = this.k >> 2;
      int l = k & 0xF;
      int i1 = k >> 8 & 0xF;
      int j1 = k >> 16 & 0xFF;
      Block block = chunk.getType(l, j1, i1);
      
      l += i;
      i1 += j;
      if ((block.getMaterial() == Material.AIR) && (j(l, j1, i1) <= this.random.nextInt(8)) && (b(EnumSkyBlock.SKY, l, j1, i1) <= 0)) {
        EntityHuman entityhuman = findNearbyPlayer(l + 0.5D, j1 + 0.5D, i1 + 0.5D, 8.0D);
        
        if ((entityhuman != null) && (entityhuman.e(l + 0.5D, j1 + 0.5D, i1 + 0.5D) > 4.0D)) {
          makeSound(l + 0.5D, j1 + 0.5D, i1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
          this.K = (this.random.nextInt(12000) + 6000);
        }
      }
    }
    
    this.methodProfiler.c("checkLight");
    chunk.o();
  }
  
  protected void g() {
    C();
  }
  
  public boolean r(int i, int j, int k) {
    return d(i, j, k, false);
  }
  
  public boolean s(int i, int j, int k) {
    return d(i, j, k, true);
  }
  
  public boolean d(int i, int j, int k, boolean flag) {
    BiomeBase biomebase = getBiome(i, k);
    float f = biomebase.a(i, j, k);
    
    if (f > 0.15F) {
      return false;
    }
    if ((j >= 0) && (j < 256) && (b(EnumSkyBlock.BLOCK, i, j, k) < 10)) {
      Block block = getType(i, j, k);
      
      if (((block == Blocks.STATIONARY_WATER) || (block == Blocks.WATER)) && (getData(i, j, k) == 0)) {
        if (!flag) {
          return true;
        }
        
        boolean flag1 = true;
        
        if ((flag1) && (getType(i - 1, j, k).getMaterial() != Material.WATER)) {
          flag1 = false;
        }
        
        if ((flag1) && (getType(i + 1, j, k).getMaterial() != Material.WATER)) {
          flag1 = false;
        }
        
        if ((flag1) && (getType(i, j, k - 1).getMaterial() != Material.WATER)) {
          flag1 = false;
        }
        
        if ((flag1) && (getType(i, j, k + 1).getMaterial() != Material.WATER)) {
          flag1 = false;
        }
        
        if (!flag1) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean e(int i, int j, int k, boolean flag)
  {
    BiomeBase biomebase = getBiome(i, k);
    float f = biomebase.a(i, j, k);
    
    if (f > 0.15F)
      return false;
    if (!flag) {
      return true;
    }
    if ((j >= 0) && (j < 256) && (b(EnumSkyBlock.BLOCK, i, j, k) < 10)) {
      Block block = getType(i, j, k);
      
      if ((block.getMaterial() == Material.AIR) && (Blocks.SNOW.canPlace(this, i, j, k))) {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean t(int i, int j, int k)
  {
    boolean flag = false;
    
    if (!this.worldProvider.g) {
      flag |= updateLight(EnumSkyBlock.SKY, i, j, k);
    }
    
    flag |= updateLight(EnumSkyBlock.BLOCK, i, j, k);
    return flag;
  }
  
  private int a(int i, int j, int k, EnumSkyBlock enumskyblock) {
    if ((enumskyblock == EnumSkyBlock.SKY) && (i(i, j, k))) {
      return 15;
    }
    Block block = getType(i, j, k);
    int l = enumskyblock == EnumSkyBlock.SKY ? 0 : block.m();
    int i1 = block.k();
    
    if ((i1 >= 15) && (block.m() > 0)) {
      i1 = 1;
    }
    
    if (i1 < 1) {
      i1 = 1;
    }
    
    if (i1 >= 15)
      return 0;
    if (l >= 14) {
      return l;
    }
    for (int j1 = 0; j1 < 6; j1++) {
      int k1 = i + Facing.b[j1];
      int l1 = j + Facing.c[j1];
      int i2 = k + Facing.d[j1];
      int j2 = b(enumskyblock, k1, l1, i2) - i1;
      
      if (j2 > l) {
        l = j2;
      }
      
      if (l >= 14) {
        return l;
      }
    }
    
    return l;
  }
  



  public boolean c(EnumSkyBlock enumskyblock, int i, int j, int k, Chunk chunk, List<Chunk> neighbors)
  {
    if (chunk == null)
    {
      return false;
    }
    int l = 0;
    int i1 = 0;
    
    this.methodProfiler.a("getBrightness");
    int j1 = b(enumskyblock, i, j, k);
    int k1 = a(i, j, k, enumskyblock);
    









    if (k1 > j1) {
      this.I[(i1++)] = 133152;
    } else if (k1 < j1) {
      this.I[(i1++)] = (0x20820 | j1 << 18);
      
      while (l < i1) {
        int l1 = this.I[(l++)];
        int i2 = (l1 & 0x3F) - 32 + i;
        int j2 = (l1 >> 6 & 0x3F) - 32 + j;
        int k2 = (l1 >> 12 & 0x3F) - 32 + k;
        int l2 = l1 >> 18 & 0xF;
        int i3 = b(enumskyblock, i2, j2, k2);
        if (i3 == l2) {
          b(enumskyblock, i2, j2, k2, 0);
          if (l2 > 0) {
            int j3 = MathHelper.a(i2 - i);
            int l3 = MathHelper.a(j2 - j);
            int k3 = MathHelper.a(k2 - k);
            if (j3 + l3 + k3 < 17) {
              for (int i4 = 0; i4 < 6; i4++) {
                int j4 = i2 + Facing.b[i4];
                int k4 = j2 + Facing.c[i4];
                int l4 = k2 + Facing.d[i4];
                int i5 = Math.max(1, getType(j4, k4, l4).k());
                
                i3 = b(enumskyblock, j4, k4, l4);
                if ((i3 == l2 - i5) && (i1 < this.I.length)) {
                  this.I[(i1++)] = (j4 - i + 32 | k4 - j + 32 << 6 | l4 - k + 32 << 12 | l2 - i5 << 18);
                }
              }
            }
          }
        }
      }
      
      l = 0;
    }
    
    this.methodProfiler.b();
    this.methodProfiler.a("checkedPosition < toCheckCount");
    boolean flag;
    while (l < i1) {
      int l1 = this.I[(l++)];
      int i2 = (l1 & 0x3F) - 32 + i;
      int j2 = (l1 >> 6 & 0x3F) - 32 + j;
      int k2 = (l1 >> 12 & 0x3F) - 32 + k;
      int l2 = b(enumskyblock, i2, j2, k2);
      int i3 = a(i2, j2, k2, enumskyblock);
      if (i3 != l2) {
        b(enumskyblock, i2, j2, k2, i3);
        if (i3 > l2) {
          int j3 = Math.abs(i2 - i);
          int l3 = Math.abs(j2 - j);
          int k3 = Math.abs(k2 - k);
          flag = i1 < this.I.length - 6;
          
          if ((j3 + l3 + k3 < 17) && (flag)) {
            if (b(enumskyblock, i2 - 1, j2, k2) < i3) {
              this.I[(i1++)] = (i2 - 1 - i + 32 + (j2 - j + 32 << 6) + (k2 - k + 32 << 12));
            }
            
            if (b(enumskyblock, i2 + 1, j2, k2) < i3) {
              this.I[(i1++)] = (i2 + 1 - i + 32 + (j2 - j + 32 << 6) + (k2 - k + 32 << 12));
            }
            
            if (b(enumskyblock, i2, j2 - 1, k2) < i3) {
              this.I[(i1++)] = (i2 - i + 32 + (j2 - 1 - j + 32 << 6) + (k2 - k + 32 << 12));
            }
            
            if (b(enumskyblock, i2, j2 + 1, k2) < i3) {
              this.I[(i1++)] = (i2 - i + 32 + (j2 + 1 - j + 32 << 6) + (k2 - k + 32 << 12));
            }
            
            if (b(enumskyblock, i2, j2, k2 - 1) < i3) {
              this.I[(i1++)] = (i2 - i + 32 + (j2 - j + 32 << 6) + (k2 - 1 - k + 32 << 12));
            }
            
            if (b(enumskyblock, i2, j2, k2 + 1) < i3) {
              this.I[(i1++)] = (i2 - i + 32 + (j2 - j + 32 << 6) + (k2 + 1 - k + 32 << 12));
            }
          }
        }
      }
    }
    

    if (chunk.world.paperSpigotConfig.useAsyncLighting) {
      chunk.pendingLightUpdates.decrementAndGet();
      if (neighbors != null) {
        for (Chunk neighbor : neighbors) {
          neighbor.pendingLightUpdates.decrementAndGet();
        }
      }
    }
    
    this.methodProfiler.b();
    return true;
  }
  



  public boolean updateLight(final EnumSkyBlock enumskyblock, final int x, final int y, final int z)
  {
    final Chunk chunk = getChunkIfLoaded(x >> 4, z >> 4);
    if ((chunk == null) || (!chunk.areNeighborsLoaded(1))) {
      return false;
    }
    
    if (!chunk.world.paperSpigotConfig.useAsyncLighting) {
      return c(enumskyblock, x, y, z, chunk, null);
    }
    
    chunk.pendingLightUpdates.incrementAndGet();
    chunk.lightUpdateTime = chunk.world.getTime();
    
    final List<Chunk> neighbors = new ArrayList();
    for (int cx = (x >> 4) - 1; cx <= (x >> 4) + 1; cx++) {
      for (int cz = (z >> 4) - 1; cz <= (z >> 4) + 1; cz++) {
        if ((cx != x >> 4) && (cz != z >> 4)) {
          Chunk neighbor = getChunkIfLoaded(cx, cz);
          if (neighbor != null) {
            neighbor.pendingLightUpdates.incrementAndGet();
            neighbor.lightUpdateTime = chunk.world.getTime();
            neighbors.add(neighbor);
          }
        }
      }
    }
    
    if (!Bukkit.isPrimaryThread()) {
      return c(enumskyblock, x, y, z, chunk, neighbors);
    }
    
    this.lightingExecutor.submit(new Runnable()
    {
      public void run() {
        World.this.c(enumskyblock, x, y, z, chunk, neighbors);
      }
    });
    return true;
  }
  
  public boolean a(boolean flag) {
    return false;
  }
  
  public List a(Chunk chunk, boolean flag) {
    return null;
  }
  
  public List getEntities(Entity entity, AxisAlignedBB axisalignedbb) {
    return getEntities(entity, axisalignedbb, (IEntitySelector)null);
  }
  
  public List getEntities(Entity entity, AxisAlignedBB axisalignedbb, IEntitySelector ientityselector) {
    ArrayList arraylist = new ArrayList();
    int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
    int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
    int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
    int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
    
    for (int i1 = i; i1 <= j; i1++) {
      for (int j1 = k; j1 <= l; j1++) {
        if (isChunkLoaded(i1, j1)) {
          getChunkAt(i1, j1).a(entity, axisalignedbb, arraylist, ientityselector);
        }
      }
    }
    
    return arraylist;
  }
  
  public List a(Class oclass, AxisAlignedBB axisalignedbb) {
    return a(oclass, axisalignedbb, (IEntitySelector)null);
  }
  
  public List a(Class oclass, AxisAlignedBB axisalignedbb, IEntitySelector ientityselector) {
    int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
    int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
    int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
    int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
    ArrayList arraylist = new ArrayList();
    
    for (int i1 = i; i1 <= j; i1++) {
      for (int j1 = k; j1 <= l; j1++) {
        if (isChunkLoaded(i1, j1)) {
          getChunkAt(i1, j1).a(oclass, axisalignedbb, arraylist, ientityselector);
        }
      }
    }
    
    return arraylist;
  }
  
  public Entity a(Class oclass, AxisAlignedBB axisalignedbb, Entity entity) {
    List list = a(oclass, axisalignedbb);
    Entity entity1 = null;
    double d0 = Double.MAX_VALUE;
    
    for (int i = 0; i < list.size(); i++) {
      Entity entity2 = (Entity)list.get(i);
      
      if (entity2 != entity) {
        double d1 = entity.f(entity2);
        
        if (d1 <= d0) {
          entity1 = entity2;
          d0 = d1;
        }
      }
    }
    
    return entity1;
  }
  
  public abstract Entity getEntity(int paramInt);
  
  public void b(int i, int j, int k, TileEntity tileentity) {
    if (isLoaded(i, j, k)) {
      getChunkAtWorldCoords(i, k).e();
    }
  }
  
  public int a(Class oclass) {
    int i = 0;
    
    for (int j = 0; j < this.entityList.size(); j++) {
      Entity entity = (Entity)this.entityList.get(j);
      

      if ((entity instanceof EntityInsentient)) {
        EntityInsentient entityinsentient = (EntityInsentient)entity;
        if ((entityinsentient.isTypeNotPersistent()) && (entityinsentient.isPersistent())) {}



      }
      else if (oclass.isAssignableFrom(entity.getClass()))
      {

        i++;
      }
    }
    
    return i;
  }
  
  public void a(List list) {
    AsyncCatcher.catchOp("entity world add");
    

    Entity entity = null;
    
    for (int i = 0; i < list.size(); i++) {
      entity = (Entity)list.get(i);
      if (entity != null)
      {

        this.entityList.add(entity);
        
        a((Entity)list.get(i));
      }
    }
  }
  
  public void b(List list) { this.f.addAll(list); }
  
  public boolean mayPlace(Block block, int i, int j, int k, boolean flag, int l, Entity entity, ItemStack itemstack)
  {
    Block block1 = getType(i, j, k);
    AxisAlignedBB axisalignedbb = flag ? null : block.a(this, i, j, k);
    

    boolean defaultReturn = (axisalignedbb == null) || (a(axisalignedbb, entity));
    

    BlockCanBuildEvent event = new BlockCanBuildEvent(getWorld().getBlockAt(i, j, k), CraftMagicNumbers.getId(block), defaultReturn);
    getServer().getPluginManager().callEvent(event);
    
    return event.isBuildable();
  }
  
  public PathEntity findPath(Entity entity, Entity entity1, float f, boolean flag, boolean flag1, boolean flag2, boolean flag3)
  {
    this.methodProfiler.a("pathfind");
    int i = MathHelper.floor(entity.locX);
    int j = MathHelper.floor(entity.locY + 1.0D);
    int k = MathHelper.floor(entity.locZ);
    int l = (int)(f + 16.0F);
    int i1 = i - l;
    int j1 = j - l;
    int k1 = k - l;
    int l1 = i + l;
    int i2 = j + l;
    int j2 = k + l;
    ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2, 0);
    PathEntity pathentity = new Pathfinder(chunkcache, flag, flag1, flag2, flag3).a(entity, entity1, f);
    
    this.methodProfiler.b();
    return pathentity;
  }
  
  public PathEntity a(Entity entity, int i, int j, int k, float f, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
    this.methodProfiler.a("pathfind");
    int l = MathHelper.floor(entity.locX);
    int i1 = MathHelper.floor(entity.locY);
    int j1 = MathHelper.floor(entity.locZ);
    int k1 = (int)(f + 8.0F);
    int l1 = l - k1;
    int i2 = i1 - k1;
    int j2 = j1 - k1;
    int k2 = l + k1;
    int l2 = i1 + k1;
    int i3 = j1 + k1;
    ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3, 0);
    PathEntity pathentity = new Pathfinder(chunkcache, flag, flag1, flag2, flag3).a(entity, i, j, k, f);
    
    this.methodProfiler.b();
    return pathentity;
  }
  
  public int getBlockPower(int i, int j, int k, int l) {
    return getType(i, j, k).c(this, i, j, k, l);
  }
  
  public int getBlockPower(int i, int j, int k) {
    byte b0 = 0;
    int l = Math.max(b0, getBlockPower(i, j - 1, k, 0));
    
    if (l >= 15) {
      return l;
    }
    l = Math.max(l, getBlockPower(i, j + 1, k, 1));
    if (l >= 15) {
      return l;
    }
    l = Math.max(l, getBlockPower(i, j, k - 1, 2));
    if (l >= 15) {
      return l;
    }
    l = Math.max(l, getBlockPower(i, j, k + 1, 3));
    if (l >= 15) {
      return l;
    }
    l = Math.max(l, getBlockPower(i - 1, j, k, 4));
    if (l >= 15) {
      return l;
    }
    l = Math.max(l, getBlockPower(i + 1, j, k, 5));
    return l >= 15 ? l : l;
  }
  




  public boolean isBlockFacePowered(int i, int j, int k, int l)
  {
    return getBlockFacePower(i, j, k, l) > 0;
  }
  
  public int getBlockFacePower(int i, int j, int k, int l) {
    return getType(i, j, k).r() ? getBlockPower(i, j, k) : getType(i, j, k).b(this, i, j, k, l);
  }
  
  public boolean isBlockIndirectlyPowered(int i, int j, int k) {
    return getBlockFacePower(i, j - 1, k, 0) > 0;
  }
  
  public int getHighestNeighborSignal(int i, int j, int k) {
    int l = 0;
    
    for (int i1 = 0; i1 < 6; i1++) {
      int j1 = getBlockFacePower(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1], i1);
      
      if (j1 >= 15) {
        return 15;
      }
      
      if (j1 > l) {
        l = j1;
      }
    }
    
    return l;
  }
  
  public EntityHuman findNearbyPlayer(Entity entity, double d0) {
    return findNearbyPlayer(entity.locX, entity.locY, entity.locZ, d0);
  }
  
  public EntityHuman findNearbyPlayer(double d0, double d1, double d2, double d3) {
    double d4 = -1.0D;
    EntityHuman entityhuman = null;
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
      
      if ((entityhuman1 != null) && (!entityhuman1.dead))
      {


        double d5 = entityhuman1.e(d0, d1, d2);
        
        if (((d3 < 0.0D) || (d5 < d3 * d3)) && ((d4 == -1.0D) || (d5 < d4))) {
          d4 = d5;
          entityhuman = entityhuman1;
        }
      }
    }
    return entityhuman;
  }
  
  public EntityHuman findNearbyVulnerablePlayer(Entity entity, double d0) {
    return findNearbyVulnerablePlayer(entity.locX, entity.locY, entity.locZ, d0);
  }
  
  public EntityHuman findNearbyVulnerablePlayer(double d0, double d1, double d2, double d3) {
    double d4 = -1.0D;
    EntityHuman entityhuman = null;
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
      
      if ((entityhuman1 != null) && (!entityhuman1.dead))
      {



        if ((!entityhuman1.abilities.isInvulnerable) && (entityhuman1.isAlive())) {
          double d5 = entityhuman1.e(d0, d1, d2);
          double d6 = d3;
          
          if (entityhuman1.isSneaking()) {
            d6 = d3 * 0.800000011920929D;
          }
          
          if (entityhuman1.isInvisible()) {
            float f = entityhuman1.bE();
            
            if (f < 0.1F) {
              f = 0.1F;
            }
            
            d6 *= 0.7F * f;
          }
          
          if (((d3 < 0.0D) || (d5 < d6 * d6)) && ((d4 == -1.0D) || (d5 < d4))) {
            d4 = d5;
            entityhuman = entityhuman1;
          }
        }
      }
    }
    return entityhuman;
  }
  
  public EntityHuman findNearbyPlayerWhoAffectsSpawning(Entity entity, double radius)
  {
    return findNearbyPlayerWhoAffectsSpawning(entity.locX, entity.locY, entity.locZ, radius);
  }
  
  public EntityHuman findNearbyPlayerWhoAffectsSpawning(double x, double y, double z, double radius) {
    double nearestRadius = -1.0D;
    EntityHuman entityHuman = null;
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman nearestPlayer = (EntityHuman)this.players.get(i);
      
      if ((nearestPlayer != null) && (!nearestPlayer.dead) && (nearestPlayer.affectsSpawning))
      {


        double distance = nearestPlayer.e(x, y, z);
        
        if (((radius < 0.0D) || (distance < radius * radius)) && ((nearestRadius == -1.0D) || (distance < nearestRadius))) {
          nearestRadius = distance;
          entityHuman = nearestPlayer;
        }
      }
    }
    return entityHuman;
  }
  
  public EntityHuman a(String s)
  {
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman entityhuman = (EntityHuman)this.players.get(i);
      
      if (s.equals(entityhuman.getName())) {
        return entityhuman;
      }
    }
    
    return null;
  }
  
  public EntityHuman a(UUID uuid) {
    for (int i = 0; i < this.players.size(); i++) {
      EntityHuman entityhuman = (EntityHuman)this.players.get(i);
      
      if (uuid.equals(entityhuman.getUniqueID())) {
        return entityhuman;
      }
    }
    
    return null;
  }
  
  public void G() throws ExceptionWorldConflict {
    this.dataManager.checkSession();
  }
  
  public long getSeed() {
    return this.worldData.getSeed();
  }
  
  public long getTime() {
    return this.worldData.getTime();
  }
  
  public long getDayTime() {
    return this.worldData.getDayTime();
  }
  
  public void setDayTime(long i) {
    this.worldData.setDayTime(i);
  }
  
  public ChunkCoordinates getSpawn() {
    return new ChunkCoordinates(this.worldData.c(), this.worldData.d(), this.worldData.e());
  }
  
  public void x(int i, int j, int k) {
    this.worldData.setSpawn(i, j, k);
  }
  
  public boolean a(EntityHuman entityhuman, int i, int j, int k) {
    return true;
  }
  
  public void broadcastEntityEffect(Entity entity, byte b0) {}
  
  public IChunkProvider L() {
    return this.chunkProvider;
  }
  
  public void playBlockAction(int i, int j, int k, Block block, int l, int i1) {
    block.a(this, i, j, k, l, i1);
  }
  
  public IDataManager getDataManager() {
    return this.dataManager;
  }
  
  public WorldData getWorldData() {
    return this.worldData;
  }
  
  public GameRules getGameRules() {
    return this.worldData.getGameRules();
  }
  

  public void everyoneSleeping() {}
  

  public void checkSleepStatus()
  {
    if (!this.isStatic) {
      everyoneSleeping();
    }
  }
  
  public float h(float f)
  {
    return (this.o + (this.p - this.o) * f) * j(f);
  }
  
  public float j(float f) {
    return this.m + (this.n - this.m) * f;
  }
  
  public boolean P() {
    return h(1.0F) > 0.9D;
  }
  
  public boolean Q() {
    return j(1.0F) > 0.2D;
  }
  
  public boolean isRainingAt(int i, int j, int k) {
    if (!Q())
      return false;
    if (!i(i, j, k))
      return false;
    if (h(i, k) > j) {
      return false;
    }
    BiomeBase biomebase = getBiome(i, k);
    
    return e(i, j, k, false) ? false : biomebase.d() ? false : biomebase.e();
  }
  
  public boolean z(int i, int j, int k)
  {
    BiomeBase biomebase = getBiome(i, k);
    
    return biomebase.f();
  }
  
  public void a(String s, PersistentBase persistentbase) {
    this.worldMaps.a(s, persistentbase);
  }
  
  public PersistentBase a(Class oclass, String s) {
    return this.worldMaps.get(oclass, s);
  }
  
  public int b(String s) {
    return this.worldMaps.a(s);
  }
  
  public void b(int i, int j, int k, int l, int i1) {
    for (int j1 = 0; j1 < this.u.size(); j1++) {
      ((IWorldAccess)this.u.get(j1)).a(i, j, k, l, i1);
    }
  }
  
  public void triggerEffect(int i, int j, int k, int l, int i1) {
    a((EntityHuman)null, i, j, k, l, i1);
  }
  
  public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
    try {
      for (int j1 = 0; j1 < this.u.size(); j1++) {
        ((IWorldAccess)this.u.get(j1)).a(entityhuman, i, j, k, l, i1);
      }
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
      
      crashreportsystemdetails.a("Block coordinates", CrashReportSystemDetails.a(j, k, l));
      crashreportsystemdetails.a("Event source", entityhuman);
      crashreportsystemdetails.a("Event type", Integer.valueOf(i));
      crashreportsystemdetails.a("Event data", Integer.valueOf(i1));
      throw new ReportedException(crashreport);
    }
  }
  
  public int getHeight() {
    return 256;
  }
  
  public int S() {
    return this.worldProvider.g ? 128 : 256;
  }
  
  public Random A(int i, int j, int k) {
    long l = i * 341873128712L + j * 132897987541L + getWorldData().getSeed() + k;
    
    this.random.setSeed(l);
    return this.random;
  }
  
  public ChunkPosition b(String s, int i, int j, int k) {
    return L().findNearestMapFeature(this, s, i, j, k);
  }
  
  public CrashReportSystemDetails a(CrashReport crashreport) {
    CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
    
    crashreportsystemdetails.a("Level name", this.worldData == null ? "????" : this.worldData.getName());
    crashreportsystemdetails.a("All players", new CrashReportPlayers(this));
    crashreportsystemdetails.a("Chunk stats", new CrashReportChunkStats(this));
    try
    {
      this.worldData.a(crashreportsystemdetails);
    } catch (Throwable throwable) {
      crashreportsystemdetails.a("Level Data Unobtainable", throwable);
    }
    
    return crashreportsystemdetails;
  }
  
  public void d(int i, int j, int k, int l, int i1) {
    for (int j1 = 0; j1 < this.u.size(); j1++) {
      IWorldAccess iworldaccess = (IWorldAccess)this.u.get(j1);
      
      iworldaccess.b(i, j, k, l, i1);
    }
  }
  
  public Calendar V() {
    if (getTime() % 600L == 0L) {
      this.J.setTimeInMillis(MinecraftServer.ar());
    }
    
    return this.J;
  }
  
  public Scoreboard getScoreboard() {
    return this.scoreboard;
  }
  
  public void updateAdjacentComparators(int i, int j, int k, Block block) {
    for (int l = 0; l < 4; l++) {
      int i1 = i + Direction.a[l];
      int j1 = k + Direction.b[l];
      Block block1 = getType(i1, j, j1);
      
      if (Blocks.REDSTONE_COMPARATOR_OFF.e(block1)) {
        block1.doPhysics(this, i1, j, j1, block);
      } else if (block1.r()) {
        i1 += Direction.a[l];
        j1 += Direction.b[l];
        Block block2 = getType(i1, j, j1);
        
        if (Blocks.REDSTONE_COMPARATOR_OFF.e(block2)) {
          block2.doPhysics(this, i1, j, j1, block);
        }
      }
    }
  }
  
  public float b(double d0, double d1, double d2) {
    return B(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
  }
  
  public float B(int i, int j, int k) {
    float f = 0.0F;
    boolean flag = this.difficulty == EnumDifficulty.HARD;
    
    if (isLoaded(i, j, k)) {
      float f1 = y();
      
      f += MathHelper.a((float)getChunkAtWorldCoords(i, k).s / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
      f += f1 * 0.25F;
    }
    
    if ((this.difficulty == EnumDifficulty.EASY) || (this.difficulty == EnumDifficulty.PEACEFUL)) {
      f *= this.difficulty.a() / 2.0F;
    }
    
    return MathHelper.a(f, 0.0F, flag ? 1.5F : 1.0F);
  }
  
  public void X() {
    Iterator iterator = this.u.iterator();
    
    while (iterator.hasNext()) {
      IWorldAccess iworldaccess = (IWorldAccess)iterator.next();
      
      iworldaccess.b();
    }
  }
}
