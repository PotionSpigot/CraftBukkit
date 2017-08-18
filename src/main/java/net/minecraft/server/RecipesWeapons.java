package net.minecraft.server;



public class RecipesWeapons
{
  private String[][] a = { { "X", "X", "#" } };
  




  private Object[][] b = { { Blocks.WOOD, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.WOOD_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.GOLD_SWORD } };
  


  public void a(CraftingManager paramCraftingManager)
  {
    for (int i = 0; i < this.b[0].length; i++) {
      Object localObject = this.b[0][i];
      
      for (int j = 0; j < this.b.length - 1; j++) {
        Item localItem = (Item)this.b[(j + 1)][i];
        paramCraftingManager.registerShapedRecipe(new ItemStack(localItem), new Object[] { this.a[j], Character.valueOf('#'), Items.STICK, Character.valueOf('X'), localObject });
      }
    }
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Items.BOW, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Items.STRING, Character.valueOf('#'), Items.STICK });
    






    paramCraftingManager.registerShapedRecipe(new ItemStack(Items.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Items.FEATHER, Character.valueOf('X'), Items.FLINT, Character.valueOf('#'), Items.STICK });
  }
}
