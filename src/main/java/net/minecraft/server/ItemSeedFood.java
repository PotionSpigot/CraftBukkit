package net.minecraft.server;


public class ItemSeedFood
  extends ItemFood
{
  private Block b;
  
  private Block c;
  

  public ItemSeedFood(int paramInt, float paramFloat, Block paramBlock1, Block paramBlock2)
  {
    super(paramInt, paramFloat, false);
    
    this.b = paramBlock1;
    this.c = paramBlock2;
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramInt4 != 1) { return false;
    }
    if ((!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) || (!paramEntityHuman.a(paramInt1, paramInt2 + 1, paramInt3, paramInt4, paramItemStack))) { return false;
    }
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.c) && (paramWorld.isEmpty(paramInt1, paramInt2 + 1, paramInt3))) {
      paramWorld.setTypeUpdate(paramInt1, paramInt2 + 1, paramInt3, this.b);
      paramItemStack.count -= 1;
      return true;
    }
    return false;
  }
}
