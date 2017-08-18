package net.minecraft.server;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

final class DispenseBehaviorTNT extends DispenseBehaviorItem
{
  protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    World world = isourceblock.k();
    int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
    int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
    int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
    

    ItemStack itemstack1 = itemstack.a(1);
    Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(i + 0.5D, j + 0.5D, k + 0.5D));
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
    

    Location loc = new Location(world.getWorld(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
    EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(loc, world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), (EntityLiving)null);
    


    world.addEntity(entitytntprimed);
    
    return itemstack;
  }
}
