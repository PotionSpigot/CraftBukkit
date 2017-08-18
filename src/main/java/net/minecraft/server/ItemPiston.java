package net.minecraft.server;

public class ItemPiston
  extends ItemBlock
{
  public ItemPiston(Block paramBlock)
  {
    super(paramBlock);
  }
  

  public int filterData(int paramInt)
  {
    return 7;
  }
}
