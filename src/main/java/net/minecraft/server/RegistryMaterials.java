package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.com.google.common.collect.HashBiMap;



public class RegistryMaterials
  extends RegistrySimple
  implements Registry
{
  protected final RegistryID a = new RegistryID();
  protected final Map b;
  
  public RegistryMaterials()
  {
    this.b = ((BiMap)this.c).inverse();
  }
  
  public void a(int paramInt, String paramString, Object paramObject) {
    this.a.a(paramObject, paramInt);
    a(c(paramString), paramObject);
  }
  
  protected Map a()
  {
    return HashBiMap.create();
  }
  

  public Object get(String paramString)
  {
    return super.get(c(paramString));
  }
  
  public String c(Object paramObject)
  {
    return (String)this.b.get(paramObject);
  }
  
  public boolean b(String paramString)
  {
    return super.d(c(paramString));
  }
  
  public int b(Object paramObject)
  {
    return this.a.b(paramObject);
  }
  
  public Object a(int paramInt)
  {
    return this.a.a(paramInt);
  }
  
  public Iterator iterator()
  {
    return this.a.iterator();
  }
  
  public boolean b(int paramInt) {
    return this.a.b(paramInt);
  }
  
  private static String c(String paramString)
  {
    return paramString.indexOf(':') == -1 ? "minecraft:" + paramString : paramString;
  }
}
