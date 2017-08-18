package net.minecraft.server;





































final class DispenseBehaviorExpBottle
  extends DispenseBehaviorProjectile
{
  protected IProjectile a(World paramWorld, IPosition paramIPosition)
  {
    return new EntityThrownExpBottle(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
  }
  
  protected float a()
  {
    return super.a() * 0.5F;
  }
  
  protected float b()
  {
    return super.b() * 1.25F;
  }
}
