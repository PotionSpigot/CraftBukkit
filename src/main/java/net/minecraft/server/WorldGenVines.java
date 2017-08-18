package net.minecraft.server;

import java.util.Random;





public class WorldGenVines
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt3;
    
    while (paramInt2 < 128) {
      if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) {
        for (int k = 2; k <= 5; k++) {
          if (Blocks.VINE.canPlace(paramWorld, paramInt1, paramInt2, paramInt3, k)) {
            paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.VINE, 1 << Direction.e[Facing.OPPOSITE_FACING[k]], 2);
            break;
          }
        }
      } else {
        paramInt1 = i + paramRandom.nextInt(4) - paramRandom.nextInt(4);
        paramInt3 = j + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      }
      paramInt2++;
    }
    
    return true;
  }
}
