package net.minecraft.server;


public class PathfinderGoalRestrictOpenDoor
  extends PathfinderGoal
{
  private EntityCreature a;
  private VillageDoor b;
  
  public PathfinderGoalRestrictOpenDoor(EntityCreature paramEntityCreature)
  {
    this.a = paramEntityCreature;
  }
  
  public boolean a()
  {
    if (this.a.world.w()) return false;
    Village localVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 16);
    if (localVillage == null) return false;
    this.b = localVillage.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
    if (this.b == null) return false;
    return this.b.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ)) < 2.25D;
  }
  
  public boolean b()
  {
    if (this.a.world.w()) return false;
    return (!this.b.removed) && (this.b.a(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locZ)));
  }
  
  public void c()
  {
    this.a.getNavigation().b(false);
    this.a.getNavigation().c(false);
  }
  
  public void d()
  {
    this.a.getNavigation().b(true);
    this.a.getNavigation().c(true);
    this.b = null;
  }
  
  public void e()
  {
    this.b.e();
  }
}
