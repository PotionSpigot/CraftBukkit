package net.minecraft.server;

import java.util.Random;



public class WorldGenSwampTree
  extends WorldGenTreeAbstract
{
  public WorldGenSwampTree()
  {
    super(false);
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(4) + 5;
    while (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3).getMaterial() == Material.WATER) {
      paramInt2--;
    }
    int j = 1;
    if ((paramInt2 < 1) || (paramInt2 + i + 1 > 256)) return false;
    int n;
    int i2; for (int k = paramInt2; k <= paramInt2 + 1 + i; k++) {
      m = 1;
      if (k == paramInt2) m = 0;
      if (k >= paramInt2 + 1 + i - 2) m = 3;
      for (n = paramInt1 - m; (n <= paramInt1 + m) && (j != 0); n++) {
        for (i2 = paramInt3 - m; (i2 <= paramInt3 + m) && (j != 0); i2++) {
          if ((k >= 0) && (k < 256)) {
            Block localBlock3 = paramWorld.getType(n, k, i2);
            if ((localBlock3.getMaterial() != Material.AIR) && (localBlock3.getMaterial() != Material.LEAVES)) {
              if ((localBlock3 == Blocks.STATIONARY_WATER) || (localBlock3 == Blocks.WATER)) {
                if (k > paramInt2) j = 0;
              } else {
                j = 0;
              }
            }
          } else {
            j = 0;
          }
        }
      }
    }
    
    if (j == 0) { return false;
    }
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT)) || (paramInt2 >= 256 - i - 1)) {
      return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    int i3;
    int i4; for (int m = paramInt2 - 3 + i; m <= paramInt2 + i; m++) {
      n = m - (paramInt2 + i);
      i2 = 2 - n / 2;
      for (i3 = paramInt1 - i2; i3 <= paramInt1 + i2; i3++) {
        i4 = i3 - paramInt1;
        for (int i5 = paramInt3 - i2; i5 <= paramInt3 + i2; i5++) {
          int i6 = i5 - paramInt3;
          if (((Math.abs(i4) != i2) || (Math.abs(i6) != i2) || ((paramRandom.nextInt(2) != 0) && (n != 0))) && 
            (!paramWorld.getType(i3, m, i5).j())) { setType(paramWorld, i3, m, i5, Blocks.LEAVES);
          }
        }
      }
    }
    for (m = 0; m < i; m++) {
      Block localBlock2 = paramWorld.getType(paramInt1, paramInt2 + m, paramInt3);
      if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES) || (localBlock2 == Blocks.WATER) || (localBlock2 == Blocks.STATIONARY_WATER)) {
        setType(paramWorld, paramInt1, paramInt2 + m, paramInt3, Blocks.LOG);
      }
    }
    for (m = paramInt2 - 3 + i; m <= paramInt2 + i; m++) {
      int i1 = m - (paramInt2 + i);
      i2 = 2 - i1 / 2;
      for (i3 = paramInt1 - i2; i3 <= paramInt1 + i2; i3++) {
        for (i4 = paramInt3 - i2; i4 <= paramInt3 + i2; i4++) {
          if (paramWorld.getType(i3, m, i4).getMaterial() == Material.LEAVES) {
            if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i3 - 1, m, i4).getMaterial() == Material.AIR)) {
              a(paramWorld, i3 - 1, m, i4, 8);
            }
            if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i3 + 1, m, i4).getMaterial() == Material.AIR)) {
              a(paramWorld, i3 + 1, m, i4, 2);
            }
            if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i3, m, i4 - 1).getMaterial() == Material.AIR)) {
              a(paramWorld, i3, m, i4 - 1, 1);
            }
            if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i3, m, i4 + 1).getMaterial() == Material.AIR)) {
              a(paramWorld, i3, m, i4 + 1, 4);
            }
          }
        }
      }
    }
    return true;
  }
  
  private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
    int i = 4;
    while ((paramWorld.getType(paramInt1, --paramInt2, paramInt3).getMaterial() == Material.AIR) && (i > 0)) {
      setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.VINE, paramInt4);
      i--;
    }
  }
}
