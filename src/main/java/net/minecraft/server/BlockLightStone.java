package net.minecraft.server;

import java.util.Random;



public class BlockLightStone
  extends Block
{
  public BlockLightStone(Material paramMaterial)
  {
    super(paramMaterial);
    a(CreativeModeTab.b);
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    return MathHelper.a(a(paramRandom) + paramRandom.nextInt(paramInt + 1), 1, 4);
  }
  
  public int a(Random paramRandom)
  {
    return 2 + paramRandom.nextInt(3);
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.GLOWSTONE_DUST;
  }
  
  public MaterialMapColor f(int paramInt)
  {
    return MaterialMapColor.d;
  }
}
