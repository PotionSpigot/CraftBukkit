package net.minecraft.server;


public class ItemNameTag
  extends Item
{
  public ItemNameTag()
  {
    a(CreativeModeTab.i);
  }
  
  public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving)
  {
    if (!paramItemStack.hasName()) { return false;
    }
    if ((paramEntityLiving instanceof EntityInsentient)) {
      EntityInsentient localEntityInsentient = (EntityInsentient)paramEntityLiving;
      localEntityInsentient.setCustomName(paramItemStack.getName());
      localEntityInsentient.bF();
      paramItemStack.count -= 1;
      return true;
    }
    
    return super.a(paramItemStack, paramEntityHuman, paramEntityLiving);
  }
}
