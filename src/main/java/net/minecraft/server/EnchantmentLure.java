package net.minecraft.server;

public class EnchantmentLure extends Enchantment
{
  protected EnchantmentLure(int paramInt1, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType) {
    super(paramInt1, paramInt2, paramEnchantmentSlotType);
    
    b("fishingSpeed");
  }
  
  public int a(int paramInt)
  {
    return 15 + (paramInt - 1) * 9;
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
}
