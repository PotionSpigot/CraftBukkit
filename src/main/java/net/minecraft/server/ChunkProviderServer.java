package net.minecraft.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.WorldTimingsHandler;
import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;
import org.bukkit.craftbukkit.v1_7_R4.util.LongHashSet;
import org.bukkit.craftbukkit.v1_7_R4.util.LongObjectHashMap;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.PluginManager;
import org.github.paperspigot.PaperSpigotWorldConfig;
import org.spigotmc.CustomTimingsHandler;

public class ChunkProviderServer implements IChunkProvider
{
  private static final Logger b = ;
  
  public LongHashSet unloadQueue = new LongHashSet();
  public Chunk emptyChunk;
  public IChunkProvider chunkProvider;
  private IChunkLoader f;
  public boolean forceChunkLoad = false;
  public LongObjectHashMap<Chunk> chunks = new LongObjectHashMap();
  public WorldServer world;
  
  public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
  {
    this.emptyChunk = new EmptyChunk(worldserver, 0, 0);
    this.world = worldserver;
    this.f = ichunkloader;
    this.chunkProvider = ichunkprovider;
  }
  
  public boolean isChunkLoaded(int i, int j) {
    return this.chunks.containsKey(LongHash.toLong(i, j));
  }
  

  public Collection a()
  {
    return this.chunks.values();
  }
  

  public void queueUnload(int i, int j)
  {
    Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
    if ((chunk != null) && (chunk.world.paperSpigotConfig.useAsyncLighting) && ((chunk.pendingLightUpdates.get() > 0) || (chunk.world.getTime() - chunk.lightUpdateTime < 20L))) {
      return;
    }
    

    if (chunk != null) {
      for (List<Entity> entities : chunk.entitySlices) {
        for (Entity entity : entities) {
          if (entity.loadChunks) {
            return;
          }
        }
      }
    }
    
    if (this.world.worldProvider.e()) {
      ChunkCoordinates chunkcoordinates = this.world.getSpawn();
      int k = i * 16 + 8 - chunkcoordinates.x;
      int l = j * 16 + 8 - chunkcoordinates.z;
      short short1 = 128;
      

      if ((k < -short1) || (k > short1) || (l < -short1) || (l > short1) || (!this.world.keepSpawnInMemory)) {
        this.unloadQueue.add(i, j);
        
        Chunk c = (Chunk)this.chunks.get(LongHash.toLong(i, j));
        if (c != null) {
          c.mustSave = true;
        }
      }
    }
    else
    {
      this.unloadQueue.add(i, j);
      
      Chunk c = (Chunk)this.chunks.get(LongHash.toLong(i, j));
      if (c != null) {
        c.mustSave = true;
      }
    }
  }
  
  public void b()
  {
    Iterator iterator = this.chunks.values().iterator();
    
    while (iterator.hasNext()) {
      Chunk chunk = (Chunk)iterator.next();
      
      queueUnload(chunk.locX, chunk.locZ);
    }
  }
  
  public Chunk getChunkIfLoaded(int x, int z)
  {
    return (Chunk)this.chunks.get(LongHash.toLong(x, z));
  }
  
  public Chunk getChunkAt(int i, int j) {
    return getChunkAt(i, j, null);
  }
  
  public Chunk getChunkAt(int i, int j, Runnable runnable) {
    this.unloadQueue.remove(i, j);
    Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
    ChunkRegionLoader loader = null;
    
    if ((this.f instanceof ChunkRegionLoader)) {
      loader = (ChunkRegionLoader)this.f;
    }
    

    if ((chunk == null) && (loader != null) && (loader.chunkExists(this.world, i, j))) {
      if (runnable != null) {
        ChunkIOExecutor.queueChunkLoad(this.world, loader, this, i, j, runnable);
        return null;
      }
      chunk = ChunkIOExecutor.syncChunkLoad(this.world, loader, this, i, j);
    }
    else if (chunk == null) {
      chunk = originalGetChunkAt(i, j);
    }
    

    if (runnable != null) {
      runnable.run();
    }
    
    return chunk;
  }
  
  public Chunk originalGetChunkAt(int i, int j) {
    this.unloadQueue.remove(i, j);
    Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
    boolean newChunk = false;
    
    if (chunk == null) {
      this.world.timings.syncChunkLoadTimer.startTiming();
      chunk = loadChunk(i, j);
      if (chunk == null) {
        if (this.chunkProvider == null) {
          chunk = this.emptyChunk;
        } else {
          try {
            chunk = this.chunkProvider.getOrCreateChunk(i, j);
          } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");
            
            crashreportsystemdetails.a("Location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
            crashreportsystemdetails.a("Position hash", Long.valueOf(LongHash.toLong(i, j)));
            crashreportsystemdetails.a("Generator", this.chunkProvider.getName());
            throw new ReportedException(crashreport);
          }
        }
        newChunk = true;
      }
      
      this.chunks.put(LongHash.toLong(i, j), chunk);
      chunk.addEntities();
      

      Server server = this.world.getServer();
      if (server != null)
      {




        server.getPluginManager().callEvent(new ChunkLoadEvent(chunk.bukkitChunk, newChunk));
      }
      

      for (int x = -2; x < 3; x++) {
        for (int z = -2; z < 3; z++) {
          if ((x != 0) || (z != 0))
          {


            Chunk neighbor = getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
            if (neighbor != null) {
              neighbor.setNeighborLoaded(-x, -z);
              chunk.setNeighborLoaded(x, z);
            }
          }
        }
      }
      chunk.loadNearby(this, this, i, j);
      this.world.timings.syncChunkLoadTimer.stopTiming();
    }
    
    return chunk;
  }
  
  public Chunk getOrCreateChunk(int i, int j)
  {
    Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(i, j));
    
    chunk = chunk == null ? getChunkAt(i, j) : (!this.world.isLoading) && (!this.forceChunkLoad) ? this.emptyChunk : chunk;
    if (chunk == this.emptyChunk) return chunk;
    if ((i != chunk.locX) || (j != chunk.locZ)) {
      b.error("Chunk (" + chunk.locX + ", " + chunk.locZ + ") stored at  (" + i + ", " + j + ") in world '" + this.world.getWorld().getName() + "'");
      b.error(chunk.getClass().getName());
      Throwable ex = new Throwable();
      ex.fillInStackTrace();
      ex.printStackTrace();
    }
    return chunk;
  }
  
  public Chunk loadChunk(int i, int j)
  {
    if (this.f == null) {
      return null;
    }
    try {
      Chunk chunk = this.f.a(this.world, i, j);
      
      if (chunk != null) {
        chunk.lastSaved = this.world.getTime();
        if (this.chunkProvider != null) {
          this.world.timings.syncChunkLoadStructuresTimer.startTiming();
          this.chunkProvider.recreateStructures(i, j);
          this.world.timings.syncChunkLoadStructuresTimer.stopTiming();
        }
      }
      
      return chunk;
    } catch (Exception exception) {
      b.error("Couldn't load chunk", exception); }
    return null;
  }
  

  public void saveChunkNOP(Chunk chunk)
  {
    if (this.f != null) {
      try {
        this.f.b(this.world, chunk);
      } catch (Exception exception) {
        b.error("Couldn't save entities", exception);
      }
    }
  }
  
  public void saveChunk(Chunk chunk) {
    if (this.f != null) {
      try {
        chunk.lastSaved = this.world.getTime();
        this.f.a(this.world, chunk);
      }
      catch (Exception ioexception) {
        b.error("Couldn't save chunk", ioexception);
      }
    }
  }
  



  public void getChunkAt(IChunkProvider ichunkprovider, int i, int j)
  {
    Chunk chunk = getOrCreateChunk(i, j);
    
    if (!chunk.done) {
      chunk.p();
      if (this.chunkProvider != null) {
        this.chunkProvider.getChunkAt(ichunkprovider, i, j);
        

        BlockSand.instaFall = true;
        Random random = new Random();
        random.setSeed(this.world.getSeed());
        long xRand = random.nextLong() / 2L * 2L + 1L;
        long zRand = random.nextLong() / 2L * 2L + 1L;
        random.setSeed(i * xRand + j * zRand ^ this.world.getSeed());
        
        org.bukkit.World world = this.world.getWorld();
        if (world != null) {
          this.world.populating = true;
          try {
            for (BlockPopulator populator : world.getPopulators()) {
              populator.populate(world, random, chunk.bukkitChunk);
            }
          } finally {
            this.world.populating = false;
          }
        }
        BlockSand.instaFall = false;
        this.world.getServer().getPluginManager().callEvent(new ChunkPopulateEvent(chunk.bukkitChunk));
        

        chunk.e();
      }
    }
  }
  
  public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
    int i = 0;
    
    Iterator iterator = this.chunks.values().iterator();
    
    while (iterator.hasNext()) {
      Chunk chunk = (Chunk)iterator.next();
      

      if (flag) {
        saveChunkNOP(chunk);
      }
      
      if (chunk.a(flag)) {
        saveChunk(chunk);
        chunk.n = false;
        i++;
        if ((i == 24) && (!flag)) {
          return false;
        }
      }
    }
    
    return true;
  }
  
  public void c() {
    if (this.f != null) {
      this.f.b();
    }
  }
  
  public boolean unloadChunks() {
    if (!this.world.savingDisabled)
    {
      Server server = this.world.getServer();
      for (int i = 0; (i < 100) && (!this.unloadQueue.isEmpty()); i++) {
        long chunkcoordinates = this.unloadQueue.popFirst();
        Chunk chunk = (Chunk)this.chunks.get(chunkcoordinates);
        if (chunk != null)
        {
          ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
          server.getPluginManager().callEvent(event);
          if (!event.isCancelled()) {
            if (chunk != null) {
              chunk.removeEntities();
              saveChunk(chunk);
              saveChunkNOP(chunk);
              this.chunks.remove(chunkcoordinates);
            }
            




            for (int x = -2; x < 3; x++) {
              for (int z = -2; z < 3; z++) {
                if ((x != 0) || (z != 0))
                {


                  Chunk neighbor = getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
                  if (neighbor != null) {
                    neighbor.setNeighborUnloaded(-x, -z);
                    chunk.setNeighborUnloaded(x, z);
                  }
                }
              }
            }
          }
        }
      }
      if (this.f != null) {
        this.f.a();
      }
    }
    
    return this.chunkProvider.unloadChunks();
  }
  
  public boolean canSave() {
    return !this.world.savingDisabled;
  }
  
  public String getName()
  {
    return "ServerChunkCache: " + this.chunks.values().size() + " Drop: " + this.unloadQueue.size();
  }
  
  public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
    return this.chunkProvider.getMobsFor(enumcreaturetype, i, j, k);
  }
  
  public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
    return this.chunkProvider.findNearestMapFeature(world, s, i, j, k);
  }
  
  public int getLoadedChunks()
  {
    return this.chunks.size();
  }
  
  public void recreateStructures(int i, int j) {}
}
