package net.minecraft.server;














class SlotHorseSaddle
  extends Slot
{
  SlotHorseSaddle(ContainerHorse paramContainerHorse, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean isAllowed(ItemStack paramItemStack) { return (super.isAllowed(paramItemStack)) && (paramItemStack.getItem() == Items.SADDLE) && (!hasItem()); }
}
