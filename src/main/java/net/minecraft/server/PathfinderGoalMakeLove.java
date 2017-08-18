package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalMakeLove extends PathfinderGoal {
  private EntityVillager b;
  private EntityVillager c;
  private World d;
  private int e;
  Village a;
  
  public PathfinderGoalMakeLove(EntityVillager entityvillager) { this.b = entityvillager;
    this.d = entityvillager.world;
    a(3);
  }
  
  public boolean a() {
    if (this.b.getAge() != 0)
      return false;
    if (this.b.aI().nextInt(500) != 0) {
      return false;
    }
    this.a = this.d.villages.getClosestVillage(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.locY), MathHelper.floor(this.b.locZ), 0);
    if (this.a == null)
      return false;
    if (!f()) {
      return false;
    }
    Entity entity = this.d.a(EntityVillager.class, this.b.boundingBox.grow(8.0D, 3.0D, 8.0D), this.b);
    
    if (entity == null) {
      return false;
    }
    this.c = ((EntityVillager)entity);
    return this.c.getAge() == 0;
  }
  


  public void c()
  {
    this.e = 300;
    this.b.i(true);
  }
  
  public void d() {
    this.a = null;
    this.c = null;
    this.b.i(false);
  }
  
  public boolean b() {
    return (this.e >= 0) && (f()) && (this.b.getAge() == 0);
  }
  
  public void e() {
    this.e -= 1;
    this.b.getControllerLook().a(this.c, 10.0F, 30.0F);
    if (this.b.f(this.c) > 2.25D) {
      this.b.getNavigation().a(this.c, 0.25D);
    } else if ((this.e == 0) && (this.c.ca())) {
      g();
    }
    
    if (this.b.aI().nextInt(35) == 0) {
      this.d.broadcastEntityEffect(this.b, (byte)12);
    }
  }
  
  private boolean f() {
    if (!this.a.i()) {
      return false;
    }
    int i = (int)(this.a.getDoorCount() * 0.35D);
    
    return this.a.getPopulationCount() < i;
  }
  
  private void g()
  {
    EntityVillager entityvillager = this.b.b(this.c);
    
    this.c.setAge(6000);
    this.b.setAge(6000);
    entityvillager.setAge(41536);
    entityvillager.setPositionRotation(this.b.locX, this.b.locY, this.b.locZ, 0.0F, 0.0F);
    this.d.addEntity(entityvillager, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING);
    this.d.broadcastEntityEffect(entityvillager, (byte)12);
  }
}
