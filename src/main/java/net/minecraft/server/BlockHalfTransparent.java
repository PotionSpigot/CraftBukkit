package net.minecraft.server;


public class BlockHalfTransparent
  extends Block
{
  private boolean a;
  
  private String b;
  
  protected BlockHalfTransparent(String paramString, Material paramMaterial, boolean paramBoolean)
  {
    super(paramMaterial);
    this.a = paramBoolean;
    this.b = paramString;
  }
  
  public boolean c()
  {
    return false;
  }
}
