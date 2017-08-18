package net.minecraft.server;

import java.util.Random;




public class BlockWeb
  extends Block
{
  public BlockWeb()
  {
    super(Material.WEB);
    a(CreativeModeTab.c);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    paramEntity.as();
  }
  
  public boolean c()
  {
    return false;
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }
  
  public int b()
  {
    return 1;
  }
  




  public boolean d()
  {
    return false;
  }
  

  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.STRING;
  }
  
  protected boolean E()
  {
    return true;
  }
}
