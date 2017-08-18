package net.minecraft.server;

public class ControllerJump
{
  private EntityInsentient a;
  private boolean b;
  
  public ControllerJump(EntityInsentient paramEntityInsentient)
  {
    this.a = paramEntityInsentient;
  }
  
  public void a() {
    this.b = true;
  }
  
  public void b() {
    this.a.f(this.b);
    this.b = false;
  }
}
