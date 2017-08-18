package net.minecraft.server;


public class PathfinderGoalMoveTowardsTarget
  extends PathfinderGoal
{
  private EntityCreature a;
  private EntityLiving b;
  private double c;
  private double d;
  private double e;
  private double f;
  private float g;
  
  public PathfinderGoalMoveTowardsTarget(EntityCreature paramEntityCreature, double paramDouble, float paramFloat)
  {
    this.a = paramEntityCreature;
    this.f = paramDouble;
    this.g = paramFloat;
    a(1);
  }
  
  public boolean a()
  {
    this.b = this.a.getGoalTarget();
    if (this.b == null) return false;
    if (this.b.f(this.a) > this.g * this.g) return false;
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.a(this.b.locX, this.b.locY, this.b.locZ));
    if (localVec3D == null) return false;
    this.c = localVec3D.a;
    this.d = localVec3D.b;
    this.e = localVec3D.c;
    return true;
  }
  
  public boolean b()
  {
    return (!this.a.getNavigation().g()) && (this.b.isAlive()) && (this.b.f(this.a) < this.g * this.g);
  }
  
  public void d()
  {
    this.b = null;
  }
  
  public void c()
  {
    this.a.getNavigation().a(this.c, this.d, this.e, this.f);
  }
}
