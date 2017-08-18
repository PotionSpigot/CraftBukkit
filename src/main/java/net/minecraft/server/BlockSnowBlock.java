package net.minecraft.server;

import java.util.Random;


public class BlockSnowBlock
  extends Block
{
  protected BlockSnowBlock()
  {
    super(Material.SNOW_BLOCK);
    a(true);
    a(CreativeModeTab.b);
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.SNOW_BALL;
  }
  
  public int a(Random paramRandom)
  {
    return 4;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
  {
    if (paramWorld.b(EnumSkyBlock.BLOCK, paramInt1, paramInt2, paramInt3) > 11) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
    }
  }
}
