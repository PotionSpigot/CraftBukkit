package net.minecraft.server;

import java.util.Random;




















































class SlotAnvilResult
  extends Slot
{
  SlotAnvilResult(ContainerAnvil paramContainerAnvil, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, World paramWorld, int paramInt4, int paramInt5, int paramInt6)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean isAllowed(ItemStack paramItemStack) { return false; }
  

  public boolean isAllowed(EntityHuman paramEntityHuman)
  {
    return ((paramEntityHuman.abilities.canInstantlyBuild) || (paramEntityHuman.expLevel >= this.e.a)) && (this.e.a > 0) && (hasItem());
  }
  
  public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
  {
    if (!paramEntityHuman.abilities.canInstantlyBuild) paramEntityHuman.levelDown(-this.e.a);
    ContainerAnvil.a(this.e).setItem(0, null);
    if (ContainerAnvil.b(this.e) > 0) {
      ItemStack localItemStack = ContainerAnvil.a(this.e).getItem(1);
      if ((localItemStack != null) && (localItemStack.count > ContainerAnvil.b(this.e))) {
        localItemStack.count -= ContainerAnvil.b(this.e);
        ContainerAnvil.a(this.e).setItem(1, localItemStack);
      } else {
        ContainerAnvil.a(this.e).setItem(1, null);
      }
    } else {
      ContainerAnvil.a(this.e).setItem(1, null);
    }
    this.e.a = 0;
    
    if ((!paramEntityHuman.abilities.canInstantlyBuild) && (!this.a.isStatic) && (this.a.getType(this.b, this.c, this.d) == Blocks.ANVIL) && (paramEntityHuman.aI().nextFloat() < 0.12F)) {
      int i = this.a.getData(this.b, this.c, this.d);
      int j = i & 0x3;
      int k = i >> 2;
      
      k++; if (k > 2) {
        this.a.setAir(this.b, this.c, this.d);
        this.a.triggerEffect(1020, this.b, this.c, this.d, 0);
      } else {
        this.a.setData(this.b, this.c, this.d, j | k << 2, 2);
        this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
      }
    } else if (!this.a.isStatic) {
      this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
    }
  }
}
