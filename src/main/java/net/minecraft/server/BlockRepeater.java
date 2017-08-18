package net.minecraft.server;

import java.util.Random;









public class BlockRepeater
  extends BlockDiodeAbstract
{
  public static final double[] b = { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
  


  private static final int[] M = { 1, 2, 3, 4 };
  

  protected BlockRepeater(boolean paramBoolean)
  {
    super(paramBoolean);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = (i & 0xC) >> 2;
    j = j + 1 << 2 & 0xC;
    
    paramWorld.setData(paramInt1, paramInt2, paramInt3, j | i & 0x3, 3);
    return true;
  }
  
  protected int b(int paramInt)
  {
    return M[((paramInt & 0xC) >> 2)] * 2;
  }
  
  protected BlockDiodeAbstract e()
  {
    return Blocks.DIODE_ON;
  }
  
  protected BlockDiodeAbstract i()
  {
    return Blocks.DIODE_OFF;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.DIODE;
  }
  





  public int b()
  {
    return 15;
  }
  
  public boolean g(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return h(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4) > 0;
  }
  
  protected boolean a(Block paramBlock)
  {
    return d(paramBlock);
  }
  



















































  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
}
