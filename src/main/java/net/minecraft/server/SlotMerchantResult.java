package net.minecraft.server;


public class SlotMerchantResult
  extends Slot
{
  private final InventoryMerchant a;
  
  private EntityHuman b;
  private int c;
  private final IMerchant d;
  
  public SlotMerchantResult(EntityHuman paramEntityHuman, IMerchant paramIMerchant, InventoryMerchant paramInventoryMerchant, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInventoryMerchant, paramInt1, paramInt2, paramInt3);
    this.b = paramEntityHuman;
    this.d = paramIMerchant;
    this.a = paramInventoryMerchant;
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    return false;
  }
  
  public ItemStack a(int paramInt)
  {
    if (hasItem()) {
      this.c += Math.min(paramInt, getItem().count);
    }
    return super.a(paramInt);
  }
  
  protected void a(ItemStack paramItemStack, int paramInt)
  {
    this.c += paramInt;
    b(paramItemStack);
  }
  
  protected void b(ItemStack paramItemStack)
  {
    paramItemStack.a(this.b.world, this.b, this.c);
    this.c = 0;
  }
  
  public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
  {
    b(paramItemStack);
    
    MerchantRecipe localMerchantRecipe = this.a.getRecipe();
    if (localMerchantRecipe != null)
    {
      ItemStack localItemStack1 = this.a.getItem(0);
      ItemStack localItemStack2 = this.a.getItem(1);
      

      if ((a(localMerchantRecipe, localItemStack1, localItemStack2)) || (a(localMerchantRecipe, localItemStack2, localItemStack1))) {
        this.d.a(localMerchantRecipe);
        
        if ((localItemStack1 != null) && (localItemStack1.count <= 0)) {
          localItemStack1 = null;
        }
        if ((localItemStack2 != null) && (localItemStack2.count <= 0)) {
          localItemStack2 = null;
        }
        this.a.setItem(0, localItemStack1);
        this.a.setItem(1, localItemStack2);
      }
    }
  }
  
  private boolean a(MerchantRecipe paramMerchantRecipe, ItemStack paramItemStack1, ItemStack paramItemStack2) {
    ItemStack localItemStack1 = paramMerchantRecipe.getBuyItem1();
    ItemStack localItemStack2 = paramMerchantRecipe.getBuyItem2();
    
    if ((paramItemStack1 != null) && (paramItemStack1.getItem() == localItemStack1.getItem())) {
      if ((localItemStack2 != null) && (paramItemStack2 != null) && (localItemStack2.getItem() == paramItemStack2.getItem())) {
        paramItemStack1.count -= localItemStack1.count;
        paramItemStack2.count -= localItemStack2.count;
        return true; }
      if ((localItemStack2 == null) && (paramItemStack2 == null)) {
        paramItemStack1.count -= localItemStack1.count;
        return true;
      }
    }
    return false;
  }
}
