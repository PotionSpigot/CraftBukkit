package net.minecraft.server;

import java.util.Random;




























































































































































































































class Location2D
{
  double a;
  double b;
  
  Location2D() {}
  
  Location2D(double paramDouble1, double paramDouble2)
  {
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  




  double a(Location2D paramLocation2D)
  {
    double d1 = this.a - paramLocation2D.a;
    double d2 = this.b - paramLocation2D.b;
    
    return Math.sqrt(d1 * d1 + d2 * d2);
  }
  
  void a() {
    double d = b();
    this.a /= d;
    this.b /= d;
  }
  
  float b() {
    return MathHelper.sqrt(this.a * this.a + this.b * this.b);
  }
  
  public void b(Location2D paramLocation2D) {
    this.a -= paramLocation2D.a;
    this.b -= paramLocation2D.b;
  }
  
  public boolean a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    boolean bool = false;
    
    if (this.a < paramDouble1) {
      this.a = paramDouble1;
      bool = true;
    } else if (this.a > paramDouble3) {
      this.a = paramDouble3;
      bool = true;
    }
    
    if (this.b < paramDouble2) {
      this.b = paramDouble2;
      bool = true;
    } else if (this.b > paramDouble4) {
      this.b = paramDouble4;
      bool = true;
    }
    
    return bool;
  }
  
  public int a(World paramWorld) {
    int i = MathHelper.floor(this.a);
    int j = MathHelper.floor(this.b);
    
    for (int k = 256; k > 0; k--) {
      if (paramWorld.getType(i, k, j).getMaterial() != Material.AIR) {
        return k + 1;
      }
    }
    
    return 257;
  }
  
  public boolean b(World paramWorld) {
    int i = MathHelper.floor(this.a);
    int j = MathHelper.floor(this.b);
    
    int k = 256; if (k > 0) {
      Material localMaterial = paramWorld.getType(i, k, j).getMaterial();
      
      return (!localMaterial.isLiquid()) && (localMaterial != Material.FIRE);
    }
    
    return false;
  }
  
  public void a(Random paramRandom, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.a = MathHelper.a(paramRandom, paramDouble1, paramDouble3);
    this.b = MathHelper.a(paramRandom, paramDouble2, paramDouble4);
  }
}
