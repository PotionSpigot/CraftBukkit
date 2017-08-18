package net.minecraft.server;

import java.util.Random;





public class BlockLongGrass
  extends BlockPlant
  implements IBlockFragilePlantElement
{
  private static final String[] a = { "deadbush", "tallgrass", "fern" };
  







  protected BlockLongGrass()
  {
    super(Material.REPLACEABLE_PLANT);
    float f = 0.4F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
  }
  














  public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3));
  }
  















  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    if (paramRandom.nextInt(8) == 0) {
      return Items.SEEDS;
    }
    
    return null;
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    return 1 + paramRandom.nextInt(paramInt * 2 + 1);
  }
  
  public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!paramWorld.isStatic) && (paramEntityHuman.bF() != null) && (paramEntityHuman.bF().getItem() == Items.SHEARS)) {
      paramEntityHuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
      

      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Blocks.LONG_GRASS, 1, paramInt4));
    } else {
      super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramWorld.getData(paramInt1, paramInt2, paramInt3);
  }
  
















  public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (i == 0) {
      return false;
    }
    return true;
  }
  
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    return true;
  }
  
  public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = 2;
    if (i == 2) {
      j = 3;
    }
    if (Blocks.DOUBLE_PLANT.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) {
      Blocks.DOUBLE_PLANT.c(paramWorld, paramInt1, paramInt2, paramInt3, j, 2);
    }
  }
}
