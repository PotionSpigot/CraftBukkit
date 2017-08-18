package net.minecraft.server;


public class ChunkCache
  implements IBlockAccess
{
  private int a;
  
  private int b;
  
  private Chunk[][] c;
  
  private boolean d;
  private World e;
  
  public ChunkCache(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    this.e = paramWorld;
    
    this.a = (paramInt1 - paramInt7 >> 4);
    this.b = (paramInt3 - paramInt7 >> 4);
    int i = paramInt4 + paramInt7 >> 4;
    int j = paramInt6 + paramInt7 >> 4;
    
    this.c = new Chunk[i - this.a + 1][j - this.b + 1];
    
    this.d = true;
    int m; Chunk localChunk; for (int k = this.a; k <= i; k++) {
      for (m = this.b; m <= j; m++) {
        localChunk = paramWorld.getChunkAt(k, m);
        if (localChunk != null) {
          this.c[(k - this.a)][(m - this.b)] = localChunk;
        }
      }
    }
    for (k = paramInt1 >> 4; k <= paramInt4 >> 4; k++) {
      for (m = paramInt3 >> 4; m <= paramInt6 >> 4; m++) {
        localChunk = this.c[(k - this.a)][(m - this.b)];
        if ((localChunk != null) && 
          (!localChunk.c(paramInt2, paramInt5))) {
          this.d = false;
        }
      }
    }
  }
  






  public Block getType(int paramInt1, int paramInt2, int paramInt3)
  {
    Block localBlock = Blocks.AIR;
    if ((paramInt2 >= 0) && 
      (paramInt2 < 256))
    {
      int i = (paramInt1 >> 4) - this.a;
      int j = (paramInt3 >> 4) - this.b;
      
      if ((i >= 0) && (i < this.c.length) && (j >= 0) && (j < this.c[i].length))
      {
        Chunk localChunk = this.c[i][j];
        if (localChunk != null) {
          localBlock = localChunk.getType(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
        }
      }
    }
    
    return localBlock;
  }
  
  public TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (paramInt1 >> 4) - this.a;
    int j = (paramInt3 >> 4) - this.b;
    
    return this.c[i][j].e(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
  }
  


























































  public int getData(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 < 0) return 0;
    if (paramInt2 >= 256) return 0;
    int i = (paramInt1 >> 4) - this.a;
    int j = (paramInt3 >> 4) - this.b;
    
    return this.c[i][j].getData(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
  }
  






























































  public int getBlockPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return getType(paramInt1, paramInt2, paramInt3).c(this, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}
