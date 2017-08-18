package net.minecraft.server;

import java.util.Map;

public class InsensitiveStringMap implements Map
{
  private final Map a = new java.util.LinkedHashMap();
  
  public int size()
  {
    return this.a.size();
  }
  
  public boolean isEmpty()
  {
    return this.a.isEmpty();
  }
  
  public boolean containsKey(Object paramObject)
  {
    return this.a.containsKey(paramObject.toString().toLowerCase());
  }
  
  public boolean containsValue(Object paramObject)
  {
    return this.a.containsKey(paramObject);
  }
  
  public Object get(Object paramObject)
  {
    return this.a.get(paramObject.toString().toLowerCase());
  }
  
  public Object put(String paramString, Object paramObject)
  {
    return this.a.put(paramString.toLowerCase(), paramObject);
  }
  
  public Object remove(Object paramObject)
  {
    return this.a.remove(paramObject.toString().toLowerCase());
  }
  
  public void putAll(Map paramMap)
  {
    for (Entry localEntry : paramMap.entrySet()) {
      put((String)localEntry.getKey(), localEntry.getValue());
    }
  }
  
  public void clear()
  {
    this.a.clear();
  }
  

  public java.util.Set keySet()
  {
    return this.a.keySet();
  }
  

  public java.util.Collection values()
  {
    return this.a.values();
  }
  

  public java.util.Set entrySet()
  {
    return this.a.entrySet();
  }
}
