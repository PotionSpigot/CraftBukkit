package net.minecraft.server;

import java.util.Random;







public class EnchantmentWeaponDamage
  extends Enchantment
{
  private static final String[] E = { "all", "undead", "arthropods" };
  


  private static final int[] F = { 1, 5, 5 };
  


  private static final int[] G = { 11, 8, 8 };
  


  private static final int[] H = { 20, 20, 20 };
  
  public final int a;
  

  public EnchantmentWeaponDamage(int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.WEAPON);
    this.a = paramInt3;
  }
  
  public int a(int paramInt)
  {
    return F[this.a] + (paramInt - 1) * G[this.a];
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + H[this.a];
  }
  
  public int getMaxLevel()
  {
    return 5;
  }
  
  public float a(int paramInt, EnumMonsterType paramEnumMonsterType)
  {
    if (this.a == 0) {
      return paramInt * 1.25F;
    }
    if ((this.a == 1) && (paramEnumMonsterType == EnumMonsterType.UNDEAD)) {
      return paramInt * 2.5F;
    }
    if ((this.a == 2) && (paramEnumMonsterType == EnumMonsterType.ARTHROPOD)) {
      return paramInt * 2.5F;
    }
    return 0.0F;
  }
  
  public String a()
  {
    return "enchantment.damage." + E[this.a];
  }
  
  public boolean a(Enchantment paramEnchantment)
  {
    return !(paramEnchantment instanceof EnchantmentWeaponDamage);
  }
  
  public boolean canEnchant(ItemStack paramItemStack)
  {
    if ((paramItemStack.getItem() instanceof ItemAxe)) return true;
    return super.canEnchant(paramItemStack);
  }
  
  public void a(EntityLiving paramEntityLiving, Entity paramEntity, int paramInt)
  {
    if ((paramEntity instanceof EntityLiving)) {
      EntityLiving localEntityLiving = (EntityLiving)paramEntity;
      
      if ((this.a == 2) && (localEntityLiving.getMonsterType() == EnumMonsterType.ARTHROPOD)) {
        int i = 20 + paramEntityLiving.aI().nextInt(10 * paramInt);
        localEntityLiving.addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, i, 3));
      }
    }
  }
}
