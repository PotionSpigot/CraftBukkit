package net.minecraft.server;

public class PathfinderGoalOwnerHurtByTarget extends PathfinderGoalTarget
{
  EntityTameableAnimal a;
  EntityLiving b;
  private int e;
  
  public PathfinderGoalOwnerHurtByTarget(EntityTameableAnimal paramEntityTameableAnimal)
  {
    super(paramEntityTameableAnimal, false);
    this.a = paramEntityTameableAnimal;
    a(1);
  }
  
  public boolean a()
  {
    if (!this.a.isTamed()) return false;
    EntityLiving localEntityLiving = this.a.getOwner();
    if (localEntityLiving == null) return false;
    this.b = localEntityLiving.getLastDamager();
    int i = localEntityLiving.aK();
    return (i != this.e) && (a(this.b, false)) && (this.a.a(this.b, localEntityLiving));
  }
  
  public void c()
  {
    this.c.setGoalTarget(this.b);
    
    EntityLiving localEntityLiving = this.a.getOwner();
    if (localEntityLiving != null) {
      this.e = localEntityLiving.aK();
    }
    
    super.c();
  }
}
