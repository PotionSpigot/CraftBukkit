package net.minecraft.server;

import java.util.Random;



public class WorldGenDeadBush
  extends WorldGenerator
{
  private Block a;
  
  public WorldGenDeadBush(Block paramBlock)
  {
    this.a = paramBlock;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    Block localBlock;
    while ((((localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3)).getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) && (paramInt2 > 0)) {
      paramInt2--;
    }
    for (int i = 0; i < 4; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((paramWorld.isEmpty(j, k, m)) && 
        (this.a.j(paramWorld, j, k, m))) {
        paramWorld.setTypeAndData(j, k, m, this.a, 0, 2);
      }
    }
    

    return true;
  }
}
