package net.minecraft.server;

import java.util.Random;



public class WorldGenMelon
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < 64; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((Blocks.MELON.canPlace(paramWorld, j, k, m)) && (paramWorld.getType(j, k - 1, m) == Blocks.GRASS)) {
        paramWorld.setTypeAndData(j, k, m, Blocks.MELON, 0, 2);
      }
    }
    
    return true;
  }
}
