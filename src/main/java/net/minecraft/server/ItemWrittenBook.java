package net.minecraft.server;















public class ItemWrittenBook
  extends Item
{
  public ItemWrittenBook()
  {
    e(1);
  }
  
  public static boolean a(NBTTagCompound paramNBTTagCompound)
  {
    if (!ItemBookAndQuill.a(paramNBTTagCompound)) {
      return false;
    }
    
    if (!paramNBTTagCompound.hasKeyOfType("title", 8)) {
      return false;
    }
    String str = paramNBTTagCompound.getString("title");
    if ((str == null) || (str.length() > 16)) {
      return false;
    }
    
    if (!paramNBTTagCompound.hasKeyOfType("author", 8)) {
      return false;
    }
    
    return true;
  }
  
  public String n(ItemStack paramItemStack)
  {
    if (paramItemStack.hasTag()) {
      NBTTagCompound localNBTTagCompound = paramItemStack.getTag();
      
      String str = localNBTTagCompound.getString("title");
      if (!UtilColor.b(str)) {
        return str;
      }
    }
    return super.n(paramItemStack);
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
}
