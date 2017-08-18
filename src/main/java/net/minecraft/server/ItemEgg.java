package net.minecraft.server;

import java.util.Random;

public class ItemEgg extends Item
{
  public ItemEgg()
  {
    this.maxStackSize = 16;
    a(CreativeModeTab.l);
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!paramEntityHuman.abilities.canInstantlyBuild) {
      paramItemStack.count -= 1;
    }
    paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
    if (!paramWorld.isStatic) paramWorld.addEntity(new EntityEgg(paramWorld, paramEntityHuman));
    return paramItemStack;
  }
}
