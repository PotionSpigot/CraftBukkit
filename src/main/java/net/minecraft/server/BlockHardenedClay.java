package net.minecraft.server;


public class BlockHardenedClay
  extends Block
{
  public BlockHardenedClay()
  {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  
  public MaterialMapColor f(int paramInt)
  {
    return MaterialMapColor.q;
  }
}
