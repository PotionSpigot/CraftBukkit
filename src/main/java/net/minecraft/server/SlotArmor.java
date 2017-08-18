package net.minecraft.server;

































class SlotArmor
  extends Slot
{
  SlotArmor(ContainerPlayer paramContainerPlayer, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public int getMaxStackSize() { return 1; }
  

  public boolean isAllowed(ItemStack paramItemStack)
  {
    if (paramItemStack == null) {
      return false;
    }
    if ((paramItemStack.getItem() instanceof ItemArmor)) {
      return ((ItemArmor)paramItemStack.getItem()).b == this.a;
    }
    if ((paramItemStack.getItem() == Item.getItemOf(Blocks.PUMPKIN)) || (paramItemStack.getItem() == Items.SKULL)) {
      return this.a == 0;
    }
    return false;
  }
}
