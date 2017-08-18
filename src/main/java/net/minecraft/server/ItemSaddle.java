package net.minecraft.server;


public class ItemSaddle
  extends Item
{
  public ItemSaddle()
  {
    this.maxStackSize = 1;
    a(CreativeModeTab.e);
  }
  
  public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving)
  {
    if ((paramEntityLiving instanceof EntityPig)) {
      EntityPig localEntityPig = (EntityPig)paramEntityLiving;
      if ((!localEntityPig.hasSaddle()) && (!localEntityPig.isBaby())) {
        localEntityPig.setSaddle(true);
        localEntityPig.world.makeSound(localEntityPig, "mob.horse.leather", 0.5F, 1.0F);
        paramItemStack.count -= 1;
      }
      
      return true;
    }
    return false;
  }
  
  public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
  {
    a(paramItemStack, null, paramEntityLiving1);
    return true;
  }
}
