package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalLookAtPlayer
  extends PathfinderGoal
{
  private EntityInsentient b;
  protected Entity a;
  private float c;
  private int d;
  private float e;
  private Class f;
  
  public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class paramClass, float paramFloat)
  {
    this.b = paramEntityInsentient;
    this.f = paramClass;
    this.c = paramFloat;
    this.e = 0.02F;
    a(2);
  }
  
  public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class paramClass, float paramFloat1, float paramFloat2) {
    this.b = paramEntityInsentient;
    this.f = paramClass;
    this.c = paramFloat1;
    this.e = paramFloat2;
    a(2);
  }
  
  public boolean a()
  {
    if (this.b.aI().nextFloat() >= this.e) { return false;
    }
    if (this.b.getGoalTarget() != null)
      this.a = this.b.getGoalTarget();
    if (this.f == EntityHuman.class) {
      this.a = this.b.world.findNearbyPlayer(this.b, this.c);
    } else {
      this.a = this.b.world.a(this.f, this.b.boundingBox.grow(this.c, 3.0D, this.c), this.b);
    }
    
    return this.a != null;
  }
  
  public boolean b()
  {
    if (!this.a.isAlive()) return false;
    if (this.b.f(this.a) > this.c * this.c) return false;
    return this.d > 0;
  }
  
  public void c()
  {
    this.d = (40 + this.b.aI().nextInt(40));
  }
  
  public void d()
  {
    this.a = null;
  }
  
  public void e()
  {
    this.b.getControllerLook().a(this.a.locX, this.a.locY + this.a.getHeadHeight(), this.a.locZ, 10.0F, this.b.x());
    this.d -= 1;
  }
}
