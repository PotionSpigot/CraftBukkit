package net.minecraft.server;








public class Direction
{
  public static final int[] a = { 0, -1, 0, 1 };
  

  public static final int[] b = { 1, 0, -1, 0 };
  


  public static final String[] c = { "SOUTH", "WEST", "NORTH", "EAST" };
  



  public static final int[] d = { 3, 4, 2, 5 };
  



  public static final int[] e = { -1, -1, 2, 0, 1, 3 };
  



  public static final int[] f = { 2, 3, 0, 1 };
  



  public static final int[] g = { 1, 2, 3, 0 };
  



  public static final int[] h = { 3, 0, 1, 2 };
  



  public static final int[][] i = { { 1, 0, 3, 2, 5, 4 }, { 1, 0, 5, 4, 2, 3 }, { 1, 0, 2, 3, 4, 5 }, { 1, 0, 4, 5, 3, 2 } };
  
















  public static int a(double paramDouble1, double paramDouble2)
  {
    if (MathHelper.abs((float)paramDouble1) > MathHelper.abs((float)paramDouble2)) {
      if (paramDouble1 > 0.0D) {
        return 1;
      }
      return 3;
    }
    
    if (paramDouble2 > 0.0D) {
      return 2;
    }
    return 0;
  }
}
