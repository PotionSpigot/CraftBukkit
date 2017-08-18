package net.minecraft.server;

import java.util.Random;









public class BlockPistonMoving
  extends BlockContainer
{
  public BlockPistonMoving()
  {
    super(Material.PISTON);
    c(-1.0F);
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return null;
  }
  

  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
  

  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntity instanceof TileEntityPiston)) {
      ((TileEntityPiston)localTileEntity).f();
    } else {
      super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
    }
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return false;
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return false;
  }
  
  public int b()
  {
    return -1;
  }
  
  public boolean c()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  

  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if ((!paramWorld.isStatic) && (paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null))
    {
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      return true;
    }
    return false;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return null;
  }
  
  public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    if (paramWorld.isStatic) { return;
    }
    TileEntityPiston localTileEntityPiston = e(paramWorld, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston == null) {
      return;
    }
    
    localTileEntityPiston.a().b(paramWorld, paramInt1, paramInt2, paramInt3, localTileEntityPiston.p(), 0);
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    if (!paramWorld.isStatic) {
      paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    }
  }
  
  public static TileEntity a(Block paramBlock, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    return new TileEntityPiston(paramBlock, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntityPiston localTileEntityPiston = e(paramWorld, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston == null) {
      return null;
    }
    

    float f = localTileEntityPiston.a(0.0F);
    if (localTileEntityPiston.b()) {
      f = 1.0F - f;
    }
    return a(paramWorld, paramInt1, paramInt2, paramInt3, localTileEntityPiston.a(), f, localTileEntityPiston.c());
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntityPiston localTileEntityPiston = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston != null) {
      Block localBlock = localTileEntityPiston.a();
      if ((localBlock == this) || (localBlock.getMaterial() == Material.AIR)) {
        return;
      }
      localBlock.updateShape(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
      
      float f = localTileEntityPiston.a(0.0F);
      if (localTileEntityPiston.b()) {
        f = 1.0F - f;
      }
      int i = localTileEntityPiston.c();
      this.minX = (localBlock.x() - Facing.b[i] * f);
      this.minY = (localBlock.z() - Facing.c[i] * f);
      this.minZ = (localBlock.B() - Facing.d[i] * f);
      this.maxX = (localBlock.y() - Facing.b[i] * f);
      this.maxY = (localBlock.A() - Facing.c[i] * f);
      this.maxZ = (localBlock.C() - Facing.d[i] * f);
    }
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, float paramFloat, int paramInt4) {
    if ((paramBlock == this) || (paramBlock.getMaterial() == Material.AIR)) {
      return null;
    }
    AxisAlignedBB localAxisAlignedBB = paramBlock.a(paramWorld, paramInt1, paramInt2, paramInt3);
    
    if (localAxisAlignedBB == null) {
      return null;
    }
    

    if (Facing.b[paramInt4] < 0) {
      localAxisAlignedBB.a -= Facing.b[paramInt4] * paramFloat;
    } else {
      localAxisAlignedBB.d -= Facing.b[paramInt4] * paramFloat;
    }
    if (Facing.c[paramInt4] < 0) {
      localAxisAlignedBB.b -= Facing.c[paramInt4] * paramFloat;
    } else {
      localAxisAlignedBB.e -= Facing.c[paramInt4] * paramFloat;
    }
    if (Facing.d[paramInt4] < 0) {
      localAxisAlignedBB.c -= Facing.d[paramInt4] * paramFloat;
    } else {
      localAxisAlignedBB.f -= Facing.d[paramInt4] * paramFloat;
    }
    return localAxisAlignedBB;
  }
  
  private TileEntityPiston e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    TileEntity localTileEntity = paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntity instanceof TileEntityPiston)) {
      return (TileEntityPiston)localTileEntity;
    }
    return null;
  }
}
