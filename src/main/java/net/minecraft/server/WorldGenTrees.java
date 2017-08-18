package net.minecraft.server;

import java.util.Random;



public class WorldGenTrees
  extends WorldGenTreeAbstract
{
  private final int a;
  private final boolean b;
  private final int c;
  private final int d;
  
  public WorldGenTrees(boolean paramBoolean)
  {
    this(paramBoolean, 4, 0, 0, false);
  }
  
  public WorldGenTrees(boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean2) {
    super(paramBoolean1);
    this.a = paramInt1;
    this.c = paramInt2;
    this.d = paramInt3;
    this.b = paramBoolean2;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(3) + this.a;
    
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
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.SOIL)) || (paramInt2 >= 256 - i - 1)) { return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    
    int m = 3;
    int n = 0;
    int i4; int i5; int i6; for (int i1 = paramInt2 - m + i; i1 <= paramInt2 + i; i1++) {
      int i2 = i1 - (paramInt2 + i);
      i4 = n + 1 - i2 / 2;
      for (i5 = paramInt1 - i4; i5 <= paramInt1 + i4; i5++) {
        i6 = i5 - paramInt1;
        for (int i7 = paramInt3 - i4; i7 <= paramInt3 + i4; i7++) {
          int i8 = i7 - paramInt3;
          if ((Math.abs(i6) != i4) || (Math.abs(i8) != i4) || ((paramRandom.nextInt(2) != 0) && (i2 != 0))) {
            Block localBlock4 = paramWorld.getType(i5, i1, i7);
            if ((localBlock4.getMaterial() == Material.AIR) || (localBlock4.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, i5, i1, i7, Blocks.LEAVES, this.d);
          }
        }
      } }
    for (i1 = 0; i1 < i; i1++) {
      Block localBlock3 = paramWorld.getType(paramInt1, paramInt2 + i1, paramInt3);
      if ((localBlock3.getMaterial() == Material.AIR) || (localBlock3.getMaterial() == Material.LEAVES)) {
        setTypeAndData(paramWorld, paramInt1, paramInt2 + i1, paramInt3, Blocks.LOG, this.c);
        if ((this.b) && (i1 > 0)) {
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 - 1, paramInt2 + i1, paramInt3))) {
            setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + i1, paramInt3, Blocks.VINE, 8);
          }
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 + 1, paramInt2 + i1, paramInt3))) {
            setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + i1, paramInt3, Blocks.VINE, 2);
          }
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1, paramInt2 + i1, paramInt3 - 1))) {
            setTypeAndData(paramWorld, paramInt1, paramInt2 + i1, paramInt3 - 1, Blocks.VINE, 1);
          }
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1, paramInt2 + i1, paramInt3 + 1))) {
            setTypeAndData(paramWorld, paramInt1, paramInt2 + i1, paramInt3 + 1, Blocks.VINE, 4);
          }
        }
      }
    }
    
    if (this.b) { int i3;
      for (i1 = paramInt2 - 3 + i; i1 <= paramInt2 + i; i1++) {
        i3 = i1 - (paramInt2 + i);
        i4 = 2 - i3 / 2;
        for (i5 = paramInt1 - i4; i5 <= paramInt1 + i4; i5++) {
          for (i6 = paramInt3 - i4; i6 <= paramInt3 + i4; i6++) {
            if (paramWorld.getType(i5, i1, i6).getMaterial() == Material.LEAVES) {
              if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i5 - 1, i1, i6).getMaterial() == Material.AIR)) {
                a(paramWorld, i5 - 1, i1, i6, 8);
              }
              if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i5 + 1, i1, i6).getMaterial() == Material.AIR)) {
                a(paramWorld, i5 + 1, i1, i6, 2);
              }
              if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i5, i1, i6 - 1).getMaterial() == Material.AIR)) {
                a(paramWorld, i5, i1, i6 - 1, 1);
              }
              if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(i5, i1, i6 + 1).getMaterial() == Material.AIR)) {
                a(paramWorld, i5, i1, i6 + 1, 4);
              }
            }
          }
        }
      }
      

      if ((paramRandom.nextInt(5) == 0) && (i > 5)) {
        for (i1 = 0; i1 < 2; i1++) {
          for (i3 = 0; i3 < 4; i3++) {
            if (paramRandom.nextInt(4 - i1) == 0) {
              i4 = paramRandom.nextInt(3);
              setTypeAndData(paramWorld, paramInt1 + Direction.a[Direction.f[i3]], paramInt2 + i - 5 + i1, paramInt3 + Direction.b[Direction.f[i3]], Blocks.COCOA, i4 << 2 | i3);
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
