package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget
{
  boolean a;
  private int b;
  
  public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag)
  {
    super(entitycreature, false);
    this.a = flag;
    a(1);
  }
  
  public boolean a() {
    int i = this.c.aK();
    
    return (i != this.b) && (a(this.c.getLastDamager(), false));
  }
  
  public void c() {
    this.c.setGoalTarget(this.c.getLastDamager());
    this.b = this.c.aK();
    if (this.a) {
      double d0 = f();
      java.util.List list = this.c.world.a(this.c.getClass(), AxisAlignedBB.a(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(d0, 10.0D, d0));
      java.util.Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        EntityCreature entitycreature = (EntityCreature)iterator.next();
        
        if ((this.c != entitycreature) && (entitycreature.getGoalTarget() == null) && (!entitycreature.c(this.c.getLastDamager())))
        {
          EntityTargetLivingEntityEvent event = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityTargetLivingEvent(entitycreature, this.c.getLastDamager(), org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY);
          if (!event.isCancelled())
          {

            entitycreature.setGoalTarget(event.getTarget() == null ? null : ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity)event.getTarget()).getHandle());
          }
        }
      }
    }
    
    super.c();
  }
}
