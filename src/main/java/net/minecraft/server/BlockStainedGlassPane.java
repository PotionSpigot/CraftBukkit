package net.minecraft.server;









public class BlockStainedGlassPane
  extends BlockThin
{
  private static final IIcon[] a = new IIcon[16];
  private static final IIcon[] b = new IIcon[16];
  
  public BlockStainedGlassPane() {
    super("glass", "glass_pane_top", Material.SHATTERABLE, false);
    a(CreativeModeTab.c);
  }
  














  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
