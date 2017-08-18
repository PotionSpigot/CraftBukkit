package net.minecraft.server;

public class ItemCloth
  extends ItemBlock
{
  public ItemCloth(Block paramBlock)
  {
    super(paramBlock);
    
    setMaxDurability(0);
    a(true);
  }
  





  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public String a(ItemStack paramItemStack)
  {
    return super.getName() + "." + ItemDye.a[BlockCloth.b(paramItemStack.getData())];
  }
}
