package net.minecraft.server;




final class EntitySelectorLiving
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return paramEntity.isAlive();
  }
}
