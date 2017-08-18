package net.minecraft.server;

import java.util.Random;











public class ItemEnchantedBook
  extends Item
{
  public boolean e_(ItemStack paramItemStack)
  {
    return false;
  }
  
  public EnumItemRarity f(ItemStack paramItemStack)
  {
    if (g(paramItemStack).size() > 0) {
      return EnumItemRarity.UNCOMMON;
    }
    return super.f(paramItemStack);
  }
  
  public NBTTagList g(ItemStack paramItemStack)
  {
    if ((paramItemStack.tag == null) || (!paramItemStack.tag.hasKeyOfType("StoredEnchantments", 9))) {
      return new NBTTagList();
    }
    
    return (NBTTagList)paramItemStack.tag.get("StoredEnchantments");
  }
  

















  public void a(ItemStack paramItemStack, EnchantmentInstance paramEnchantmentInstance)
  {
    NBTTagList localNBTTagList = g(paramItemStack);
    int i = 1;
    
    for (int j = 0; j < localNBTTagList.size(); j++) {
      NBTTagCompound localNBTTagCompound2 = localNBTTagList.get(j);
      
      if (localNBTTagCompound2.getShort("id") == paramEnchantmentInstance.enchantment.id) {
        if (localNBTTagCompound2.getShort("lvl") < paramEnchantmentInstance.level) {
          localNBTTagCompound2.setShort("lvl", (short)paramEnchantmentInstance.level);
        }
        
        i = 0;
        break;
      }
    }
    
    if (i != 0) {
      NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
      
      localNBTTagCompound1.setShort("id", (short)paramEnchantmentInstance.enchantment.id);
      localNBTTagCompound1.setShort("lvl", (short)paramEnchantmentInstance.level);
      
      localNBTTagList.add(localNBTTagCompound1);
    }
    
    if (!paramItemStack.hasTag()) paramItemStack.setTag(new NBTTagCompound());
    paramItemStack.getTag().set("StoredEnchantments", localNBTTagList);
  }
  
  public ItemStack a(EnchantmentInstance paramEnchantmentInstance) {
    ItemStack localItemStack = new ItemStack(this);
    a(localItemStack, paramEnchantmentInstance);
    return localItemStack;
  }
  















  public StructurePieceTreasure b(Random paramRandom)
  {
    return a(paramRandom, 1, 1, 1);
  }
  
  public StructurePieceTreasure a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
    ItemStack localItemStack = new ItemStack(Items.BOOK, 1, 0);
    EnchantmentManager.a(paramRandom, localItemStack, 30);
    
    return new StructurePieceTreasure(localItemStack, paramInt1, paramInt2, paramInt3);
  }
}
