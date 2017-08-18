package net.minecraft.server;

import java.util.List;
import java.util.Random;








public abstract class BlockStepAbstract
  extends Block
{
  protected final boolean a;
  
  public BlockStepAbstract(boolean paramBoolean, Material paramMaterial)
  {
    super(paramMaterial);
    this.a = paramBoolean;
    
    if (paramBoolean) {
      this.q = true;
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
    g(255);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.a) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      int i = (paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) & 0x8) != 0 ? 1 : 0;
      if (i != 0) {
        a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
    }
  }
  
  public void g()
  {
    if (this.a) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
  }
  
  public boolean c()
  {
    return this.a;
  }
  
  public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5)
  {
    if (this.a) { return paramInt5;
    }
    if ((paramInt4 == 0) || ((paramInt4 != 1) && (paramFloat2 > 0.5D))) {
      return paramInt5 | 0x8;
    }
    return paramInt5;
  }
  
  public int a(Random paramRandom)
  {
    if (this.a) {
      return 2;
    }
    return 1;
  }
  
  public int getDropData(int paramInt)
  {
    return paramInt & 0x7;
  }
  
  public boolean d()
  {
    return this.a;
  }
  















  public abstract String b(int paramInt);
  














  public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return super.getDropData(paramWorld, paramInt1, paramInt2, paramInt3) & 0x7;
  }
}
