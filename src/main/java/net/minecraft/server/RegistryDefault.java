package net.minecraft.server;

public class RegistryDefault extends RegistrySimple {
  private final Object a;
  
  public RegistryDefault(Object paramObject) {
    this.a = paramObject;
  }
  
  public Object get(Object paramObject)
  {
    Object localObject = super.get(paramObject);
    return localObject == null ? this.a : localObject;
  }
}
