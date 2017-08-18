package net.minecraft.server;

import java.util.Random;

public class NoiseGenerator3Handler {
  private static int[][] e = { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
  

























  public static final double a = Math.sqrt(3.0D);
  
  private int[] f = new int['Ȁ'];
  public double b;
  public double c;
  public double d;
  
  public NoiseGenerator3Handler() {
    this(new Random());
  }
  
  public NoiseGenerator3Handler(Random paramRandom) {
    this.b = (paramRandom.nextDouble() * 256.0D);
    this.c = (paramRandom.nextDouble() * 256.0D);
    this.d = (paramRandom.nextDouble() * 256.0D);
    for (int i = 0; i < 256; i++) {
      this.f[i] = i;
    }
    
    for (i = 0; i < 256; i++) {
      int j = paramRandom.nextInt(256 - i) + i;
      int k = this.f[i];
      this.f[i] = this.f[j];
      this.f[j] = k;
      
      this.f[(i + 256)] = this.f[i];
    }
  }
  
  private static int a(double paramDouble)
  {
    return paramDouble > 0.0D ? (int)paramDouble : (int)paramDouble - 1;
  }
  
  private static double a(int[] paramArrayOfInt, double paramDouble1, double paramDouble2) {
    return paramArrayOfInt[0] * paramDouble1 + paramArrayOfInt[1] * paramDouble2;
  }
  






  public double a(double paramDouble1, double paramDouble2)
  {
    double d1 = 0.5D * (a - 1.0D);
    double d2 = (paramDouble1 + paramDouble2) * d1;
    int i = a(paramDouble1 + d2);
    int j = a(paramDouble2 + d2);
    double d3 = (3.0D - a) / 6.0D;
    double d4 = (i + j) * d3;
    double d5 = i - d4;
    double d6 = j - d4;
    double d7 = paramDouble1 - d5;
    double d8 = paramDouble2 - d6;
    
    int k;
    int m;
    if (d7 > d8) {
      k = 1;
      m = 0;
    }
    else {
      k = 0;
      m = 1;
    }
    


    double d9 = d7 - k + d3;
    double d10 = d8 - m + d3;
    double d11 = d7 - 1.0D + 2.0D * d3;
    double d12 = d8 - 1.0D + 2.0D * d3;
    
    int n = i & 0xFF;
    int i1 = j & 0xFF;
    int i2 = this.f[(n + this.f[i1])] % 12;
    int i3 = this.f[(n + k + this.f[(i1 + m)])] % 12;
    int i4 = this.f[(n + 1 + this.f[(i1 + 1)])] % 12;
    
    double d13 = 0.5D - d7 * d7 - d8 * d8;
    double d14; if (d13 < 0.0D) { d14 = 0.0D;
    } else {
      d13 *= d13;
      d14 = d13 * d13 * a(e[i2], d7, d8);
    }
    double d15 = 0.5D - d9 * d9 - d10 * d10;
    double d16; if (d15 < 0.0D) { d16 = 0.0D;
    } else {
      d15 *= d15;
      d16 = d15 * d15 * a(e[i3], d9, d10);
    }
    double d17 = 0.5D - d11 * d11 - d12 * d12;
    double d18; if (d17 < 0.0D) { d18 = 0.0D;
    } else {
      d17 *= d17;
      d18 = d17 * d17 * a(e[i4], d11, d12);
    }
    

    return 70.0D * (d14 + d16 + d18);
  }
  

























































































































  private static final double g = 0.5D * (a - 1.0D);
  private static final double h = (3.0D - a) / 6.0D;
  
  public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) {
    int i = 0;
    for (int j = 0; j < paramInt2; j++) {
      double d1 = (paramDouble2 + j) * paramDouble4 + this.c;
      for (int k = 0; k < paramInt1; k++) {
        double d2 = (paramDouble1 + k) * paramDouble3 + this.b;
        

        double d3 = (d2 + d1) * g;
        int m = a(d2 + d3);
        int n = a(d1 + d3);
        double d4 = (m + n) * h;
        double d5 = m - d4;
        double d6 = n - d4;
        double d7 = d2 - d5;
        double d8 = d1 - d6;
        
        int i1;
        int i2;
        if (d7 > d8) {
          i1 = 1;
          i2 = 0;
        }
        else {
          i1 = 0;
          i2 = 1;
        }
        


        double d9 = d7 - i1 + h;
        double d10 = d8 - i2 + h;
        double d11 = d7 - 1.0D + 2.0D * h;
        double d12 = d8 - 1.0D + 2.0D * h;
        
        int i3 = m & 0xFF;
        int i4 = n & 0xFF;
        int i5 = this.f[(i3 + this.f[i4])] % 12;
        int i6 = this.f[(i3 + i1 + this.f[(i4 + i2)])] % 12;
        int i7 = this.f[(i3 + 1 + this.f[(i4 + 1)])] % 12;
        
        double d13 = 0.5D - d7 * d7 - d8 * d8;
        double d14; if (d13 < 0.0D) { d14 = 0.0D;
        } else {
          d13 *= d13;
          d14 = d13 * d13 * a(e[i5], d7, d8);
        }
        double d15 = 0.5D - d9 * d9 - d10 * d10;
        double d16; if (d15 < 0.0D) { d16 = 0.0D;
        } else {
          d15 *= d15;
          d16 = d15 * d15 * a(e[i6], d9, d10);
        }
        double d17 = 0.5D - d11 * d11 - d12 * d12;
        double d18; if (d17 < 0.0D) { d18 = 0.0D;
        } else {
          d17 *= d17;
          d18 = d17 * d17 * a(e[i7], d11, d12);
        }
        

        paramArrayOfDouble[(i++)] += 70.0D * (d14 + d16 + d18) * paramDouble5;
      }
    }
  }
}
