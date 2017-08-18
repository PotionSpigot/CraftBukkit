package net.minecraft.server;

import java.util.Random;




public abstract class WorldGenTreeAbstract
  extends WorldGenerator
{
  public WorldGenTreeAbstract(boolean paramBoolean)
  {
    super(paramBoolean);
  }
  
  protected boolean a(Block paramBlock) {
    return (paramBlock.getMaterial() == Material.AIR) || (paramBlock.getMaterial() == Material.LEAVES) || (paramBlock == Blocks.GRASS) || (paramBlock == Blocks.DIRT) || (paramBlock == Blocks.LOG) || (paramBlock == Blocks.LOG2) || (paramBlock == Blocks.SAPLING) || (paramBlock == Blocks.VINE);
  }
  
  public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {}
}
