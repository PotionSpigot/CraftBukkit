package net.minecraft.server;

import java.util.Random;




public class BlockWoodStep
  extends BlockStepAbstract
{
  public static final String[] b = { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
  

  public BlockWoodStep(boolean paramBoolean)
  {
    super(paramBoolean, Material.WOOD);
    a(CreativeModeTab.b);
  }
  





  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(Blocks.WOOD_STEP);
  }
  
  protected ItemStack j(int paramInt)
  {
    return new ItemStack(Item.getItemOf(Blocks.WOOD_STEP), 2, paramInt & 0x7);
  }
  
  public String b(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= b.length)) {
      paramInt = 0;
    }
    return super.a() + "." + b[paramInt];
  }
}
