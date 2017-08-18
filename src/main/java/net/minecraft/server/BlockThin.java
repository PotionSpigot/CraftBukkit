package net.minecraft.server;

import java.util.List;
import java.util.Random;






public class BlockThin
  extends Block
{
  private final String a;
  private final boolean b;
  private final String M;
  
  protected BlockThin(String paramString1, String paramString2, Material paramMaterial, boolean paramBoolean)
  {
    super(paramMaterial);
    this.a = paramString2;
    this.b = paramBoolean;
    this.M = paramString1;
    a(CreativeModeTab.c);
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    if (!this.b) {
      return null;
    }
    return super.getDropType(paramInt1, paramRandom, paramInt2);
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
    return this.material == Material.SHATTERABLE ? 41 : 18;
  }
  






  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    boolean bool1 = a(paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1));
    boolean bool2 = a(paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1));
    boolean bool3 = a(paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3));
    boolean bool4 = a(paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3));
    
    if (((bool3) && (bool4)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
      a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    } else if ((bool3) && (!bool4)) {
      a(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    } else if ((!bool3) && (bool4)) {
      a(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
    if (((bool1) && (bool2)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
      a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    } else if ((bool1) && (!bool2)) {
      a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    } else if ((!bool1) && (bool2)) {
      a(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
  }
  
  public void g()
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    float f1 = 0.4375F;
    float f2 = 0.5625F;
    float f3 = 0.4375F;
    float f4 = 0.5625F;
    
    boolean bool1 = a(paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1));
    boolean bool2 = a(paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1));
    boolean bool3 = a(paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3));
    boolean bool4 = a(paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3));
    
    if (((bool3) && (bool4)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
      f1 = 0.0F;
      f2 = 1.0F;
    } else if ((bool3) && (!bool4)) {
      f1 = 0.0F;
    } else if ((!bool3) && (bool4)) {
      f2 = 1.0F;
    }
    if (((bool1) && (bool2)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
      f3 = 0.0F;
      f4 = 1.0F;
    } else if ((bool1) && (!bool2)) {
      f3 = 0.0F;
    } else if ((!bool1) && (bool2)) {
      f4 = 1.0F;
    }
    
    a(f1, 0.0F, f3, f2, 1.0F, f4);
  }
  



  public final boolean a(Block paramBlock)
  {
    return (paramBlock.j()) || (paramBlock == this) || (paramBlock == Blocks.GLASS) || (paramBlock == Blocks.STAINED_GLASS) || (paramBlock == Blocks.STAINED_GLASS_PANE) || ((paramBlock instanceof BlockThin));
  }
  
  protected boolean E()
  {
    return true;
  }
  
  protected ItemStack j(int paramInt)
  {
    return new ItemStack(Item.getItemOf(this), 1, paramInt);
  }
}
