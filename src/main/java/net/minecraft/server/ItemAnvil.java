package net.minecraft.server;

public class ItemAnvil
  extends ItemMultiTexture
{
  public ItemAnvil(Block paramBlock)
  {
    super(paramBlock, paramBlock, BlockAnvil.a);
  }
  
  public int filterData(int paramInt)
  {
    return paramInt << 2;
  }
}
