package net.minecraft.server;

import java.util.Random;

public class ItemSnowball extends Item
{
  public ItemSnowball()
  {
    this.maxStackSize = 16;
    a(CreativeModeTab.f);
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!paramEntityHuman.abilities.canInstantlyBuild) {
      paramItemStack.count -= 1;
    }
    paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
    if (!paramWorld.isStatic) paramWorld.addEntity(new EntitySnowball(paramWorld, paramEntityHuman));
    return paramItemStack;
  }
}
