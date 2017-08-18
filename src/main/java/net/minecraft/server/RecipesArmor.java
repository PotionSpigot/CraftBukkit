package net.minecraft.server;



public class RecipesArmor
{
  private String[][] a = { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
  






















  private Object[][] b = { { Items.LEATHER, Blocks.FIRE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.GOLD_HELMET }, { Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.GOLD_CHESTPLATE }, { Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.GOLD_LEGGINGS }, { Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.GOLD_BOOTS } };
  





  public void a(CraftingManager paramCraftingManager)
  {
    for (int i = 0; i < this.b[0].length; i++)
    {
      Object localObject = this.b[0][i];
      
      for (int j = 0; j < this.b.length - 1; j++) {
        Item localItem = (Item)this.b[(j + 1)][i];
        
        paramCraftingManager.registerShapedRecipe(new ItemStack(localItem), new Object[] { this.a[j], Character.valueOf('X'), localObject });
      }
    }
  }
}
