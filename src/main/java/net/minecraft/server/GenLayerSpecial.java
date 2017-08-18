package net.minecraft.server;


public class GenLayerSpecial
  extends GenLayer
{
  private final EnumGenLayerSpecial c;
  

  public GenLayerSpecial(long paramLong, GenLayer paramGenLayer, EnumGenLayerSpecial paramEnumGenLayerSpecial)
  {
    super(paramLong);
    this.a = paramGenLayer;
    this.c = paramEnumGenLayerSpecial;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (GenLayerJumpTable.a[this.c.ordinal()]) {
    case 1: 
    default: 
      return c(paramInt1, paramInt2, paramInt3, paramInt4);
    case 2: 
      return d(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return e(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  private int[] c(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 - 1;
    int j = paramInt2 - 1;
    int k = 1 + paramInt3 + 1;
    int m = 1 + paramInt4 + 1;
    int[] arrayOfInt1 = this.a.a(i, j, k, m);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    
    for (int n = 0; n < paramInt4; n++) {
      for (int i1 = 0; i1 < paramInt3; i1++) {
        a(i1 + paramInt1, n + paramInt2);
        
        int i2 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
        
        if (i2 == 1) {
          int i3 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * k)];
          int i4 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * k)];
          int i5 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * k)];
          int i6 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * k)];
          
          int i7 = (i3 == 3) || (i4 == 3) || (i5 == 3) || (i6 == 3) ? 1 : 0;
          int i8 = (i3 == 4) || (i4 == 4) || (i5 == 4) || (i6 == 4) ? 1 : 0;
          if ((i7 != 0) || (i8 != 0)) {
            i2 = 2;
          }
        }
        
        arrayOfInt2[(i1 + n * paramInt3)] = i2;
      }
    }
    
    return arrayOfInt2;
  }
  
  private int[] d(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt1 - 1;
    int j = paramInt2 - 1;
    int k = 1 + paramInt3 + 1;
    int m = 1 + paramInt4 + 1;
    int[] arrayOfInt1 = this.a.a(i, j, k, m);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    
    for (int n = 0; n < paramInt4; n++) {
      for (int i1 = 0; i1 < paramInt3; i1++) {
        int i2 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
        
        if (i2 == 4) {
          int i3 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * k)];
          int i4 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * k)];
          int i5 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * k)];
          int i6 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * k)];
          
          int i7 = (i3 == 2) || (i4 == 2) || (i5 == 2) || (i6 == 2) ? 1 : 0;
          int i8 = (i3 == 1) || (i4 == 1) || (i5 == 1) || (i6 == 1) ? 1 : 0;
          
          if ((i8 != 0) || (i7 != 0)) {
            i2 = 3;
          }
        }
        
        arrayOfInt2[(i1 + n * paramInt3)] = i2;
      }
    }
    
    return arrayOfInt2;
  }
  
  private int[] e(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    
    for (int i = 0; i < paramInt4; i++) {
      for (int j = 0; j < paramInt3; j++) {
        a(j + paramInt1, i + paramInt2);
        
        int k = arrayOfInt1[(j + i * paramInt3)];
        
        if ((k != 0) && (a(13) == 0)) {
          k |= 1 + a(15) << 8 & 0xF00;
        }
        
        arrayOfInt2[(j + i * paramInt3)] = k;
      }
    }
    
    return arrayOfInt2;
  }
}
