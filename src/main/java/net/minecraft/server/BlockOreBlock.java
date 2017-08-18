package net.minecraft.server;


public class BlockOreBlock
  extends Block
{
  private final MaterialMapColor a;
  
  public BlockOreBlock(MaterialMapColor paramMaterialMapColor)
  {
    super(Material.ORE);
    this.a = paramMaterialMapColor;
    a(CreativeModeTab.b);
  }
  
  public MaterialMapColor f(int paramInt)
  {
    return this.a;
  }
}
