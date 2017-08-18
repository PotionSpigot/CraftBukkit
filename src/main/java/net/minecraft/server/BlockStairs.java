package net.minecraft.server;

import java.util.List;
import java.util.Random;











public class BlockStairs
  extends Block
{
  private static final int[][] a = { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
  


  private final Block b;
  

  private final int M;
  

  private boolean N;
  

  private int O;
  


  protected BlockStairs(Block paramBlock, int paramInt)
  {
    super(paramBlock.material);
    this.b = paramBlock;
    this.M = paramInt;
    c(paramBlock.strength);
    b(paramBlock.durability / 3.0F);
    a(paramBlock.stepSound);
    g(255);
    a(CreativeModeTab.b);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.N) {
      a(0.5F * (this.O % 2), 0.5F * (this.O / 2 % 2), 0.5F * (this.O / 4 % 2), 0.5F + 0.5F * (this.O % 2), 0.5F + 0.5F * (this.O / 2 % 2), 0.5F + 0.5F * (this.O / 4 % 2));
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
    return 10;
  }
  
  public void e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
    
    if ((i & 0x4) != 0) {
      a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
  }
  
  public static boolean a(Block paramBlock) {
    return paramBlock instanceof BlockStairs;
  }
  
  private boolean f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Block localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
    if ((a(localBlock)) && (paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) == paramInt4)) {
      return true;
    }
    
    return false;
  }
  
  public boolean f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x3;
    
    float f1 = 0.5F;
    float f2 = 1.0F;
    
    if ((i & 0x4) != 0) {
      f1 = 0.0F;
      f2 = 0.5F;
    }
    
    float f3 = 0.0F;
    float f4 = 1.0F;
    float f5 = 0.0F;
    float f6 = 0.5F;
    
    boolean bool = true;
    Block localBlock;
    int k; int m; if (j == 0) {
      f3 = 0.5F;
      f6 = 1.0F;
      
      localBlock = paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3);
      k = paramIBlockAccess.getData(paramInt1 + 1, paramInt2, paramInt3);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 3) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i))) {
          f6 = 0.5F;
          bool = false;
        } else if ((m == 2) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i))) {
          f5 = 0.5F;
          bool = false;
        }
      }
    } else if (j == 1) {
      f4 = 0.5F;
      f6 = 1.0F;
      
      localBlock = paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3);
      k = paramIBlockAccess.getData(paramInt1 - 1, paramInt2, paramInt3);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 3) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i))) {
          f6 = 0.5F;
          bool = false;
        } else if ((m == 2) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i))) {
          f5 = 0.5F;
          bool = false;
        }
      }
    } else if (j == 2) {
      f5 = 0.5F;
      f6 = 1.0F;
      
      localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1);
      k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 + 1);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 1) && (!f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i))) {
          f4 = 0.5F;
          bool = false;
        } else if ((m == 0) && (!f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i))) {
          f3 = 0.5F;
          bool = false;
        }
      }
    } else if (j == 3) {
      localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1);
      k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 - 1);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 1) && (!f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i))) {
          f4 = 0.5F;
          bool = false;
        } else if ((m == 0) && (!f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i))) {
          f3 = 0.5F;
          bool = false;
        }
      }
    }
    
    a(f3, f1, f5, f4, f2, f6);
    return bool;
  }
  



  public boolean g(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x3;
    
    float f1 = 0.5F;
    float f2 = 1.0F;
    
    if ((i & 0x4) != 0) {
      f1 = 0.0F;
      f2 = 0.5F;
    }
    
    float f3 = 0.0F;
    float f4 = 0.5F;
    float f5 = 0.5F;
    float f6 = 1.0F;
    
    boolean bool = false;
    Block localBlock;
    int k; int m; if (j == 0) {
      localBlock = paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3);
      k = paramIBlockAccess.getData(paramInt1 - 1, paramInt2, paramInt3);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 3) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i))) {
          f5 = 0.0F;
          f6 = 0.5F;
          bool = true;
        } else if ((m == 2) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i))) {
          f5 = 0.5F;
          f6 = 1.0F;
          bool = true;
        }
      }
    } else if (j == 1) {
      localBlock = paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3);
      k = paramIBlockAccess.getData(paramInt1 + 1, paramInt2, paramInt3);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        f3 = 0.5F;
        f4 = 1.0F;
        m = k & 0x3;
        if ((m == 3) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i))) {
          f5 = 0.0F;
          f6 = 0.5F;
          bool = true;
        } else if ((m == 2) && (!f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i))) {
          f5 = 0.5F;
          f6 = 1.0F;
          bool = true;
        }
      }
    } else if (j == 2) {
      localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1);
      k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 - 1);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        f5 = 0.0F;
        f6 = 0.5F;
        
        m = k & 0x3;
        if ((m == 1) && (!f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i))) {
          bool = true;
        } else if ((m == 0) && (!f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i))) {
          f3 = 0.5F;
          f4 = 1.0F;
          bool = true;
        }
      }
    } else if (j == 3) {
      localBlock = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1);
      k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 + 1);
      if ((a(localBlock)) && ((i & 0x4) == (k & 0x4))) {
        m = k & 0x3;
        if ((m == 1) && (!f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i))) {
          bool = true;
        } else if ((m == 0) && (!f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i))) {
          f3 = 0.5F;
          f4 = 1.0F;
          bool = true;
        }
      }
    }
    
    if (bool) {
      a(f3, f1, f5, f4, f2, f6);
    }
    return bool;
  }
  

  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    e(paramWorld, paramInt1, paramInt2, paramInt3);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    
    boolean bool = f(paramWorld, paramInt1, paramInt2, paramInt3);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    
    if ((bool) && 
      (g(paramWorld, paramInt1, paramInt2, paramInt3))) {
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
    

    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  













  public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    this.b.attack(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
  }
  
  public void postBreak(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.b.postBreak(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  





  public float a(Entity paramEntity)
  {
    return this.b.a(paramEntity);
  }
  










  public int a(World paramWorld)
  {
    return this.b.a(paramWorld);
  }
  





  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D)
  {
    this.b.a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity, paramVec3D);
  }
  
  public boolean v()
  {
    return this.b.v();
  }
  
  public boolean a(int paramInt, boolean paramBoolean)
  {
    return this.b.a(paramInt, paramBoolean);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return this.b.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.AIR);
    this.b.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    this.b.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
  }
  





  public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    this.b.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
  {
    this.b.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.b.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman, 0, 0.0F, 0.0F, 0.0F);
  }
  
  public void wasExploded(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Explosion paramExplosion)
  {
    this.b.wasExploded(paramWorld, paramInt1, paramInt2, paramInt3, paramExplosion);
  }
  
  public MaterialMapColor f(int paramInt)
  {
    return this.b.f(this.M);
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    int i = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    int j = paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x4;
    
    if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x2 | j, 2);
    if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x1 | j, 2);
    if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x3 | j, 2);
    if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x0 | j, 2);
  }
  
  public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5)
  {
    if ((paramInt4 == 0) || ((paramInt4 != 1) && (paramFloat2 > 0.5D))) {
      return paramInt5 | 0x4;
    }
    return paramInt5;
  }
  
  public MovingObjectPosition a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Vec3D paramVec3D1, Vec3D paramVec3D2)
  {
    MovingObjectPosition[] arrayOfMovingObjectPosition1 = new MovingObjectPosition[8];
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x3;
    int k = (i & 0x4) == 4 ? 1 : 0;
    int[] arrayOfInt1 = a[(j + 0)];
    
    this.N = true;
    int i3; for (int m = 0; m < 8; m++) {
      this.O = m;
      
      for (i3 : arrayOfInt1) {
        if (i3 == m) {}
      }
      
      arrayOfMovingObjectPosition1[m] = super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramVec3D1, paramVec3D2);
    }
    
    for (??? : arrayOfInt1) {
      arrayOfMovingObjectPosition1[???] = null;
    }
    
    ??? = null;
    double d1 = 0.0D;
    
    for (MovingObjectPosition localMovingObjectPosition : arrayOfMovingObjectPosition1) {
      if (localMovingObjectPosition != null) {
        double d2 = localMovingObjectPosition.pos.distanceSquared(paramVec3D2);
        
        if (d2 > d1) {
          ??? = localMovingObjectPosition;
          d1 = d2;
        }
      }
    }
    
    return (MovingObjectPosition)???;
  }
}
