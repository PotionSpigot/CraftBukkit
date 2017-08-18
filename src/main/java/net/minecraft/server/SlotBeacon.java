package net.minecraft.server;















































































































class SlotBeacon
  extends Slot
{
  public SlotBeacon(ContainerBeacon paramContainerBeacon, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    if (paramItemStack != null) {
      return (paramItemStack.getItem() == Items.EMERALD) || (paramItemStack.getItem() == Items.DIAMOND) || (paramItemStack.getItem() == Items.GOLD_INGOT) || (paramItemStack.getItem() == Items.IRON_INGOT);
    }
    return false;
  }
  
  public int getMaxStackSize()
  {
    return 1;
  }
}
