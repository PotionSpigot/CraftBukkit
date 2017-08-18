package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

final class DispenseBehaviorBoat extends DispenseBehaviorItem
{
  private final DispenseBehaviorItem b = new DispenseBehaviorItem();
  

  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    World world = isourceblock.k();
    double d0 = isourceblock.getX() + enumfacing.getAdjacentX() * 1.125F;
    double d1 = isourceblock.getY() + enumfacing.getAdjacentY() * 1.125F;
    double d2 = isourceblock.getZ() + enumfacing.getAdjacentZ() * 1.125F;
    int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
    int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
    int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
    Material material = world.getType(i, j, k).getMaterial();
    double d3;
    double d3;
    if (Material.WATER.equals(material)) {
      d3 = 1.0D;
    } else {
      if ((!Material.AIR.equals(material)) || (!Material.WATER.equals(world.getType(i, j - 1, k).getMaterial()))) {
        return this.b.a(isourceblock, itemstack);
      }
      
      d3 = 0.0D;
    }
    

    ItemStack itemstack1 = itemstack.a(1);
    org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1 + d3, d2));
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
    
    EntityBoat entityboat = new EntityBoat(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
    

    world.addEntity(entityboat);
    
    return itemstack;
  }
  
  protected void a(ISourceBlock isourceblock) {
    isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
  }
}
