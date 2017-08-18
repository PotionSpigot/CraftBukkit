package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget
{
  EntityIronGolem a;
  EntityLiving b;
  
  public PathfinderGoalDefendVillage(EntityIronGolem paramEntityIronGolem)
  {
    super(paramEntityIronGolem, false, true);
    this.a = paramEntityIronGolem;
    a(1);
  }
  
  public boolean a()
  {
    Village localVillage = this.a.bZ();
    if (localVillage == null) return false;
    this.b = localVillage.b(this.a);
    if (!a(this.b, false))
    {
      if (this.c.aI().nextInt(20) == 0) {
        this.b = localVillage.c(this.a);
        return a(this.b, false);
      }
      return false;
    }
    return true;
  }
  

  public void c()
  {
    this.a.setGoalTarget(this.b);
    super.c();
  }
}
