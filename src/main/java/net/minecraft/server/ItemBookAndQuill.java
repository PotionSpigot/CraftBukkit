package net.minecraft.server;



public class ItemBookAndQuill
  extends Item
{
  public ItemBookAndQuill()
  {
    e(1);
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramEntityHuman.b(paramItemStack);
    return paramItemStack;
  }
  
  public boolean s()
  {
    return true;
  }
  
  public static boolean a(NBTTagCompound paramNBTTagCompound)
  {
    if (paramNBTTagCompound == null) {
      return false;
    }
    if (!paramNBTTagCompound.hasKeyOfType("pages", 9)) {
      return false;
    }
    
    NBTTagList localNBTTagList = paramNBTTagCompound.getList("pages", 8);
    for (int i = 0; i < localNBTTagList.size(); i++) {
      String str = localNBTTagList.getString(i);
      
      if (str == null) {
        return false;
      }
      if (str.length() > 256) {
        return false;
      }
    }
    
    return true;
  }
}
