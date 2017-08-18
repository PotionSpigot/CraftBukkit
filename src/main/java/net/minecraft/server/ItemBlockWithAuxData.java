package net.minecraft.server;

public class ItemBlockWithAuxData
  extends ItemBlock
{
  private Block b;
  
  public ItemBlockWithAuxData(Block paramBlock1, Block paramBlock2)
  {
    super(paramBlock1);
    
    this.b = paramBlock2;
    
    setMaxDurability(0);
    a(true);
  }
  





  public int filterData(int paramInt)
  {
    return paramInt;
  }
}
