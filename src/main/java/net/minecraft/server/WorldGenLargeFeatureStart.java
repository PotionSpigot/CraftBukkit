package net.minecraft.server;

import java.util.LinkedList;
import java.util.Random;












































































public class WorldGenLargeFeatureStart
  extends StructureStart
{
  public WorldGenLargeFeatureStart() {}
  
  public WorldGenLargeFeatureStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
    BiomeBase localBiomeBase = paramWorld.getBiome(paramInt1 * 16 + 8, paramInt2 * 16 + 8);
    Object localObject; if ((localBiomeBase == BiomeBase.JUNGLE) || (localBiomeBase == BiomeBase.JUNGLE_HILLS)) {
      localObject = new WorldGenJungleTemple(paramRandom, paramInt1 * 16, paramInt2 * 16);
      this.a.add(localObject);
    } else if (localBiomeBase == BiomeBase.SWAMPLAND) {
      localObject = new WorldGenWitchHut(paramRandom, paramInt1 * 16, paramInt2 * 16);
      this.a.add(localObject);
    } else {
      localObject = new WorldGenPyramidPiece(paramRandom, paramInt1 * 16, paramInt2 * 16);
      this.a.add(localObject);
    }
    
    c();
  }
}
