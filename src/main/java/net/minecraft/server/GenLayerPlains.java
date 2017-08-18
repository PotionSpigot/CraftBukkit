package net.minecraft.server;

public class GenLayerPlains extends GenLayer
{
  public GenLayerPlains(long paramLong, GenLayer paramGenLayer)
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
        if (a(57) == 0) {
          if (k == BiomeBase.PLAINS.id) {
            arrayOfInt2[(j + i * paramInt3)] = (BiomeBase.PLAINS.id + 128);
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else {
          arrayOfInt2[(j + i * paramInt3)] = k;
        }
      }
    }
    
    return arrayOfInt2;
  }
}
