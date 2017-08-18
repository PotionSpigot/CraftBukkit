package net.minecraft.server;


public class GenLayerBiome
  extends GenLayer
{
  private BiomeBase[] c = { BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.SAVANNA, BiomeBase.SAVANNA, BiomeBase.PLAINS };
  


  private BiomeBase[] d = { BiomeBase.FOREST, BiomeBase.ROOFED_FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.PLAINS, BiomeBase.BIRCH_FOREST, BiomeBase.SWAMPLAND };
  


  private BiomeBase[] e = { BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.TAIGA, BiomeBase.PLAINS };
  


  private BiomeBase[] f = { BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.COLD_TAIGA };
  


  public GenLayerBiome(long paramLong, GenLayer paramGenLayer, WorldType paramWorldType)
  {
    super(paramLong);
    this.a = paramGenLayer;
    
    if (paramWorldType == WorldType.NORMAL_1_1) {
      this.c = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA };
    }
  }
  


  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    for (int i = 0; i < paramInt4; i++) {
      for (int j = 0; j < paramInt3; j++) {
        a(j + paramInt1, i + paramInt2);
        int k = arrayOfInt1[(j + i * paramInt3)];
        int m = (k & 0xF00) >> 8;
        k &= 0xF0FF;
        if (b(k)) {
          arrayOfInt2[(j + i * paramInt3)] = k;
        } else if (k == BiomeBase.MUSHROOM_ISLAND.id) {
          arrayOfInt2[(j + i * paramInt3)] = k;
        } else if (k == 1) {
          if (m > 0) {
            if (a(3) == 0) {
              arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MESA_PLATEAU.id;
            } else {
              arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MESA_PLATEAU_F.id;
            }
          } else {
            arrayOfInt2[(j + i * paramInt3)] = this.c[a(this.c.length)].id;
          }
        } else if (k == 2) {
          if (m > 0) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = this.d[a(this.d.length)].id;
          }
        } else if (k == 3) {
          if (m > 0) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MEGA_TAIGA.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = this.e[a(this.e.length)].id;
          }
        } else if (k == 4) {
          arrayOfInt2[(j + i * paramInt3)] = this.f[a(this.f.length)].id;
        } else {
          arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MUSHROOM_ISLAND.id;
        }
      }
    }
    
    return arrayOfInt2;
  }
}
