package net.minecraft.server;

public class PathfinderGoalSit extends PathfinderGoal
{
  private EntityTameableAnimal entity;
  private boolean willSit;
  
  public PathfinderGoalSit(EntityTameableAnimal entitytameableanimal) {
    this.entity = entitytameableanimal;
    a(5);
  }
  
  public boolean a() {
    if (!this.entity.isTamed())
      return (this.willSit) && (this.entity.getGoalTarget() == null);
    if (this.entity.M())
      return false;
    if (!this.entity.onGround) {
      return false;
    }
    EntityLiving entityliving = this.entity.getOwner();
    
    return (this.entity.f(entityliving) < 144.0D) && (entityliving.getLastDamager() != null) ? false : entityliving == null ? true : this.willSit;
  }
  
  public void c()
  {
    this.entity.getNavigation().h();
    this.entity.setSitting(true);
  }
  
  public void d() {
    this.entity.setSitting(false);
  }
  
  public void setSitting(boolean flag) {
    this.willSit = flag;
  }
}
