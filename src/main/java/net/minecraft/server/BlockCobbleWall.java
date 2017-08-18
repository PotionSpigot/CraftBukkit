package net.minecraft.server;

















public class BlockCobbleWall
  extends Block
{
  public static final String[] a = { "normal", "mossy" };
  

  public BlockCobbleWall(Block paramBlock)
  {
    super(paramBlock.material);
    
    c(paramBlock.strength);
    b(paramBlock.durability / 3.0F);
    a(paramBlock.stepSound);
    a(CreativeModeTab.b);
  }
  








  public int b()
  {
    return 32;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    return false;
  }
  
  public boolean c()
  {
    return false;
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1);
    boolean bool2 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1);
    boolean bool3 = e(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3);
    boolean bool4 = e(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3);
    
    float f1 = 0.25F;
    float f2 = 0.75F;
    float f3 = 0.25F;
    float f4 = 0.75F;
    float f5 = 1.0F;
    
    if (bool1) {
      f3 = 0.0F;
    }
    if (bool2) {
      f4 = 1.0F;
    }
    if (bool3) {
      f1 = 0.0F;
    }
    if (bool4) {
      f2 = 1.0F;
    }
    
    if ((bool1) && (bool2) && (!bool3) && (!bool4)) {
      f5 = 0.8125F;
      f1 = 0.3125F;
      f2 = 0.6875F;
    } else if ((!bool1) && (!bool2) && (bool3) && (bool4)) {
      f5 = 0.8125F;
      f3 = 0.3125F;
      f4 = 0.6875F;
    }
    
    a(f1, 0.0F, f3, f2, f5, f4);
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
    this.maxY = 1.5D;
    return super.a(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    Block localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
    if ((localBlock == this) || (localBlock == Blocks.FENCE_GATE)) {
      return true;
    }
    if ((localBlock.material.k()) && (localBlock.d())) {
      return localBlock.material != Material.PUMPKIN;
    }
    return false;
  }
  






  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
