package net.minecraft.server;


public class ItemRedstone
  extends Item
{
  public ItemRedstone()
  {
    a(CreativeModeTab.d);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SNOW) {
      if (paramInt4 == 0) paramInt2--;
      if (paramInt4 == 1) paramInt2++;
      if (paramInt4 == 2) paramInt3--;
      if (paramInt4 == 3) paramInt3++;
      if (paramInt4 == 4) paramInt1--;
      if (paramInt4 == 5) paramInt1++;
      if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false;
    }
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
    if (Blocks.REDSTONE_WIRE.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) {
      paramItemStack.count -= 1;
      paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.REDSTONE_WIRE);
    }
    
    return true;
  }
}
