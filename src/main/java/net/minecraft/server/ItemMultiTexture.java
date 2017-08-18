package net.minecraft.server;

public class ItemMultiTexture
  extends ItemBlock
{
  protected final Block b;
  protected final String[] c;
  
  public ItemMultiTexture(Block paramBlock1, Block paramBlock2, String[] paramArrayOfString)
  {
    super(paramBlock1);
    
    this.b = paramBlock2;
    this.c = paramArrayOfString;
    
    setMaxDurability(0);
    a(true);
  }
  





  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public String a(ItemStack paramItemStack)
  {
    int i = paramItemStack.getData();
    if ((i < 0) || (i >= this.c.length)) {
      i = 0;
    }
    return super.getName() + "." + this.c[i];
  }
}
