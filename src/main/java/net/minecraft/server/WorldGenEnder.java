package net.minecraft.server;

import java.util.Random;




public class WorldGenEnder
  extends WorldGenerator
{
  private Block a;
  
  public WorldGenEnder(Block paramBlock)
  {
    this.a = paramBlock;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) || (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) != this.a)) {
      return false;
    }
    int i = paramRandom.nextInt(32) + 6;
    int j = paramRandom.nextInt(4) + 1;
    int m; int n; int i1; for (int k = paramInt1 - j; k <= paramInt1 + j; k++) {
      for (m = paramInt3 - j; m <= paramInt3 + j; m++) {
        n = k - paramInt1;
        i1 = m - paramInt3;
        if ((n * n + i1 * i1 <= j * j + 1) && 
          (paramWorld.getType(k, paramInt2 - 1, m) != this.a)) return false;
      }
    }
    for (k = paramInt2; k < paramInt2 + i; k++) {
      if (k >= 256) break;
      for (m = paramInt1 - j; m <= paramInt1 + j; m++) {
        for (n = paramInt3 - j; n <= paramInt3 + j; n++) {
          i1 = m - paramInt1;
          int i2 = n - paramInt3;
          if (i1 * i1 + i2 * i2 <= j * j + 1) {
            paramWorld.setTypeAndData(m, k, n, Blocks.OBSIDIAN, 0, 2);
          }
        }
      }
    }
    
    EntityEnderCrystal localEntityEnderCrystal = new EntityEnderCrystal(paramWorld);
    localEntityEnderCrystal.setPositionRotation(paramInt1 + 0.5F, paramInt2 + i, paramInt3 + 0.5F, paramRandom.nextFloat() * 360.0F, 0.0F);
    paramWorld.addEntity(localEntityEnderCrystal);
    paramWorld.setTypeAndData(paramInt1, paramInt2 + i, paramInt3, Blocks.BEDROCK, 0, 2);
    
    return true;
  }
}
