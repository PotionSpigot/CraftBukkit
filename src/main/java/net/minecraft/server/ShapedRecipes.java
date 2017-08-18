package net.minecraft.server;

import java.util.Arrays;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftShapedRecipe;

public class ShapedRecipes implements IRecipe
{
  private int width;
  private int height;
  private ItemStack[] items;
  public ItemStack result;
  private boolean e;
  
  public ShapedRecipes(int i, int j, ItemStack[] aitemstack, ItemStack itemstack)
  {
    this.width = i;
    this.height = j;
    this.items = aitemstack;
    this.result = itemstack;
  }
  
  public org.bukkit.inventory.ShapedRecipe toBukkitRecipe()
  {
    CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
    CraftShapedRecipe recipe = new CraftShapedRecipe(result, this);
    switch (this.height) {
    case 1: 
      switch (this.width) {
      case 1: 
        recipe.shape(new String[] { "a" });
        break;
      case 2: 
        recipe.shape(new String[] { "ab" });
        break;
      case 3: 
        recipe.shape(new String[] { "abc" });
      }
      
      break;
    case 2: 
      switch (this.width) {
      case 1: 
        recipe.shape(new String[] { "a", "b" });
        break;
      case 2: 
        recipe.shape(new String[] { "ab", "cd" });
        break;
      case 3: 
        recipe.shape(new String[] { "abc", "def" });
      }
      
      break;
    case 3: 
      switch (this.width) {
      case 1: 
        recipe.shape(new String[] { "a", "b", "c" });
        break;
      case 2: 
        recipe.shape(new String[] { "ab", "cd", "ef" });
        break;
      case 3: 
        recipe.shape(new String[] { "abc", "def", "ghi" });
      }
      
      break;
    }
    char c = 'a';
    for (ItemStack stack : this.items) {
      if (stack != null) {
        recipe.setIngredient(c, org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getMaterial(stack.getItem()), stack.getData());
      }
      c = (char)(c + '\001');
    }
    return recipe;
  }
  
  public ItemStack b()
  {
    return this.result;
  }
  
  public boolean a(InventoryCrafting inventorycrafting, World world) {
    for (int i = 0; i <= 3 - this.width; i++) {
      for (int j = 0; j <= 3 - this.height; j++) {
        if (a(inventorycrafting, i, j, true)) {
          return true;
        }
        
        if (a(inventorycrafting, i, j, false)) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  private boolean a(InventoryCrafting inventorycrafting, int i, int j, boolean flag) {
    for (int k = 0; k < 3; k++) {
      for (int l = 0; l < 3; l++) {
        int i1 = k - i;
        int j1 = l - j;
        ItemStack itemstack = null;
        
        if ((i1 >= 0) && (j1 >= 0) && (i1 < this.width) && (j1 < this.height)) {
          if (flag) {
            itemstack = this.items[(this.width - i1 - 1 + j1 * this.width)];
          } else {
            itemstack = this.items[(i1 + j1 * this.width)];
          }
        }
        
        ItemStack itemstack1 = inventorycrafting.b(k, l);
        
        if ((itemstack1 != null) || (itemstack != null)) {
          if (((itemstack1 == null) && (itemstack != null)) || ((itemstack1 != null) && (itemstack == null))) {
            return false;
          }
          
          if (itemstack.getItem() != itemstack1.getItem()) {
            return false;
          }
          
          if ((itemstack.getData() != 32767) && (itemstack.getData() != itemstack1.getData())) {
            return false;
          }
        }
      }
    }
    
    return true;
  }
  
  public ItemStack a(InventoryCrafting inventorycrafting) {
    ItemStack itemstack = b().cloneItemStack();
    
    if (this.e) {
      for (int i = 0; i < inventorycrafting.getSize(); i++) {
        ItemStack itemstack1 = inventorycrafting.getItem(i);
        
        if ((itemstack1 != null) && (itemstack1.hasTag())) {
          itemstack.setTag((NBTTagCompound)itemstack1.tag.clone());
        }
      }
    }
    
    return itemstack;
  }
  
  public int a() {
    return this.width * this.height;
  }
  
  public ShapedRecipes c() {
    this.e = true;
    return this;
  }
  

  public java.util.List<ItemStack> getIngredients()
  {
    return Arrays.asList(this.items);
  }
}
