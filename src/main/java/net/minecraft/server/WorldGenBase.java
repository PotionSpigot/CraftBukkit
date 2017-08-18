package net.minecraft.server;

import java.util.Random;







public class WorldGenBase
{
  protected int a = 8;
  protected Random b = new Random();
  protected World c;
  
  public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, Block[] paramArrayOfBlock) {
    int i = this.a;
    this.c = paramWorld;
    
    this.b.setSeed(paramWorld.getSeed());
    long l1 = this.b.nextLong();
    long l2 = this.b.nextLong();
    
    for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
      for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
        long l3 = j * l1;
        long l4 = k * l2;
        this.b.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
        a(paramWorld, j, k, paramInt1, paramInt2, paramArrayOfBlock);
      }
    }
  }
  
  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Block[] paramArrayOfBlock) {}
}
