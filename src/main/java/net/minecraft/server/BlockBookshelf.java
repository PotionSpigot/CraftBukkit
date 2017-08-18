package net.minecraft.server;

import java.util.Random;



public class BlockBookshelf
  extends Block
{
  public BlockBookshelf()
  {
    super(Material.WOOD);
    a(CreativeModeTab.b);
  }
  






  public int a(Random paramRandom)
  {
    return 3;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.BOOK;
  }
}
