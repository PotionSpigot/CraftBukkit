package net.minecraft.server;

import java.util.List;
import org.bukkit.inventory.Recipe;

public abstract interface IRecipe
{
  public abstract boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  public abstract ItemStack a(InventoryCrafting paramInventoryCrafting);
  
  public abstract int a();
  
  public abstract ItemStack b();
  
  public abstract Recipe toBukkitRecipe();
  
  public abstract List<ItemStack> getIngredients();
}
