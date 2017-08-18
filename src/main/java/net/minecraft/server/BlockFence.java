package net.minecraft.server;

import java.util.List;






public class BlockFence
  extends Block
{
  private final String a;
  
  public BlockFence(String paramString, Material paramMaterial)
  {
    super(paramMaterial);
    this.a = paramString;
    a(CreativeModeTab.c);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    boolean bool1 = e(paramWorld, paramInt1, paramInt2, paramInt3 - 1);
    boolean bool2 = e(paramWorld, paramInt1, paramInt2, paramInt3 + 1);
    boolean bool3 = e(paramWorld, paramInt1 - 1, paramInt2, paramInt3);
    boolean bool4 = e(paramWorld, paramInt1 + 1, paramInt2, paramInt3);
    
    float f1 = 0.375F;
    float f2 = 0.625F;
    float f3 = 0.375F;
    float f4 = 0.625F;
    
    if (bool1) {
      f3 = 0.0F;
    }
    if (bool2) {
      f4 = 1.0F;
    }
    if ((bool1) || (bool2)) {
      a(f1, 0.0F, f3, f2, 1.5F, f4);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
    f3 = 0.375F;
    f4 = 0.625F;
    if (bool3) {
      f1 = 0.0F;
    }
    if (bool4) {
      f2 = 1.0F;
    }
    if ((bool3) || (bool4) || ((!bool1) && (!bool2))) {
      a(f1, 0.0F, f3, f2, 1.5F, f4);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
    
    if (bool1) {
      f3 = 0.0F;
    }
    if (bool2) {
      f4 = 1.0F;
    }
    
    a(f1, 0.0F, f3, f2, 1.0F, f4);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1);
    boolean bool2 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1);
    boolean bool3 = e(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3);
    boolean bool4 = e(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3);
    
    float f1 = 0.375F;
    float f2 = 0.625F;
    float f3 = 0.375F;
    float f4 = 0.625F;
    
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
    
    a(f1, 0.0F, f3, f2, 1.0F, f4);
  }
  
  public boolean c()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    return false;
  }
  
  public int b()
  {
    return 11;
  }
  
  public boolean e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    Block localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
    if ((localBlock == this) || (localBlock == Blocks.FENCE_GATE)) {
      return true;
    }
    if ((localBlock.material.k()) && (localBlock.d())) {
      return localBlock.material != Material.PUMPKIN;
    }
    return false;
  }
  
  public static boolean a(Block paramBlock) {
    return (paramBlock == Blocks.FENCE) || (paramBlock == Blocks.NETHER_FENCE);
  }
  










  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) return true;
    if (ItemLeash.a(paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3)) {
      return true;
    }
    return false;
  }
}
