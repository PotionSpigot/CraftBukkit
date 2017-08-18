package net.minecraft.server;

import java.util.Random;


public class WorldGenPackedIce1
  extends WorldGenerator
{
  private Block a;
  private int b;
  
  public WorldGenPackedIce1(int paramInt)
  {
    this.a = Blocks.PACKED_ICE;
    this.b = paramInt;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    while ((paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) && (paramInt2 > 2)) {
      paramInt2--;
    }
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SNOW_BLOCK) {
      return false;
    }
    int i = paramRandom.nextInt(this.b - 2) + 2;
    int j = 1;
    for (int k = paramInt1 - i; k <= paramInt1 + i; k++) {
      for (int m = paramInt3 - i; m <= paramInt3 + i; m++) {
        int n = k - paramInt1;
        int i1 = m - paramInt3;
        if (n * n + i1 * i1 <= i * i) {
          for (int i2 = paramInt2 - j; i2 <= paramInt2 + j; i2++) {
            Block localBlock = paramWorld.getType(k, i2, m);
            if ((localBlock == Blocks.DIRT) || (localBlock == Blocks.SNOW_BLOCK) || (localBlock == Blocks.ICE)) {
              paramWorld.setTypeAndData(k, i2, m, this.a, 0, 2);
            }
          }
        }
      }
    }
    return true;
  }
}
