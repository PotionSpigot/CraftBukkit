package net.minecraft.server;


















public enum EnumArmorMaterial
{
  private int f;
  
















  private int[] g;
  
















  private int h;
  
















  private EnumArmorMaterial(int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    this.f = paramInt2;
    this.g = paramArrayOfInt;
    this.h = paramInt3;
  }
  
  public int a(int paramInt) {
    return ItemArmor.e()[paramInt] * this.f;
  }
  
  public int b(int paramInt) {
    return this.g[paramInt];
  }
  
  public int a() {
    return this.h;
  }
  
  public Item b() {
    if (this == CLOTH)
      return Items.LEATHER;
    if (this == CHAIN)
      return Items.IRON_INGOT;
    if (this == GOLD)
      return Items.GOLD_INGOT;
    if (this == IRON)
      return Items.IRON_INGOT;
    if (this == DIAMOND) {
      return Items.DIAMOND;
    }
    return null;
  }
}
