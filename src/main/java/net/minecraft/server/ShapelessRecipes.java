package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftShapelessRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ShapelessRecipes implements IRecipe
{
  public final ItemStack result;
  private final List ingredients;
  
  public ShapelessRecipes(ItemStack itemstack, List list)
  {
    this.result = itemstack;
    this.ingredients = list;
  }
  

  public ShapelessRecipe toBukkitRecipe()
  {
    CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
    CraftShapelessRecipe recipe = new CraftShapelessRecipe(result, this);
    for (ItemStack stack : this.ingredients) {
      if (stack != null) {
        recipe.addIngredient(org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getMaterial(stack.getItem()), stack.getData());
      }
    }
    return recipe;
  }
  
  public ItemStack b()
  {
    return this.result;
  }
  
  public boolean a(InventoryCrafting inventorycrafting, World world) {
    ArrayList arraylist = new ArrayList(this.ingredients);
    
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        ItemStack itemstack = inventorycrafting.b(j, i);
        
        if (itemstack != null) {
          boolean flag = false;
          Iterator iterator = arraylist.iterator();
          
          while (iterator.hasNext()) {
            ItemStack itemstack1 = (ItemStack)iterator.next();
            
            if ((itemstack.getItem() == itemstack1.getItem()) && ((itemstack1.getData() == 32767) || (itemstack.getData() == itemstack1.getData()))) {
              flag = true;
              arraylist.remove(itemstack1);
              break;
            }
          }
          
          if (!flag) {
            return false;
          }
        }
      }
    }
    
    return arraylist.isEmpty();
  }
  
  public ItemStack a(InventoryCrafting inventorycrafting) {
    return this.result.cloneItemStack();
  }
  
  public int a() {
    return this.ingredients.size();
  }
  

  public List<ItemStack> getIngredients()
  {
    return Collections.unmodifiableList(this.ingredients);
  }
}
