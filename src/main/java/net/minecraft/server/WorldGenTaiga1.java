package net.minecraft.server;

import java.util.Random;



public class WorldGenTaiga1
  extends WorldGenTreeAbstract
{
  public WorldGenTaiga1()
  {
    super(false);
  }
  


  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRandom.nextInt(5) + 7;
    int j = i - paramRandom.nextInt(2) - 3;
    int k = i - j;
    int m = 1 + paramRandom.nextInt(k + 1);
    
    int n = 1;
    
    if ((paramInt2 < 1) || (paramInt2 + i + 1 > 256)) {
      return false;
    }
    
    int i4;
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
            Block localBlock3 = paramWorld.getType(i3, i1, i4);
            if (!a(localBlock3)) n = 0;
          } else {
            n = 0;
          }
        }
      }
    }
    
    if (n == 0) { return false;
    }
    
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
    if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT)) || (paramInt2 >= 256 - i - 1)) {
      return false;
    }
    setType(paramWorld, paramInt1, paramInt2 - 1, paramInt3, Blocks.DIRT);
    

    int i2 = 0;
    for (int i3 = paramInt2 + i; i3 >= paramInt2 + j; i3--)
    {
      for (i4 = paramInt1 - i2; i4 <= paramInt1 + i2; i4++) {
        int i5 = i4 - paramInt1;
        for (int i6 = paramInt3 - i2; i6 <= paramInt3 + i2; i6++) {
          int i7 = i6 - paramInt3;
          if (((Math.abs(i5) != i2) || (Math.abs(i7) != i2) || (i2 <= 0)) && 
            (!paramWorld.getType(i4, i3, i6).j())) {
            setTypeAndData(paramWorld, i4, i3, i6, Blocks.LEAVES, 1);
          }
        }
      }
      if ((i2 >= 1) && (i3 == paramInt2 + j + 1)) {
        i2--;
      } else if (i2 < m) {
        i2++;
      }
    }
    for (i3 = 0; i3 < i - 1; i3++) {
      Block localBlock2 = paramWorld.getType(paramInt1, paramInt2 + i3, paramInt3);
      if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES)) setTypeAndData(paramWorld, paramInt1, paramInt2 + i3, paramInt3, Blocks.LOG, 1);
    }
    return true;
  }
}
