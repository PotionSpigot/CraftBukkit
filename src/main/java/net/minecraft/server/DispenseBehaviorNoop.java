package net.minecraft.server;





final class DispenseBehaviorNoop
  implements IDispenseBehavior
{
  public ItemStack a(ISourceBlock paramISourceBlock, ItemStack paramItemStack)
  {
    return paramItemStack;
  }
}
