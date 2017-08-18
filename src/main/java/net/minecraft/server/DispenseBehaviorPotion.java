package net.minecraft.server;






















































final class DispenseBehaviorPotion
  implements IDispenseBehavior
{
  private final DispenseBehaviorItem b = new DispenseBehaviorItem();
  
  public ItemStack a(ISourceBlock paramISourceBlock, ItemStack paramItemStack)
  {
    if (ItemPotion.g(paramItemStack.getData())) {
      return new DispenseBehaviorThrownPotion(this, paramItemStack).a(paramISourceBlock, paramItemStack);
    }
    














    return this.b.a(paramISourceBlock, paramItemStack);
  }
}
