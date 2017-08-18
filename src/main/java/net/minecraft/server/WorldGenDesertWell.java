package net.minecraft.server;

import java.util.Random;



public class WorldGenDesertWell
  extends WorldGenerator
{
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    while ((paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) && (paramInt2 > 2)) {
      paramInt2--;
    }
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SAND) {
      return false;
    }
    
    int j;
    for (int i = -2; i <= 2; i++) {
      for (j = -2; j <= 2; j++) {
        if ((paramWorld.isEmpty(paramInt1 + i, paramInt2 - 1, paramInt3 + j)) && (paramWorld.isEmpty(paramInt1 + i, paramInt2 - 2, paramInt3 + j))) {
          return false;
        }
      }
    }
    

    for (i = -1; i <= 0; i++) {
      for (j = -2; j <= 2; j++) {
        for (int k = -2; k <= 2; k++) {
          paramWorld.setTypeAndData(paramInt1 + j, paramInt2 + i, paramInt3 + k, Blocks.SANDSTONE, 0, 2);
        }
      }
    }
    

    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
    paramWorld.setTypeAndData(paramInt1 - 1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
    paramWorld.setTypeAndData(paramInt1 + 1, paramInt2, paramInt3, Blocks.WATER, 0, 2);
    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3 - 1, Blocks.WATER, 0, 2);
    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3 + 1, Blocks.WATER, 0, 2);
    

    for (i = -2; i <= 2; i++) {
      for (j = -2; j <= 2; j++) {
        if ((i == -2) || (i == 2) || (j == -2) || (j == 2)) {
          paramWorld.setTypeAndData(paramInt1 + i, paramInt2 + 1, paramInt3 + j, Blocks.SANDSTONE, 0, 2);
        }
      }
    }
    paramWorld.setTypeAndData(paramInt1 + 2, paramInt2 + 1, paramInt3, Blocks.STEP, 1, 2);
    paramWorld.setTypeAndData(paramInt1 - 2, paramInt2 + 1, paramInt3, Blocks.STEP, 1, 2);
    paramWorld.setTypeAndData(paramInt1, paramInt2 + 1, paramInt3 + 2, Blocks.STEP, 1, 2);
    paramWorld.setTypeAndData(paramInt1, paramInt2 + 1, paramInt3 - 2, Blocks.STEP, 1, 2);
    

    for (i = -1; i <= 1; i++) {
      for (j = -1; j <= 1; j++) {
        if ((i == 0) && (j == 0)) {
          paramWorld.setTypeAndData(paramInt1 + i, paramInt2 + 4, paramInt3 + j, Blocks.SANDSTONE, 0, 2);
        } else {
          paramWorld.setTypeAndData(paramInt1 + i, paramInt2 + 4, paramInt3 + j, Blocks.STEP, 1, 2);
        }
      }
    }
    

    for (i = 1; i <= 3; i++) {
      paramWorld.setTypeAndData(paramInt1 - 1, paramInt2 + i, paramInt3 - 1, Blocks.SANDSTONE, 0, 2);
      paramWorld.setTypeAndData(paramInt1 - 1, paramInt2 + i, paramInt3 + 1, Blocks.SANDSTONE, 0, 2);
      paramWorld.setTypeAndData(paramInt1 + 1, paramInt2 + i, paramInt3 - 1, Blocks.SANDSTONE, 0, 2);
      paramWorld.setTypeAndData(paramInt1 + 1, paramInt2 + i, paramInt3 + 1, Blocks.SANDSTONE, 0, 2);
    }
    
    return true;
  }
}
