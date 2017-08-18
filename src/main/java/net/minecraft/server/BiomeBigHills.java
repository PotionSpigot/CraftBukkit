package net.minecraft.server;

import java.util.Random;







public class BiomeBigHills
  extends BiomeBase
{
  private WorldGenerator aC = new WorldGenMinable(Blocks.MONSTER_EGGS, 8);
  private WorldGenTaiga2 aD = new WorldGenTaiga2(false);
  
  private int aE = 0;
  private int aF = 1;
  private int aG = 2;
  private int aH;
  
  protected BiomeBigHills(int paramInt, boolean paramBoolean)
  {
    super(paramInt);
    this.aH = this.aE;
    if (paramBoolean) {
      this.ar.x = 3;
      this.aH = this.aF;
    }
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    if (paramRandom.nextInt(3) > 0) {
      return this.aD;
    }
    return super.a(paramRandom);
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
    

    int i = 3 + paramRandom.nextInt(6);
    int k; int m; for (int j = 0; j < i; j++) {
      k = paramInt1 + paramRandom.nextInt(16);
      m = paramRandom.nextInt(28) + 4;
      int n = paramInt2 + paramRandom.nextInt(16);
      if (paramWorld.getType(k, m, n) == Blocks.STONE) {
        paramWorld.setTypeAndData(k, m, n, Blocks.EMERALD_ORE, 0, 2);
      }
    }
    

    for (i = 0; i < 7; i++) {
      j = paramInt1 + paramRandom.nextInt(16);
      k = paramRandom.nextInt(64);
      m = paramInt2 + paramRandom.nextInt(16);
      this.aC.generate(paramWorld, paramRandom, j, k, m);
    }
  }
  
  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    this.ai = Blocks.GRASS;
    this.aj = 0;
    this.ak = Blocks.DIRT;
    if (((paramDouble < -1.0D) || (paramDouble > 2.0D)) && (this.aH == this.aG)) {
      this.ai = Blocks.GRAVEL;
      this.ak = Blocks.GRAVEL;
    } else if ((paramDouble > 1.0D) && (this.aH != this.aF)) {
      this.ai = Blocks.STONE;
      this.ak = Blocks.STONE;
    }
    b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
  
  private BiomeBigHills b(BiomeBase paramBiomeBase) {
    this.aH = this.aG;
    
    a(paramBiomeBase.ag, true);
    a(paramBiomeBase.af + " M");
    a(new BiomeTemperature(paramBiomeBase.am, paramBiomeBase.an));
    a(paramBiomeBase.temperature, paramBiomeBase.humidity);
    return this;
  }
  
  protected BiomeBase k()
  {
    return new BiomeBigHills(this.id + 128, false).b(this);
  }
}
