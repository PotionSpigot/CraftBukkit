package net.minecraft.server;

public abstract class PathfinderGoal
{
  private int a;
  
  public abstract boolean a();
  
  public boolean b() {
    return a();
  }
  
  public boolean i() {
    return true;
  }
  

  public void c() {}
  

  public void d() {}
  
  public void e() {}
  
  public void a(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int j() {
    return this.a;
  }
}
