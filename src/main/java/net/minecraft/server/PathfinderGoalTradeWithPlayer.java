package net.minecraft.server;



public class PathfinderGoalTradeWithPlayer
  extends PathfinderGoal
{
  private EntityVillager a;
  

  public PathfinderGoalTradeWithPlayer(EntityVillager paramEntityVillager)
  {
    this.a = paramEntityVillager;
    a(5);
  }
  
  public boolean a()
  {
    if (!this.a.isAlive()) return false;
    if (this.a.M()) return false;
    if (!this.a.onGround) return false;
    if (this.a.velocityChanged) { return false;
    }
    EntityHuman localEntityHuman = this.a.b();
    if (localEntityHuman == null)
    {
      return false;
    }
    
    if (this.a.f(localEntityHuman) > 16.0D)
    {
      return false;
    }
    
    if (!(localEntityHuman.activeContainer instanceof Container))
    {
      return false;
    }
    
    return true;
  }
  
  public void c()
  {
    this.a.getNavigation().h();
  }
  
  public void d()
  {
    this.a.a_(null);
  }
}
