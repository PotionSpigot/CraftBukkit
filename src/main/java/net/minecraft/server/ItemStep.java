package net.minecraft.server;


public class ItemStep
  extends ItemBlock
{
  private final boolean b;
  
  private final BlockStepAbstract c;
  
  private final BlockStepAbstract d;
  
  public ItemStep(Block paramBlock, BlockStepAbstract paramBlockStepAbstract1, BlockStepAbstract paramBlockStepAbstract2, boolean paramBoolean)
  {
    super(paramBlock);
    this.c = paramBlockStepAbstract1;
    this.d = paramBlockStepAbstract2;
    
    this.b = paramBoolean;
    
    setMaxDurability(0);
    a(true);
  }
  





  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public String a(ItemStack paramItemStack)
  {
    return this.c.b(paramItemStack.getData());
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.b) { return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
    }
    if (paramItemStack.count == 0) return false;
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) { return false;
    }
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x7;
    int k = (i & 0x8) != 0 ? 1 : 0;
    
    if (((paramInt4 == 1) && (k == 0)) || ((paramInt4 == 0) && (k != 0) && (localBlock == this.c) && (j == paramItemStack.getData()))) {
      if ((paramWorld.b(this.d.a(paramWorld, paramInt1, paramInt2, paramInt3))) && (paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.d, j, 3))) {
        paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, this.d.stepSound.getPlaceSound(), (this.d.stepSound.getVolume1() + 1.0F) / 2.0F, this.d.stepSound.getVolume2() * 0.8F);
        paramItemStack.count -= 1;
      }
      return true; }
    if (a(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4)) {
      return true;
    }
    return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
  }
  































  private boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt4 == 0) paramInt2--;
    if (paramInt4 == 1) paramInt2++;
    if (paramInt4 == 2) paramInt3--;
    if (paramInt4 == 3) paramInt3++;
    if (paramInt4 == 4) paramInt1--;
    if (paramInt4 == 5) { paramInt1++;
    }
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x7;
    
    if ((localBlock == this.c) && (j == paramItemStack.getData())) {
      if ((paramWorld.b(this.d.a(paramWorld, paramInt1, paramInt2, paramInt3))) && (paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.d, j, 3))) {
        paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, this.d.stepSound.getPlaceSound(), (this.d.stepSound.getVolume1() + 1.0F) / 2.0F, this.d.stepSound.getVolume2() * 0.8F);
        paramItemStack.count -= 1;
      }
      return true;
    }
    
    return false;
  }
}
