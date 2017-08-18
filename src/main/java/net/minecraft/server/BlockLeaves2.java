package net.minecraft.server;

import java.util.Random;










public class BlockLeaves2
  extends BlockLeaves
{
  public static final String[][] N = { { "leaves_acacia", "leaves_big_oak" }, { "leaves_acacia_opaque", "leaves_big_oak_opaque" } };
  





  public static final String[] O = { "acacia", "big_oak" };
  


  protected void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (((paramInt4 & 0x3) == 1) && (paramWorld.random.nextInt(paramInt5) == 0)) {
      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.APPLE, 1, 0));
    }
  }
  
  public int getDropData(int paramInt)
  {
    return super.getDropData(paramInt) + 4;
  }
  
  public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x3;
  }
  

























  public String[] e()
  {
    return O;
  }
}
