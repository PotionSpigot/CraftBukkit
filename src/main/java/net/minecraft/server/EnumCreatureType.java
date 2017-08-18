package net.minecraft.server;




public enum EnumCreatureType
{
  private final Class e;
  


  private final int f;
  

  private final Material g;
  

  private final boolean h;
  

  private final boolean i;
  


  private EnumCreatureType(Class paramClass, int paramInt1, Material paramMaterial, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.e = paramClass;
    this.f = paramInt1;
    this.g = paramMaterial;
    this.h = paramBoolean1;
    this.i = paramBoolean2;
  }
  
  public Class a() {
    return this.e;
  }
  
  public int b() {
    return this.f;
  }
  
  public Material c() {
    return this.g;
  }
  
  public boolean d() {
    return this.h;
  }
  
  public boolean e() {
    return this.i;
  }
}
