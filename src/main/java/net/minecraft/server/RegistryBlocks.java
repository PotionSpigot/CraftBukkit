package net.minecraft.server;

public class RegistryBlocks extends RegistryMaterials
{
  private final String d;
  private Object e;
  
  public RegistryBlocks(String paramString)
  {
    this.d = paramString;
  }
  
  public void a(int paramInt, String paramString, Object paramObject)
  {
    if (this.d.equals(paramString)) {
      this.e = paramObject;
    }
    
    super.a(paramInt, paramString, paramObject);
  }
  
  public Object get(String paramString)
  {
    Object localObject = super.get(paramString);
    return localObject == null ? this.e : localObject;
  }
  
  public Object a(int paramInt)
  {
    Object localObject = super.a(paramInt);
    return localObject == null ? this.e : localObject;
  }
}
