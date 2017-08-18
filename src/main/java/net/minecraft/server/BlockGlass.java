package net.minecraft.server;

import java.util.Random;


public class BlockGlass
  extends BlockHalfTransparent
{
  public BlockGlass(Material paramMaterial, boolean paramBoolean)
  {
    super("glass", paramMaterial, paramBoolean);
    a(CreativeModeTab.b);
  }
  
  public int a(Random paramRandom)
  {
    return 0;
  }
  





  public boolean d()
  {
    return false;
  }
  
  protected boolean E()
  {
    return true;
  }
}
