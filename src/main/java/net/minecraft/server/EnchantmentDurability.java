package net.minecraft.server;

import java.util.Random;


public class EnchantmentDurability
  extends Enchantment
{
  protected EnchantmentDurability(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.BREAKABLE);
    
    b("durability");
  }
  
  public int a(int paramInt)
  {
    return 5 + (paramInt - 1) * 8;
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
    if (paramItemStack.g()) return true;
    return super.canEnchant(paramItemStack);
  }
  
  public static boolean a(ItemStack paramItemStack, int paramInt, Random paramRandom) {
    if (((paramItemStack.getItem() instanceof ItemArmor)) && (paramRandom.nextFloat() < 0.6F)) return false;
    return paramRandom.nextInt(paramInt + 1) > 0;
  }
}
