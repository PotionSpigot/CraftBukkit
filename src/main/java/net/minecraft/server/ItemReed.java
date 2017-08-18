package net.minecraft.server;



public class ItemReed
  extends Item
{
  private Block block;
  


  public ItemReed(Block paramBlock)
  {
    this.block = paramBlock;
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    if ((localBlock == Blocks.SNOW) && ((paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x7) < 1)) {
      paramInt4 = 1;
    } else if ((localBlock != Blocks.VINE) && (localBlock != Blocks.LONG_GRASS) && (localBlock != Blocks.DEAD_BUSH))
    {
      if (paramInt4 == 0) paramInt2--;
      if (paramInt4 == 1) paramInt2++;
      if (paramInt4 == 2) paramInt3--;
      if (paramInt4 == 3) paramInt3++;
      if (paramInt4 == 4) paramInt1--;
      if (paramInt4 == 5) { paramInt1++;
      }
    }
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
    if (paramItemStack.count == 0) { return false;
    }
    if (paramWorld.mayPlace(this.block, paramInt1, paramInt2, paramInt3, false, paramInt4, null, paramItemStack)) {
      int i = this.block.getPlacedData(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3, 0);
      if (paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.block, i, 3))
      {


        if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.block) {
          this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman, paramItemStack);
          this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, i);
        }
        paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume1() + 1.0F) / 2.0F, this.block.stepSound.getVolume2() * 0.8F);
        paramItemStack.count -= 1;
      }
    }
    return true;
  }
}
