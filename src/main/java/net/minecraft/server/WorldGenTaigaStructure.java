package net.minecraft.server;

import java.util.Random;


public class WorldGenTaigaStructure
  extends WorldGenerator
{
  private Block a;
  private int b;
  
  public WorldGenTaigaStructure(Block paramBlock, int paramInt)
  {
    super(false);
    this.a = paramBlock;
    this.b = paramInt;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    while (paramInt2 > 3) {
      if (!paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3)) {
        Block localBlock = paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3);
        if ((localBlock == Blocks.GRASS) || (localBlock == Blocks.DIRT) || (localBlock == Blocks.STONE)) {
          break;
        }
      }
      paramInt2--;
    }
    if (paramInt2 <= 3) {
      return false;
    }
    
    int i = this.b;
    int j = 0;
    while ((i >= 0) && (j < 3))
    {
      int k = i + paramRandom.nextInt(2);
      int m = i + paramRandom.nextInt(2);
      int n = i + paramRandom.nextInt(2);
      float f1 = (k + m + n) * 0.333F + 0.5F;
      for (int i1 = paramInt1 - k; i1 <= paramInt1 + k; i1++) {
        for (int i2 = paramInt3 - n; i2 <= paramInt3 + n; i2++) {
          for (int i3 = paramInt2 - m; i3 <= paramInt2 + m; i3++) {
            float f2 = i1 - paramInt1;
            float f3 = i2 - paramInt3;
            float f4 = i3 - paramInt2;
            if (f2 * f2 + f3 * f3 + f4 * f4 <= f1 * f1)
            {
              paramWorld.setTypeAndData(i1, i3, i2, this.a, 0, 4);
            }
          }
        }
      }
      paramInt1 += -(i + 1) + paramRandom.nextInt(2 + i * 2);
      paramInt3 += -(i + 1) + paramRandom.nextInt(2 + i * 2);
      paramInt2 += 0 - paramRandom.nextInt(2);
      j++;
    }
    
    return true;
  }
}
