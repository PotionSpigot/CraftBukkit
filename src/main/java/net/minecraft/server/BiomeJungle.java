package net.minecraft.server;

import java.util.List;
import java.util.Random;



public class BiomeJungle
  extends BiomeBase
{
  private boolean aC;
  
  public BiomeJungle(int paramInt, boolean paramBoolean)
  {
    super(paramInt);
    this.aC = paramBoolean;
    if (paramBoolean) {
      this.ar.x = 2;
    } else {
      this.ar.x = 50;
    }
    this.ar.z = 25;
    this.ar.y = 4;
    
    if (!paramBoolean) {
      this.as.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
    }
    

    this.at.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    if (paramRandom.nextInt(10) == 0) {
      return this.aA;
    }
    if (paramRandom.nextInt(2) == 0) {
      return new WorldGenGroundBush(3, 0);
    }
    if ((!this.aC) && (paramRandom.nextInt(3) == 0)) {
      return new WorldGenJungleTree(false, 10, 20, 3, 3);
    }
    return new WorldGenTrees(false, 4 + paramRandom.nextInt(7), 3, 3, true);
  }
  
  public WorldGenerator b(Random paramRandom)
  {
    if (paramRandom.nextInt(4) == 0) {
      return new WorldGenGrass(Blocks.LONG_GRASS, 2);
    }
    return new WorldGenGrass(Blocks.LONG_GRASS, 1);
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
    

    int i = paramInt1 + paramRandom.nextInt(16) + 8;
    int j = paramInt2 + paramRandom.nextInt(16) + 8;
    int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) * 2);
    new WorldGenMelon().generate(paramWorld, paramRandom, i, k, j);
    

    WorldGenVines localWorldGenVines = new WorldGenVines();
    
    for (j = 0; j < 50; j++) {
      k = paramInt1 + paramRandom.nextInt(16) + 8;
      int m = 128;
      int n = paramInt2 + paramRandom.nextInt(16) + 8;
      localWorldGenVines.generate(paramWorld, paramRandom, k, m, n);
    }
  }
}
