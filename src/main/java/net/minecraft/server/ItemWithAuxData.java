package net.minecraft.server;


public class ItemWithAuxData
  extends ItemBlock
{
  private final Block b;
  private String[] c;
  
  public ItemWithAuxData(Block paramBlock, boolean paramBoolean)
  {
    super(paramBlock);
    this.b = paramBlock;
    
    if (paramBoolean) {
      setMaxDurability(0);
      a(true);
    }
  }
  










  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public ItemWithAuxData a(String[] paramArrayOfString) {
    this.c = paramArrayOfString;
    return this;
  }
  
  public String a(ItemStack paramItemStack)
  {
    if (this.c == null) {
      return super.a(paramItemStack);
    }
    int i = paramItemStack.getData();
    if ((i >= 0) && (i < this.c.length)) {
      return super.a(paramItemStack) + "." + this.c[i];
    }
    return super.a(paramItemStack);
  }
}
