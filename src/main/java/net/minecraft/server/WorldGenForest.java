package net.minecraft.server;

import java.util.Random;






public class WorldGenForest
  extends WorldGenTreeAbstract
{
  private boolean a;
  
  public WorldGenForest(boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramBoolean1);
    this.a = paramBoolean2;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(3) + 5;
    if (this.a) {
      i += paramRandom.nextInt(7);
    }
    
    int j = 1;
    if ((paramInt2 < 1) || (paramInt2 + i + 1 > 256)) return false;
    int n;
    int i1; for (int k = paramInt2; k <= paramInt2 + 1 + i; k++) {
      m = 1;
      if (k == paramInt2) m = 0;
      if (k >= paramInt2 + 1 + i - 2) m = 2;
      for (n = paramInt1 - m; (n <= paramInt1 + m) && (j != 0); n++) {
        for (i1 = paramInt3 - m; (i1 <= paramInt3 + m) && (j != 0); i1++) {
          if ((k >= 0) && (k < 256)) {
            Block localBlock3 = paramWorld.getType(n, k, i1);
            if (!a(localBlock3)) j = 0;
          } else {
            j = 0;
          }
        }
      }
    }
    
    if (j == 0) { return false;
    }
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.SOIL)) || (paramInt2 >= 256 - i - 1)) { return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    
    for (int m = paramInt2 - 3 + i; m <= paramInt2 + i; m++) {
      n = m - (paramInt2 + i);
      i1 = 1 - n / 2;
      for (int i2 = paramInt1 - i1; i2 <= paramInt1 + i1; i2++) {
        int i3 = i2 - paramInt1;
        for (int i4 = paramInt3 - i1; i4 <= paramInt3 + i1; i4++) {
          int i5 = i4 - paramInt3;
          if ((Math.abs(i3) != i1) || (Math.abs(i5) != i1) || ((paramRandom.nextInt(2) != 0) && (n != 0))) {
            Block localBlock4 = paramWorld.getType(i2, m, i4);
            if ((localBlock4.getMaterial() == Material.AIR) || (localBlock4.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, i2, m, i4, Blocks.LEAVES, 2);
          }
        }
      } }
    for (m = 0; m < i; m++) {
      Block localBlock2 = paramWorld.getType(paramInt1, paramInt2 + m, paramInt3);
      if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, paramInt1, paramInt2 + m, paramInt3, Blocks.LOG, 2);
    }
    return true;
  }
}
