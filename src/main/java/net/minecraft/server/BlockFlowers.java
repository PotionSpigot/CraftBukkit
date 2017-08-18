package net.minecraft.server;









public class BlockFlowers
  extends BlockPlant
{
  private static final String[][] M = { { "flower_dandelion" }, { "flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy" } };
  



















  public static final String[] a = { "poppy", "blueOrchid", "allium", "houstonia", "tulipRed", "tulipOrange", "tulipWhite", "tulipPink", "oxeyeDaisy" };
  


  public static final String[] b = { "dandelion" };
  

  private int O;
  

  protected BlockFlowers(int paramInt)
  {
    super(Material.PLANT);
    this.O = paramInt;
  }
  















  public int getDropData(int paramInt)
  {
    return paramInt;
  }
  



  public static BlockFlowers e(String paramString)
  {
    String str;
    

    for (str : b) {
      if (str.equals(paramString)) {
        return Blocks.YELLOW_FLOWER;
      }
    }
    for (str : a) {
      if (str.equals(paramString)) {
        return Blocks.RED_ROSE;
      }
    }
    return null;
  }
  
  public static int f(String paramString) {
    for (int i = 0; i < b.length; i++) {
      if (b[i].equals(paramString)) {
        return i;
      }
    }
    for (i = 0; i < a.length; i++) {
      if (a[i].equals(paramString)) {
        return i;
      }
    }
    return 0;
  }
}
