package net.minecraft.server;




public class ItemSnow
  extends ItemBlockWithAuxData
{
  public ItemSnow(Block paramBlock1, Block paramBlock2)
  {
    super(paramBlock1, paramBlock2);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramItemStack.count == 0) return false;
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) { return false;
    }
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    if (localBlock == Blocks.SNOW) {
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
      int j = i & 0x7;
      
      if ((j <= 6) && 
        (paramWorld.b(this.block.a(paramWorld, paramInt1, paramInt2, paramInt3))) && (paramWorld.setData(paramInt1, paramInt2, paramInt3, j + 1 | i & 0xFFFFFFF8, 2))) {
        paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume1() + 1.0F) / 2.0F, this.block.stepSound.getVolume2() * 0.8F);
        paramItemStack.count -= 1;
        return true;
      }
    }
    

    return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
  }
}
