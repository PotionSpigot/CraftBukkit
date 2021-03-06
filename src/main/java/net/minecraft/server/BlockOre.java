package net.minecraft.server;

import java.util.Random;

public class BlockOre extends Block
{
  public BlockOre() {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  
  public Item getDropType(int i, Random random, int j) {
    return this == Blocks.QUARTZ_ORE ? Items.QUARTZ : this == Blocks.EMERALD_ORE ? Items.EMERALD : this == Blocks.LAPIS_ORE ? Items.INK_SACK : this == Blocks.DIAMOND_ORE ? Items.DIAMOND : this == Blocks.COAL_ORE ? Items.COAL : Item.getItemOf(this);
  }
  
  public int a(Random random) {
    return this == Blocks.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
  }
  
  public int getDropCount(int i, Random random) {
    if ((i > 0) && (Item.getItemOf(this) != getDropType(0, random, i))) {
      int j = random.nextInt(i + 2) - 1;
      
      if (j < 0) {
        j = 0;
      }
      
      return a(random) * (j + 1);
    }
    return a(random);
  }
  
  public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1)
  {
    super.dropNaturally(world, i, j, k, l, f, i1);
  }
  


















  public int getExpDrop(World world, int l, int i1)
  {
    if (getDropType(l, world.random, i1) != Item.getItemOf(this)) {
      int j1 = 0;
      
      if (this == Blocks.COAL_ORE) {
        j1 = MathHelper.nextInt(world.random, 0, 2);
      } else if (this == Blocks.DIAMOND_ORE) {
        j1 = MathHelper.nextInt(world.random, 3, 7);
      } else if (this == Blocks.EMERALD_ORE) {
        j1 = MathHelper.nextInt(world.random, 3, 7);
      } else if (this == Blocks.LAPIS_ORE) {
        j1 = MathHelper.nextInt(world.random, 2, 5);
      } else if (this == Blocks.QUARTZ_ORE) {
        j1 = MathHelper.nextInt(world.random, 2, 5);
      }
      
      return j1;
    }
    
    return 0;
  }
  
  public int getDropData(int i)
  {
    return this == Blocks.LAPIS_ORE ? 4 : 0;
  }
}
