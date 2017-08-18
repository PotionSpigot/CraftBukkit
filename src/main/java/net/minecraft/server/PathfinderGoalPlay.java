package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;



public class PathfinderGoalPlay
  extends PathfinderGoal
{
  private EntityVillager a;
  private EntityLiving b;
  private double c;
  private int d;
  
  public PathfinderGoalPlay(EntityVillager paramEntityVillager, double paramDouble)
  {
    this.a = paramEntityVillager;
    this.c = paramDouble;
    a(1);
  }
  
  public boolean a()
  {
    if (this.a.getAge() >= 0) return false;
    if (this.a.aI().nextInt(400) != 0) { return false;
    }
    List localList = this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 3.0D, 6.0D));
    double d1 = Double.MAX_VALUE;
    for (Object localObject = localList.iterator(); ((Iterator)localObject).hasNext();) { EntityVillager localEntityVillager = (EntityVillager)((Iterator)localObject).next();
      if ((localEntityVillager != this.a) && 
        (!localEntityVillager.cb()) && 
        (localEntityVillager.getAge() < 0)) {
        double d2 = localEntityVillager.f(this.a);
        if (d2 <= d1) {
          d1 = d2;
          this.b = localEntityVillager;
        }
      } }
    if (this.b == null) {
      localObject = RandomPositionGenerator.a(this.a, 16, 3);
      if (localObject == null) return false;
    }
    return true;
  }
  
  public boolean b()
  {
    return this.d > 0;
  }
  
  public void c()
  {
    if (this.b != null) this.a.j(true);
    this.d = 1000;
  }
  
  public void d()
  {
    this.a.j(false);
    this.b = null;
  }
  
  public void e()
  {
    this.d -= 1;
    if (this.b != null) {
      if (this.a.f(this.b) > 4.0D) this.a.getNavigation().a(this.b, this.c);
    }
    else if (this.a.getNavigation().g()) {
      Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 3);
      if (localVec3D == null) return;
      this.a.getNavigation().a(localVec3D.a, localVec3D.b, localVec3D.c, this.c);
    }
  }
}
