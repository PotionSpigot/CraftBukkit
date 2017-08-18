package net.minecraft.server;



public class BlockRedstone
  extends BlockOreBlock
{
  public BlockRedstone(MaterialMapColor paramMaterialMapColor)
  {
    super(paramMaterialMapColor);
    a(CreativeModeTab.d);
  }
  
  public boolean isPowerSource()
  {
    return true;
  }
  
  public int b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return 15;
  }
}
