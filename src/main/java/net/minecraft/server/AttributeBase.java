package net.minecraft.server;

public abstract class AttributeBase implements IAttribute {
  private final String a;
  private final double b;
  private boolean c;
  
  protected AttributeBase(String paramString, double paramDouble) {
    this.a = paramString;
    this.b = paramDouble;
    
    if (paramString == null) throw new IllegalArgumentException("Name cannot be null!");
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public double b()
  {
    return this.b;
  }
  
  public boolean c()
  {
    return this.c;
  }
  
  public AttributeBase a(boolean paramBoolean) {
    this.c = paramBoolean;
    return this;
  }
  
  public int hashCode()
  {
    return this.a.hashCode();
  }
}
