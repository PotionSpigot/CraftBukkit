package net.minecraft.server;

public class GenLayerDesert
  extends GenLayer
{
  public GenLayerDesert(long paramLong, GenLayer paramGenLayer)
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
        if ((!a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.EXTREME_HILLS.id, BiomeBase.SMALL_MOUNTAINS.id)) && 
          (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MESA_PLATEAU_F.id, BiomeBase.MESA.id)) && 
          (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MESA_PLATEAU.id, BiomeBase.MESA.id)) && 
          (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MEGA_TAIGA.id, BiomeBase.TAIGA.id))) { int m;
          int n; int i1; int i2; if (k == BiomeBase.DESERT.id) {
            m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
            n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
            i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
            i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
            if ((m == BiomeBase.ICE_PLAINS.id) || (n == BiomeBase.ICE_PLAINS.id) || (i1 == BiomeBase.ICE_PLAINS.id) || (i2 == BiomeBase.ICE_PLAINS.id)) {
              arrayOfInt2[(j + i * paramInt3)] = BiomeBase.EXTREME_HILLS_PLUS.id;
            } else {
              arrayOfInt2[(j + i * paramInt3)] = k;
            }
          } else if (k == BiomeBase.SWAMPLAND.id)
          {
            m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
            n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
            i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
            i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
            if ((m == BiomeBase.DESERT.id) || (n == BiomeBase.DESERT.id) || (i1 == BiomeBase.DESERT.id) || (i2 == BiomeBase.DESERT.id) || (m == BiomeBase.COLD_TAIGA.id) || (n == BiomeBase.COLD_TAIGA.id) || (i1 == BiomeBase.COLD_TAIGA.id) || (i2 == BiomeBase.COLD_TAIGA.id) || (m == BiomeBase.ICE_PLAINS.id) || (n == BiomeBase.ICE_PLAINS.id) || (i1 == BiomeBase.ICE_PLAINS.id) || (i2 == BiomeBase.ICE_PLAINS.id))
            {
              arrayOfInt2[(j + i * paramInt3)] = BiomeBase.PLAINS.id;
            } else if ((m == BiomeBase.JUNGLE.id) || (i2 == BiomeBase.JUNGLE.id) || (n == BiomeBase.JUNGLE.id) || (i1 == BiomeBase.JUNGLE.id)) {
              arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE_EDGE.id;
            } else {
              arrayOfInt2[(j + i * paramInt3)] = k;
            }
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        }
      }
    }
    return arrayOfInt2;
  }
  
  private boolean a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (a(paramInt4, paramInt5)) {
      int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
      int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
      int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
      int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
      if ((!b(i, paramInt5)) || (!b(j, paramInt5)) || (!b(k, paramInt5)) || (!b(m, paramInt5))) {
        paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt6;
      } else {
        paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
      }
      return true;
    }
    return false;
  }
  
  private boolean b(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramInt4 == paramInt5) {
      int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
      int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
      int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
      int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
      if ((!a(i, paramInt5)) || (!a(j, paramInt5)) || (!a(k, paramInt5)) || (!a(m, paramInt5))) {
        paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt6;
      } else {
        paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
      }
      return true;
    }
    return false;
  }
  
  private boolean b(int paramInt1, int paramInt2) {
    if (a(paramInt1, paramInt2)) {
      return true;
    }
    if ((BiomeBase.getBiome(paramInt1) != null) && (BiomeBase.getBiome(paramInt2) != null)) {
      EnumTemperature localEnumTemperature1 = BiomeBase.getBiome(paramInt1).m();
      EnumTemperature localEnumTemperature2 = BiomeBase.getBiome(paramInt2).m();
      return (localEnumTemperature1 == localEnumTemperature2) || (localEnumTemperature1 == EnumTemperature.MEDIUM) || (localEnumTemperature2 == EnumTemperature.MEDIUM);
    }
    return false;
  }
}
