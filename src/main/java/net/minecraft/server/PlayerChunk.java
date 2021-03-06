package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;



class PlayerChunk
{
  private final List b;
  private final ChunkCoordIntPair location;
  private short[] dirtyBlocks;
  private int dirtyCount;
  private int f;
  private long g;
  final PlayerChunkMap playerChunkMap;
  private final HashMap<EntityPlayer, Runnable> players = new HashMap();
  private boolean loaded = false;
  private Runnable loadedRunnable = new Runnable() {
    public void run() {
      PlayerChunk.this.loaded = true;
    }
  };
  
  public PlayerChunk(PlayerChunkMap playerchunkmap, int i, int j)
  {
    this.playerChunkMap = playerchunkmap;
    this.b = new ArrayList();
    this.dirtyBlocks = new short[64];
    this.location = new ChunkCoordIntPair(i, j);
    playerchunkmap.a().chunkProviderServer.getChunkAt(i, j, this.loadedRunnable);
  }
  
  public void a(final EntityPlayer entityplayer) {
    if (this.b.contains(entityplayer)) {
      PlayerChunkMap.c().debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { entityplayer, Integer.valueOf(this.location.x), Integer.valueOf(this.location.z) });
    } else {
      if (this.b.isEmpty()) {
        this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
      }
      
      this.b.add(entityplayer);
      
      Runnable playerRunnable;
      if (this.loaded) {
        Runnable playerRunnable = null;
        entityplayer.chunkCoordIntPairQueue.add(this.location);
      } else {
        playerRunnable = new Runnable() {
          public void run() {
            entityplayer.chunkCoordIntPairQueue.add(PlayerChunk.this.location);
          }
        };
        this.playerChunkMap.a().chunkProviderServer.getChunkAt(this.location.x, this.location.z, playerRunnable);
      }
      
      this.players.put(entityplayer, playerRunnable);
    }
  }
  
  public void b(EntityPlayer entityplayer)
  {
    if (this.b.contains(entityplayer))
    {
      if (!this.loaded) {
        ChunkIOExecutor.dropQueuedChunkLoad(this.playerChunkMap.a(), this.location.x, this.location.z, (Runnable)this.players.get(entityplayer));
        this.b.remove(entityplayer);
        this.players.remove(entityplayer);
        
        if (this.b.isEmpty()) {
          ChunkIOExecutor.dropQueuedChunkLoad(this.playerChunkMap.a(), this.location.x, this.location.z, this.loadedRunnable);
          long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32;
          PlayerChunkMap.b(this.playerChunkMap).remove(i);
          PlayerChunkMap.c(this.playerChunkMap).remove(this);
        }
        
        return;
      }
      

      Chunk chunk = PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z);
      
      if (chunk.isReady()) {
        entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0, entityplayer.playerConnection.networkManager.getVersion()));
      }
      
      this.players.remove(entityplayer);
      this.b.remove(entityplayer);
      entityplayer.chunkCoordIntPairQueue.remove(this.location);
      if (this.b.isEmpty()) {
        long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32;
        
        a(chunk);
        PlayerChunkMap.b(this.playerChunkMap).remove(i);
        PlayerChunkMap.c(this.playerChunkMap).remove(this);
        if (this.dirtyCount > 0) {
          PlayerChunkMap.d(this.playerChunkMap).remove(this);
        }
        
        this.playerChunkMap.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
      }
    }
  }
  
  public void a() {
    a(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z));
  }
  
  private void a(Chunk chunk) {
    chunk.s += PlayerChunkMap.a(this.playerChunkMap).getTime() - this.g;
    this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
  }
  
  public void a(int i, int j, int k) {
    if (this.dirtyCount == 0) {
      PlayerChunkMap.d(this.playerChunkMap).add(this);
    }
    
    this.f |= 1 << (j >> 4);
    if (this.dirtyCount < 64) {
      short short1 = (short)(i << 12 | k << 8 | j);
      
      for (int l = 0; l < this.dirtyCount; l++) {
        if (this.dirtyBlocks[l] == short1) {
          return;
        }
      }
      
      this.dirtyBlocks[(this.dirtyCount++)] = short1;
    }
  }
  
  public void sendAll(Packet packet) {
    for (int i = 0; i < this.b.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.b.get(i);
      
      if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
        entityplayer.playerConnection.sendPacket(packet);
      }
    }
  }
  
  public void b() {
    if (this.dirtyCount != 0)
    {



      if (this.dirtyCount == 1) {
        int i = this.location.x * 16 + (this.dirtyBlocks[0] >> 12 & 0xF);
        int j = this.dirtyBlocks[0] & 0xFF;
        int k = this.location.z * 16 + (this.dirtyBlocks[0] >> 8 & 0xF);
        sendAll(new PacketPlayOutBlockChange(i, j, k, PlayerChunkMap.a(this.playerChunkMap)));
        if (PlayerChunkMap.a(this.playerChunkMap).getType(i, j, k).isTileEntity()) {
          sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(i, j, k));
        }
        

      }
      else if (this.dirtyCount == 64) {
        int i = this.location.x * 16;
        int j = this.location.z * 16;
        


        Chunk chunk = PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z);
        for (int idx = 0; idx < this.b.size(); idx++) {
          EntityPlayer entityplayer = (EntityPlayer)this.b.get(idx);
          
          if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, this.f == 65535, this.f, entityplayer.playerConnection.networkManager
              .getVersion()));
          }
        }
        


        for (int k = 0; k < 16; k++) {
          if ((this.f & 1 << k) != 0) {
            int l = k << 4;
            List list = PlayerChunkMap.a(this.playerChunkMap).getTileEntities(i, l, j, i + 16, l + 16, j + 16);
            
            for (int i1 = 0; i1 < list.size(); i1++) {
              sendTileEntity((TileEntity)list.get(i1));
            }
          }
        }
      } else {
        sendAll(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z)));
        
        for (int i = 0; i < this.dirtyCount; i++) {
          int j = this.location.x * 16 + (this.dirtyBlocks[i] >> 12 & 0xF);
          int k = this.dirtyBlocks[i] & 0xFF;
          int l = this.location.z * 16 + (this.dirtyBlocks[i] >> 8 & 0xF);
          if (PlayerChunkMap.a(this.playerChunkMap).getType(j, k, l).isTileEntity()) {
            sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(j, k, l));
          }
        }
      }
      

      this.dirtyCount = 0;
      this.f = 0;
    }
  }
  
  private void sendTileEntity(TileEntity tileentity) {
    if (tileentity != null) {
      Packet packet = tileentity.getUpdatePacket();
      
      if (packet != null) {
        sendAll(packet);
      }
    }
  }
  
  static ChunkCoordIntPair a(PlayerChunk playerchunk) {
    return playerchunk.location;
  }
  
  static List b(PlayerChunk playerchunk) {
    return playerchunk.b;
  }
}
