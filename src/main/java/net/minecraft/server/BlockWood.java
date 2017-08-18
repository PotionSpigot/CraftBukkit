package net.minecraft.server;






public class BlockWood
  extends Block
{
  public static final String[] a = { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
  



  public BlockWood()
  {
    super(Material.WOOD);
    a(CreativeModeTab.b);
  }
  








  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
