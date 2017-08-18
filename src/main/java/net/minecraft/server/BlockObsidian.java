package net.minecraft.server;

import java.util.Random;


public class BlockObsidian
  extends BlockStone
{
  public int a(Random paramRandom)
  {
    return 1;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getItemOf(Blocks.OBSIDIAN);
  }
  
  public MaterialMapColor f(int paramInt)
  {
    return MaterialMapColor.J;
  }
}
