package net.minecraft.server;

public class ItemBook extends Item
{
  public boolean e_(ItemStack paramItemStack) {
    return paramItemStack.count == 1;
  }
  
  public int c()
  {
    return 1;
  }
}
