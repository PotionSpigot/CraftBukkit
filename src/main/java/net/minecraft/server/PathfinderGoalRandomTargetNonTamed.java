package net.minecraft.server;

public class PathfinderGoalRandomTargetNonTamed extends PathfinderGoalNearestAttackableTarget
{
  private EntityTameableAnimal a;
  
  public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal paramEntityTameableAnimal, Class paramClass, int paramInt, boolean paramBoolean)
  {
    super(paramEntityTameableAnimal, paramClass, paramInt, paramBoolean);
    this.a = paramEntityTameableAnimal;
  }
  
  public boolean a()
  {
    return (!this.a.isTamed()) && (super.a());
  }
}
