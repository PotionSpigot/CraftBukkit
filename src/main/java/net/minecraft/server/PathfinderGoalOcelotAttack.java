package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalOcelotAttack extends PathfinderGoal
{
  World a;
  EntityInsentient b;
  EntityLiving c;
  int d;
  
  public PathfinderGoalOcelotAttack(EntityInsentient entityinsentient) {
    this.b = entityinsentient;
    this.a = entityinsentient.world;
    a(3);
  }
  
  public boolean a() {
    EntityLiving entityliving = this.b.getGoalTarget();
    
    if (entityliving == null) {
      return false;
    }
    this.c = entityliving;
    return true;
  }
  
  public boolean b()
  {
    return this.c.isAlive();
  }
  
  public void d()
  {
    EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityTargetEvent(this.c, null, reason);
    
    this.c = null;
    this.b.getNavigation().h();
  }
  
  public void e() {
    this.b.getControllerLook().a(this.c, 30.0F, 30.0F);
    double d0 = this.b.width * 2.0F * this.b.width * 2.0F;
    double d1 = this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
    double d2 = 0.8D;
    
    if ((d1 > d0) && (d1 < 16.0D)) {
      d2 = 1.33D;
    } else if (d1 < 225.0D) {
      d2 = 0.6D;
    }
    
    this.b.getNavigation().a(this.c, d2);
    this.d = Math.max(this.d - 1, 0);
    if ((d1 <= d0) && 
      (this.d <= 0)) {
      this.d = 20;
      this.b.n(this.c);
    }
  }
}
