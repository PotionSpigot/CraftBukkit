package net.minecraft.server;

import java.util.Random;







public class WorldGenAcaciaTree
  extends WorldGenTreeAbstract
{
  public WorldGenAcaciaTree(boolean paramBoolean)
  {
    super(paramBoolean);
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(3) + paramRandom.nextInt(3) + 5;
    
    int j = 1;
    if ((paramInt2 < 1) || (paramInt2 + i + 1 > 256)) { return false;
    }
    for (int k = paramInt2; k <= paramInt2 + 1 + i; k++) {
      m = 1;
      if (k == paramInt2) m = 0;
      if (k >= paramInt2 + 1 + i - 2) m = 2;
      for (n = paramInt1 - m; (n <= paramInt1 + m) && (j != 0); n++) {
        for (i1 = paramInt3 - m; (i1 <= paramInt3 + m) && (j != 0); i1++) {
          if ((k >= 0) && (k < 256)) {
            Block localBlock2 = paramWorld.getType(n, k, i1);
            if (!a(localBlock2)) j = 0;
          } else {
            j = 0;
          }
        }
      }
    }
    
    if (j == 0) { return false;
    }
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT)) || (paramInt2 >= 256 - i - 1)) { return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    
    int m = paramRandom.nextInt(4);
    int n = i - paramRandom.nextInt(4) - 1;
    int i1 = 3 - paramRandom.nextInt(3);
    
    int i2 = paramInt1;int i3 = paramInt3;
    int i4 = 0;
    int i6; for (int i5 = 0; i5 < i; i5++) {
      i6 = paramInt2 + i5;
      if ((i5 >= n) && (i1 > 0)) {
        i2 += Direction.a[m];
        i3 += Direction.b[m];
        i1--;
      }
      Block localBlock3 = paramWorld.getType(i2, i6, i3);
      if ((localBlock3.getMaterial() == Material.AIR) || (localBlock3.getMaterial() == Material.LEAVES)) {
        setTypeAndData(paramWorld, i2, i6, i3, Blocks.LOG2, 0);
        i4 = i6;
      }
    }
    
    for (i5 = -1; i5 <= 1; i5++) {
      for (i6 = -1; i6 <= 1; i6++) {
        a(paramWorld, i2 + i5, i4 + 1, i3 + i6);
      }
    }
    a(paramWorld, i2 + 2, i4 + 1, i3);
    a(paramWorld, i2 - 2, i4 + 1, i3);
    a(paramWorld, i2, i4 + 1, i3 + 2);
    a(paramWorld, i2, i4 + 1, i3 - 2);
    for (i5 = -3; i5 <= 3; i5++) {
      for (i6 = -3; i6 <= 3; i6++) {
        if ((Math.abs(i5) != 3) || (Math.abs(i6) != 3))
        {

          a(paramWorld, i2 + i5, i4, i3 + i6);
        }
      }
    }
    

    i2 = paramInt1;
    i3 = paramInt3;
    i5 = paramRandom.nextInt(4);
    if (i5 != m) {
      i6 = n - paramRandom.nextInt(2) - 1;
      int i7 = 1 + paramRandom.nextInt(3);
      
      i4 = 0;
      int i9; for (int i8 = i6; (i8 < i) && (i7 > 0); i7--) {
        if (i8 >= 1)
        {

          i9 = paramInt2 + i8;
          i2 += Direction.a[i5];
          i3 += Direction.b[i5];
          Block localBlock4 = paramWorld.getType(i2, i9, i3);
          if ((localBlock4.getMaterial() == Material.AIR) || (localBlock4.getMaterial() == Material.LEAVES)) {
            setTypeAndData(paramWorld, i2, i9, i3, Blocks.LOG2, 0);
            i4 = i9;
          }
        }
        i8++;
      }
      










      if (i4 > 0) {
        for (i8 = -1; i8 <= 1; i8++) {
          for (i9 = -1; i9 <= 1; i9++) {
            a(paramWorld, i2 + i8, i4 + 1, i3 + i9);
          }
        }
        for (i8 = -2; i8 <= 2; i8++) {
          for (i9 = -2; i9 <= 2; i9++) {
            if ((Math.abs(i8) != 2) || (Math.abs(i9) != 2))
            {

              a(paramWorld, i2 + i8, i4, i3 + i9);
            }
          }
        }
      }
    }
    return true;
  }
  
  private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.LEAVES2, 0);
  }
}
