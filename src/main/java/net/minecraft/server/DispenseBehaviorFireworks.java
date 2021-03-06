package net.minecraft.server;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

final class DispenseBehaviorFireworks extends DispenseBehaviorItem
{
  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    double d0 = isourceblock.getX() + enumfacing.getAdjacentX();
    double d1 = isourceblock.getBlockY() + 0.2F;
    double d2 = isourceblock.getZ() + enumfacing.getAdjacentZ();
    

    World world = isourceblock.k();
    ItemStack itemstack1 = itemstack.a(1);
    Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1, d2));
    if (!BlockDispenser.eventFired) {
      world.getServer().getPluginManager().callEvent(event);
    }
    
    if (event.isCancelled()) {
      itemstack.count += 1;
      return itemstack;
    }
    
    if (!event.getItem().equals(craftItem)) {
      itemstack.count += 1;
      
      ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
      IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
      if ((idispensebehavior != IDispenseBehavior.a) && (idispensebehavior != this)) {
        idispensebehavior.a(isourceblock, eventStack);
        return itemstack;
      }
    }
    
    itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
    EntityFireworks entityfireworks = new EntityFireworks(isourceblock.k(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), itemstack1);
    
    isourceblock.k().addEntity(entityfireworks);
    


    return itemstack;
  }
  
  protected void a(ISourceBlock isourceblock) {
    isourceblock.k().triggerEffect(1002, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
  }
}
