package net.minecraft.server;

import java.util.List;
import java.util.Random;



public class BiomeIcePlains
  extends BiomeBase
{
  private boolean aC;
  private WorldGenPackedIce2 aD = new WorldGenPackedIce2();
  private WorldGenPackedIce1 aE = new WorldGenPackedIce1(4);
  
  public BiomeIcePlains(int paramInt, boolean paramBoolean) {
    super(paramInt);
    this.aC = paramBoolean;
    
    if (paramBoolean) {
      this.ai = Blocks.SNOW_BLOCK;
    }
    this.at.clear();
  }
  

  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    if (this.aC) { int j;
      int k;
      for (int i = 0; i < 3; i++) {
        j = paramInt1 + paramRandom.nextInt(16) + 8;
        k = paramInt2 + paramRandom.nextInt(16) + 8;
        this.aD.generate(paramWorld, paramRandom, j, paramWorld.getHighestBlockYAt(j, k), k);
      }
      for (i = 0; i < 2; i++) {
        j = paramInt1 + paramRandom.nextInt(16) + 8;
        k = paramInt2 + paramRandom.nextInt(16) + 8;
        this.aE.generate(paramWorld, paramRandom, j, paramWorld.getHighestBlockYAt(j, k), k);
      }
    }
    
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    return new WorldGenTaiga2(false);
  }
  
  protected BiomeBase k()
  {
    BiomeBase localBiomeBase = new BiomeIcePlains(this.id + 128, true).a(13828095, true).a(this.af + " Spikes").c().a(0.0F, 0.5F).a(new BiomeTemperature(this.am + 0.1F, this.an + 0.1F));
    

    localBiomeBase.am = (this.am + 0.3F);
    localBiomeBase.an = (this.an + 0.4F);
    
    return localBiomeBase;
  }
}
