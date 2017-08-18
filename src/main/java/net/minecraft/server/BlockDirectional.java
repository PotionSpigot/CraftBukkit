package net.minecraft.server;




public abstract class BlockDirectional
  extends Block
{
  protected BlockDirectional(Material paramMaterial)
  {
    super(paramMaterial);
  }
  
  public static int l(int paramInt) {
    return paramInt & 0x3;
  }
}
