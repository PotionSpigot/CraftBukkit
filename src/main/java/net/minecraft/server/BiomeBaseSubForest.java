package net.minecraft.server;

import java.util.Random;

class BiomeBaseSubForest extends BiomeBaseSub
{
  BiomeBaseSubForest(BiomeForest paramBiomeForest, int paramInt, BiomeBase paramBiomeBase) { super(paramInt, paramBiomeBase); }
  
  public WorldGenTreeAbstract a(Random paramRandom) {
    if (paramRandom.nextBoolean()) {
      return BiomeForest.aC;
    }
    return BiomeForest.aD;
  }
}
