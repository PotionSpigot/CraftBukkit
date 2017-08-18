package net.minecraft.server;

import java.util.Random;




public class WorldGenHugeMushroom
  extends WorldGenerator
{
  private int a = -1;
  
  public WorldGenHugeMushroom(int paramInt) {
    super(true);
    this.a = paramInt;
  }
  
  public WorldGenHugeMushroom() {
    super(false);
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(2);
    if (this.a >= 0) { i = this.a;
    }
    int j = paramRandom.nextInt(3) + 4;
    
    int k = 1;
    if ((paramInt2 < 1) || (paramInt2 + j + 1 >= 256)) return false;
    int i2;
    for (int m = paramInt2; m <= paramInt2 + 1 + j; m++) {
      n = 3;
      if (m <= paramInt2 + 3) n = 0;
      for (i1 = paramInt1 - n; (i1 <= paramInt1 + n) && (k != 0); i1++) {
        for (i2 = paramInt3 - n; (i2 <= paramInt3 + n) && (k != 0); i2++) {
          if ((m >= 0) && (m < 256)) {
            Block localBlock3 = paramWorld.getType(i1, m, i2);
            if ((localBlock3.getMaterial() != Material.AIR) && (localBlock3.getMaterial() != Material.LEAVES)) {
              k = 0;
            }
          } else {
            k = 0;
          }
        }
      }
    }
    
    if (k == 0) { return false;
    }
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if ((localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.MYCEL)) {
      return false;
    }
    
    int n = paramInt2 + j;
    if (i == 1) {
      n = paramInt2 + j - 3;
    }
    for (int i1 = n; i1 <= paramInt2 + j; i1++) {
      i2 = 1;
      if (i1 < paramInt2 + j) i2++;
      if (i == 0) i2 = 3;
      for (int i3 = paramInt1 - i2; i3 <= paramInt1 + i2; i3++) {
        for (int i4 = paramInt3 - i2; i4 <= paramInt3 + i2; i4++) {
          int i5 = 5;
          if (i3 == paramInt1 - i2) i5--;
          if (i3 == paramInt1 + i2) i5++;
          if (i4 == paramInt3 - i2) i5 -= 3;
          if (i4 == paramInt3 + i2) { i5 += 3;
          }
          if ((i == 0) || (i1 < paramInt2 + j)) {
            if (((i3 == paramInt1 - i2) || (i3 == paramInt1 + i2)) && ((i4 == paramInt3 - i2) || (i4 == paramInt3 + i2))) continue;
            if ((i3 == paramInt1 - (i2 - 1)) && (i4 == paramInt3 - i2)) i5 = 1;
            if ((i3 == paramInt1 - i2) && (i4 == paramInt3 - (i2 - 1))) { i5 = 1;
            }
            if ((i3 == paramInt1 + (i2 - 1)) && (i4 == paramInt3 - i2)) i5 = 3;
            if ((i3 == paramInt1 + i2) && (i4 == paramInt3 - (i2 - 1))) { i5 = 3;
            }
            if ((i3 == paramInt1 - (i2 - 1)) && (i4 == paramInt3 + i2)) i5 = 7;
            if ((i3 == paramInt1 - i2) && (i4 == paramInt3 + (i2 - 1))) { i5 = 7;
            }
            if ((i3 == paramInt1 + (i2 - 1)) && (i4 == paramInt3 + i2)) i5 = 9;
            if ((i3 == paramInt1 + i2) && (i4 == paramInt3 + (i2 - 1))) { i5 = 9;
            }
          }
          if ((i5 == 5) && (i1 < paramInt2 + j)) i5 = 0;
          if ((i5 != 0) || (paramInt2 >= paramInt2 + j - 1))
          {
            if (!paramWorld.getType(i3, i1, i4).j()) setTypeAndData(paramWorld, i3, i1, i4, Block.getById(Block.getId(Blocks.BIG_MUSHROOM_1) + i), i5);
          }
        }
      }
    }
    for (i1 = 0; i1 < j; i1++) {
      Block localBlock2 = paramWorld.getType(paramInt1, paramInt2 + i1, paramInt3);
      
      if (!localBlock2.j()) setTypeAndData(paramWorld, paramInt1, paramInt2 + i1, paramInt3, Block.getById(Block.getId(Blocks.BIG_MUSHROOM_1) + i), 10);
    }
    return true;
  }
}
