package net.minecraft.server;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.WorldTimingsHandler;
import org.spigotmc.CustomTimingsHandler;

public class ChunkRegionLoader implements IChunkLoader, IAsyncChunkSaver
{
  private LinkedHashMap<ChunkCoordIntPair, PendingChunkToSave> pendingSaves = new LinkedHashMap();
  private static final Logger a = ;
  private List b = new ArrayList();
  private Set c = new java.util.HashSet();
  private Object d = new Object();
  private final File e;
  
  public ChunkRegionLoader(File file1) {
    this.e = file1;
  }
  
  public boolean chunkExists(World world, int i, int j)
  {
    ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
    
    synchronized (this.d)
    {
      if (this.pendingSaves.containsKey(chunkcoordintpair)) {
        return true;
      }
    }
    

    return RegionFileCache.a(this.e, i, j).chunkExists(i & 0x1F, j & 0x1F);
  }
  

  public Chunk a(World world, int i, int j)
  {
    world.timings.syncChunkLoadDataTimer.startTiming();
    Object[] data = loadChunk(world, i, j);
    world.timings.syncChunkLoadDataTimer.stopTiming();
    if (data != null) {
      Chunk chunk = (Chunk)data[0];
      NBTTagCompound nbttagcompound = (NBTTagCompound)data[1];
      loadEntities(chunk, nbttagcompound.getCompound("Level"), world);
      return chunk;
    }
    
    return null;
  }
  
  public Object[] loadChunk(World world, int i, int j)
  {
    NBTTagCompound nbttagcompound = null;
    ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
    Object object = this.d;
    
    synchronized (this.d)
    {
      PendingChunkToSave pendingchunktosave = (PendingChunkToSave)this.pendingSaves.get(chunkcoordintpair);
      if (pendingchunktosave != null) {
        nbttagcompound = pendingchunktosave.b;
      }
    }
    

    if (nbttagcompound == null) {
      java.io.DataInputStream datainputstream = RegionFileCache.c(this.e, i, j);
      
      if (datainputstream == null) {
        return null;
      }
      
      nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
    }
    
    return a(world, i, j, nbttagcompound);
  }
  
  protected Object[] a(World world, int i, int j, NBTTagCompound nbttagcompound) {
    if (!nbttagcompound.hasKeyOfType("Level", 10)) {
      a.error("Chunk file at " + i + "," + j + " is missing level data, skipping");
      return null; }
    if (!nbttagcompound.getCompound("Level").hasKeyOfType("Sections", 9)) {
      a.error("Chunk file at " + i + "," + j + " is missing block data, skipping");
      return null;
    }
    Chunk chunk = a(world, nbttagcompound.getCompound("Level"));
    
    if (!chunk.a(i, j)) {
      a.error("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.locX + ", " + chunk.locZ + ")");
      nbttagcompound.getCompound("Level").setInt("xPos", i);
      nbttagcompound.getCompound("Level").setInt("zPos", j);
      

      NBTTagList tileEntities = nbttagcompound.getCompound("Level").getList("TileEntities", 10);
      if (tileEntities != null) {
        for (int te = 0; te < tileEntities.size(); te++) {
          NBTTagCompound tileEntity = tileEntities.get(te);
          int x = tileEntity.getInt("x") - chunk.locX * 16;
          int z = tileEntity.getInt("z") - chunk.locZ * 16;
          tileEntity.setInt("x", i * 16 + x);
          tileEntity.setInt("z", j * 16 + z);
        }
      }
      
      chunk = a(world, nbttagcompound.getCompound("Level"));
    }
    

    Object[] data = new Object[2];
    data[0] = chunk;
    data[1] = nbttagcompound;
    return data;
  }
  

  public void a(World world, Chunk chunk)
  {
    try
    {
      world.G();
    } catch (ExceptionWorldConflict ex) {
      ex.printStackTrace();
    }
    
    try
    {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      
      nbttagcompound.set("Level", nbttagcompound1);
      a(chunk, world, nbttagcompound1);
      a(chunk.l(), nbttagcompound);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
  
  protected void a(ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
    Object object = this.d;
    
    synchronized (this.d)
    {
      if (this.pendingSaves.put(chunkcoordintpair, new PendingChunkToSave(chunkcoordintpair, nbttagcompound)) != null) {
        return;
      }
      
      FileIOThread.a.a(this);
    }
  }
  
  public boolean c() {
    PendingChunkToSave pendingchunktosave = null;
    Object object = this.d;
    
    synchronized (this.d)
    {
      if (this.pendingSaves.isEmpty()) {
        return false;
      }
      
      pendingchunktosave = (PendingChunkToSave)this.pendingSaves.values().iterator().next();
      this.pendingSaves.remove(pendingchunktosave.a);
    }
    

    if (pendingchunktosave != null) {
      try {
        a(pendingchunktosave);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    
    return true;
  }
  
  public void a(PendingChunkToSave pendingchunktosave) throws java.io.IOException {
    DataOutputStream dataoutputstream = RegionFileCache.d(this.e, pendingchunktosave.a.x, pendingchunktosave.a.z);
    
    NBTCompressedStreamTools.a(pendingchunktosave.b, dataoutputstream);
    dataoutputstream.close();
  }
  
  public void b(World world, Chunk chunk) {}
  
  public void a() {}
  
  public void b() {
    while (c()) {}
  }
  

  private void a(Chunk chunk, World world, NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setByte("V", (byte)1);
    nbttagcompound.setInt("xPos", chunk.locX);
    nbttagcompound.setInt("zPos", chunk.locZ);
    nbttagcompound.setLong("LastUpdate", world.getTime());
    nbttagcompound.setIntArray("HeightMap", chunk.heightMap);
    nbttagcompound.setBoolean("TerrainPopulated", chunk.done);
    nbttagcompound.setBoolean("LightPopulated", chunk.lit);
    nbttagcompound.setLong("InhabitedTime", chunk.s);
    ChunkSection[] achunksection = chunk.getSections();
    NBTTagList nbttaglist = new NBTTagList();
    boolean flag = !world.worldProvider.g;
    ChunkSection[] achunksection1 = achunksection;
    int i = achunksection.length;
    


    for (int j = 0; j < i; j++) {
      ChunkSection chunksection = achunksection1[j];
      
      if (chunksection != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Y", (byte)(chunksection.getYPosition() >> 4 & 0xFF));
        nbttagcompound1.setByteArray("Blocks", chunksection.getIdArray());
        if (chunksection.getExtendedIdArray() != null) {
          nbttagcompound1.setByteArray("Add", chunksection.getExtendedIdArray().a);
        }
        
        nbttagcompound1.setByteArray("Data", chunksection.getDataArray().a);
        nbttagcompound1.setByteArray("BlockLight", chunksection.getEmittedLightArray().a);
        if (flag) {
          nbttagcompound1.setByteArray("SkyLight", chunksection.getSkyLightArray().a);
        } else {
          nbttagcompound1.setByteArray("SkyLight", new byte[chunksection.getEmittedLightArray().a.length]);
        }
        
        nbttaglist.add(nbttagcompound1);
      }
    }
    
    nbttagcompound.set("Sections", nbttaglist);
    nbttagcompound.setByteArray("Biomes", chunk.m());
    chunk.o = false;
    NBTTagList nbttaglist1 = new NBTTagList();
    


    for (i = 0; i < chunk.entitySlices.length; i++) {
      Iterator iterator = chunk.entitySlices[i].iterator();
      
      while (iterator.hasNext()) {
        Entity entity = (Entity)iterator.next();
        
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        if (entity.d(nbttagcompound1)) {
          chunk.o = true;
          nbttaglist1.add(nbttagcompound1);
        }
      }
    }
    
    nbttagcompound.set("Entities", nbttaglist1);
    NBTTagList nbttaglist2 = new NBTTagList();
    
    Iterator iterator = chunk.tileEntities.values().iterator();
    
    while (iterator.hasNext()) {
      TileEntity tileentity = (TileEntity)iterator.next();
      
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      tileentity.b(nbttagcompound1);
      nbttaglist2.add(nbttagcompound1);
    }
    
    nbttagcompound.set("TileEntities", nbttaglist2);
    List list = world.a(chunk, false);
    
    if (list != null) {
      long k = world.getTime();
      NBTTagList nbttaglist3 = new NBTTagList();
      Iterator iterator1 = list.iterator();
      
      while (iterator1.hasNext()) {
        NextTickListEntry nextticklistentry = (NextTickListEntry)iterator1.next();
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        
        nbttagcompound2.setInt("i", Block.getId(nextticklistentry.a()));
        nbttagcompound2.setInt("x", nextticklistentry.a);
        nbttagcompound2.setInt("y", nextticklistentry.b);
        nbttagcompound2.setInt("z", nextticklistentry.c);
        nbttagcompound2.setInt("t", (int)(nextticklistentry.d - k));
        nbttagcompound2.setInt("p", nextticklistentry.e);
        nbttaglist3.add(nbttagcompound2);
      }
      
      nbttagcompound.set("TileTicks", nbttaglist3);
    }
  }
  
  private Chunk a(World world, NBTTagCompound nbttagcompound) {
    int i = nbttagcompound.getInt("xPos");
    int j = nbttagcompound.getInt("zPos");
    Chunk chunk = new Chunk(world, i, j);
    
    chunk.heightMap = nbttagcompound.getIntArray("HeightMap");
    chunk.done = nbttagcompound.getBoolean("TerrainPopulated");
    chunk.lit = nbttagcompound.getBoolean("LightPopulated");
    chunk.s = nbttagcompound.getLong("InhabitedTime");
    NBTTagList nbttaglist = nbttagcompound.getList("Sections", 10);
    byte b0 = 16;
    ChunkSection[] achunksection = new ChunkSection[b0];
    boolean flag = !world.worldProvider.g;
    
    for (int k = 0; k < nbttaglist.size(); k++) {
      NBTTagCompound nbttagcompound1 = nbttaglist.get(k);
      byte b1 = nbttagcompound1.getByte("Y");
      ChunkSection chunksection = new ChunkSection(b1 << 4, flag);
      
      chunksection.setIdArray(nbttagcompound1.getByteArray("Blocks"));
      if (nbttagcompound1.hasKeyOfType("Add", 7)) {
        chunksection.setExtendedIdArray(new NibbleArray(nbttagcompound1.getByteArray("Add"), 4));
      }
      
      chunksection.setDataArray(new NibbleArray(nbttagcompound1.getByteArray("Data"), 4));
      chunksection.setEmittedLightArray(new NibbleArray(nbttagcompound1.getByteArray("BlockLight"), 4));
      if (flag) {
        chunksection.setSkyLightArray(new NibbleArray(nbttagcompound1.getByteArray("SkyLight"), 4));
      }
      
      chunksection.recalcBlockCounts();
      achunksection[b1] = chunksection;
    }
    
    chunk.a(achunksection);
    if (nbttagcompound.hasKeyOfType("Biomes", 7)) {
      chunk.a(nbttagcompound.getByteArray("Biomes"));
    }
    

    return chunk;
  }
  
  public void loadEntities(Chunk chunk, NBTTagCompound nbttagcompound, World world)
  {
    world.timings.syncChunkLoadEntitiesTimer.startTiming();
    NBTTagList nbttaglist1 = nbttagcompound.getList("Entities", 10);
    
    if (nbttaglist1 != null) {
      for (int l = 0; l < nbttaglist1.size(); l++) {
        NBTTagCompound nbttagcompound2 = nbttaglist1.get(l);
        Entity entity = EntityTypes.a(nbttagcompound2, world);
        
        chunk.o = true;
        if (entity != null) {
          chunk.a(entity);
          Entity entity1 = entity;
          
          for (NBTTagCompound nbttagcompound3 = nbttagcompound2; nbttagcompound3.hasKeyOfType("Riding", 10); nbttagcompound3 = nbttagcompound3.getCompound("Riding")) {
            Entity entity2 = EntityTypes.a(nbttagcompound3.getCompound("Riding"), world);
            
            if (entity2 != null) {
              chunk.a(entity2);
              entity1.mount(entity2);
            }
            
            entity1 = entity2;
          }
        }
      }
    }
    world.timings.syncChunkLoadEntitiesTimer.stopTiming();
    world.timings.syncChunkLoadTileEntitiesTimer.startTiming();
    NBTTagList nbttaglist2 = nbttagcompound.getList("TileEntities", 10);
    
    if (nbttaglist2 != null) {
      for (int i1 = 0; i1 < nbttaglist2.size(); i1++) {
        NBTTagCompound nbttagcompound4 = nbttaglist2.get(i1);
        TileEntity tileentity = TileEntity.c(nbttagcompound4);
        
        if (tileentity != null) {
          chunk.a(tileentity);
        }
      }
    }
    world.timings.syncChunkLoadTileEntitiesTimer.stopTiming();
    world.timings.syncChunkLoadTileTicksTimer.startTiming();
    
    if (nbttagcompound.hasKeyOfType("TileTicks", 9)) {
      NBTTagList nbttaglist3 = nbttagcompound.getList("TileTicks", 10);
      
      if (nbttaglist3 != null) {
        for (int j1 = 0; j1 < nbttaglist3.size(); j1++) {
          NBTTagCompound nbttagcompound5 = nbttaglist3.get(j1);
          
          world.b(nbttagcompound5.getInt("x"), nbttagcompound5.getInt("y"), nbttagcompound5.getInt("z"), Block.getById(nbttagcompound5.getInt("i")), nbttagcompound5.getInt("t"), nbttagcompound5.getInt("p"));
        }
      }
    }
    world.timings.syncChunkLoadTileTicksTimer.stopTiming();
  }
}
