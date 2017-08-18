package net.minecraft.server;

import java.util.Random;

public class WorldGenFlowers
  extends WorldGenerator
{
  private Block a;
  private int b;
  
  public WorldGenFlowers(Block paramBlock)
  {
    this.a = paramBlock;
  }
  
  public void a(Block paramBlock, int paramInt) {
    this.a = paramBlock;
    this.b = paramInt;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < 64; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((paramWorld.isEmpty(j, k, m)) && ((!paramWorld.worldProvider.g) || (k < 255)) && 
        (this.a.j(paramWorld, j, k, m))) {
        paramWorld.setTypeAndData(j, k, m, this.a, this.b, 2);
      }
    }
    

    return true;
  }
}
