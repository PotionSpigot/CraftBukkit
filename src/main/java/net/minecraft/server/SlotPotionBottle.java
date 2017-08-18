package net.minecraft.server;



























































class SlotPotionBottle
  extends Slot
{
  private EntityHuman a;
  

























































  public SlotPotionBottle(EntityHuman paramEntityHuman, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
    this.a = paramEntityHuman;
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    return b_(paramItemStack);
  }
  
  public int getMaxStackSize()
  {
    return 1;
  }
  
  public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
  {
    if ((paramItemStack.getItem() == Items.POTION) && (paramItemStack.getData() > 0)) this.a.a(AchievementList.B, 1);
    super.a(paramEntityHuman, paramItemStack);
  }
  
  public static boolean b_(ItemStack paramItemStack) {
    return (paramItemStack != null) && ((paramItemStack.getItem() == Items.POTION) || (paramItemStack.getItem() == Items.GLASS_BOTTLE));
  }
}
