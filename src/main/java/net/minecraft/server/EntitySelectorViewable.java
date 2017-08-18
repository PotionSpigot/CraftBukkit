package net.minecraft.server;





class EntitySelectorViewable
  implements IEntitySelector
{
  EntitySelectorViewable(PathfinderGoalAvoidPlayer paramPathfinderGoalAvoidPlayer) {}
  



  public boolean a(Entity paramEntity)
  {
    return (paramEntity.isAlive()) && (PathfinderGoalAvoidPlayer.a(this.d).getEntitySenses().canSee(paramEntity));
  }
}
