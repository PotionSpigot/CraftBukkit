package net.minecraft.server;

























final class DispenseBehaviorEgg
  extends DispenseBehaviorProjectile
{
  protected IProjectile a(World paramWorld, IPosition paramIPosition)
  {
    return new EntityEgg(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
  }
}
