package net.minecraft.server;





public class RecipeMapExtend
  extends ShapedRecipes
{
  public RecipeMapExtend()
  {
    super(3, 3, new ItemStack[] { new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.MAP, 0, 32767), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER) }, new ItemStack(Items.MAP_EMPTY, 0, 0));
  }
  




  public boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld)
  {
    if (!super.a(paramInventoryCrafting, paramWorld)) return false;
    Object localObject = null;
    
    for (int i = 0; (i < paramInventoryCrafting.getSize()) && (localObject == null); i++) {
      ItemStack localItemStack = paramInventoryCrafting.getItem(i);
      if ((localItemStack != null) && (localItemStack.getItem() == Items.MAP)) { localObject = localItemStack;
      }
    }
    if (localObject == null) return false;
    WorldMap localWorldMap = Items.MAP.getSavedMap((ItemStack)localObject, paramWorld);
    if (localWorldMap == null) return false;
    return localWorldMap.scale < 4;
  }
  
  public ItemStack a(InventoryCrafting paramInventoryCrafting)
  {
    Object localObject = null;
    
    for (int i = 0; (i < paramInventoryCrafting.getSize()) && (localObject == null); i++) {
      ItemStack localItemStack = paramInventoryCrafting.getItem(i);
      if ((localItemStack != null) && (localItemStack.getItem() == Items.MAP)) { localObject = localItemStack;
      }
    }
    localObject = ((ItemStack)localObject).cloneItemStack();
    ((ItemStack)localObject).count = 1;
    
    if (((ItemStack)localObject).getTag() == null) ((ItemStack)localObject).setTag(new NBTTagCompound());
    ((ItemStack)localObject).getTag().setBoolean("map_is_scaling", true);
    
    return (ItemStack)localObject;
  }
}
