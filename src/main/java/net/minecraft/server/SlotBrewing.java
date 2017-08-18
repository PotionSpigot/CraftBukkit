package net.minecraft.server;

















































































































































class SlotBrewing
  extends Slot
{
  public SlotBrewing(ContainerBrewingStand paramContainerBrewingStand, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    if (paramItemStack != null)
    {
      return paramItemStack.getItem().m(paramItemStack);
    }
    


    return false;
  }
  
  public int getMaxStackSize()
  {
    return 64;
  }
}
