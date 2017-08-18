package net.minecraft.server;

import java.util.Random;




public class WorldGenMegaTree
  extends WorldGenMegaTreeAbstract
{
  private boolean e;
  
  public WorldGenMegaTree(boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramBoolean1, 13, 15, 1, 1);
    this.e = paramBoolean2;
  }
  

  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = a(paramRandom);
    if (!a(paramWorld, paramRandom, paramInt1, paramInt2, paramInt3, i)) { return false;
    }
    c(paramWorld, paramInt1, paramInt3, paramInt2 + i, 0, paramRandom);
    
    for (int j = 0; j < i; j++) {
      Block localBlock = paramWorld.getType(paramInt1, paramInt2 + j, paramInt3);
      if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
        setTypeAndData(paramWorld, paramInt1, paramInt2 + j, paramInt3, Blocks.LOG, this.b);
      }
      if (j < i - 1) {
        localBlock = paramWorld.getType(paramInt1 + 1, paramInt2 + j, paramInt3);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + j, paramInt3, Blocks.LOG, this.b);
        }
        localBlock = paramWorld.getType(paramInt1 + 1, paramInt2 + j, paramInt3 + 1);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1 + 1, paramInt2 + j, paramInt3 + 1, Blocks.LOG, this.b);
        }
        localBlock = paramWorld.getType(paramInt1, paramInt2 + j, paramInt3 + 1);
        if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
          setTypeAndData(paramWorld, paramInt1, paramInt2 + j, paramInt3 + 1, Blocks.LOG, this.b);
        }
      }
    }
    
    return true;
  }
  
  private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Random paramRandom) {
    int i = paramRandom.nextInt(5);
    if (this.e) {
      i += this.a;
    } else {
      i += 3;
    }
    int j = 0;
    for (int k = paramInt3 - i; k <= paramInt3; k++) {
      int m = paramInt3 - k;
      int n = paramInt4 + MathHelper.d(m / i * 3.5F);
      a(paramWorld, paramInt1, k, paramInt2, n + ((m > 0) && (n == j) && ((k & 0x1) == 0) ? 1 : 0), paramRandom);
      j = n;
    }
  }
  
  public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    c(paramWorld, paramRandom, paramInt1 - 1, paramInt2, paramInt3 - 1);
    c(paramWorld, paramRandom, paramInt1 + 2, paramInt2, paramInt3 - 1);
    c(paramWorld, paramRandom, paramInt1 - 1, paramInt2, paramInt3 + 2);
    c(paramWorld, paramRandom, paramInt1 + 2, paramInt2, paramInt3 + 2);
    
    for (int i = 0; i < 5; i++) {
      int j = paramRandom.nextInt(64);
      int k = j % 8;
      int m = j / 8;
      if ((k == 0) || (k == 7) || (m == 0) || (m == 7)) {
        c(paramWorld, paramRandom, paramInt1 - 3 + k, paramInt2, paramInt3 - 3 + m);
      }
    }
  }
  
  private void c(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = -2; i <= 2; i++) {
      for (int j = -2; j <= 2; j++) {
        if ((Math.abs(i) != 2) || (Math.abs(j) != 2)) {
          a(paramWorld, paramInt1 + i, paramInt2, paramInt3 + j);
        }
      }
    }
  }
  
  private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = paramInt2 + 2; i >= paramInt2 - 3; i--) {
      Block localBlock = paramWorld.getType(paramInt1, i, paramInt3);
      if ((localBlock == Blocks.GRASS) || (localBlock == Blocks.DIRT)) {
        setTypeAndData(paramWorld, paramInt1, i, paramInt3, Blocks.DIRT, 2);
      } else {
        if ((localBlock.getMaterial() != Material.AIR) && (i < paramInt2)) {
          break;
        }
      }
    }
  }
}
