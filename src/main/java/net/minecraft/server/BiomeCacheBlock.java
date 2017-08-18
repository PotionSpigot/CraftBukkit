package net.minecraft.server;


public class BiomeCacheBlock
{
  public float[] a = new float['Ā'];
  public BiomeBase[] b = new BiomeBase['Ā'];
  public int c;
  public int d;
  public long e;
  
  public BiomeCacheBlock(BiomeCache paramBiomeCache, int paramInt1, int paramInt2) { this.c = paramInt1;
    this.d = paramInt2;
    BiomeCache.a(paramBiomeCache).getWetness(this.a, paramInt1 << 4, paramInt2 << 4, 16, 16);
    BiomeCache.a(paramBiomeCache).a(this.b, paramInt1 << 4, paramInt2 << 4, 16, 16, false);
  }
  
  public BiomeBase a(int paramInt1, int paramInt2) {
    return this.b[(paramInt1 & 0xF | (paramInt2 & 0xF) << 4)];
  }
}