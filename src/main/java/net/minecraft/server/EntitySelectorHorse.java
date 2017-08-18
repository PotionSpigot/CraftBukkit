package net.minecraft.server;





























final class EntitySelectorHorse
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return ((paramEntity instanceof EntityHorse)) && (((EntityHorse)paramEntity).co());
  }
}
