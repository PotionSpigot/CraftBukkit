package net.minecraft.server;

import java.util.List;
import java.util.Random;


public class PathfinderGoalTakeFlower
  extends PathfinderGoal
{
  private EntityVillager a;
  private EntityIronGolem b;
  private int c;
  private boolean d;
  
  public PathfinderGoalTakeFlower(EntityVillager paramEntityVillager)
  {
    this.a = paramEntityVillager;
    a(3);
  }
  
  public boolean a()
  {
    if (this.a.getAge() >= 0) return false;
    if (!this.a.world.w()) { return false;
    }
    List localList = this.a.world.a(EntityIronGolem.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D));
    if (localList.isEmpty()) { return false;
    }
    for (EntityIronGolem localEntityIronGolem : localList) {
      if (localEntityIronGolem.cb() > 0) {
        this.b = localEntityIronGolem;
        break;
      }
    }
    return this.b != null;
  }
  
  public boolean b()
  {
    return this.b.cb() > 0;
  }
  
  public void c()
  {
    this.c = this.a.aI().nextInt(320);
    this.d = false;
    this.b.getNavigation().h();
  }
  
  public void d()
  {
    this.b = null;
    this.a.getNavigation().h();
  }
  
  public void e()
  {
    this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
    if (this.b.cb() == this.c) {
      this.a.getNavigation().a(this.b, 0.5D);
      this.d = true;
    }
    
    if ((this.d) && 
      (this.a.f(this.b) < 4.0D)) {
      this.b.a(false);
      this.a.getNavigation().h();
    }
  }
}
