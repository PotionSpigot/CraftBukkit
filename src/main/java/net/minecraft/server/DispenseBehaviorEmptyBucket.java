package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;

final class DispenseBehaviorEmptyBucket extends DispenseBehaviorItem
{
  private final DispenseBehaviorItem b = new DispenseBehaviorItem();
  

  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    World world = isourceblock.k();
    int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
    int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
    int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
    Material material = world.getType(i, j, k).getMaterial();
    int l = world.getData(i, j, k);
    Item item;
    Item item;
    if ((Material.WATER.equals(material)) && (l == 0)) {
      item = Items.WATER_BUCKET;
    } else {
      if ((!Material.LAVA.equals(material)) || (l != 0)) {
        return super.b(isourceblock, itemstack);
      }
      
      item = Items.LAVA_BUCKET;
    }
    

    org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(i, j, k));
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
    

    world.setAir(i, j, k);
    if (--itemstack.count == 0) {
      itemstack.setItem(item);
      itemstack.count = 1;
    } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
      this.b.a(isourceblock, new ItemStack(item));
    }
    
    return itemstack;
  }
}
