package net.minecraft.server;

import java.util.UUID;
import net.minecraft.util.org.apache.commons.lang3.Validate;






public class AttributeModifier
{
  private final double a;
  private final int b;
  private final String c;
  private final UUID d;
  private boolean e = true;
  
  public AttributeModifier(String paramString, double paramDouble, int paramInt) {
    this(UUID.randomUUID(), paramString, paramDouble, paramInt);
  }
  
  public AttributeModifier(UUID paramUUID, String paramString, double paramDouble, int paramInt) {
    this.d = paramUUID;
    this.c = paramString;
    this.a = paramDouble;
    this.b = paramInt;
    
    Validate.notEmpty(paramString, "Modifier name cannot be empty", new Object[0]);
    Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(paramInt), "Invalid operation", new Object[0]);
  }
  
  public UUID a() {
    return this.d;
  }
  
  public String b() {
    return this.c;
  }
  
  public int c() {
    return this.b;
  }
  
  public double d() {
    return this.a;
  }
  
  public boolean e() {
    return this.e;
  }
  
  public AttributeModifier a(boolean paramBoolean) {
    this.e = paramBoolean;
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) return true;
    if ((paramObject == null) || (getClass() != paramObject.getClass())) { return false;
    }
    AttributeModifier localAttributeModifier = (AttributeModifier)paramObject;
    
    if (this.d != null ? !this.d.equals(localAttributeModifier.d) : localAttributeModifier.d != null) { return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    return this.d != null ? this.d.hashCode() : 0;
  }
  
  public String toString()
  {
    return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name='" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
  }
}
