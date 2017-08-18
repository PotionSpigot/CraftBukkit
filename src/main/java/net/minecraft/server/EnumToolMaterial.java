package net.minecraft.server;













public enum EnumToolMaterial
{
  private final int f;
  











  private final int g;
  











  private final float h;
  











  private final float i;
  











  private final int j;
  












  private EnumToolMaterial(int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, int paramInt4)
  {
    this.f = paramInt2;
    this.g = paramInt3;
    this.h = paramFloat1;
    this.i = paramFloat2;
    this.j = paramInt4;
  }
  
  public int a() {
    return this.g;
  }
  
  public float b() {
    return this.h;
  }
  
  public float c() {
    return this.i;
  }
  
  public int d() {
    return this.f;
  }
  
  public int e() {
    return this.j;
  }
  
  public Item f() {
    if (this == WOOD)
      return Item.getItemOf(Blocks.WOOD);
    if (this == STONE)
      return Item.getItemOf(Blocks.COBBLESTONE);
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
