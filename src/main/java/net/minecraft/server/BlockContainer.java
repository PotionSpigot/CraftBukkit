package net.minecraft.server;

public abstract class BlockContainer
  extends Block
  implements IContainer
{
  protected BlockContainer(Material paramMaterial)
  {
    super(paramMaterial);
    this.isTileEntity = true;
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
    paramWorld.p(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntity != null) {
      return localTileEntity.c(paramInt4, paramInt5);
    }
    return false;
  }
}
