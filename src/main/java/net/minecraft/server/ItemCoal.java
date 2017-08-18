package net.minecraft.server;










public class ItemCoal
  extends Item
{
  public ItemCoal()
  {
    a(true);
    setMaxDurability(0);
    a(CreativeModeTab.l);
  }
  
  public String a(ItemStack paramItemStack)
  {
    if (paramItemStack.getData() == 1) {
      return "item.charcoal";
    }
    return "item.coal";
  }
}
