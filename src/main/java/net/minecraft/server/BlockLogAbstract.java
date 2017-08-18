package net.minecraft.server;

import java.util.Random;










public abstract class BlockLogAbstract
  extends BlockRotatable
{
  public BlockLogAbstract()
  {
    super(Material.WOOD);
    a(CreativeModeTab.b);
    c(2.0F);
    a(f);
  }
  
  public static int c(int paramInt) {
    return paramInt & 0x3;
  }
  
  public int a(Random paramRandom)
  {
    return 1;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(this);
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    int i = 4;
    int j = i + 1;
    
    if (paramWorld.b(paramInt1 - j, paramInt2 - j, paramInt3 - j, paramInt1 + j, paramInt2 + j, paramInt3 + j)) {
      for (int k = -i; k <= i; k++) {
        for (int m = -i; m <= i; m++) {
          for (int n = -i; n <= i; n++) {
            if (paramWorld.getType(paramInt1 + k, paramInt2 + m, paramInt3 + n).getMaterial() == Material.LEAVES) {
              int i1 = paramWorld.getData(paramInt1 + k, paramInt2 + m, paramInt3 + n);
              if ((i1 & 0x8) == 0) {
                paramWorld.setData(paramInt1 + k, paramInt2 + m, paramInt3 + n, i1 | 0x8, 4);
              }
            }
          }
        }
      }
    }
  }
}
