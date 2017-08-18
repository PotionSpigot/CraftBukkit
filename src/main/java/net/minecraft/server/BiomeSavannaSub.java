package net.minecraft.server;

import java.util.Random;



















































public class BiomeSavannaSub
  extends BiomeBaseSub
{
  public BiomeSavannaSub(int paramInt, BiomeBase paramBiomeBase)
  {
    super(paramInt, paramBiomeBase);
    
    this.ar.x = 2;
    this.ar.y = 2;
    this.ar.z = 5;
  }
  
  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    this.ai = Blocks.GRASS;
    this.aj = 0;
    this.ak = Blocks.DIRT;
    if (paramDouble > 1.75D) {
      this.ai = Blocks.STONE;
      this.ak = Blocks.STONE;
    } else if (paramDouble > -0.5D) {
      this.ai = Blocks.DIRT;
      this.aj = 1;
    }
    b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    this.ar.a(paramWorld, paramRandom, this, paramInt1, paramInt2);
  }
}
