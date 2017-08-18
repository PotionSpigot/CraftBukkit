package net.minecraft.server;


















public class BlockEnchantmentTable
  extends BlockContainer
{
  protected BlockEnchantmentTable()
  {
    super(Material.STONE);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    g(0);
    a(CreativeModeTab.c);
  }
  
  public boolean d()
  {
    return false;
  }
  





















  public boolean c()
  {
    return false;
  }
  







  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityEnchantTable();
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) {
      return true;
    }
    TileEntityEnchantTable localTileEntityEnchantTable = (TileEntityEnchantTable)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    paramEntityHuman.startEnchanting(paramInt1, paramInt2, paramInt3, localTileEntityEnchantTable.b() ? localTileEntityEnchantTable.a() : null);
    return true;
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    super.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLiving, paramItemStack);
    if (paramItemStack.hasName()) {
      ((TileEntityEnchantTable)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
    }
  }
}
