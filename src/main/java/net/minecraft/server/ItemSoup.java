package net.minecraft.server;

public class ItemSoup
  extends ItemFood
{
  public ItemSoup(int paramInt)
  {
    super(paramInt, false);
    
    e(1);
  }
  
  public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    super.b(paramItemStack, paramWorld, paramEntityHuman);
    
    return new ItemStack(Items.BOWL);
  }
}
