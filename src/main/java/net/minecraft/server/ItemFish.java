package net.minecraft.server;







public class ItemFish
  extends ItemFood
{
  private final boolean b;
  





  public ItemFish(boolean paramBoolean)
  {
    super(0, 0.0F, false);
    
    this.b = paramBoolean;
  }
  
  public int getNutrition(ItemStack paramItemStack)
  {
    EnumFish localEnumFish = EnumFish.a(paramItemStack);
    
    if ((this.b) && (localEnumFish.i())) {
      return localEnumFish.e();
    }
    return localEnumFish.c();
  }
  

  public float getSaturationModifier(ItemStack paramItemStack)
  {
    EnumFish localEnumFish = EnumFish.a(paramItemStack);
    
    if ((this.b) && (localEnumFish.i())) {
      return localEnumFish.f();
    }
    return localEnumFish.d();
  }
  

  public String i(ItemStack paramItemStack)
  {
    if (EnumFish.a(paramItemStack) == EnumFish.PUFFERFISH) {
      return PotionBrewer.m;
    }
    return null;
  }
  








  protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    EnumFish localEnumFish = EnumFish.a(paramItemStack);
    
    if (localEnumFish == EnumFish.PUFFERFISH) {
      paramEntityHuman.addEffect(new MobEffect(MobEffectList.POISON.id, 1200, 3));
      paramEntityHuman.addEffect(new MobEffect(MobEffectList.HUNGER.id, 300, 2));
      paramEntityHuman.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 300, 1));
    }
    
    super.c(paramItemStack, paramWorld, paramEntityHuman);
  }
  




















  public String a(ItemStack paramItemStack)
  {
    EnumFish localEnumFish = EnumFish.a(paramItemStack);
    return getName() + "." + localEnumFish.b() + "." + ((this.b) && (localEnumFish.i()) ? "cooked" : "raw");
  }
}
