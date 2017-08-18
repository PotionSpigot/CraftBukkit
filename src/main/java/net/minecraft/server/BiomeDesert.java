package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BiomeDesert extends BiomeBase
{
  public BiomeDesert(int paramInt)
  {
    super(paramInt);
    

    this.at.clear();
    this.ai = Blocks.SAND;
    this.ak = Blocks.SAND;
    
    this.ar.x = 64537;
    this.ar.A = 2;
    this.ar.C = 50;
    this.ar.D = 10;
    
    this.at.clear();
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
    
    if (paramRandom.nextInt(1000) == 0) {
      int i = paramInt1 + paramRandom.nextInt(16) + 8;
      int j = paramInt2 + paramRandom.nextInt(16) + 8;
      WorldGenDesertWell localWorldGenDesertWell = new WorldGenDesertWell();
      localWorldGenDesertWell.generate(paramWorld, paramRandom, i, paramWorld.getHighestBlockYAt(i, j) + 1, j);
    }
  }
}
