package net.minecraft.server;



public class RecipesFood
{
  public void a(CraftingManager paramCraftingManager)
  {
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MUSHROOM_SOUP), new Object[] { Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL });
    

    paramCraftingManager.registerShapedRecipe(new ItemStack(Items.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Items.INK_SACK, 1, 3), Character.valueOf('#'), Items.WHEAT });
    



    paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.MELON), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Items.MELON });
    





    paramCraftingManager.registerShapedRecipe(new ItemStack(Items.MELON_SEEDS), new Object[] { "M", Character.valueOf('M'), Items.MELON });
    



    paramCraftingManager.registerShapedRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 4), new Object[] { "M", Character.valueOf('M'), Blocks.PUMPKIN });
    



    paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[] { Blocks.PUMPKIN, Items.SUGAR, Items.EGG });
    


    paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), new Object[] { Items.SPIDER_EYE, Blocks.BROWN_MUSHROOM, Items.SUGAR });
    

    paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2), new Object[] { Items.BLAZE_ROD });
    

    paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), new Object[] { Items.BLAZE_POWDER, Items.SLIME_BALL });
  }
}
