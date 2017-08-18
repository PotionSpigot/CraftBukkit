package net.minecraft.server;

import java.util.Random;



public class WorldGenCactus
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < 10; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if (paramWorld.isEmpty(j, k, m)) {
        int n = 1 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
        for (int i1 = 0; i1 < n; i1++) {
          if (Blocks.CACTUS.j(paramWorld, j, k + i1, m)) {
            paramWorld.setTypeAndData(j, k + i1, m, Blocks.CACTUS, 0, 2);
          }
        }
      }
    }
    
    return true;
  }
}
