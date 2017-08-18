package net.minecraft.server;

import java.util.Set;
import net.minecraft.util.com.google.common.collect.Multimap;






public class ItemTool
  extends Item
{
  private Set c;
  protected float a = 4.0F;
  private float d;
  protected EnumToolMaterial b;
  
  protected ItemTool(float paramFloat, EnumToolMaterial paramEnumToolMaterial, Set paramSet)
  {
    this.b = paramEnumToolMaterial;
    this.c = paramSet;
    this.maxStackSize = 1;
    setMaxDurability(paramEnumToolMaterial.a());
    this.a = paramEnumToolMaterial.b();
    this.d = (paramFloat + paramEnumToolMaterial.c());
    a(CreativeModeTab.i);
  }
  
  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return this.c.contains(paramBlock) ? this.a : 1.0F;
  }
  
  public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
  {
    paramItemStack.damage(2, paramEntityLiving2);
    return true;
  }
  

  public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving)
  {
    if (paramBlock.f(paramWorld, paramInt1, paramInt2, paramInt3) != 0.0D) paramItemStack.damage(1, paramEntityLiving);
    return true;
  }
  




  public EnumToolMaterial i()
  {
    return this.b;
  }
  
  public int c()
  {
    return this.b.e();
  }
  
  public String j() {
    return this.b.toString();
  }
  
  public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2)
  {
    if (this.b.f() == paramItemStack2.getItem()) {
      return true;
    }
    return super.a(paramItemStack1, paramItemStack2);
  }
  
  public Multimap k()
  {
    Multimap localMultimap = super.k();
    
    localMultimap.put(GenericAttributes.e.getName(), new AttributeModifier(f, "Tool modifier", this.d, 0));
    
    return localMultimap;
  }
}
