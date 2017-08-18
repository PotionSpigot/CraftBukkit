package net.minecraft.server;

public abstract interface IWorldInventory
  extends IInventory
{
  public abstract int[] getSlotsForFace(int paramInt);
  
  public abstract boolean canPlaceItemThroughFace(int paramInt1, ItemStack paramItemStack, int paramInt2);
  
  public abstract boolean canTakeItemThroughFace(int paramInt1, ItemStack paramItemStack, int paramInt2);
}
