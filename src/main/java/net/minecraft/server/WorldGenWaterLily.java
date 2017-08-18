package net.minecraft.server;

import java.util.Random;





public class WorldGenWaterLily
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < 10; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((paramWorld.isEmpty(j, k, m)) && 
        (Blocks.WATER_LILY.canPlace(paramWorld, j, k, m))) {
        paramWorld.setTypeAndData(j, k, m, Blocks.WATER_LILY, 0, 2);
      }
    }
    

    return true;
  }
}
