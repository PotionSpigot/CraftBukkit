package net.minecraft.server;

import java.util.Random;




public class WorldGenJungleTree
  extends WorldGenMegaTreeAbstract
{
  public WorldGenJungleTree(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  

  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = a(paramRandom);
    if (!a(paramWorld, paramRandom, paramInt1, paramInt2, paramInt3, i)) { return false;
    }
    c(paramWorld, paramInt1, paramInt3, paramInt2 + i, 2, paramRandom);
    
    int j = paramInt2 + i - 2 - paramRandom.nextInt(4);
    while (j > paramInt2 + i / 2) {
      float f = paramRandom.nextFloat() * 3.1415927F * 2.0F;
      int m = paramInt1 + (int)(0.5F + MathHelper.cos(f) * 4.0F);
      int n = paramInt3 + (int)(0.5F + MathHelper.sin(f) * 4.0F);
      
      for (int i1 = 0; i1 < 5; i1++) {
        m = paramInt1 + (int)(1.5F + MathHelper.cos(f) * i1);
        n = paramInt3 + (int)(1.5F + MathHelper.sin(f) * i1);
        setTypeAndData(paramWorld, m, j - 3 + i1 / 2, n, Blocks.LOG, this.b);
      }
      i1 = 1 + paramRandom.nextInt(2);
      int i2 = j;
      for (int i3 = i2 - i1; i3 <= i2; i3++) {
        int i4 = i3 - i2;
        b(paramWorld, m, i3, n, 1 - i4, paramRandom);
      }
      
      j -= 2 + paramRandom.nextInt(4);
    }
    
    for (int k = 0; k < i; k++) {
      Block localBlock = paramWorld.getType(paramInt1, paramInt2 + k, paramInt3);
      if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
        setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3, Blocks.LOG, this.b);
        if (k > 0) {
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 - 1, paramInt2 + k, paramInt3))) {
            setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + k, paramInt3, Blocks.VINE, 8);
          }
          if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1, paramInt2 + k, paramInt3 - 1))) {
            setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3 - 1, Blocks.VINE, 1);
          }
        }
      }
      if (k < i - 1) {
        localBlock = paramWorld.getType(paramInt1 + 1, paramInt2 + k, paramInt3);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + k, paramInt3, Blocks.LOG, this.b);
          if (k > 0) {
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 + 2, paramInt2 + k, paramInt3))) {
              setTypeAndData(paramWorld, paramInt1 + 2, paramInt2 + k, paramInt3, Blocks.VINE, 2);
            }
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 + 1, paramInt2 + k, paramInt3 - 1))) {
              setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + k, paramInt3 - 1, Blocks.VINE, 1);
            }
          }
        }
        localBlock = paramWorld.getType(paramInt1 + 1, paramInt2 + k, paramInt3 + 1);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + k, paramInt3 + 1, Blocks.LOG, this.b);
          if (k > 0) {
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 + 2, paramInt2 + k, paramInt3 + 1))) {
              setTypeAndData(paramWorld, paramInt1 + 2, paramInt2 + k, paramInt3 + 1, Blocks.VINE, 2);
            }
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 + 1, paramInt2 + k, paramInt3 + 2))) {
              setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + k, paramInt3 + 2, Blocks.VINE, 4);
            }
          }
        }
        localBlock = paramWorld.getType(paramInt1, paramInt2 + k, paramInt3 + 1);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3 + 1, Blocks.LOG, this.b);
          if (k > 0) {
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1 - 1, paramInt2 + k, paramInt3 + 1))) {
              setTypeAndData(paramWorld, paramInt1 - 1, paramInt2 + k, paramInt3 + 1, Blocks.VINE, 8);
            }
            if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramInt1, paramInt2 + k, paramInt3 + 2))) {
              setTypeAndData(paramWorld, paramInt1, paramInt2 + k, paramInt3 + 2, Blocks.VINE, 4);
            }
          }
        }
      }
    }
    
    return true;
  }
  
  private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Random paramRandom) {
    int i = 2;
    for (int j = paramInt3 - i; j <= paramInt3; j++) {
      int k = j - paramInt3;
      a(paramWorld, paramInt1, j, paramInt2, paramInt4 + 1 - k, paramRandom);
    }
  }
}
