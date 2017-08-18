package net.minecraft.server;

import java.util.Random;



public class WorldGenTaiga2
  extends WorldGenTreeAbstract
{
  public WorldGenTaiga2(boolean paramBoolean)
  {
    super(paramBoolean);
  }
  


  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(4) + 6;
    int j = 1 + paramRandom.nextInt(2);
    int k = i - j;
    int m = 2 + paramRandom.nextInt(2);
    
    int n = 1;
    
    if ((paramInt2 < 1) || (paramInt2 + i + 1 > 256)) {
      return false;
    }
    

    for (int i1 = paramInt2; (i1 <= paramInt2 + 1 + i) && (n != 0); i1++)
    {
      i2 = 1;
      if (i1 - paramInt2 < j) {
        i2 = 0;
      } else {
        i2 = m;
      }
      for (i3 = paramInt1 - i2; (i3 <= paramInt1 + i2) && (n != 0); i3++) {
        for (i4 = paramInt3 - i2; (i4 <= paramInt3 + i2) && (n != 0); i4++) {
          if ((i1 >= 0) && (i1 < 256)) {
            Block localBlock2 = paramWorld.getType(i3, i1, i4);
            if ((localBlock2.getMaterial() != Material.AIR) && (localBlock2.getMaterial() != Material.LEAVES)) n = 0;
          } else {
            n = 0;
          }
        }
      }
    }
    
    if (n == 0) { return false;
    }
    
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.SOIL)) || (paramInt2 >= 256 - i - 1)) {
      return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    

    int i2 = paramRandom.nextInt(2);
    int i3 = 1;
    int i4 = 0;
    for (int i5 = 0; i5 <= k; i5++)
    {
      i6 = paramInt2 + i - i5;
      
      for (int i7 = paramInt1 - i2; i7 <= paramInt1 + i2; i7++) {
        int i8 = i7 - paramInt1;
        for (int i9 = paramInt3 - i2; i9 <= paramInt3 + i2; i9++) {
          int i10 = i9 - paramInt3;
          if (((Math.abs(i8) != i2) || (Math.abs(i10) != i2) || (i2 <= 0)) && 
            (!paramWorld.getType(i7, i6, i9).j())) {
            setTypeAndData(paramWorld, i7, i6, i9, Blocks.LEAVES, 1);
          }
        }
      }
      if (i2 >= i3) {
        i2 = i4;
        i4 = 1;
        i3++;
        if (i3 > m) {
          i3 = m;
        }
      } else {
        i2++;
      }
    }
    i5 = paramRandom.nextInt(3);
    for (int i6 = 0; i6 < i - i5; i6++) {
      Block localBlock3 = paramWorld.getType(paramInt1, paramInt2 + i6, paramInt3);
      if ((localBlock3.getMaterial() == Material.AIR) || (localBlock3.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, paramInt1, paramInt2 + i6, paramInt3, Blocks.LOG, 1);
    }
    return true;
  }
}
