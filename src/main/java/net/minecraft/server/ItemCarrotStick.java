package net.minecraft.server;


public class ItemCarrotStick
  extends Item
{
  public ItemCarrotStick()
  {
    a(CreativeModeTab.e);
    e(1);
    setMaxDurability(25);
  }
  










  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if ((paramEntityHuman.am()) && ((paramEntityHuman.vehicle instanceof EntityPig))) {
      EntityPig localEntityPig = (EntityPig)paramEntityHuman.vehicle;
      
      if ((localEntityPig.ca().h()) && (paramItemStack.l() - paramItemStack.getData() >= 7)) {
        localEntityPig.ca().g();
        paramItemStack.damage(7, paramEntityHuman);
        
        if (paramItemStack.count == 0) {
          ItemStack localItemStack = new ItemStack(Items.FISHING_ROD);
          localItemStack.setTag(paramItemStack.tag);
          return localItemStack;
        }
      }
    }
    
    return paramItemStack;
  }
}
