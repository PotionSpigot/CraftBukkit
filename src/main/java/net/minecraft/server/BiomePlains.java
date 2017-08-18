package net.minecraft.server;

import java.util.List;
import java.util.Random;


public class BiomePlains
  extends BiomeBase
{
  protected boolean aC;
  
  protected BiomePlains(int paramInt)
  {
    super(paramInt);
    
    a(0.8F, 0.4F);
    a(e);
    
    this.at.add(new BiomeMeta(EntityHorse.class, 5, 2, 6));
    
    this.ar.x = 64537;
    this.ar.y = 4;
    this.ar.z = 10;
  }
  
  public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    double d = ad.a(paramInt1 / 200.0D, paramInt3 / 200.0D);
    int i; if (d < -0.8D) {
      i = paramRandom.nextInt(4);
      return BlockFlowers.a[(4 + i)];
    }
    if (paramRandom.nextInt(3) > 0) {
      i = paramRandom.nextInt(3);
      if (i == 0)
        return BlockFlowers.a[0];
      if (i == 1) {
        return BlockFlowers.a[3];
      }
      return BlockFlowers.a[8];
    }
    
    return BlockFlowers.b[0];
  }
  

  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    double d = ad.a((paramInt1 + 8) / 200.0D, (paramInt2 + 8) / 200.0D);
    int i; int j; int k; int m; if (d < -0.8D) {
      this.ar.y = 15;
      this.ar.z = 5;
    } else {
      this.ar.y = 4;
      this.ar.z = 10;
      
      ae.a(2);
      for (i = 0; i < 7; i++) {
        j = paramInt1 + paramRandom.nextInt(16) + 8;
        k = paramInt2 + paramRandom.nextInt(16) + 8;
        m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(j, k) + 32);
        ae.generate(paramWorld, paramRandom, j, m, k);
      }
    }
    if (this.aC) {
      ae.a(0);
      for (i = 0; i < 10; i++) {
        j = paramInt1 + paramRandom.nextInt(16) + 8;
        k = paramInt2 + paramRandom.nextInt(16) + 8;
        m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(j, k) + 32);
        ae.generate(paramWorld, paramRandom, j, m, k);
      }
    }
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
  }
  
  protected BiomeBase k()
  {
    BiomePlains localBiomePlains = new BiomePlains(this.id + 128);
    localBiomePlains.a("Sunflower Plains");
    localBiomePlains.aC = true;
    localBiomePlains.b(9286496);
    localBiomePlains.ah = 14273354;
    return localBiomePlains;
  }
}
