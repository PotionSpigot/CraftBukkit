package net.minecraft.server;

import java.util.List;
import java.util.Random;








public class BiomeSwamp
  extends BiomeBase
{
  protected BiomeSwamp(int paramInt)
  {
    super(paramInt);
    this.ar.x = 2;
    this.ar.y = 1;
    this.ar.A = 1;
    this.ar.B = 8;
    this.ar.C = 10;
    this.ar.G = 1;
    this.ar.w = 4;
    this.ar.F = 0;
    this.ar.E = 0;
    this.ar.z = 5;
    
    this.aq = 14745518;
    
    this.as.add(new BiomeMeta(EntitySlime.class, 1, 1, 1));
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    return this.aB;
  }
  















  public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    return BlockFlowers.a[1];
  }
  
  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    double d = ad.a(paramInt1 * 0.25D, paramInt2 * 0.25D);
    if (d > 0.0D) {
      int i = paramInt1 & 0xF;
      int j = paramInt2 & 0xF;
      int k = paramArrayOfBlock.length / 256;
      for (int m = 255; m >= 0; m--) {
        int n = (j * 16 + i) * k + m;
        if ((paramArrayOfBlock[n] == null) || (paramArrayOfBlock[n].getMaterial() != Material.AIR)) {
          if ((m != 62) || (paramArrayOfBlock[n] == Blocks.STATIONARY_WATER)) break;
          paramArrayOfBlock[n] = Blocks.STATIONARY_WATER;
          if (d >= 0.12D) break;
          paramArrayOfBlock[(n + 1)] = Blocks.WATER_LILY; break;
        }
      }
    }
    



    b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
}
