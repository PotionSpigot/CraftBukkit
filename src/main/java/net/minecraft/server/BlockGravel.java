package net.minecraft.server;

import java.util.Random;


public class BlockGravel
  extends BlockFalling
{
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    if (paramInt2 > 3) paramInt2 = 3;
    if (paramRandom.nextInt(10 - paramInt2 * 3) == 0) return Items.FLINT;
    return Item.getItemOf(this);
  }
}
