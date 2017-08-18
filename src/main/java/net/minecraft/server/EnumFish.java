package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.Maps;








































































































public enum EnumFish
{
  private static final Map e;
  private final int f;
  private final String g;
  private final int j;
  private final float k;
  private final int l;
  private final float m;
  private boolean n = false;
  
  static
  {
    e = Maps.newHashMap();
    










    for (EnumFish localEnumFish : values()) {
      e.put(Integer.valueOf(localEnumFish.a()), localEnumFish);
    }
  }
  
  private EnumFish(int paramInt2, String paramString1, int paramInt3, float paramFloat1, int paramInt4, float paramFloat2) {
    this.f = paramInt2;
    this.g = paramString1;
    this.j = paramInt3;
    this.k = paramFloat1;
    this.l = paramInt4;
    this.m = paramFloat2;
    this.n = true;
  }
  
  private EnumFish(int paramInt2, String paramString1, int paramInt3, float paramFloat) {
    this.f = paramInt2;
    this.g = paramString1;
    this.j = paramInt3;
    this.k = paramFloat;
    this.l = 0;
    this.m = 0.0F;
    this.n = false;
  }
  
  public int a() {
    return this.f;
  }
  
  public String b() {
    return this.g;
  }
  
  public int c() {
    return this.j;
  }
  
  public float d() {
    return this.k;
  }
  
  public int e() {
    return this.l;
  }
  
  public float f() {
    return this.m;
  }
  















  public boolean i()
  {
    return this.n;
  }
  
  public static EnumFish a(int paramInt) {
    EnumFish localEnumFish = (EnumFish)e.get(Integer.valueOf(paramInt));
    
    if (localEnumFish == null) {
      return COD;
    }
    return localEnumFish;
  }
  
  public static EnumFish a(ItemStack paramItemStack)
  {
    if ((paramItemStack.getItem() instanceof ItemFish)) {
      return a(paramItemStack.getData());
    }
    return COD;
  }
}
