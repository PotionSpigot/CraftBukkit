package net.minecraft.server;

import java.util.Random;



public class BlockLadder
  extends Block
{
  protected BlockLadder()
  {
    super(Material.ORIENTABLE);
    a(CreativeModeTab.c);
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
    return super.a(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  






  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
  }
  
  public void b(int paramInt) {
    int i = paramInt;
    float f = 0.125F;
    
    if (i == 2) a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
    if (i == 3) a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
    if (i == 4) a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    if (i == 5) { a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
    }
  }
  



  public boolean c()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public int b()
  {
    return 8;
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r())
      return true;
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r())
      return true;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r())
      return true;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r()) {
      return true;
    }
    return false;
  }
  
  public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5)
  {
    int i = paramInt5;
    
    if (((i == 0) || (paramInt4 == 2)) && (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r())) i = 2;
    if (((i == 0) || (paramInt4 == 3)) && (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r())) i = 3;
    if (((i == 0) || (paramInt4 == 4)) && (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r())) i = 4;
    if (((i == 0) || (paramInt4 == 5)) && (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r())) { i = 5;
    }
    return i;
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = 0;
    
    if ((i == 2) && (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r())) j = 1;
    if ((i == 3) && (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r())) j = 1;
    if ((i == 4) && (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r())) j = 1;
    if ((i == 5) && (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r())) j = 1;
    if (j == 0) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, i, 0);
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
    }
    
    super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
  }
  
  public int a(Random paramRandom)
  {
    return 1;
  }
}
