package net.minecraft.server;







public class BlockBeacon
  extends BlockContainer
{
  public BlockBeacon()
  {
    super(Material.SHATTERABLE);
    c(3.0F);
    a(CreativeModeTab.f);
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityBeacon();
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) { return true;
    }
    TileEntityBeacon localTileEntityBeacon = (TileEntityBeacon)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityBeacon != null) { paramEntityHuman.openBeacon(localTileEntityBeacon);
    }
    return true;
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
    return 34;
  }
  





  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    super.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLiving, paramItemStack);
    if (paramItemStack.hasName()) {
      ((TileEntityBeacon)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
    }
  }
}
