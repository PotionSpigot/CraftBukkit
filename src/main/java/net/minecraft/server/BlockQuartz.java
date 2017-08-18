package net.minecraft.server;














public class BlockQuartz
  extends Block
{
  public static final String[] a = { "default", "chiseled", "lines" };
  






  private static final String[] b = { "side", "chiseled", "lines", null, null };
  







  public BlockQuartz()
  {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  



























  public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5)
  {
    if (paramInt5 == 2) {
      switch (paramInt4) {
      case 2: 
      case 3: 
        paramInt5 = 4;
        break;
      case 4: 
      case 5: 
        paramInt5 = 3;
        break;
      case 0: 
      case 1: 
        paramInt5 = 2;
      }
      
    }
    
    return paramInt5;
  }
  
  public int getDropData(int paramInt)
  {
    if ((paramInt == 3) || (paramInt == 4)) { return 2;
    }
    return paramInt;
  }
  
  protected ItemStack j(int paramInt)
  {
    if ((paramInt == 3) || (paramInt == 4)) return new ItemStack(Item.getItemOf(this), 1, 2);
    return super.j(paramInt);
  }
  
  public int b()
  {
    return 39;
  }
  

























  public MaterialMapColor f(int paramInt)
  {
    return MaterialMapColor.p;
  }
}
