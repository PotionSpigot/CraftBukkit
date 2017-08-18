package net.minecraft.server;











public class BlockSand
  extends BlockFalling
{
  public static final String[] a = { "default", "red" };
  
















  public int getDropData(int paramInt)
  {
    return paramInt;
  }
  






  public MaterialMapColor f(int paramInt)
  {
    if (paramInt == 1) {
      return MaterialMapColor.l;
    }
    return MaterialMapColor.d;
  }
}
