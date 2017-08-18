package net.minecraft.server;

import java.util.Map;
import java.util.Set;

public class AttributeModifiable implements AttributeInstance
{
  private final AttributeMapBase a;
  private final IAttribute b;
  private final Map c = net.minecraft.util.com.google.common.collect.Maps.newHashMap();
  private final Map d = net.minecraft.util.com.google.common.collect.Maps.newHashMap();
  private final Map e = net.minecraft.util.com.google.common.collect.Maps.newHashMap();
  private double f;
  private boolean g = true;
  private double h;
  
  public AttributeModifiable(AttributeMapBase paramAttributeMapBase, IAttribute paramIAttribute) {
    this.a = paramAttributeMapBase;
    this.b = paramIAttribute;
    this.f = paramIAttribute.b();
    
    for (int i = 0; i < 3; i++) {
      this.c.put(Integer.valueOf(i), new java.util.HashSet());
    }
  }
  
  public IAttribute getAttribute()
  {
    return this.b;
  }
  
  public double b()
  {
    return this.f;
  }
  
  public void setValue(double paramDouble)
  {
    if (paramDouble == b()) return;
    this.f = paramDouble;
    f();
  }
  
  public java.util.Collection a(int paramInt)
  {
    return (java.util.Collection)this.c.get(Integer.valueOf(paramInt));
  }
  
  public java.util.Collection c()
  {
    java.util.HashSet localHashSet = new java.util.HashSet();
    
    for (int i = 0; i < 3; i++) {
      localHashSet.addAll(a(i));
    }
    
    return localHashSet;
  }
  
  public AttributeModifier a(java.util.UUID paramUUID)
  {
    return (AttributeModifier)this.e.get(paramUUID);
  }
  







  public void a(AttributeModifier paramAttributeModifier)
  {
    if (a(paramAttributeModifier.a()) != null) { throw new IllegalArgumentException("Modifier is already applied on this attribute!");
    }
    Object localObject = (Set)this.d.get(paramAttributeModifier.b());
    
    if (localObject == null) {
      localObject = new java.util.HashSet();
      this.d.put(paramAttributeModifier.b(), localObject);
    }
    
    ((Set)this.c.get(Integer.valueOf(paramAttributeModifier.c()))).add(paramAttributeModifier);
    ((Set)localObject).add(paramAttributeModifier);
    this.e.put(paramAttributeModifier.a(), paramAttributeModifier);
    
    f();
  }
  
  private void f() {
    this.g = true;
    this.a.a(this);
  }
  
  public void b(AttributeModifier paramAttributeModifier)
  {
    for (int i = 0; i < 3; i++) {
      Set localSet2 = (Set)this.c.get(Integer.valueOf(i));
      localSet2.remove(paramAttributeModifier);
    }
    
    Set localSet1 = (Set)this.d.get(paramAttributeModifier.b());
    
    if (localSet1 != null) {
      localSet1.remove(paramAttributeModifier);
      
      if (localSet1.isEmpty()) {
        this.d.remove(paramAttributeModifier.b());
      }
    }
    
    this.e.remove(paramAttributeModifier.a());
    f();
  }
  



































  public double getValue()
  {
    if (this.g) {
      this.h = g();
      this.g = false;
    }
    
    return this.h;
  }
  
  private double g() {
    double d1 = b();
    
    for (AttributeModifier localAttributeModifier1 : a(0)) {
      d1 += localAttributeModifier1.d();
    }
    
    double d2 = d1;
    
    for (java.util.Iterator localIterator2 = a(1).iterator(); localIterator2.hasNext();) { localAttributeModifier2 = (AttributeModifier)localIterator2.next();
      d2 += d1 * localAttributeModifier2.d();
    }
    AttributeModifier localAttributeModifier2;
    for (localIterator2 = a(2).iterator(); localIterator2.hasNext();) { localAttributeModifier2 = (AttributeModifier)localIterator2.next();
      d2 *= (1.0D + localAttributeModifier2.d());
    }
    
    return this.b.a(d2);
  }
}
