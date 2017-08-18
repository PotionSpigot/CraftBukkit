package net.minecraft.server;





public abstract class GenLayer
{
  private long c;
  



  protected GenLayer a;
  


  private long d;
  


  protected long b;
  



  public static GenLayer[] a(long paramLong, WorldType paramWorldType)
  {
    int i = 0;
    
    Object localObject1 = new LayerIsland(1L);
    localObject1 = new GenLayerZoomFuzzy(2000L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(1L, (GenLayer)localObject1);
    localObject1 = new GenLayerZoom(2001L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(2L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(50L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(70L, (GenLayer)localObject1);
    localObject1 = new GenLayerIcePlains(2L, (GenLayer)localObject1);
    localObject1 = new GenLayerTopSoil(2L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(3L, (GenLayer)localObject1);
    localObject1 = new GenLayerSpecial(2L, (GenLayer)localObject1, EnumGenLayerSpecial.COOL_WARM);
    localObject1 = new GenLayerSpecial(2L, (GenLayer)localObject1, EnumGenLayerSpecial.HEAT_ICE);
    localObject1 = new GenLayerSpecial(3L, (GenLayer)localObject1, EnumGenLayerSpecial.PUFFERFISH);
    localObject1 = new GenLayerZoom(2002L, (GenLayer)localObject1);
    localObject1 = new GenLayerZoom(2003L, (GenLayer)localObject1);
    localObject1 = new GenLayerIsland(4L, (GenLayer)localObject1);
    localObject1 = new GenLayerMushroomIsland(5L, (GenLayer)localObject1);
    localObject1 = new GenLayerDeepOcean(4L, (GenLayer)localObject1);
    localObject1 = GenLayerZoom.b(1000L, (GenLayer)localObject1, 0);
    


    int j = 4;
    if (paramWorldType == WorldType.LARGE_BIOMES) {
      j = 6;
    }
    if (i != 0) {
      j = 4;
    }
    
    Object localObject2 = localObject1;
    localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, 0);
    localObject2 = new GenLayerCleaner(100L, (GenLayer)localObject2);
    
    Object localObject3 = localObject1;
    localObject3 = new GenLayerBiome(200L, (GenLayer)localObject3, paramWorldType);
    if (i == 0) {
      localObject3 = GenLayerZoom.b(1000L, (GenLayer)localObject3, 2);
      localObject3 = new GenLayerDesert(1000L, (GenLayer)localObject3);
    }
    Object localObject4 = localObject2;
    localObject4 = GenLayerZoom.b(1000L, (GenLayer)localObject4, 2);
    localObject3 = new GenLayerRegionHills(1000L, (GenLayer)localObject3, (GenLayer)localObject4);
    
    localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, 2);
    localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, j);
    localObject2 = new GenLayerRiver(1L, (GenLayer)localObject2);
    localObject2 = new GenLayerSmooth(1000L, (GenLayer)localObject2);
    
    localObject3 = new GenLayerPlains(1001L, (GenLayer)localObject3);
    for (int k = 0; k < j; k++) {
      localObject3 = new GenLayerZoom(1000 + k, (GenLayer)localObject3);
      if (k == 0) {
        localObject3 = new GenLayerIsland(3L, (GenLayer)localObject3);
      }
      
      if (k == 1) {
        localObject3 = new GenLayerMushroomShore(1000L, (GenLayer)localObject3);
      }
    }
    
    localObject3 = new GenLayerSmooth(1000L, (GenLayer)localObject3);
    
    localObject3 = new GenLayerRiverMix(100L, (GenLayer)localObject3, (GenLayer)localObject2);
    
    Object localObject5 = localObject3;
    GenLayerZoomVoronoi localGenLayerZoomVoronoi = new GenLayerZoomVoronoi(10L, (GenLayer)localObject3);
    
    ((GenLayer)localObject3).a(paramLong);
    localGenLayerZoomVoronoi.a(paramLong);
    
    return new GenLayer[] { localObject3, localGenLayerZoomVoronoi, localObject5 };
  }
  

  public GenLayer(long paramLong)
  {
    this.b = paramLong;
    this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
    this.b += paramLong;
    this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
    this.b += paramLong;
    this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
    this.b += paramLong;
  }
  
  public void a(long paramLong) {
    this.c = paramLong;
    if (this.a != null) this.a.a(paramLong);
    this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
    this.c += this.b;
    this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
    this.c += this.b;
    this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
    this.c += this.b;
  }
  
  public void a(long paramLong1, long paramLong2) {
    this.d = this.c;
    this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
    this.d += paramLong1;
    this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
    this.d += paramLong2;
    this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
    this.d += paramLong1;
    this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
    this.d += paramLong2;
  }
  
  protected int a(int paramInt) {
    int i = (int)((this.d >> 24) % paramInt);
    if (i < 0) i += paramInt;
    this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
    this.d += this.c;
    return i;
  }
  
  public abstract int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  protected static boolean a(int paramInt1, int paramInt2) {
    if (paramInt1 == paramInt2) {
      return true;
    }
    if ((paramInt1 == BiomeBase.MESA_PLATEAU_F.id) || (paramInt1 == BiomeBase.MESA_PLATEAU.id)) {
      return (paramInt2 == BiomeBase.MESA_PLATEAU_F.id) || (paramInt2 == BiomeBase.MESA_PLATEAU.id);
    }
    try
    {
      if ((BiomeBase.getBiome(paramInt1) != null) && (BiomeBase.getBiome(paramInt2) != null)) {
        return BiomeBase.getBiome(paramInt1).a(BiomeBase.getBiome(paramInt2));
      }
    } catch (Throwable localThrowable) {
      CrashReport localCrashReport = CrashReport.a(localThrowable, "Comparing biomes");
      CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("Biomes being compared");
      
      localCrashReportSystemDetails.a("Biome A ID", Integer.valueOf(paramInt1));
      localCrashReportSystemDetails.a("Biome B ID", Integer.valueOf(paramInt2));
      
      localCrashReportSystemDetails.a("Biome A", new CrashReportGenLayer1(paramInt1));
      




      localCrashReportSystemDetails.a("Biome B", new CrashReportGenLayer2(paramInt2));
      





      throw new ReportedException(localCrashReport);
    }
    
    return false;
  }
  
  protected static boolean b(int paramInt) {
    return (paramInt == BiomeBase.OCEAN.id) || (paramInt == BiomeBase.DEEP_OCEAN.id) || (paramInt == BiomeBase.FROZEN_OCEAN.id);
  }
  
  protected int a(int... paramVarArgs) {
    return paramVarArgs[a(paramVarArgs.length)];
  }
  
  protected int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if ((paramInt2 == paramInt3) && (paramInt3 == paramInt4)) return paramInt2;
    if ((paramInt1 == paramInt2) && (paramInt1 == paramInt3)) return paramInt1;
    if ((paramInt1 == paramInt2) && (paramInt1 == paramInt4)) return paramInt1;
    if ((paramInt1 == paramInt3) && (paramInt1 == paramInt4)) { return paramInt1;
    }
    if ((paramInt1 == paramInt2) && (paramInt3 != paramInt4)) return paramInt1;
    if ((paramInt1 == paramInt3) && (paramInt2 != paramInt4)) return paramInt1;
    if ((paramInt1 == paramInt4) && (paramInt2 != paramInt3)) { return paramInt1;
    }
    
    if ((paramInt2 == paramInt3) && (paramInt1 != paramInt4)) return paramInt2;
    if ((paramInt2 == paramInt4) && (paramInt1 != paramInt3)) { return paramInt2;
    }
    

    if ((paramInt3 == paramInt4) && (paramInt1 != paramInt2)) { return paramInt3;
    }
    



    return a(new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
  }
}
