package net.minecraft.server;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;

final class DispenseBehaviorBonemeal extends DispenseBehaviorItem
{
  private boolean b = true;
  

  protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    if (itemstack.getData() == 15) {
      EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
      World world = isourceblock.k();
      int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
      int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
      int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
      

      Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
      
      BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(0, 0, 0));
      if (!BlockDispenser.eventFired) {
        world.getServer().getPluginManager().callEvent(event);
      }
      
      if (event.isCancelled()) {
        return itemstack;
      }
      
      if (!event.getItem().equals(craftItem))
      {
        ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
        IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
        if ((idispensebehavior != IDispenseBehavior.a) && (idispensebehavior != this)) {
          idispensebehavior.a(isourceblock, eventStack);
          return itemstack;
        }
      }
      

      if (ItemDye.a(itemstack, world, i, j, k)) {
        if (!world.isStatic) {
          world.triggerEffect(2005, i, j, k, 0);
        }
      } else {
        this.b = false;
      }
      
      return itemstack;
    }
    return super.b(isourceblock, itemstack);
  }
  
  protected void a(ISourceBlock isourceblock)
  {
    if (this.b) {
      isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    } else {
      isourceblock.k().triggerEffect(1001, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    }
  }
}
