package net.minecraft.server;

public abstract interface IMerchant
{
  public abstract void a_(EntityHuman paramEntityHuman);
  
  public abstract EntityHuman b();
  
  public abstract MerchantRecipeList getOffers(EntityHuman paramEntityHuman);
  
  public abstract void a(MerchantRecipe paramMerchantRecipe);
  
  public abstract void a_(ItemStack paramItemStack);
}
