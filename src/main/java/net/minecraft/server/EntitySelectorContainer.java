package net.minecraft.server;
















final class EntitySelectorContainer
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return ((paramEntity instanceof IInventory)) && (paramEntity.isAlive());
  }
}
