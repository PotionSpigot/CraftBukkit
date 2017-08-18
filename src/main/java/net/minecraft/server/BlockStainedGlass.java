package net.minecraft.server;

import java.util.Random;




public class BlockStainedGlass
  extends BlockHalfTransparent
{
  private static final IIcon[] a = new IIcon[16];
  
  public BlockStainedGlass(Material paramMaterial) {
    super("glass", paramMaterial, false);
    a(CreativeModeTab.b);
  }
  





  public int getDropData(int paramInt)
  {
    return paramInt;
  }
  























  public int a(Random paramRandom)
  {
    return 0;
  }
  
  protected boolean E()
  {
    return true;
  }
  
  public boolean d()
  {
    return false;
  }
}
