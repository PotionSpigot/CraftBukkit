package net.minecraft.server;









final class EntitySelectorMonster
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return paramEntity instanceof IMonster;
  }
}
