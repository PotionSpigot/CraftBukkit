package net.minecraft.server;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
  private int[] d = new int['Ȁ'];
  public double a;
  public double b;
  public double c;
  
  public NoiseGeneratorPerlin() {
    this(new Random());
  }
  
  public NoiseGeneratorPerlin(Random paramRandom) {
    this.a = (paramRandom.nextDouble() * 256.0D);
    this.b = (paramRandom.nextDouble() * 256.0D);
    this.c = (paramRandom.nextDouble() * 256.0D);
    for (int j = 0; j < 256; j++) {
      this.d[j] = j;
    }
    
    for (j = 0; j < 256; j++) {
      int k = paramRandom.nextInt(256 - j) + j;
      int m = this.d[j];
      this.d[j] = this.d[k];
      this.d[k] = m;
      
      this.d[(j + 256)] = this.d[j];
    }
  }
  




































  public final double b(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return paramDouble2 + paramDouble1 * (paramDouble3 - paramDouble2);
  }
  
  private static final double[] e = { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
  private static final double[] f = { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
  private static final double[] g = { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
  private static final double[] h = { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
  private static final double[] i = { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
  
  public final double a(int paramInt, double paramDouble1, double paramDouble2) {
    int j = paramInt & 0xF;
    return h[j] * paramDouble1 + i[j] * paramDouble2;
  }
  
  public final double a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
    int j = paramInt & 0xF;
    return e[j] * paramDouble1 + f[j] * paramDouble2 + g[j] * paramDouble3;
  }
  

  public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, int paramInt3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
  {
    double d6;
    
    int i6;
    
    int i7;
    
    double d7;
    if (paramInt2 == 1) {
      j = 0;int k = 0;int m = 0;n = 0;
      double d1 = 0.0D;double d2 = 0.0D;
      i1 = 0;
      double d3 = 1.0D / paramDouble7;
      for (int i2 = 0; i2 < paramInt1; i2++) {
        d4 = paramDouble1 + i2 * paramDouble4 + this.a;
        int i3 = (int)d4;
        if (d4 < i3) i3--;
        int i4 = i3 & 0xFF;
        d4 -= i3;
        d5 = d4 * d4 * d4 * (d4 * (d4 * 6.0D - 15.0D) + 10.0D);
        
        for (i5 = 0; i5 < paramInt3; i5++) {
          d6 = paramDouble3 + i5 * paramDouble6 + this.c;
          i6 = (int)d6;
          if (d6 < i6) i6--;
          i7 = i6 & 0xFF;
          d6 -= i6;
          d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
          
          j = this.d[i4] + 0;
          k = this.d[j] + i7;
          m = this.d[(i4 + 1)] + 0;
          n = this.d[m] + i7;
          d1 = b(d5, a(this.d[k], d4, d6), a(this.d[n], d4 - 1.0D, 0.0D, d6));
          d2 = b(d5, a(this.d[(k + 1)], d4, 0.0D, d6 - 1.0D), a(this.d[(n + 1)], d4 - 1.0D, 0.0D, d6 - 1.0D));
          
          double d8 = b(d7, d1, d2);
          
          paramArrayOfDouble[(i1++)] += d8 * d3;
        }
      }
      return;
    }
    int j = 0;
    double d9 = 1.0D / paramDouble7;
    int n = -1;
    int i8 = 0;int i9 = 0;int i10 = 0;int i11 = 0;int i1 = 0;int i12 = 0;
    double d10 = 0.0D;double d4 = 0.0D;double d11 = 0.0D;double d5 = 0.0D;
    
    for (int i5 = 0; i5 < paramInt1; i5++) {
      d6 = paramDouble1 + i5 * paramDouble4 + this.a;
      i6 = (int)d6;
      if (d6 < i6) i6--;
      i7 = i6 & 0xFF;
      d6 -= i6;
      d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
      
      for (int i13 = 0; i13 < paramInt3; i13++) {
        double d12 = paramDouble3 + i13 * paramDouble6 + this.c;
        int i14 = (int)d12;
        if (d12 < i14) i14--;
        int i15 = i14 & 0xFF;
        d12 -= i14;
        double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0D - 15.0D) + 10.0D);
        
        for (int i16 = 0; i16 < paramInt2; i16++) {
          double d14 = paramDouble2 + i16 * paramDouble5 + this.b;
          int i17 = (int)d14;
          if (d14 < i17) i17--;
          int i18 = i17 & 0xFF;
          d14 -= i17;
          double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0D - 15.0D) + 10.0D);
          
          if ((i16 == 0) || (i18 != n)) {
            n = i18;
            i8 = this.d[i7] + i18;
            i9 = this.d[i8] + i15;
            i10 = this.d[(i8 + 1)] + i15;
            i11 = this.d[(i7 + 1)] + i18;
            i1 = this.d[i11] + i15;
            i12 = this.d[(i11 + 1)] + i15;
            d10 = b(d7, a(this.d[i9], d6, d14, d12), a(this.d[i1], d6 - 1.0D, d14, d12));
            d4 = b(d7, a(this.d[i10], d6, d14 - 1.0D, d12), a(this.d[i12], d6 - 1.0D, d14 - 1.0D, d12));
            d11 = b(d7, a(this.d[(i9 + 1)], d6, d14, d12 - 1.0D), a(this.d[(i1 + 1)], d6 - 1.0D, d14, d12 - 1.0D));
            d5 = b(d7, a(this.d[(i10 + 1)], d6, d14 - 1.0D, d12 - 1.0D), a(this.d[(i12 + 1)], d6 - 1.0D, d14 - 1.0D, d12 - 1.0D));
          }
          
          double d16 = b(d15, d10, d4);
          double d17 = b(d15, d11, d5);
          double d18 = b(d13, d16, d17);
          
          paramArrayOfDouble[(j++)] += d18 * d9;
        }
      }
    }
  }
}
