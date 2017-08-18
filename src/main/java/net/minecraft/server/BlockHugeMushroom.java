package net.minecraft.server;

import java.util.Random;











public class BlockHugeMushroom
  extends Block
{
  private static final String[] a = { "skin_brown", "skin_red" };
  


  private final int b;
  


  public BlockHugeMushroom(Material paramMaterial, int paramInt)
  {
    super(paramMaterial);
    this.b = paramInt;
  }
  
























  public int a(Random paramRandom)
  {
    int i = paramRandom.nextInt(10) - 7;
    if (i < 0) i = 0;
    return i;
  }
  

  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.getById(Block.getId(Blocks.BROWN_MUSHROOM) + this.b);
  }
}
