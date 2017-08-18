package net.minecraft.server;




















class SlotHorseArmor
  extends Slot
{
  SlotHorseArmor(ContainerHorse paramContainerHorse, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, EntityHorse paramEntityHorse)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean isAllowed(ItemStack paramItemStack) { return (super.isAllowed(paramItemStack)) && (this.a.cB()) && (EntityHorse.a(paramItemStack.getItem())); }
}
