package net.minecraft.server;

public class BlockTransparent
  extends Block
{
  protected boolean P;
  
  protected BlockTransparent(Material paramMaterial, boolean paramBoolean)
  {
    super(paramMaterial);
    this.P = paramBoolean;
  }
  
  public boolean c()
  {
    return false;
  }
}
