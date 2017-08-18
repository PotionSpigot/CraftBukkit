package net.minecraft.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.com.google.common.collect.Multimap;

public abstract class AttributeMapBase
{
  protected final Map a = new HashMap();
  protected final Map b = new InsensitiveStringMap();
  
  public AttributeInstance a(IAttribute paramIAttribute) {
    return (AttributeInstance)this.a.get(paramIAttribute);
  }
  
  public AttributeInstance a(String paramString) {
    return (AttributeInstance)this.b.get(paramString);
  }
  
  public abstract AttributeInstance b(IAttribute paramIAttribute);
  
  public Collection a() {
    return this.b.values();
  }
  

  public void a(AttributeModifiable paramAttributeModifiable) {}
  
  public void a(Multimap paramMultimap)
  {
    for (Entry localEntry : paramMultimap.entries()) {
      AttributeInstance localAttributeInstance = a((String)localEntry.getKey());
      
      if (localAttributeInstance != null) {
        localAttributeInstance.b((AttributeModifier)localEntry.getValue());
      }
    }
  }
  
  public void b(Multimap paramMultimap) {
    for (Entry localEntry : paramMultimap.entries()) {
      AttributeInstance localAttributeInstance = a((String)localEntry.getKey());
      
      if (localAttributeInstance != null) {
        localAttributeInstance.b((AttributeModifier)localEntry.getValue());
        localAttributeInstance.a((AttributeModifier)localEntry.getValue());
      }
    }
  }
}
