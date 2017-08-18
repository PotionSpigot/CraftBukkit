package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class ItemFireball extends Item
{
  public ItemFireball()
  {
    a(CreativeModeTab.f);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
    if (world.isStatic) {
      return true;
    }
    if (l == 0) {
      j--;
    }
    
    if (l == 1) {
      j++;
    }
    
    if (l == 2) {
      k--;
    }
    
    if (l == 3) {
      k++;
    }
    
    if (l == 4) {
      i--;
    }
    
    if (l == 5) {
      i++;
    }
    
    if (!entityhuman.a(i, j, k, l, itemstack)) {
      return false;
    }
    if (world.getType(i, j, k).getMaterial() == Material.AIR)
    {
      if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, BlockIgniteEvent.IgniteCause.FIREBALL, entityhuman).isCancelled()) {
        if (!entityhuman.abilities.canInstantlyBuild) {
          itemstack.count -= 1;
        }
        return false;
      }
      

      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
      world.setTypeUpdate(i, j, k, Blocks.FIRE);
    }
    
    if (!entityhuman.abilities.canInstantlyBuild) {
      itemstack.count -= 1;
    }
    
    return true;
  }
}
