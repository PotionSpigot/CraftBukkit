package net.minecraft.server;



public class ItemHoe
  extends Item
{
  protected EnumToolMaterial a;
  

  public ItemHoe(EnumToolMaterial paramEnumToolMaterial)
  {
    this.a = paramEnumToolMaterial;
    this.maxStackSize = 1;
    setMaxDurability(paramEnumToolMaterial.a());
    a(CreativeModeTab.i);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) { return false;
    }
    Block localBlock1 = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    if ((paramInt4 != 0) && (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).getMaterial() == Material.AIR) && ((localBlock1 == Blocks.GRASS) || (localBlock1 == Blocks.DIRT))) {
      Block localBlock2 = Blocks.SOIL;
      paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, localBlock2.stepSound.getStepSound(), (localBlock2.stepSound.getVolume1() + 1.0F) / 2.0F, localBlock2.stepSound.getVolume2() * 0.8F);
      
      if (paramWorld.isStatic) return true;
      paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, localBlock2);
      paramItemStack.damage(1, paramEntityHuman);
      
      return true;
    }
    
    return false;
  }
  




  public String i()
  {
    return this.a.toString();
  }
}
