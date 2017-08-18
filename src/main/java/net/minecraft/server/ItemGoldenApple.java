package net.minecraft.server;





public class ItemGoldenApple
  extends ItemFood
{
  public ItemGoldenApple(int paramInt, float paramFloat, boolean paramBoolean)
  {
    super(paramInt, paramFloat, paramBoolean);
    
    a(true);
  }
  





  public EnumItemRarity f(ItemStack paramItemStack)
  {
    if (paramItemStack.getData() == 0) {
      return EnumItemRarity.RARE;
    }
    return EnumItemRarity.EPIC;
  }
  
  protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!paramWorld.isStatic) { paramEntityHuman.addEffect(new MobEffect(MobEffectList.ABSORPTION.id, 2400, 0));
    }
    if (paramItemStack.getData() > 0) {
      if (!paramWorld.isStatic) {
        paramEntityHuman.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 4));
        paramEntityHuman.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
        paramEntityHuman.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
      }
    } else {
      super.c(paramItemStack, paramWorld, paramEntityHuman);
    }
  }
}
