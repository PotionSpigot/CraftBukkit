package net.minecraft.server;
















final class DispenseBehaviorArrow
  extends DispenseBehaviorProjectile
{
  protected IProjectile a(World paramWorld, IPosition paramIPosition)
  {
    EntityArrow localEntityArrow = new EntityArrow(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
    localEntityArrow.fromPlayer = 1;
    
    return localEntityArrow;
  }
}
