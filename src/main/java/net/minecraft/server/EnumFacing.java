package net.minecraft.server;


public enum EnumFacing
{
  private final int g;
  
  private final int h;
  
  private final int i;
  private final int j;
  private final int k;
  private static final EnumFacing[] l;
  
  static
  {
    l = new EnumFacing[6];
    
    for (EnumFacing localEnumFacing : values()) {
      l[localEnumFacing.g] = localEnumFacing;
    }
  }
  
  private EnumFacing(int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.g = paramInt2;
    this.h = paramInt3;
    this.i = paramInt4;
    this.j = paramInt5;
    this.k = paramInt6;
  }
  







  public int getAdjacentX()
  {
    return this.i;
  }
  
  public int getAdjacentY() {
    return this.j;
  }
  
  public int getAdjacentZ() {
    return this.k;
  }
  
  public static EnumFacing a(int paramInt) {
    return l[(paramInt % l.length)];
  }
}
