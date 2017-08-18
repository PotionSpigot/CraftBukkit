package net.minecraft.server;

public class PathfinderGoalPanic extends PathfinderGoal
{
  private EntityCreature a;
  private double b;
  private double c;
  private double d;
  private double e;
  
  public PathfinderGoalPanic(EntityCreature entitycreature, double d0) {
    this.a = entitycreature;
    this.b = d0;
    a(1);
  }
  
  public boolean a() {
    if ((this.a.getLastDamager() == null) && (!this.a.isBurning())) {
      return false;
    }
    Vec3D vec3d = RandomPositionGenerator.a(this.a, 5, 4);
    
    if (vec3d == null) {
      return false;
    }
    this.c = vec3d.a;
    this.d = vec3d.b;
    this.e = vec3d.c;
    return true;
  }
  

  public void c()
  {
    this.a.getNavigation().a(this.c, this.d, this.e, this.b);
  }
  
  public boolean b()
  {
    if (this.a.ticksLived - this.a.aK() > 100) {
      this.a.b((EntityLiving)null);
      return false;
    }
    
    return !this.a.getNavigation().g();
  }
}
