package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BlockDropper extends BlockDispenser
{
  private final IDispenseBehavior P = new DispenseBehaviorItem();
  

  protected IDispenseBehavior a(ItemStack itemstack)
  {
    return this.P;
  }
  
  public TileEntity a(World world, int i) {
    return new TileEntityDropper();
  }
  
  public void dispense(World world, int i, int j, int k) {
    SourceBlock sourceblock = new SourceBlock(world, i, j, k);
    TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
    
    if (tileentitydispenser != null) {
      int l = tileentitydispenser.i();
      
      if (l < 0) {
        world.triggerEffect(1001, i, j, k, 0);
      } else {
        ItemStack itemstack = tileentitydispenser.getItem(l);
        int i1 = world.getData(i, j, k) & 0x7;
        IInventory iinventory = TileEntityHopper.getInventoryAt(world, i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
        
        ItemStack itemstack1;
        if (iinventory != null)
        {
          CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().a(1));
          
          Inventory destinationInventory;
          Inventory destinationInventory;
          if ((iinventory instanceof InventoryLargeChest)) {
            destinationInventory = new org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
          } else {
            destinationInventory = iinventory.getOwner().getInventory();
          }
          
          InventoryMoveItemEvent event = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
          world.getServer().getPluginManager().callEvent(event);
          if (event.isCancelled()) {
            return;
          }
          ItemStack itemstack1 = TileEntityHopper.addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), Facing.OPPOSITE_FACING[i1]);
          if ((event.getItem().equals(oitemstack)) && (itemstack1 == null))
          {
            itemstack1 = itemstack.cloneItemStack();
            if (--itemstack1.count == 0) {
              itemstack1 = null;
            }
          } else {
            itemstack1 = itemstack.cloneItemStack();
          }
        } else {
          itemstack1 = this.P.a(sourceblock, itemstack);
          if ((itemstack1 != null) && (itemstack1.count == 0)) {
            itemstack1 = null;
          }
        }
        
        tileentitydispenser.setItem(l, itemstack1);
      }
    }
  }
}
