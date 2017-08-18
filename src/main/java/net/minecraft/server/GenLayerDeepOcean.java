package net.minecraft.server;

public class GenLayerDeepOcean
  extends GenLayer
{
  public GenLayerDeepOcean(long paramLong, GenLayer paramGenLayer)
  {
    super(paramLong);
    this.a = paramGenLayer;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 - 1;
    int j = paramInt2 - 1;
    int k = paramInt3 + 2;
    int m = paramInt4 + 2;
    int[] arrayOfInt1 = this.a.a(i, j, k, m);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    for (int n = 0; n < paramInt4; n++) {
      for (int i1 = 0; i1 < paramInt3; i1++) {
        int i2 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * (paramInt3 + 2))];
        int i3 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * (paramInt3 + 2))];
        int i4 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * (paramInt3 + 2))];
        int i5 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * (paramInt3 + 2))];
        
        int i6 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
        int i7 = 0;
        if (i2 == 0) i7++;
        if (i3 == 0) i7++;
        if (i4 == 0) i7++;
        if (i5 == 0) { i7++;
        }
        if ((i6 == 0) && (i7 > 3)) {
          arrayOfInt2[(i1 + n * paramInt3)] = BiomeBase.DEEP_OCEAN.id;
        } else {
          arrayOfInt2[(i1 + n * paramInt3)] = i6;
        }
      }
    }
    return arrayOfInt2;
  }
}
