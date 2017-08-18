package net.minecraft.server;

public class GenLayerMushroomShore extends GenLayer
{
  public GenLayerMushroomShore(long paramLong, GenLayer paramGenLayer)
  {
    super(paramLong);
    this.a = paramGenLayer;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    for (int i = 0; i < paramInt4; i++) {
      for (int j = 0; j < paramInt3; j++) {
        a(j + paramInt1, i + paramInt2);
        int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
        BiomeBase localBiomeBase = BiomeBase.getBiome(k);
        int m; int n; int i1; int i2; if (k == BiomeBase.MUSHROOM_ISLAND.id) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((m == BiomeBase.OCEAN.id) || (n == BiomeBase.OCEAN.id) || (i1 == BiomeBase.OCEAN.id) || (i2 == BiomeBase.OCEAN.id)) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MUSHROOM_SHORE.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else if ((localBiomeBase != null) && (localBiomeBase.l() == BiomeJungle.class)) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((!c(m)) || (!c(n)) || (!c(i1)) || (!c(i2))) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE_EDGE.id;
          } else if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.BEACH.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else if ((k == BiomeBase.EXTREME_HILLS.id) || (k == BiomeBase.EXTREME_HILLS_PLUS.id) || (k == BiomeBase.SMALL_MOUNTAINS.id)) {
          a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.STONE_BEACH.id);
        } else if ((localBiomeBase != null) && (localBiomeBase.j())) {
          a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.COLD_BEACH.id);
        } else if ((k == BiomeBase.MESA.id) || (k == BiomeBase.MESA_PLATEAU_F.id)) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
            arrayOfInt2[(j + i * paramInt3)] = k;
          } else if ((!d(m)) || (!d(n)) || (!d(i1)) || (!d(i2))) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.DESERT.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else if ((k != BiomeBase.OCEAN.id) && (k != BiomeBase.DEEP_OCEAN.id) && (k != BiomeBase.RIVER.id) && (k != BiomeBase.SWAMPLAND.id)) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.BEACH.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        }
        else {
          arrayOfInt2[(j + i * paramInt3)] = k;
        }
      }
    }
    

    return arrayOfInt2;
  }
  
  private void a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (b(paramInt4)) {
      paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
      return;
    }
    int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
    int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
    int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
    int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
    if ((b(i)) || (b(j)) || (b(k)) || (b(m))) {
      paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt5;
    } else {
      paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
    }
  }
  
  private boolean c(int paramInt) {
    if ((BiomeBase.getBiome(paramInt) != null) && (BiomeBase.getBiome(paramInt).l() == BiomeJungle.class)) {
      return true;
    }
    
    return (paramInt == BiomeBase.JUNGLE_EDGE.id) || (paramInt == BiomeBase.JUNGLE.id) || (paramInt == BiomeBase.JUNGLE_HILLS.id) || (paramInt == BiomeBase.FOREST.id) || (paramInt == BiomeBase.TAIGA.id) || (b(paramInt));
  }
  
  private boolean d(int paramInt) {
    return (BiomeBase.getBiome(paramInt) != null) && ((BiomeBase.getBiome(paramInt) instanceof BiomeMesa));
  }
}
