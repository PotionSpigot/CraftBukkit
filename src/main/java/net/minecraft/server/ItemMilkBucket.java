package net.minecraft.server;

import org.bukkit.Material;

public class ItemMilkBucket extends Item
{
  public ItemMilkBucket()
  {
    if (org.github.paperspigot.PaperSpigotConfig.stackableMilkBuckets) {
      e(Material.BUCKET.getMaxStackSize());
    } else {
      e(1);
    }
    
    a(CreativeModeTab.f);
  }
  
  public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
    if (!entityhuman.abilities.canInstantlyBuild) {
      itemstack.count -= 1;
    }
    
    if (!world.isStatic) {
      entityhuman.removeAllEffects();
    }
    

    if (org.github.paperspigot.PaperSpigotConfig.stackableMilkBuckets) {
      if (itemstack.count <= 0)
        return new ItemStack(Items.BUCKET);
      if (!entityhuman.inventory.pickup(new ItemStack(Items.BUCKET))) {
        entityhuman.drop(new ItemStack(Items.BUCKET), false);
      }
    }
    

    return itemstack.count <= 0 ? new ItemStack(Items.BUCKET) : itemstack;
  }
  
  public int d_(ItemStack itemstack) {
    return 32;
  }
  
  public EnumAnimation d(ItemStack itemstack) {
    return EnumAnimation.DRINK;
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
    entityhuman.a(itemstack, d_(itemstack));
    return itemstack;
  }
}
