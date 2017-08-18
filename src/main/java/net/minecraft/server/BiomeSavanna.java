package net.minecraft.server;

import java.util.List;
import java.util.Random;




public class BiomeSavanna
  extends BiomeBase
{
  private static final WorldGenAcaciaTree aC = new WorldGenAcaciaTree(false);
  
  protected BiomeSavanna(int paramInt) {
    super(paramInt);
    
    this.at.add(new BiomeMeta(EntityHorse.class, 1, 2, 6));
    
    this.ar.x = 1;
    this.ar.y = 4;
    this.ar.z = 20;
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    if (paramRandom.nextInt(5) > 0) {
      return aC;
    }
    return this.az;
  }
  
  protected BiomeBase k()
  {
    BiomeSavannaSub localBiomeSavannaSub = new BiomeSavannaSub(this.id + 128, this);
    
    localBiomeSavannaSub.temperature = ((this.temperature + 1.0F) * 0.5F);
    localBiomeSavannaSub.am = (this.am * 0.5F + 0.3F);
    localBiomeSavannaSub.an = (this.an * 0.5F + 1.2F);
    
    return localBiomeSavannaSub;
  }
  

  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    ae.a(2);
    for (int i = 0; i < 7; i++) {
      int j = paramInt1 + paramRandom.nextInt(16) + 8;
      int k = paramInt2 + paramRandom.nextInt(16) + 8;
      int m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(j, k) + 32);
      ae.generate(paramWorld, paramRandom, j, m, k);
    }
    
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
  }
}
