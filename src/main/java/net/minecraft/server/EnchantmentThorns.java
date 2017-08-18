package net.minecraft.server;

import java.util.Random;






public class EnchantmentThorns
  extends Enchantment
{
  public EnchantmentThorns(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR_TORSO);
    
    b("thorns");
  }
  
  public int a(int paramInt)
  {
    return 10 + 20 * (paramInt - 1);
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
  
  public boolean canEnchant(ItemStack paramItemStack)
  {
    if ((paramItemStack.getItem() instanceof ItemArmor)) return true;
    return super.canEnchant(paramItemStack);
  }
  
  public void b(EntityLiving paramEntityLiving, Entity paramEntity, int paramInt)
  {
    Random localRandom = paramEntityLiving.aI();
    ItemStack localItemStack = EnchantmentManager.a(Enchantment.THORNS, paramEntityLiving);
    
    if (a(paramInt, localRandom)) {
      paramEntity.damageEntity(DamageSource.a(paramEntityLiving), b(paramInt, localRandom));
      paramEntity.makeSound("damage.thorns", 0.5F, 1.0F);
      
      if (localItemStack != null) {
        localItemStack.damage(3, paramEntityLiving);
      }
    }
    else if (localItemStack != null) {
      localItemStack.damage(1, paramEntityLiving);
    }
  }
  
  public static boolean a(int paramInt, Random paramRandom)
  {
    if (paramInt <= 0) return false;
    return paramRandom.nextFloat() < 0.15F * paramInt;
  }
  
  public static int b(int paramInt, Random paramRandom) {
    if (paramInt > 10) {
      return paramInt - 10;
    }
    return 1 + paramRandom.nextInt(4);
  }
}
