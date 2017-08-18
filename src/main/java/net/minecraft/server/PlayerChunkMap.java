package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class PlayerChunkMap
{
  private static final Logger a = ;
  private final WorldServer world;
  private final List managedPlayers = new ArrayList();
  private final LongHashMap d = new LongHashMap();
  private final Queue e = new ConcurrentLinkedQueue();
  private final Queue f = new ConcurrentLinkedQueue();
  private int g;
  private long h;
  private final int[][] i = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
  private boolean wasNotEmpty;
  
  public PlayerChunkMap(WorldServer worldserver, int viewDistance) {
    this.world = worldserver;
    a(viewDistance);
  }
  
  public WorldServer a() {
    return this.world;
  }
  
  public void flush() {
    long i = this.world.getTime();
    


    if (i - this.h > 8000L) {
      this.h = i;
      

      Iterator iterator = this.f.iterator();
      while (iterator.hasNext()) {
        PlayerChunk playerchunk = (PlayerChunk)iterator.next();
        playerchunk.b();
        playerchunk.a();
      }
    } else {
      Iterator iterator = this.e.iterator();
      
      while (iterator.hasNext()) {
        PlayerChunk playerchunk = (PlayerChunk)iterator.next();
        playerchunk.b();
        iterator.remove();
      }
    }
    


    if (this.managedPlayers.isEmpty()) {
      if (!this.wasNotEmpty) return;
      WorldProvider worldprovider = this.world.worldProvider;
      
      if (!worldprovider.e()) {
        this.world.chunkProviderServer.b();
      }
      
      this.wasNotEmpty = false;
    } else {
      this.wasNotEmpty = true;
    }
  }
  
  public boolean a(int i, int j)
  {
    long k = i + 2147483647L | j + 2147483647L << 32;
    
    return this.d.getEntry(k) != null;
  }
  
  private PlayerChunk a(int i, int j, boolean flag) {
    long k = i + 2147483647L | j + 2147483647L << 32;
    PlayerChunk playerchunk = (PlayerChunk)this.d.getEntry(k);
    
    if ((playerchunk == null) && (flag)) {
      playerchunk = new PlayerChunk(this, i, j);
      this.d.put(k, playerchunk);
      this.f.add(playerchunk);
    }
    
    return playerchunk;
  }
  
  public final boolean isChunkInUse(int x, int z) {
    PlayerChunk pi = a(x, z, false);
    if (pi != null) {
      return PlayerChunk.b(pi).size() > 0;
    }
    return false;
  }
  
  public void flagDirty(int i, int j, int k)
  {
    int l = i >> 4;
    int i1 = k >> 4;
    PlayerChunk playerchunk = a(l, i1, false);
    
    if (playerchunk != null) {
      playerchunk.a(i & 0xF, j, k & 0xF);
    }
  }
  
  public void addPlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.locX >> 4;
    int j = (int)entityplayer.locZ >> 4;
    
    entityplayer.d = entityplayer.locX;
    entityplayer.e = entityplayer.locZ;
    

    List<ChunkCoordIntPair> chunkList = new LinkedList();
    
    for (int k = i - entityplayer.viewDistance; k <= i + entityplayer.viewDistance; k++) {
      for (int l = j - entityplayer.viewDistance; l <= j + entityplayer.viewDistance; l++)
      {
        chunkList.add(new ChunkCoordIntPair(k, l));
      }
    }
    
    Collections.sort(chunkList, new ChunkCoordComparator(entityplayer));
    for (ChunkCoordIntPair pair : chunkList) {
      a(pair.x, pair.z, true).a(entityplayer);
    }
    

    this.managedPlayers.add(entityplayer);
    b(entityplayer);
  }
  
  public void b(EntityPlayer entityplayer) {
    ArrayList arraylist = new ArrayList(entityplayer.chunkCoordIntPairQueue);
    int i = 0;
    int j = entityplayer.viewDistance;
    int k = (int)entityplayer.locX >> 4;
    int l = (int)entityplayer.locZ >> 4;
    int i1 = 0;
    int j1 = 0;
    ChunkCoordIntPair chunkcoordintpair = PlayerChunk.a(a(k, l, true));
    
    entityplayer.chunkCoordIntPairQueue.clear();
    if (arraylist.contains(chunkcoordintpair)) {
      entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
    }
    


    for (int k1 = 1; k1 <= j * 2; k1++) {
      for (int l1 = 0; l1 < 2; l1++) {
        int[] aint = this.i[(i++ % 4)];
        
        for (int i2 = 0; i2 < k1; i2++) {
          i1 += aint[0];
          j1 += aint[1];
          chunkcoordintpair = PlayerChunk.a(a(k + i1, l + j1, true));
          if (arraylist.contains(chunkcoordintpair)) {
            entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
          }
        }
      }
    }
    
    i %= 4;
    
    for (k1 = 0; k1 < j * 2; k1++) {
      i1 += this.i[i][0];
      j1 += this.i[i][1];
      chunkcoordintpair = PlayerChunk.a(a(k + i1, l + j1, true));
      if (arraylist.contains(chunkcoordintpair)) {
        entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
      }
    }
  }
  
  public void removePlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.d >> 4;
    int j = (int)entityplayer.e >> 4;
    

    for (int k = i - entityplayer.viewDistance; k <= i + entityplayer.viewDistance; k++) {
      for (int l = j - entityplayer.viewDistance; l <= j + entityplayer.viewDistance; l++)
      {
        PlayerChunk playerchunk = a(k, l, false);
        
        if (playerchunk != null) {
          playerchunk.b(entityplayer);
        }
      }
    }
    
    this.managedPlayers.remove(entityplayer);
  }
  
  private boolean a(int i, int j, int k, int l, int i1) {
    int j1 = i - k;
    int k1 = j - l;
    
    return (k1 >= -i1) && (k1 <= i1);
  }
  
  public void movePlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.locX >> 4;
    int j = (int)entityplayer.locZ >> 4;
    double d0 = entityplayer.d - entityplayer.locX;
    double d1 = entityplayer.e - entityplayer.locZ;
    double d2 = d0 * d0 + d1 * d1;
    
    if (d2 >= 64.0D) {
      int k = (int)entityplayer.d >> 4;
      int l = (int)entityplayer.e >> 4;
      int i1 = entityplayer.viewDistance;
      int j1 = i - k;
      int k1 = j - l;
      List<ChunkCoordIntPair> chunksToLoad = new LinkedList();
      
      if ((j1 != 0) || (k1 != 0)) {
        for (int l1 = i - i1; l1 <= i + i1; l1++) {
          for (int i2 = j - i1; i2 <= j + i1; i2++) {
            if (!a(l1, i2, k, l, i1)) {
              chunksToLoad.add(new ChunkCoordIntPair(l1, i2));
            }
            
            if (!a(l1 - j1, i2 - k1, i, j, i1)) {
              PlayerChunk playerchunk = a(l1 - j1, i2 - k1, false);
              
              if (playerchunk != null) {
                playerchunk.b(entityplayer);
              }
            }
          }
        }
        
        b(entityplayer);
        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;
        

        Collections.sort(chunksToLoad, new ChunkCoordComparator(entityplayer));
        for (ChunkCoordIntPair pair : chunksToLoad) {
          a(pair.x, pair.z, true).a(entityplayer);
        }
        
        if ((j1 > 1) || (j1 < -1) || (k1 > 1) || (k1 < -1)) {
          Collections.sort(entityplayer.chunkCoordIntPairQueue, new ChunkCoordComparator(entityplayer));
        }
      }
    }
  }
  
  public boolean a(EntityPlayer entityplayer, int i, int j)
  {
    PlayerChunk playerchunk = a(i, j, false);
    
    return (playerchunk != null) && (PlayerChunk.b(playerchunk).contains(entityplayer)) && (!entityplayer.chunkCoordIntPairQueue.contains(PlayerChunk.a(playerchunk)));
  }
  
  public void a(int i) {
    i = MathHelper.a(i, 3, 20);
    if (i != this.g) {
      int j = i - this.g;
      Iterator iterator = this.managedPlayers.iterator();
      
      while (iterator.hasNext()) {
        EntityPlayer entityplayer = (EntityPlayer)iterator.next();
        int k = (int)entityplayer.locX >> 4;
        int l = (int)entityplayer.locZ >> 4;
        


        if (j > 0) {
          for (int i1 = k - i; i1 <= k + i; i1++) {
            for (int j1 = l - i; j1 <= l + i; j1++) {
              PlayerChunk playerchunk = a(i1, j1, true);
              
              if (!PlayerChunk.b(playerchunk).contains(entityplayer)) {
                playerchunk.a(entityplayer);
              }
            }
          }
        }
        for (int i1 = k - this.g; i1 <= k + this.g; i1++) {
          for (int j1 = l - this.g; j1 <= l + this.g; j1++) {
            if (!a(i1, j1, k, l, i)) {
              a(i1, j1, true).b(entityplayer);
            }
          }
        }
      }
      

      this.g = i;
    }
  }
  
  public void updateViewDistance(EntityPlayer player, int viewDistance)
  {
    viewDistance = MathHelper.a(viewDistance, 3, 20);
    if (viewDistance != player.viewDistance) {
      int cx = (int)player.locX >> 4;
      int cz = (int)player.locZ >> 4;
      
      if (viewDistance - player.viewDistance > 0) {
        for (int x = cx - viewDistance; x <= cx + viewDistance; x++) {
          for (int z = cz - viewDistance; z <= cz + viewDistance; z++) {
            PlayerChunk playerChunk = a(x, z, true);
            if (!PlayerChunk.b(playerChunk).contains(player)) {
              playerChunk.a(player);
            }
          }
        }
      } else {
        for (int x = cx - player.viewDistance; x <= cx + player.viewDistance; x++) {
          for (int z = cz - player.viewDistance; z <= cz + player.viewDistance; z++) {
            if (!a(x, z, cx, cz, viewDistance)) {
              a(x, z, true).b(player);
            }
          }
        }
      }
      
      player.viewDistance = viewDistance;
    }
  }
  
  public static int getFurthestViewableBlock(int i)
  {
    return i * 16 - 16;
  }
  
  static Logger c() {
    return a;
  }
  
  static WorldServer a(PlayerChunkMap playerchunkmap) {
    return playerchunkmap.world;
  }
  
  static LongHashMap b(PlayerChunkMap playerchunkmap) {
    return playerchunkmap.d;
  }
  
  static Queue c(PlayerChunkMap playermanager) {
    return playermanager.f;
  }
  
  static Queue d(PlayerChunkMap playermanager) {
    return playermanager.e;
  }
  
  private static class ChunkCoordComparator implements Comparator<ChunkCoordIntPair>
  {
    private int x;
    private int z;
    
    public ChunkCoordComparator(EntityPlayer entityplayer) {
      this.x = ((int)entityplayer.locX >> 4);
      this.z = ((int)entityplayer.locZ >> 4);
    }
    
    public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
      if (a.equals(b)) {
        return 0;
      }
      

      int ax = a.x - this.x;
      int az = a.z - this.z;
      int bx = b.x - this.x;
      int bz = b.z - this.z;
      
      int result = (ax - bx) * (ax + bx) + (az - bz) * (az + bz);
      if (result != 0) {
        return result;
      }
      
      if (ax < 0) {
        if (bx < 0) {
          return bz - az;
        }
        return -1;
      }
      
      if (bx < 0) {
        return 1;
      }
      return az - bz;
    }
  }
}
