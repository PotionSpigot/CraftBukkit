package net.minecraft.server;


public class ItemSeeds
  extends Item
{
  private Block block;
  private Block b;
  
  public ItemSeeds(Block paramBlock1, Block paramBlock2)
  {
    this.block = paramBlock1;
    this.b = paramBlock2;
    a(CreativeModeTab.l);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramInt4 != 1) { return false;
    }
    if ((!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) || (!paramEntityHuman.a(paramInt1, paramInt2 + 1, paramInt3, paramInt4, paramItemStack))) { return false;
    }
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.b) && (paramWorld.isEmpty(paramInt1, paramInt2 + 1, paramInt3))) {
      paramWorld.setTypeUpdate(paramInt1, paramInt2 + 1, paramInt3, this.block);
      paramItemStack.count -= 1;
      return true;
    }
    return false;
  }
}
