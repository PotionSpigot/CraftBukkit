package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalRandomStroll
  extends PathfinderGoal
{
  private EntityCreature a;
  private double b;
  private double c;
  private double d;
  private double e;
  
  public PathfinderGoalRandomStroll(EntityCreature paramEntityCreature, double paramDouble)
  {
    this.a = paramEntityCreature;
    this.e = paramDouble;
    a(1);
  }
  
  public boolean a()
  {
    if (this.a.aN() >= 100) return false;
    if (this.a.aI().nextInt(120) != 0) { return false;
    }
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 10, 7);
    if (localVec3D == null) return false;
    this.b = localVec3D.a;
    this.c = localVec3D.b;
    this.d = localVec3D.c;
    return true;
  }
  
  public boolean b()
  {
    return !this.a.getNavigation().g();
  }
  
  public void c()
  {
    this.a.getNavigation().a(this.b, this.c, this.d, this.e);
  }
}
