package net.minecraft.server;


public class PathfinderGoalLookAtTradingPlayer
  extends PathfinderGoalLookAtPlayer
{
  private final EntityVillager b;
  
  public PathfinderGoalLookAtTradingPlayer(EntityVillager paramEntityVillager)
  {
    super(paramEntityVillager, EntityHuman.class, 8.0F);
    this.b = paramEntityVillager;
  }
  
  public boolean a()
  {
    if (this.b.cc()) {
      this.a = this.b.b();
      return true;
    }
    return false;
  }
}
