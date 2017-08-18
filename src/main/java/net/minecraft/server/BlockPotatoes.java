package net.minecraft.server;

import java.util.Random;
















public class BlockPotatoes
  extends BlockCrops
{
  protected Item i()
  {
    return Items.POTATO;
  }
  
  protected Item P()
  {
    return Items.POTATO;
  }
  
  public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, paramInt5);
    
    if (paramWorld.isStatic) {
      return;
    }
    if ((paramInt4 >= 7) && 
      (paramWorld.random.nextInt(50) == 0)) {
      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.POTATO_POISON));
    }
  }
}
