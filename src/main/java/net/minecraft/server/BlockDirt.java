package net.minecraft.server;














public class BlockDirt
  extends Block
{
  public static final String[] a = { "default", "default", "podzol" };
  




  protected BlockDirt()
  {
    super(Material.EARTH);
    a(CreativeModeTab.b);
  }
  
































  public int getDropData(int paramInt)
  {
    return 0;
  }
  
  protected ItemStack j(int paramInt)
  {
    if (paramInt == 1) paramInt = 0;
    return super.j(paramInt);
  }
  














  public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (i == 1) {
      i = 0;
    }
    return i;
  }
}
