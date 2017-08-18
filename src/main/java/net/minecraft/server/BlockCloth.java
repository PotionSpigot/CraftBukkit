package net.minecraft.server;








public class BlockCloth
  extends Block
{
  public BlockCloth(Material paramMaterial)
  {
    super(paramMaterial);
    a(CreativeModeTab.b);
  }
  





  public int getDropData(int paramInt)
  {
    return paramInt;
  }
  
  public static int b(int paramInt) {
    return c(paramInt);
  }
  
  public static int c(int paramInt) {
    return (paramInt ^ 0xFFFFFFFF) & 0xF;
  }
  
















  public MaterialMapColor f(int paramInt)
  {
    return MaterialMapColor.a(paramInt);
  }
}
