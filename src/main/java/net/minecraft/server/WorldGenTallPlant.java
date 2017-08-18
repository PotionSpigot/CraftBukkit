package net.minecraft.server;

import java.util.Random;




public class WorldGenTallPlant
  extends WorldGenerator
{
  private int a;
  
  public void a(int paramInt)
  {
    this.a = paramInt;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = false;
    
    for (int i = 0; i < 64; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((paramWorld.isEmpty(j, k, m)) && ((!paramWorld.worldProvider.g) || (k < 254)) && 
        (Blocks.DOUBLE_PLANT.canPlace(paramWorld, j, k, m))) {
        Blocks.DOUBLE_PLANT.c(paramWorld, j, k, m, this.a, 2);
        bool = true;
      }
    }
    

    return bool;
  }
}
