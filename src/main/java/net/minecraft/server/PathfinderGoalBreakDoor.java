package net.minecraft.server;

import org.bukkit.event.entity.EntityBreakDoorEvent;

public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract { private int i;
  private int j = -1;
  
  public PathfinderGoalBreakDoor(EntityInsentient entityinsentient) {
    super(entityinsentient);
  }
  
  public boolean a() {
    return super.a();
  }
  
  public void c() {
    super.c();
    this.i = 0;
  }
  
  public boolean b() {
    double d0 = this.a.e(this.b, this.c, this.d);
    
    return (this.i <= 240) && (!this.e.f(this.a.world, this.b, this.c, this.d)) && (d0 < 4.0D);
  }
  
  public void d() {
    super.d();
    this.a.world.d(this.a.getId(), this.b, this.c, this.d, -1);
  }
  
  public void e() {
    super.e();
    if (this.a.aI().nextInt(20) == 0) {
      this.a.world.triggerEffect(1010, this.b, this.c, this.d, 0);
    }
    
    this.i += 1;
    int i = (int)(this.i / 240.0F * 10.0F);
    
    if (i != this.j) {
      this.a.world.d(this.a.getId(), this.b, this.c, this.d, i);
      this.j = i;
    }
    
    if ((this.i == 240) && (this.a.world.difficulty == EnumDifficulty.HARD))
    {
      if (org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityBreakDoorEvent(this.a, this.b, this.c, this.d).isCancelled()) {
        c();
        return;
      }
      

      this.a.world.setAir(this.b, this.c, this.d);
      this.a.world.triggerEffect(1012, this.b, this.c, this.d, 0);
      this.a.world.triggerEffect(2001, this.b, this.c, this.d, Block.getId(this.e));
    }
  }
}
