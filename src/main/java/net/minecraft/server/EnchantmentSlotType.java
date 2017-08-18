package net.minecraft.server;















public enum EnchantmentSlotType
{
  public boolean canEnchant(Item paramItem)
  {
    if (this == ALL) return true;
    if ((this == BREAKABLE) && (paramItem.usesDurability())) { return true;
    }
    if ((paramItem instanceof ItemArmor)) {
      if (this == ARMOR) return true;
      ItemArmor localItemArmor = (ItemArmor)paramItem;
      if (localItemArmor.b == 0) return this == ARMOR_HEAD;
      if (localItemArmor.b == 2) return this == ARMOR_LEGS;
      if (localItemArmor.b == 1) return this == ARMOR_TORSO;
      if (localItemArmor.b == 3) return this == ARMOR_FEET;
      return false; }
    if ((paramItem instanceof ItemSword))
      return this == WEAPON;
    if ((paramItem instanceof ItemTool))
      return this == DIGGER;
    if ((paramItem instanceof ItemBow))
      return this == BOW;
    if ((paramItem instanceof ItemFishingRod)) {
      return this == FISHING_ROD;
    }
    return false;
  }
}
