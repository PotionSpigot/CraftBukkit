package net.minecraft.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerRegionHills extends GenLayer
{
  private static final Logger c = ;
  private GenLayer d;
  
  public GenLayerRegionHills(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2) {
    super(paramLong);
    this.a = paramGenLayer1;
    this.d = paramGenLayer2;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
    int[] arrayOfInt2 = this.d.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
    
    int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
    for (int i = 0; i < paramInt4; i++) {
      for (int j = 0; j < paramInt3; j++) {
        a(j + paramInt1, i + paramInt2);
        int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
        int m = arrayOfInt2[(j + 1 + (i + 1) * (paramInt3 + 2))];
        int n = (m - 2) % 29 == 0 ? 1 : 0;
        if (k > 255) {
          c.debug("old! " + k);
        }
        if ((k != 0) && (m >= 2) && ((m - 2) % 29 == 1) && (k < 128)) {
          if (BiomeBase.getBiome(k + 128) != null) {
            arrayOfInt3[(j + i * paramInt3)] = (k + 128);
          } else {
            arrayOfInt3[(j + i * paramInt3)] = k;
          }
        } else if ((a(3) == 0) || (n != 0)) {
          int i1 = k;
          int i2; if (k == BiomeBase.DESERT.id) {
            i1 = BiomeBase.DESERT_HILLS.id;
          } else if (k == BiomeBase.FOREST.id) {
            i1 = BiomeBase.FOREST_HILLS.id;
          } else if (k == BiomeBase.BIRCH_FOREST.id) {
            i1 = BiomeBase.BIRCH_FOREST_HILLS.id;
          } else if (k == BiomeBase.ROOFED_FOREST.id) {
            i1 = BiomeBase.PLAINS.id;
          } else if (k == BiomeBase.TAIGA.id) {
            i1 = BiomeBase.TAIGA_HILLS.id;
          } else if (k == BiomeBase.MEGA_TAIGA.id) {
            i1 = BiomeBase.MEGA_TAIGA_HILLS.id;
          } else if (k == BiomeBase.COLD_TAIGA.id) {
            i1 = BiomeBase.COLD_TAIGA_HILLS.id;
          } else if (k == BiomeBase.PLAINS.id) {
            if (a(3) == 0) {
              i1 = BiomeBase.FOREST_HILLS.id;
            } else {
              i1 = BiomeBase.FOREST.id;
            }
          } else if (k == BiomeBase.ICE_PLAINS.id) {
            i1 = BiomeBase.ICE_MOUNTAINS.id;
          } else if (k == BiomeBase.JUNGLE.id) {
            i1 = BiomeBase.JUNGLE_HILLS.id;
          } else if (k == BiomeBase.OCEAN.id) {
            i1 = BiomeBase.DEEP_OCEAN.id;
          } else if (k == BiomeBase.EXTREME_HILLS.id) {
            i1 = BiomeBase.EXTREME_HILLS_PLUS.id;
          } else if (k == BiomeBase.SAVANNA.id) {
            i1 = BiomeBase.SAVANNA_PLATEAU.id;
          } else if (a(k, BiomeBase.MESA_PLATEAU_F.id)) {
            i1 = BiomeBase.MESA.id;
          } else if ((k == BiomeBase.DEEP_OCEAN.id) && 
            (a(3) == 0)) {
            i2 = a(2);
            if (i2 == 0) {
              i1 = BiomeBase.PLAINS.id;
            } else {
              i1 = BiomeBase.FOREST.id;
            }
          }
          
          if ((n != 0) && (i1 != k)) {
            if (BiomeBase.getBiome(i1 + 128) != null) {
              i1 += 128;
            } else {
              i1 = k;
            }
          }
          if (i1 == k) {
            arrayOfInt3[(j + i * paramInt3)] = k;
          } else {
            i2 = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
            int i3 = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
            int i4 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
            int i5 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
            int i6 = 0;
            if (a(i2, k)) {
              i6++;
            }
            if (a(i3, k)) {
              i6++;
            }
            if (a(i4, k)) {
              i6++;
            }
            if (a(i5, k)) {
              i6++;
            }
            if (i6 >= 3) {
              arrayOfInt3[(j + i * paramInt3)] = i1;
            } else {
              arrayOfInt3[(j + i * paramInt3)] = k;
            }
          }
        } else {
          arrayOfInt3[(j + i * paramInt3)] = k;
        }
      }
    }
    
    return arrayOfInt3;
  }
}
