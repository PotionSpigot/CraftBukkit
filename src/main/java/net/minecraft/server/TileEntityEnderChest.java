package net.minecraft.server;

import java.util.Random;

public class TileEntityEnderChest extends TileEntity
{
  public float a;
  public float i;
  public int j;
  private int k;
  
  public void h() {
    super.h();
    if (++this.k % 4 == 0) {
      this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
    }
    
    this.i = this.a;
  }
  








































  public boolean c(int i, int j)
  {
    if (i == 1) {
      this.j = j;
      return true;
    }
    return super.c(i, j);
  }
  
  public void s()
  {
    u();
    super.s();
  }
  
  public void a() {
    this.j += 1;
    this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
    



    if ((this.j > 0) && (this.a == 0.0F)) {
      double d1 = this.x + 0.5D;
      
      double d0 = this.z + 0.5D;
      this.world.makeSound(d1, this.y + 0.5D, d0, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
  }
  
  public void b()
  {
    this.j -= 1;
    this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
    

    float f = 0.1F;
    

    if (((this.j == 0) && (this.a == 0.0F)) || ((this.j > 0) && (this.a < 1.0F))) {
      float f1 = this.a;
      double d0 = this.x + 0.5D;
      double d2 = this.z + 0.5D;
      
      this.world.makeSound(d0, this.y + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      
      if (this.a < 0.0F) {
        this.a = 0.0F;
      }
    }
  }
  
  public boolean a(EntityHuman entityhuman)
  {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
}
