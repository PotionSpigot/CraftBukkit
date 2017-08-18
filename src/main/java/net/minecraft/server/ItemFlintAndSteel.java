package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;

public class ItemFlintAndSteel extends Item
{
  public ItemFlintAndSteel()
  {
    this.maxStackSize = 1;
    setMaxDurability(64);
    a(CreativeModeTab.i);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
    int clickedX = i;int clickedY = j;int clickedZ = k;
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
      if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
        itemstack.damage(1, entityhuman);
        return false;
      }
      
      CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
      

      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
      world.setTypeUpdate(i, j, k, Blocks.FIRE);
      

      BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
      
      if ((placeEvent.isCancelled()) || (!placeEvent.canBuild())) {
        placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
        return false;
      }
    }
    

    itemstack.damage(1, entityhuman);
    return true;
  }
}
