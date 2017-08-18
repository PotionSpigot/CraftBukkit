package net.minecraft.server;

import java.util.Random;











public class BlockLeaves1
  extends BlockLeaves
{
  public static final String[][] N = { { "leaves_oak", "leaves_spruce", "leaves_birch", "leaves_jungle" }, { "leaves_oak_opaque", "leaves_spruce_opaque", "leaves_birch_opaque", "leaves_jungle_opaque" } };
  





  public static final String[] O = { "oak", "spruce", "birch", "jungle" };
  




























  protected void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (((paramInt4 & 0x3) == 0) && (paramWorld.random.nextInt(paramInt5) == 0)) {
      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.APPLE, 1, 0));
    }
  }
  
  protected int b(int paramInt)
  {
    int i = super.b(paramInt);
    
    if ((paramInt & 0x3) == 3) {
      i = 40;
    }
    
    return i;
  }
  

































  public String[] e()
  {
    return O;
  }
}
