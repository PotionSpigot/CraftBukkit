package net.minecraft.server;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple
  implements IRegistry
{
  private static final Logger a = ;
  protected final Map c = a();
  
  protected Map a() {
    return Maps.newHashMap();
  }
  
  public Object get(Object paramObject)
  {
    return this.c.get(paramObject);
  }
  
  public void a(Object paramObject1, Object paramObject2)
  {
    if (this.c.containsKey(paramObject1)) {
      a.debug("Adding duplicate key '" + paramObject1 + "' to registry");
    }
    this.c.put(paramObject1, paramObject2);
  }
  
  public Set keySet() {
    return Collections.unmodifiableSet(this.c.keySet());
  }
  
  public boolean d(Object paramObject) {
    return this.c.containsKey(paramObject);
  }
}
