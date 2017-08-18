package net.minecraft.server;

import java.util.Random;

public class TileEntityEnchantTable extends TileEntity { public int a;
  public float i;
  public float j;
  public float k;
  public float l;
  public float m;
  public float n;
  public float o;
  public float p;
  public float q; private static Random r = new Random();
  private String s;
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    if (b()) paramNBTTagCompound.setString("CustomName", this.s);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    if (paramNBTTagCompound.hasKeyOfType("CustomName", 8)) this.s = paramNBTTagCompound.getString("CustomName");
  }
  
  public void h()
  {
    super.h();
    this.n = this.m;
    this.p = this.o;
    
    EntityHuman localEntityHuman = this.world.findNearbyPlayer(this.x + 0.5F, this.y + 0.5F, this.z + 0.5F, 3.0D);
    if (localEntityHuman != null) {
      double d1 = localEntityHuman.locX - (this.x + 0.5F);
      double d2 = localEntityHuman.locZ - (this.z + 0.5F);
      
      this.q = ((float)Math.atan2(d2, d1));
      
      this.m += 0.1F;
      
      if ((this.m < 0.5F) || (r.nextInt(40) == 0)) {
        float f1 = this.k;
        do {
          this.k += r.nextInt(4) - r.nextInt(4);
        } while (f1 == this.k);
      }
    }
    else {
      this.q += 0.02F;
      this.m -= 0.1F;
    }
    
    while (this.o >= 3.1415927F)
      this.o -= 6.2831855F;
    while (this.o < -3.1415927F)
      this.o += 6.2831855F;
    while (this.q >= 3.1415927F)
      this.q -= 6.2831855F;
    while (this.q < -3.1415927F)
      this.q += 6.2831855F;
    float f2 = this.q - this.o;
    while (f2 >= 3.1415927F)
      f2 -= 6.2831855F;
    while (f2 < -3.1415927F) {
      f2 += 6.2831855F;
    }
    this.o += f2 * 0.4F;
    
    if (this.m < 0.0F) this.m = 0.0F;
    if (this.m > 1.0F) { this.m = 1.0F;
    }
    this.a += 1;
    this.j = this.i;
    
    float f3 = (this.k - this.i) * 0.4F;
    float f4 = 0.2F;
    if (f3 < -f4) f3 = -f4;
    if (f3 > f4) f3 = f4;
    this.l += (f3 - this.l) * 0.9F;
    
    this.i += this.l;
  }
  
  public String a() {
    return b() ? this.s : "container.enchant";
  }
  
  public boolean b() {
    return (this.s != null) && (this.s.length() > 0);
  }
  
  public void a(String paramString) {
    this.s = paramString;
  }
}
