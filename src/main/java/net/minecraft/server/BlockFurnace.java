package net.minecraft.server;

import java.util.Random;















public class BlockFurnace
  extends BlockContainer
{
  private final Random a = new Random();
  
  private final boolean b;
  private static boolean M;
  
  protected BlockFurnace(boolean paramBoolean)
  {
    super(Material.STONE);
    this.b = paramBoolean;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(Blocks.FURNACE);
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  private void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.isStatic) {
      return;
    }
    
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
    Block localBlock2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
    Block localBlock3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
    Block localBlock4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
    
    int i = 3;
    if ((localBlock1.j()) && (!localBlock2.j())) i = 3;
    if ((localBlock2.j()) && (!localBlock1.j())) i = 2;
    if ((localBlock3.j()) && (!localBlock4.j())) i = 5;
    if ((localBlock4.j()) && (!localBlock3.j())) i = 4;
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
  }
  











































  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) {
      return true;
    }
    TileEntityFurnace localTileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityFurnace != null) paramEntityHuman.openFurnace(localTileEntityFurnace);
    return true;
  }
  
  public static void a(boolean paramBoolean, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    
    M = true;
    if (paramBoolean) paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.BURNING_FURNACE); else
      paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.FURNACE);
    M = false;
    
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
    
    if (localTileEntity != null) {
      localTileEntity.t();
      paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, localTileEntity);
    }
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityFurnace();
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    int i = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 2, 2);
    if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 5, 2);
    if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 3, 2);
    if (i == 3) { paramWorld.setData(paramInt1, paramInt2, paramInt3, 4, 2);
    }
    if (paramItemStack.hasName()) {
      ((TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
    }
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    if (!M) {
      TileEntityFurnace localTileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if (localTileEntityFurnace != null) {
        for (int i = 0; i < localTileEntityFurnace.getSize(); i++) {
          ItemStack localItemStack = localTileEntityFurnace.getItem(i);
          if (localItemStack != null) {
            float f1 = this.a.nextFloat() * 0.8F + 0.1F;
            float f2 = this.a.nextFloat() * 0.8F + 0.1F;
            float f3 = this.a.nextFloat() * 0.8F + 0.1F;
            
            while (localItemStack.count > 0) {
              int j = this.a.nextInt(21) + 10;
              if (j > localItemStack.count) j = localItemStack.count;
              localItemStack.count -= j;
              
              EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + f1, paramInt2 + f2, paramInt3 + f3, new ItemStack(localItemStack.getItem(), j, localItemStack.getData()));
              
              if (localItemStack.hasTag()) {
                localEntityItem.getItemStack().setTag((NBTTagCompound)localItemStack.getTag().clone());
              }
              
              float f4 = 0.05F;
              localEntityItem.motX = ((float)this.a.nextGaussian() * f4);
              localEntityItem.motY = ((float)this.a.nextGaussian() * f4 + 0.2F);
              localEntityItem.motZ = ((float)this.a.nextGaussian() * f4);
              paramWorld.addEntity(localEntityItem);
            }
          }
        }
        paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, paramBlock);
      }
    }
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
  }
  
  public boolean isComplexRedstone()
  {
    return true;
  }
  
  public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return Container.b((IInventory)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3));
  }
}
