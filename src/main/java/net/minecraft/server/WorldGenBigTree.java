package net.minecraft.server;

import java.util.Random;

public class WorldGenBigTree extends WorldGenTreeAbstract
{
  static final byte[] a = { 2, 0, 0, 1, 2, 1 };
  Random b = new Random();
  World world;
  int[] d = { 0, 0, 0 };
  int e;
  int f;
  double g = 0.618D;
  double h = 1.0D;
  double i = 0.381D;
  double j = 1.0D;
  double k = 1.0D;
  int l = 1;
  int m = 12;
  int n = 4;
  int[][] o;
  
  public WorldGenBigTree(boolean flag) {
    super(flag);
  }
  
  void a() {
    this.f = ((int)(this.e * this.g));
    if (this.f >= this.e) {
      this.f = (this.e - 1);
    }
    
    int i = (int)(1.382D + Math.pow(this.k * this.e / 13.0D, 2.0D));
    
    if (i < 1) {
      i = 1;
    }
    
    int[][] aint = new int[i * this.e][4];
    int j = this.d[1] + this.e - this.n;
    int k = 1;
    int l = this.d[1] + this.f;
    int i1 = j - this.d[1];
    
    aint[0][0] = this.d[0];
    aint[0][1] = j;
    aint[0][2] = this.d[2];
    aint[0][3] = l;
    j--;
    
    while (i1 >= 0) {
      int j1 = 0;
      float f = a(i1);
      
      if (f < 0.0F) {
        j--;
        i1--;
      } else {
        for (double d0 = 0.5D; j1 < i; j1++) {
          double d1 = this.j * f * (this.b.nextFloat() + 0.328D);
          double d2 = this.b.nextFloat() * 2.0D * 3.14159D;
          int k1 = MathHelper.floor(d1 * Math.sin(d2) + this.d[0] + d0);
          int l1 = MathHelper.floor(d1 * Math.cos(d2) + this.d[2] + d0);
          int[] aint1 = { k1, j, l1 };
          int[] aint2 = { k1, j + this.n, l1 };
          
          if (a(aint1, aint2) == -1) {
            int[] aint3 = { this.d[0], this.d[1], this.d[2] };
            double d3 = Math.sqrt(Math.pow(Math.abs(this.d[0] - aint1[0]), 2.0D) + Math.pow(Math.abs(this.d[2] - aint1[2]), 2.0D));
            double d4 = d3 * this.i;
            
            if (aint1[1] - d4 > l) {
              aint3[1] = l;
            } else {
              aint3[1] = ((int)(aint1[1] - d4));
            }
            
            if (a(aint3, aint1) == -1) {
              aint[k][0] = k1;
              aint[k][1] = j;
              aint[k][2] = l1;
              aint[k][3] = aint3[1];
              k++;
            }
          }
        }
        
        j--;
        i1--;
      }
    }
    
    this.o = new int[k][4];
    System.arraycopy(aint, 0, this.o, 0, k);
  }
  
  void a(int i, int j, int k, float f, byte b0, Block block) {
    int l = (int)(f + 0.618D);
    byte b1 = a[b0];
    byte b2 = a[(b0 + 3)];
    int[] aint = { i, j, k };
    int[] aint1 = { 0, 0, 0 };
    int i1 = -l;
    int j1 = -l;
    
    for (aint1[b0] = aint[b0]; i1 <= l; i1++) {
      aint[b1] += i1;
      j1 = -l;
      
      while (j1 <= l) {
        double d0 = Math.pow(Math.abs(i1) + 0.5D, 2.0D) + Math.pow(Math.abs(j1) + 0.5D, 2.0D);
        
        if (d0 > f * f) {
          j1++;
        } else {
          aint[b2] += j1;
          Block block1 = this.world.getType(aint1[0], aint1[1], aint1[2]);
          
          if ((block1.getMaterial() != Material.AIR) && (block1.getMaterial() != Material.LEAVES)) {
            j1++;
          } else {
            setTypeAndData(this.world, aint1[0], aint1[1], aint1[2], block, 0);
            j1++;
          }
        }
      }
    }
  }
  
  float a(int i) {
    if (i < this.e * 0.3D) {
      return -1.618F;
    }
    float f = this.e / 2.0F;
    float f1 = this.e / 2.0F - i;
    float f2;
    float f2;
    if (f1 == 0.0F) {
      f2 = f; } else { float f2;
      if (Math.abs(f1) >= f) {
        f2 = 0.0F;
      } else {
        f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
      }
    }
    f2 *= 0.5F;
    return f2;
  }
  
  float b(int i)
  {
    return (i >= 0) && (i < this.n) ? 2.0F : (i != 0) && (i != this.n - 1) ? 3.0F : -1.0F;
  }
  
  void a(int i, int j, int k) {
    int l = j;
    
    for (int i1 = j + this.n; l < i1; l++) {
      float f = b(l - j);
      
      a(i, l, k, f, (byte)1, Blocks.LEAVES);
    }
  }
  
  void a(int[] aint, int[] aint1, Block block) {
    int[] aint2 = { 0, 0, 0 };
    byte b0 = 0;
    


    for (byte b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
      aint1[b0] -= aint[b0];
      if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
        b1 = b0;
      }
    }
    
    if (aint2[b1] != 0) {
      byte b2 = a[b1];
      byte b3 = a[(b1 + 3)];
      byte b4;
      byte b4;
      if (aint2[b1] > 0) {
        b4 = 1;
      } else {
        b4 = -1;
      }
      
      double d0 = aint2[b2] / aint2[b1];
      double d1 = aint2[b3] / aint2[b1];
      int[] aint3 = { 0, 0, 0 };
      int i = 0;
      
      for (int j = aint2[b1] + b4; i != j; i += b4) {
        aint3[b1] = MathHelper.floor(aint[b1] + i + 0.5D);
        aint3[b2] = MathHelper.floor(aint[b2] + i * d0 + 0.5D);
        aint3[b3] = MathHelper.floor(aint[b3] + i * d1 + 0.5D);
        byte b5 = 0;
        int k = Math.abs(aint3[0] - aint[0]);
        int l = Math.abs(aint3[2] - aint[2]);
        int i1 = Math.max(k, l);
        
        if (i1 > 0) {
          if (k == i1) {
            b5 = 4;
          } else if (l == i1) {
            b5 = 8;
          }
        }
        
        setTypeAndData(this.world, aint3[0], aint3[1], aint3[2], block, b5);
      }
    }
  }
  
  void b() {
    int i = 0;
    
    for (int j = this.o.length; i < j; i++) {
      int k = this.o[i][0];
      int l = this.o[i][1];
      int i1 = this.o[i][2];
      
      a(k, l, i1);
    }
  }
  
  boolean c(int i) {
    return i >= this.e * 0.2D;
  }
  
  void c() {
    int i = this.d[0];
    int j = this.d[1];
    int k = this.d[1] + this.f;
    int l = this.d[2];
    int[] aint = { i, j, l };
    int[] aint1 = { i, k, l };
    
    a(aint, aint1, Blocks.LOG);
    if (this.l == 2) {
      aint[0] += 1;
      aint1[0] += 1;
      a(aint, aint1, Blocks.LOG);
      aint[2] += 1;
      aint1[2] += 1;
      a(aint, aint1, Blocks.LOG);
      aint[0] += -1;
      aint1[0] += -1;
      a(aint, aint1, Blocks.LOG);
    }
  }
  
  void d() {
    int i = 0;
    int j = this.o.length;
    
    for (int[] aint = { this.d[0], this.d[1], this.d[2] }; i < j; i++) {
      int[] aint1 = this.o[i];
      int[] aint2 = { aint1[0], aint1[1], aint1[2] };
      
      aint[1] = aint1[3];
      int k = aint[1] - this.d[1];
      
      if (c(k)) {
        a(aint, aint2, Blocks.LOG);
      }
    }
  }
  
  int a(int[] aint, int[] aint1) {
    int[] aint2 = { 0, 0, 0 };
    byte b0 = 0;
    


    for (byte b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
      aint1[b0] -= aint[b0];
      if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
        b1 = b0;
      }
    }
    
    if (aint2[b1] == 0) {
      return -1;
    }
    byte b2 = a[b1];
    byte b3 = a[(b1 + 3)];
    byte b4;
    byte b4;
    if (aint2[b1] > 0) {
      b4 = 1;
    } else {
      b4 = -1;
    }
    
    double d0 = aint2[b2] / aint2[b1];
    double d1 = aint2[b3] / aint2[b1];
    int[] aint3 = { 0, 0, 0 };
    int i = 0;
    


    for (int j = aint2[b1] + b4; i != j; i += b4) {
      aint[b1] += i;
      aint3[b2] = MathHelper.floor(aint[b2] + i * d0);
      aint3[b3] = MathHelper.floor(aint[b3] + i * d1);
      Block block = this.world.getType(aint3[0], aint3[1], aint3[2]);
      
      if ((!a(block)) || (aint[1] >= 256)) {
        break;
      }
    }
    
    return i == j ? -1 : Math.abs(i);
  }
  
  boolean e()
  {
    int[] aint = { this.d[0], this.d[1], this.d[2] };
    int[] aint1 = { this.d[0], this.d[1] + this.e - 1, this.d[2] };
    Block block = this.world.getType(this.d[0], this.d[1] - 1, this.d[2]);
    
    if ((block != Blocks.DIRT) && (block != Blocks.GRASS) && (block != Blocks.SOIL)) {
      return false;
    }
    int i = a(aint, aint1);
    
    if (i == -1)
      return true;
    if (i < 6) {
      return false;
    }
    this.e = i;
    return true;
  }
  

  public void a(double d0, double d1, double d2)
  {
    this.m = ((int)(d0 * 12.0D));
    if (d0 > 0.5D) {
      this.n = 5;
    }
    
    this.j = d1;
    this.k = d2;
  }
  
  public boolean generate(World world, Random random, int i, int j, int k) {
    this.world = world;
    long l = random.nextLong();
    
    this.b.setSeed(l);
    this.d[0] = i;
    this.d[1] = j;
    this.d[2] = k;
    if (this.e == 0) {
      this.e = (5 + this.b.nextInt(this.m));
    }
    
    if (!e()) {
      return false;
    }
    a();
    b();
    c();
    d();
    return true;
  }
}
