package net.minecraft.server;











public class BlockSandStone
  extends Block
{
  public static final String[] a = { "default", "chiseled", "smooth" };
  


  private static final String[] b = { "normal", "carved", "smooth" };
  





  public BlockSandStone()
  {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  












  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
