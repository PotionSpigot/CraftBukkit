package net.minecraft.server;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.com.google.common.base.Predicates;
import net.minecraft.util.com.google.common.collect.Iterators;
import net.minecraft.util.com.google.common.collect.Lists;


public class RegistryID
  implements Registry
{
  private IdentityHashMap a = new IdentityHashMap(512);
  private List b = Lists.newArrayList();
  
  public void a(Object paramObject, int paramInt) {
    this.a.put(paramObject, Integer.valueOf(paramInt));
    

    while (this.b.size() <= paramInt) {
      this.b.add(null);
    }
    
    this.b.set(paramInt, paramObject);
  }
  
  public int b(Object paramObject)
  {
    Integer localInteger = (Integer)this.a.get(paramObject);
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  public Object a(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.b.size())) {
      return this.b.get(paramInt);
    }
    
    return null;
  }
  
  public Iterator iterator()
  {
    return Iterators.filter(this.b.iterator(), Predicates.notNull());
  }
  
  public boolean b(int paramInt) {
    return a(paramInt) != null;
  }
}
