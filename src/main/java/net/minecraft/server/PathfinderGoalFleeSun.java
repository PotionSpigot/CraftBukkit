package net.minecraft.server;

import java.util.Random;



public class PathfinderGoalFleeSun
  extends PathfinderGoal
{
  private EntityCreature a;
  private double b;
  private double c;
  private double d;
  private double e;
  private World f;
  
  public PathfinderGoalFleeSun(EntityCreature paramEntityCreature, double paramDouble)
  {
    this.a = paramEntityCreature;
    this.e = paramDouble;
    this.f = paramEntityCreature.world;
    a(1);
  }
  
  public boolean a()
  {
    if (!this.f.w()) return false;
    if (!this.a.isBurning()) return false;
    if (!this.f.i(MathHelper.floor(this.a.locX), (int)this.a.boundingBox.b, MathHelper.floor(this.a.locZ))) { return false;
    }
    Vec3D localVec3D = f();
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
  
  private Vec3D f() {
    Random localRandom = this.a.aI();
    for (int i = 0; i < 10; i++) {
      int j = MathHelper.floor(this.a.locX + localRandom.nextInt(20) - 10.0D);
      int k = MathHelper.floor(this.a.boundingBox.b + localRandom.nextInt(6) - 3.0D);
      int m = MathHelper.floor(this.a.locZ + localRandom.nextInt(20) - 10.0D);
      if ((!this.f.i(j, k, m)) && (this.a.a(j, k, m) < 0.0F)) return Vec3D.a(j, k, m);
    }
    return null;
  }
}
