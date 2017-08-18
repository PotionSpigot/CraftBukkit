package net.minecraft.server;

import java.util.Set;
import net.minecraft.util.com.google.common.collect.Sets;



public class ItemAxe
  extends ItemTool
{
  private static final Set c = Sets.newHashSet(new Block[] { Blocks.WOOD, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.JACK_O_LANTERN });
  

  protected ItemAxe(EnumToolMaterial paramEnumToolMaterial)
  {
    super(3.0F, paramEnumToolMaterial, c);
  }
  
  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    if ((paramBlock.getMaterial() == Material.WOOD) || (paramBlock.getMaterial() == Material.PLANT) || (paramBlock.getMaterial() == Material.REPLACEABLE_PLANT)) {
      return this.a;
    }
    return super.getDestroySpeed(paramItemStack, paramBlock);
  }
}
