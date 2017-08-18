package net.minecraft.server;


public class ItemLeaves
  extends ItemBlock
{
  private final BlockLeaves b;
  
  public ItemLeaves(BlockLeaves paramBlockLeaves)
  {
    super(paramBlockLeaves);
    this.b = paramBlockLeaves;
    
    setMaxDurability(0);
    a(true);
  }
  
  public int filterData(int paramInt)
  {
    return paramInt | 0x4;
  }
  










  public String a(ItemStack paramItemStack)
  {
    int i = paramItemStack.getData();
    if ((i < 0) || (i >= this.b.e().length)) {
      i = 0;
    }
    return super.getName() + "." + this.b.e()[i];
  }
}
