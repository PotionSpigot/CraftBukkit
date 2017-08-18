package net.minecraft.server;











public class BlockSmoothBrick
  extends Block
{
  public static final String[] a = { "default", "mossy", "cracked", "chiseled" };
  


  public static final String[] b = { null, "mossy", "cracked", "carved" };
  



  public BlockSmoothBrick()
  {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  






  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
