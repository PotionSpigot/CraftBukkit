package net.minecraft.server;

import java.util.Random;




public class WorldGenLightStone1
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false;
    if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.NETHERRACK) return false;
    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.GLOWSTONE, 0, 2);
    
    for (int i = 0; i < 1500; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 - paramRandom.nextInt(12);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if (paramWorld.getType(j, k, m).getMaterial() == Material.AIR)
      {
        int n = 0;
        for (int i1 = 0; i1 < 6; i1++) {
          Block localBlock = null;
          if (i1 == 0) localBlock = paramWorld.getType(j - 1, k, m);
          if (i1 == 1) localBlock = paramWorld.getType(j + 1, k, m);
          if (i1 == 2) localBlock = paramWorld.getType(j, k - 1, m);
          if (i1 == 3) localBlock = paramWorld.getType(j, k + 1, m);
          if (i1 == 4) localBlock = paramWorld.getType(j, k, m - 1);
          if (i1 == 5) { localBlock = paramWorld.getType(j, k, m + 1);
          }
          if (localBlock == Blocks.GLOWSTONE) { n++;
          }
        }
        if (n == 1) paramWorld.setTypeAndData(j, k, m, Blocks.GLOWSTONE, 0, 2);
      }
    }
    return true;
  }
}
