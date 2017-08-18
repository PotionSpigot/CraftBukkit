package net.minecraft.server;

import org.bukkit.event.block.BlockPlaceEvent;

public class ItemWaterLily extends ItemWithAuxData {
  public ItemWaterLily(Block block) { super(block, false); }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
  {
    MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
    
    if (movingobjectposition == null) {
      return itemstack;
    }
    if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
      int i = movingobjectposition.b;
      int j = movingobjectposition.c;
      int k = movingobjectposition.d;
      
      if (!world.a(entityhuman, i, j, k)) {
        return itemstack;
      }
      
      if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
        return itemstack;
      }
      
      if ((world.getType(i, j, k).getMaterial() == Material.WATER) && (world.getData(i, j, k) == 0) && (world.isEmpty(i, j + 1, k)))
      {
        org.bukkit.block.BlockState blockstate = org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState.getBlockState(world, i, j + 1, k);
        world.setTypeUpdate(i, j + 1, k, Blocks.WATER_LILY);
        BlockPlaceEvent placeEvent = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockstate, i, j, k);
        if ((placeEvent != null) && ((placeEvent.isCancelled()) || (!placeEvent.canBuild()))) {
          blockstate.update(true, false);
          return itemstack;
        }
        

        if (!entityhuman.abilities.canInstantlyBuild) {
          itemstack.count -= 1;
        }
      }
    }
    
    return itemstack;
  }
}
