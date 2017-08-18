package net.minecraft.server;




public class GenLayerZoomVoronoi
  extends GenLayer
{
  public GenLayerZoomVoronoi(long paramLong, GenLayer paramGenLayer)
  {
    super(paramLong);
    this.a = paramGenLayer;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt1 -= 2;
    paramInt2 -= 2;
    int i = paramInt1 >> 2;
    int j = paramInt2 >> 2;
    int k = (paramInt3 >> 2) + 2;
    int m = (paramInt4 >> 2) + 2;
    int[] arrayOfInt1 = this.a.a(i, j, k, m);
    
    int n = k - 1 << 2;
    int i1 = m - 1 << 2;
    
    int[] arrayOfInt2 = IntCache.a(n * i1);
    for (int i2 = 0; i2 < m - 1; i2++)
    {
      i3 = 0;
      int i4 = arrayOfInt1[(i3 + 0 + (i2 + 0) * k)];
      int i5 = arrayOfInt1[(i3 + 0 + (i2 + 1) * k)];
      for (; i3 < k - 1; i3++) {
        double d1 = 3.6D;
        a(i3 + i << 2, i2 + j << 2);
        double d2 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
        double d3 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
        
        a(i3 + i + 1 << 2, i2 + j << 2);
        double d4 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double d5 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
        
        a(i3 + i << 2, i2 + j + 1 << 2);
        double d6 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
        double d7 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        
        a(i3 + i + 1 << 2, i2 + j + 1 << 2);
        double d8 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double d9 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        
        int i6 = arrayOfInt1[(i3 + 1 + (i2 + 0) * k)] & 0xFF;
        int i7 = arrayOfInt1[(i3 + 1 + (i2 + 1) * k)] & 0xFF;
        
        for (int i8 = 0; i8 < 4; i8++) {
          int i9 = ((i2 << 2) + i8) * n + (i3 << 2);
          for (int i10 = 0; i10 < 4; i10++) {
            double d10 = (i8 - d3) * (i8 - d3) + (i10 - d2) * (i10 - d2);
            double d11 = (i8 - d5) * (i8 - d5) + (i10 - d4) * (i10 - d4);
            double d12 = (i8 - d7) * (i8 - d7) + (i10 - d6) * (i10 - d6);
            double d13 = (i8 - d9) * (i8 - d9) + (i10 - d8) * (i10 - d8);
            
            if ((d10 < d11) && (d10 < d12) && (d10 < d13)) {
              arrayOfInt2[(i9++)] = i4;
            } else if ((d11 < d10) && (d11 < d12) && (d11 < d13)) {
              arrayOfInt2[(i9++)] = i6;
            } else if ((d12 < d10) && (d12 < d11) && (d12 < d13)) {
              arrayOfInt2[(i9++)] = i5;
            } else {
              arrayOfInt2[(i9++)] = i7;
            }
          }
        }
        
        i4 = i6;
        i5 = i7;
      }
    }
    
    int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
    for (int i3 = 0; i3 < paramInt4; i3++) {
      System.arraycopy(arrayOfInt2, (i3 + (paramInt2 & 0x3)) * n + (paramInt1 & 0x3), arrayOfInt3, i3 * paramInt3, paramInt3);
    }
    return arrayOfInt3;
  }
}
