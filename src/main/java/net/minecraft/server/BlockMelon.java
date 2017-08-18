package net.minecraft.server;

import java.util.Random;






public class BlockMelon
  extends Block
{
  protected BlockMelon()
  {
    super(Material.PUMPKIN);
    a(CreativeModeTab.b);
  }
  






  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.MELON;
  }
  
  public int a(Random paramRandom)
  {
    return 3 + paramRandom.nextInt(5);
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    int i = a(paramRandom) + paramRandom.nextInt(1 + paramInt);
    if (i > 9) {
      i = 9;
    }
    return i;
  }
}
