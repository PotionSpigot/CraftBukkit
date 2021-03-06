package net.minecraft.server;

import java.util.Random;




public class WorldGenReed
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < 20; i++) {
      int j = paramInt1 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int k = paramInt2;
      int m = paramInt3 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      if ((paramWorld.isEmpty(j, k, m)) && (
        (paramWorld.getType(j - 1, k - 1, m).getMaterial() == Material.WATER) || (paramWorld.getType(j + 1, k - 1, m).getMaterial() == Material.WATER) || (paramWorld.getType(j, k - 1, m - 1).getMaterial() == Material.WATER) || (paramWorld.getType(j, k - 1, m + 1).getMaterial() == Material.WATER)))
      {

        int n = 2 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
        for (int i1 = 0; i1 < n; i1++) {
          if (Blocks.SUGAR_CANE_BLOCK.j(paramWorld, j, k + i1, m)) {
            paramWorld.setTypeAndData(j, k + i1, m, Blocks.SUGAR_CANE_BLOCK, 0, 2);
          }
        }
      }
    }
    

    return true;
  }
}
