package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;

final class DispenseBehaviorFilledBucket extends DispenseBehaviorItem
{
  private final DispenseBehaviorItem b = new DispenseBehaviorItem();
  

  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    ItemBucket itembucket = (ItemBucket)itemstack.getItem();
    int i = isourceblock.getBlockX();
    int j = isourceblock.getBlockY();
    int k = isourceblock.getBlockZ();
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    

    World world = isourceblock.k();
    int x = i + enumfacing.getAdjacentX();
    int y = j + enumfacing.getAdjacentY();
    int z = k + enumfacing.getAdjacentZ();
    if ((world.isEmpty(x, y, z)) || (!world.getType(x, y, z).getMaterial().isBuildable())) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
      
      BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(x, y, z));
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
      
      itembucket = (ItemBucket)CraftItemStack.asNMSCopy(event.getItem()).getItem();
    }
    

    if (itembucket.a(isourceblock.k(), i + enumfacing.getAdjacentX(), j + enumfacing.getAdjacentY(), k + enumfacing.getAdjacentZ()))
    {
      Item item = Items.BUCKET;
      if (--itemstack.count == 0) {
        itemstack.setItem(Items.BUCKET);
        itemstack.count = 1;
      } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
        this.b.a(isourceblock, new ItemStack(item));
      }
      

      return itemstack;
    }
    return this.b.a(isourceblock, itemstack);
  }
}
