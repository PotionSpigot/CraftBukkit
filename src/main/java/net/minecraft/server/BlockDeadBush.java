package net.minecraft.server;

import java.util.Random;




public class BlockDeadBush
  extends BlockPlant
{
  protected BlockDeadBush()
  {
    super(Material.REPLACEABLE_PLANT);
    float f = 0.4F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
  }
  
  protected boolean a(Block paramBlock)
  {
    return (paramBlock == Blocks.SAND) || (paramBlock == Blocks.HARDENED_CLAY) || (paramBlock == Blocks.STAINED_HARDENED_CLAY) || (paramBlock == Blocks.DIRT);
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return null;
  }
  
  public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!paramWorld.isStatic) && (paramEntityHuman.bF() != null) && (paramEntityHuman.bF().getItem() == Items.SHEARS)) {
      paramEntityHuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
      

      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Blocks.DEAD_BUSH, 1, paramInt4));
    } else {
      super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
}
