package net.minecraft.server;



public class GenLayerZoom
  extends GenLayer
{
  public GenLayerZoom(long paramLong, GenLayer paramGenLayer)
  {
    super(paramLong);
    this.a = paramGenLayer;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 >> 1;
    int j = paramInt2 >> 1;
    int k = (paramInt3 >> 1) + 2;
    int m = (paramInt4 >> 1) + 2;
    int[] arrayOfInt1 = this.a.a(i, j, k, m);
    
    int n = k - 1 << 1;
    int i1 = m - 1 << 1;
    
    int[] arrayOfInt2 = IntCache.a(n * i1);
    
    for (int i2 = 0; i2 < m - 1; i2++) {
      i3 = (i2 << 1) * n;
      
      int i4 = 0;
      int i5 = arrayOfInt1[(i4 + 0 + (i2 + 0) * k)];
      int i6 = arrayOfInt1[(i4 + 0 + (i2 + 1) * k)];
      for (; i4 < k - 1; i4++) {
        a(i4 + i << 1, i2 + j << 1);
        
        int i7 = arrayOfInt1[(i4 + 1 + (i2 + 0) * k)];
        int i8 = arrayOfInt1[(i4 + 1 + (i2 + 1) * k)];
        
        arrayOfInt2[i3] = i5;
        arrayOfInt2[(i3++ + n)] = a(new int[] { i5, i6 });
        arrayOfInt2[i3] = a(new int[] { i5, i7 });
        arrayOfInt2[(i3++ + n)] = b(i5, i7, i6, i8);
        
        i5 = i7;
        i6 = i8;
      }
    }
    
    int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
    for (int i3 = 0; i3 < paramInt4; i3++) {
      System.arraycopy(arrayOfInt2, (i3 + (paramInt2 & 0x1)) * n + (paramInt1 & 0x1), arrayOfInt3, i3 * paramInt3, paramInt3);
    }
    return arrayOfInt3;
  }
  
  public static GenLayer b(long paramLong, GenLayer paramGenLayer, int paramInt) {
    Object localObject = paramGenLayer;
    for (int i = 0; i < paramInt; i++) {
      localObject = new GenLayerZoom(paramLong + i, (GenLayer)localObject);
    }
    return (GenLayer)localObject;
  }
}
