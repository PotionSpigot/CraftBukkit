package net.minecraft.server;










final class EntitySelectorChickenJockey
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return (paramEntity.isAlive()) && (paramEntity.passenger == null) && (paramEntity.vehicle == null);
  }
}
