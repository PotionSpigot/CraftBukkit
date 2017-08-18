package net.minecraft.server;

public class Tuple
{
  private Object a;
  private Object b;
  
  public Tuple(Object paramObject1, Object paramObject2) {
    this.a = paramObject1;
    this.b = paramObject2;
  }
  
  public Object a() {
    return this.a;
  }
  



  public Object b()
  {
    return this.b;
  }
}
