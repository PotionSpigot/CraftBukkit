package net.minecraft.server;

import java.util.Random;

public class BlockClay
  extends Block
{
  public BlockClay()
  {
    super(Material.CLAY);
    a(CreativeModeTab.b);
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.CLAY_BALL;
  }
  
  public int a(Random paramRandom)
  {
    return 4;
  }
}
