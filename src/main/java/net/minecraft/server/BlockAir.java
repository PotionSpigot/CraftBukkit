package net.minecraft.server;



public class BlockAir
  extends Block
{
  protected BlockAir()
  {
    super(Material.AIR);
  }
  
  public int b()
  {
    return -1;
  }
  

  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }
  
  public boolean c()
  {
    return false;
  }
  
  public boolean a(int paramInt, boolean paramBoolean)
  {
    return false;
  }
  
  public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {}
}
