package net.minecraft.server;





public abstract interface IDispenseBehavior
{
  public static final IDispenseBehavior a = new DispenseBehaviorNoop();
  
  public abstract ItemStack a(ISourceBlock paramISourceBlock, ItemStack paramItemStack);
}
