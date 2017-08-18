package net.minecraft.server;





public class ItemSign
  extends Item
{
  public ItemSign()
  {
    this.maxStackSize = 16;
    a(CreativeModeTab.c);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramInt4 == 0) return false;
    if (!paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial().isBuildable()) { return false;
    }
    if (paramInt4 == 1) { paramInt2++;
    }
    if (paramInt4 == 2) paramInt3--;
    if (paramInt4 == 3) paramInt3++;
    if (paramInt4 == 4) paramInt1--;
    if (paramInt4 == 5) { paramInt1++;
    }
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
    if (!Blocks.SIGN_POST.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) { return false;
    }
    if (paramWorld.isStatic) {
      return true;
    }
    
    if (paramInt4 == 1) {
      int i = MathHelper.floor((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.SIGN_POST, i, 3);
    } else {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.WALL_SIGN, paramInt4, 3);
    }
    
    paramItemStack.count -= 1;
    TileEntitySign localTileEntitySign = (TileEntitySign)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntitySign != null) paramEntityHuman.a(localTileEntitySign);
    return true;
  }
}
