package net.minecraft.server;












class EntitySelectorNearestAttackableTarget
  implements IEntitySelector
{
  EntitySelectorNearestAttackableTarget(PathfinderGoalNearestAttackableTarget paramPathfinderGoalNearestAttackableTarget, IEntitySelector paramIEntitySelector) {}
  











  public boolean a(Entity paramEntity)
  {
    if (!(paramEntity instanceof EntityLiving)) return false;
    if ((this.d != null) && (!this.d.a(paramEntity))) return false;
    return this.e.a((EntityLiving)paramEntity, false);
  }
}
