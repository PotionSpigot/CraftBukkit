package net.minecraft.server;

import java.util.List;
import java.util.Random;











public class BiomeForest
  extends BiomeBase
{
  private int aF;
  protected static final WorldGenForest aC = new WorldGenForest(false, true);
  protected static final WorldGenForest aD = new WorldGenForest(false, false);
  protected static final WorldGenForestTree aE = new WorldGenForestTree(false);
  
  public BiomeForest(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.aF = paramInt2;
    this.ar.x = 10;
    this.ar.z = 2;
    
    if (this.aF == 1) {
      this.ar.x = 6;
      this.ar.y = 100;
      this.ar.z = 1;
    }
    a(5159473);
    a(0.7F, 0.8F);
    
    if (this.aF == 2) {
      this.ah = 353825;
      this.ag = 3175492;
      a(0.6F, 0.6F);
    }
    
    if (this.aF == 0) {
      this.at.add(new BiomeMeta(EntityWolf.class, 5, 4, 4));
    }
    
    if (this.aF == 3) {
      this.ar.x = 64537;
    }
  }
  
  protected BiomeBase a(int paramInt, boolean paramBoolean)
  {
    if (this.aF == 2) {
      this.ah = 353825;
      this.ag = paramInt;
      
      if (paramBoolean) {
        this.ah = ((this.ah & 0xFEFEFE) >> 1);
      }
      return this;
    }
    return super.a(paramInt, paramBoolean);
  }
  

  public WorldGenTreeAbstract a(Random paramRandom)
  {
    if ((this.aF == 3) && (paramRandom.nextInt(3) > 0)) {
      return aE;
    }
    if ((this.aF == 2) || (paramRandom.nextInt(5) == 0)) {
      return aD;
    }
    return this.az;
  }
  
  public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.aF == 1) {
      double d = MathHelper.a((1.0D + ad.a(paramInt1 / 48.0D, paramInt3 / 48.0D)) / 2.0D, 0.0D, 0.9999D);
      int i = (int)(d * BlockFlowers.a.length);
      if (i == 1) {
        i = 0;
      }
      return BlockFlowers.a[i];
    }
    return super.a(paramRandom, paramInt1, paramInt2, paramInt3); }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) { int k;
    int m;
    int n;
    if (this.aF == 3) {
      for (i = 0; i < 4; i++) {
        for (j = 0; j < 4; j++) {
          k = paramInt1 + i * 4 + 1 + 8 + paramRandom.nextInt(3);
          m = paramInt2 + j * 4 + 1 + 8 + paramRandom.nextInt(3);
          n = paramWorld.getHighestBlockYAt(k, m);
          Object localObject;
          if (paramRandom.nextInt(20) == 0) {
            localObject = new WorldGenHugeMushroom();
            ((WorldGenHugeMushroom)localObject).generate(paramWorld, paramRandom, k, n, m);
          } else {
            localObject = a(paramRandom);
            ((WorldGenTreeAbstract)localObject).a(1.0D, 1.0D, 1.0D);
            if (((WorldGenTreeAbstract)localObject).generate(paramWorld, paramRandom, k, n, m)) {
              ((WorldGenTreeAbstract)localObject).b(paramWorld, paramRandom, k, n, m);
            }
          }
        }
      }
    }
    int i = paramRandom.nextInt(5) - 3;
    if (this.aF == 1) {
      i += 2;
    }
    for (int j = 0; j < i; j++) {
      k = paramRandom.nextInt(3);
      if (k == 0) {
        ae.a(1);
      } else if (k == 1) {
        ae.a(4);
      } else if (k == 2) {
        ae.a(5);
      }
      
      for (m = 0; m < 5; m++) {
        n = paramInt1 + paramRandom.nextInt(16) + 8;
        int i1 = paramInt2 + paramRandom.nextInt(16) + 8;
        int i2 = paramRandom.nextInt(paramWorld.getHighestBlockYAt(n, i1) + 32);
        if (ae.generate(paramWorld, paramRandom, n, i2, i1)) {
          break;
        }
      }
    }
    
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
  }
  











  protected BiomeBase k()
  {
    if (this.id == BiomeBase.FOREST.id) {
      BiomeForest localBiomeForest = new BiomeForest(this.id + 128, 1);
      localBiomeForest.a(new BiomeTemperature(this.am, this.an + 0.2F));
      localBiomeForest.a("Flower Forest");
      localBiomeForest.a(6976549, true);
      localBiomeForest.a(8233509);
      return localBiomeForest;
    }
    if ((this.id == BiomeBase.BIRCH_FOREST.id) || (this.id == BiomeBase.BIRCH_FOREST_HILLS.id)) {
      return new BiomeBaseSubForest(this, this.id + 128, this);
    }
    







    return new BiomeBaseSubForest2(this, this.id + 128, this);
  }
}
