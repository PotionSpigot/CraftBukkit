package net.minecraft.server;































final class DispenseBehaviorSnowBall
  extends DispenseBehaviorProjectile
{
  protected IProjectile a(World paramWorld, IPosition paramIPosition)
  {
    return new EntitySnowball(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
  }
}
