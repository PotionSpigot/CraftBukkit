package net.minecraft.server;

import java.util.Collection;
import java.util.UUID;

public abstract interface AttributeInstance
{
  public abstract IAttribute getAttribute();
  
  public abstract double b();
  
  public abstract void setValue(double paramDouble);
  
  public abstract Collection c();
  
  public abstract AttributeModifier a(UUID paramUUID);
  
  public abstract void a(AttributeModifier paramAttributeModifier);
  
  public abstract void b(AttributeModifier paramAttributeModifier);
  
  public abstract double getValue();
}
