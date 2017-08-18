package net.minecraft.server;

import java.util.Random;













public class BlockStep
  extends BlockStepAbstract
{
  public static final String[] b = { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
  



  public BlockStep(boolean paramBoolean)
  {
    super(paramBoolean, Material.STONE);
    a(CreativeModeTab.b);
  }
  

































  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(Blocks.STEP);
  }
  
  protected ItemStack j(int paramInt)
  {
    return new ItemStack(Item.getItemOf(Blocks.STEP), 2, paramInt & 0x7);
  }
  
  public String b(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= b.length)) {
      paramInt = 0;
    }
    return super.a() + "." + b[paramInt];
  }
}
