package net.minecraft.server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.com.google.common.collect.Sets;

public class AttributeMapServer extends AttributeMapBase
{
  private final Set d = Sets.newHashSet();
  protected final Map c = new InsensitiveStringMap();
  
  public AttributeModifiable c(IAttribute paramIAttribute)
  {
    return (AttributeModifiable)super.a(paramIAttribute);
  }
  
  public AttributeModifiable b(String paramString)
  {
    AttributeInstance localAttributeInstance = super.a(paramString);
    if (localAttributeInstance == null) localAttributeInstance = (AttributeInstance)this.c.get(paramString);
    return (AttributeModifiable)localAttributeInstance;
  }
  
  public AttributeInstance b(IAttribute paramIAttribute)
  {
    if (this.b.containsKey(paramIAttribute.getName())) { throw new IllegalArgumentException("Attribute is already registered!");
    }
    AttributeModifiable localAttributeModifiable = new AttributeModifiable(this, paramIAttribute);
    this.b.put(paramIAttribute.getName(), localAttributeModifiable);
    if (((paramIAttribute instanceof AttributeRanged)) && (((AttributeRanged)paramIAttribute).f() != null)) {
      this.c.put(((AttributeRanged)paramIAttribute).f(), localAttributeModifiable);
    }
    this.a.put(paramIAttribute, localAttributeModifiable);
    
    return localAttributeModifiable;
  }
  
  public void a(AttributeModifiable paramAttributeModifiable)
  {
    if (paramAttributeModifiable.getAttribute().c()) {
      this.d.add(paramAttributeModifiable);
    }
  }
  
  public Set getAttributes() {
    return this.d;
  }
  
  public Collection c() {
    HashSet localHashSet = Sets.newHashSet();
    
    for (AttributeInstance localAttributeInstance : a()) {
      if (localAttributeInstance.getAttribute().c()) {
        localHashSet.add(localAttributeInstance);
      }
    }
    
    return localHashSet;
  }
}
