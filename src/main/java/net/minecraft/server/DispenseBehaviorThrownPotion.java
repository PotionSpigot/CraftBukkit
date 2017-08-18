package net.minecraft.server;






























class DispenseBehaviorThrownPotion
  extends DispenseBehaviorProjectile
{
  DispenseBehaviorThrownPotion(DispenseBehaviorPotion paramDispenseBehaviorPotion, ItemStack paramItemStack) {}
  




























  protected IProjectile a(World paramWorld, IPosition paramIPosition)
  {
    return new EntityPotion(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ(), this.b.cloneItemStack());
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
