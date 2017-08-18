package net.minecraft.server;

import java.util.Random;




















public class BlockEnderChest
  extends BlockContainer
{
  protected BlockEnderChest()
  {
    super(Material.STONE);
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
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(Blocks.OBSIDIAN);
  }
  
  public int a(Random paramRandom)
  {
    return 8;
  }
  
  protected boolean E()
  {
    return true;
  }
  

  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    int i = 0;
    int j = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    if (j == 0) i = 2;
    if (j == 1) i = 5;
    if (j == 2) i = 3;
    if (j == 3) { i = 4;
    }
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    InventoryEnderChest localInventoryEnderChest = paramEntityHuman.getEnderChest();
    TileEntityEnderChest localTileEntityEnderChest = (TileEntityEnderChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localInventoryEnderChest == null) || (localTileEntityEnderChest == null)) { return true;
    }
    if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).r()) { return true;
    }
    if (paramWorld.isStatic) {
      return true;
    }
    
    localInventoryEnderChest.a(localTileEntityEnderChest);
    paramEntityHuman.openContainer(localInventoryEnderChest);
    
    return true;
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityEnderChest();
  }
}
