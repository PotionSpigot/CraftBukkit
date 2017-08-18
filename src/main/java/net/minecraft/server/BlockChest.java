package net.minecraft.server;

import java.util.Random;




















public class BlockChest
  extends BlockContainer
{
  private final Random b = new Random();
  public final int a;
  
  protected BlockChest(int paramInt) {
    super(Material.WOOD);
    this.a = paramInt;
    a(CreativeModeTab.c);
    
    a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
    return 22;
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1) == this) {
      a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
    } else if (paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1) == this) {
      a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
    } else if (paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3) == this) {
      a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    } else if (paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3) == this) {
      a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
    } else {
      a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
    e(paramWorld, paramInt1, paramInt2, paramInt3);
    
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
    Block localBlock2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
    Block localBlock3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
    Block localBlock4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
    if (localBlock1 == this) e(paramWorld, paramInt1, paramInt2, paramInt3 - 1);
    if (localBlock2 == this) e(paramWorld, paramInt1, paramInt2, paramInt3 + 1);
    if (localBlock3 == this) e(paramWorld, paramInt1 - 1, paramInt2, paramInt3);
    if (localBlock4 == this) e(paramWorld, paramInt1 + 1, paramInt2, paramInt3);
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
    Block localBlock2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
    Block localBlock3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
    Block localBlock4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
    
    int i = 0;
    int j = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    if (j == 0) i = 2;
    if (j == 1) i = 5;
    if (j == 2) i = 3;
    if (j == 3) { i = 4;
    }
    if ((localBlock1 != this) && (localBlock2 != this) && (localBlock3 != this) && (localBlock4 != this)) {
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 3);
    } else {
      if (((localBlock1 == this) || (localBlock2 == this)) && ((i == 4) || (i == 5))) {
        if (localBlock1 == this) paramWorld.setData(paramInt1, paramInt2, paramInt3 - 1, i, 3); else
          paramWorld.setData(paramInt1, paramInt2, paramInt3 + 1, i, 3);
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 3);
      }
      if (((localBlock3 == this) || (localBlock4 == this)) && ((i == 2) || (i == 3))) {
        if (localBlock3 == this) paramWorld.setData(paramInt1 - 1, paramInt2, paramInt3, i, 3); else
          paramWorld.setData(paramInt1 + 1, paramInt2, paramInt3, i, 3);
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 3);
      }
    }
    
    if (paramItemStack.hasName()) {
      ((TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
    }
  }
  
  public void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (paramWorld.isStatic) {
      return;
    }
    
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
    Block localBlock2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
    Block localBlock3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
    Block localBlock4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
    

    int i = 4;
    int j; Block localBlock5; int k; Block localBlock6; int m; if ((localBlock1 == this) || (localBlock2 == this)) {
      j = localBlock1 == this ? paramInt3 - 1 : paramInt3 + 1;
      localBlock5 = paramWorld.getType(paramInt1 - 1, paramInt2, j);
      k = localBlock1 == this ? paramInt3 - 1 : paramInt3 + 1;
      localBlock6 = paramWorld.getType(paramInt1 + 1, paramInt2, k);
      
      i = 5;
      
      m = -1;
      if (localBlock1 == this) m = paramWorld.getData(paramInt1, paramInt2, paramInt3 - 1); else
        m = paramWorld.getData(paramInt1, paramInt2, paramInt3 + 1);
      if (m == 4) { i = 4;
      }
      if (((localBlock3.j()) || (localBlock5.j())) && (!localBlock4.j()) && (!localBlock6.j())) i = 5;
      if (((localBlock4.j()) || (localBlock6.j())) && (!localBlock3.j()) && (!localBlock5.j())) i = 4;
    } else if ((localBlock3 == this) || (localBlock4 == this)) {
      j = localBlock3 == this ? paramInt1 - 1 : paramInt1 + 1;
      localBlock5 = paramWorld.getType(j, paramInt2, paramInt3 - 1);
      k = localBlock3 == this ? paramInt1 - 1 : paramInt1 + 1;
      localBlock6 = paramWorld.getType(k, paramInt2, paramInt3 + 1);
      
      i = 3;
      m = -1;
      if (localBlock3 == this) m = paramWorld.getData(paramInt1 - 1, paramInt2, paramInt3); else
        m = paramWorld.getData(paramInt1 + 1, paramInt2, paramInt3);
      if (m == 2) { i = 2;
      }
      if (((localBlock1.j()) || (localBlock5.j())) && (!localBlock2.j()) && (!localBlock6.j())) i = 3;
      if (((localBlock2.j()) || (localBlock6.j())) && (!localBlock1.j()) && (!localBlock5.j())) i = 2;
    } else {
      i = 3;
      if ((localBlock1.j()) && (!localBlock2.j())) i = 3;
      if ((localBlock2.j()) && (!localBlock1.j())) i = 2;
      if ((localBlock3.j()) && (!localBlock4.j())) i = 5;
      if ((localBlock4.j()) && (!localBlock3.j())) { i = 4;
      }
    }
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 3);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) i++;
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) { i++;
    }
    if (i > 1) { return false;
    }
    if (n(paramWorld, paramInt1 - 1, paramInt2, paramInt3)) return false;
    if (n(paramWorld, paramInt1 + 1, paramInt2, paramInt3)) return false;
    if (n(paramWorld, paramInt1, paramInt2, paramInt3 - 1)) return false;
    if (n(paramWorld, paramInt1, paramInt2, paramInt3 + 1)) return false;
    return true;
  }
  
  private boolean n(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != this) return false;
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) return true;
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) return true;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) return true;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) return true;
    return false;
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
    TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityChest != null) localTileEntityChest.u();
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityChest != null) {
      for (int i = 0; i < localTileEntityChest.getSize(); i++) {
        ItemStack localItemStack = localTileEntityChest.getItem(i);
        if (localItemStack != null) {
          float f1 = this.b.nextFloat() * 0.8F + 0.1F;
          float f2 = this.b.nextFloat() * 0.8F + 0.1F;
          float f3 = this.b.nextFloat() * 0.8F + 0.1F;
          
          while (localItemStack.count > 0) {
            int j = this.b.nextInt(21) + 10;
            if (j > localItemStack.count) j = localItemStack.count;
            localItemStack.count -= j;
            
            EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + f1, paramInt2 + f2, paramInt3 + f3, new ItemStack(localItemStack.getItem(), j, localItemStack.getData()));
            float f4 = 0.05F;
            localEntityItem.motX = ((float)this.b.nextGaussian() * f4);
            localEntityItem.motY = ((float)this.b.nextGaussian() * f4 + 0.2F);
            localEntityItem.motZ = ((float)this.b.nextGaussian() * f4);
            if (localItemStack.hasTag()) {
              localEntityItem.getItemStack().setTag((NBTTagCompound)localItemStack.getTag().clone());
            }
            paramWorld.addEntity(localEntityItem);
          }
        }
      }
      paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, paramBlock);
    }
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) return true;
    IInventory localIInventory = m(paramWorld, paramInt1, paramInt2, paramInt3);
    
    if (localIInventory != null) {
      paramEntityHuman.openContainer(localIInventory);
    }
    
    return true;
  }
  
  public IInventory m(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    Object localObject = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localObject == null) { return null;
    }
    if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).r()) return null;
    if (o(paramWorld, paramInt1, paramInt2, paramInt3)) { return null;
    }
    if ((paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) && ((paramWorld.getType(paramInt1 - 1, paramInt2 + 1, paramInt3).r()) || (o(paramWorld, paramInt1 - 1, paramInt2, paramInt3)))) return null;
    if ((paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) && ((paramWorld.getType(paramInt1 + 1, paramInt2 + 1, paramInt3).r()) || (o(paramWorld, paramInt1 + 1, paramInt2, paramInt3)))) return null;
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) && ((paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3 - 1).r()) || (o(paramWorld, paramInt1, paramInt2, paramInt3 - 1)))) return null;
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) && ((paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3 + 1).r()) || (o(paramWorld, paramInt1, paramInt2, paramInt3 + 1)))) { return null;
    }
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) localObject = new InventoryLargeChest("container.chestDouble", (TileEntityChest)paramWorld.getTileEntity(paramInt1 - 1, paramInt2, paramInt3), (IInventory)localObject);
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) localObject = new InventoryLargeChest("container.chestDouble", (IInventory)localObject, (TileEntityChest)paramWorld.getTileEntity(paramInt1 + 1, paramInt2, paramInt3));
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) localObject = new InventoryLargeChest("container.chestDouble", (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 - 1), (IInventory)localObject);
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) { localObject = new InventoryLargeChest("container.chestDouble", (IInventory)localObject, (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 + 1));
    }
    return (IInventory)localObject;
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    TileEntityChest localTileEntityChest = new TileEntityChest();
    return localTileEntityChest;
  }
  
  public boolean isPowerSource()
  {
    return this.a == 1;
  }
  
  public int b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!isPowerSource()) { return 0;
    }
    int i = ((TileEntityChest)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3)).o;
    return MathHelper.a(i, 0, 15);
  }
  
  public int c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt4 == 1) {
      return b(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return 0;
  }
  
  private static boolean o(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    for (Entity localEntity : paramWorld.a(EntityOcelot.class, AxisAlignedBB.a(paramInt1, paramInt2 + 1, paramInt3, paramInt1 + 1, paramInt2 + 2, paramInt3 + 1))) {
      EntityOcelot localEntityOcelot = (EntityOcelot)localEntity;
      if (localEntityOcelot.isSitting()) return true;
    }
    return false;
  }
  
  public boolean isComplexRedstone()
  {
    return true;
  }
  
  public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return Container.b(m(paramWorld, paramInt1, paramInt2, paramInt3));
  }
}
