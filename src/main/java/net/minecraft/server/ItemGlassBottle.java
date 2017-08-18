package net.minecraft.server;





public class ItemGlassBottle
  extends Item
{
  public ItemGlassBottle()
  {
    a(CreativeModeTab.k);
  }
  





  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, true);
    if (localMovingObjectPosition == null) { return paramItemStack;
    }
    if (localMovingObjectPosition.type == EnumMovingObjectType.BLOCK) {
      int i = localMovingObjectPosition.b;
      int j = localMovingObjectPosition.c;
      int k = localMovingObjectPosition.d;
      
      if (!paramWorld.a(paramEntityHuman, i, j, k)) {
        return paramItemStack;
      }
      if (!paramEntityHuman.a(i, j, k, localMovingObjectPosition.face, paramItemStack)) {
        return paramItemStack;
      }
      if (paramWorld.getType(i, j, k).getMaterial() == Material.WATER)
      {
        paramItemStack.count -= 1;
        if (paramItemStack.count <= 0) {
          return new ItemStack(Items.POTION);
        }
        if (!paramEntityHuman.inventory.pickup(new ItemStack(Items.POTION))) {
          paramEntityHuman.drop(new ItemStack(Items.POTION, 1, 0), false);
        }
      }
    }
    

    return paramItemStack;
  }
}
