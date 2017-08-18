package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalArrowAttack extends PathfinderGoal
{
  private final EntityInsentient a;
  private final IRangedEntity b;
  private EntityLiving c;
  private int d;
  private double e;
  private int f;
  private int g;
  private int h;
  private float i;
  private float j;
  
  public PathfinderGoalArrowAttack(IRangedEntity irangedentity, double d0, int i, float f) {
    this(irangedentity, d0, i, i, f);
  }
  
  public PathfinderGoalArrowAttack(IRangedEntity irangedentity, double d0, int i, int j, float f) {
    this.d = -1;
    if (!(irangedentity instanceof EntityLiving)) {
      throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
    }
    this.b = irangedentity;
    this.a = ((EntityInsentient)irangedentity);
    this.e = d0;
    this.g = i;
    this.h = j;
    this.i = f;
    this.j = (f * f);
    a(3);
  }
  
  public boolean a()
  {
    EntityLiving entityliving = this.a.getGoalTarget();
    
    if (entityliving == null) {
      return false;
    }
    this.c = entityliving;
    return true;
  }
  
  public boolean b()
  {
    return (a()) || (!this.a.getNavigation().g());
  }
  
  public void d()
  {
    EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityTargetEvent((Entity)this.b, null, reason);
    
    this.c = null;
    this.f = 0;
    this.d = -1;
  }
  
  public void e() {
    double d0 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
    boolean flag = this.a.getEntitySenses().canSee(this.c);
    
    if (flag) {
      this.f += 1;
    } else {
      this.f = 0;
    }
    
    if ((d0 <= this.j) && (this.f >= 20)) {
      this.a.getNavigation().h();
    } else {
      this.a.getNavigation().a(this.c, this.e);
    }
    
    this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
    

    if (--this.d == 0) {
      if ((d0 > this.j) || (!flag)) {
        return;
      }
      
      float f = MathHelper.sqrt(d0) / this.i;
      float f1 = f;
      
      if (f < 0.1F) {
        f1 = 0.1F;
      }
      
      if (f1 > 1.0F) {
        f1 = 1.0F;
      }
      
      this.b.a(this.c, f1);
      this.d = MathHelper.d(f * (this.h - this.g) + this.g);
    } else if (this.d < 0) {
      float f = MathHelper.sqrt(d0) / this.i;
      this.d = MathHelper.d(f * (this.h - this.g) + this.g);
    }
  }
}
