package net.minecraft.server;


public class EntityAIBodyControl
{
  private EntityLiving entity;
  
  private int b;
  
  private float c;
  
  public EntityAIBodyControl(EntityLiving paramEntityLiving)
  {
    this.entity = paramEntityLiving;
  }
  
  public void a() {
    double d1 = this.entity.locX - this.entity.lastX;
    double d2 = this.entity.locZ - this.entity.lastZ;
    
    if (d1 * d1 + d2 * d2 > 2.500000277905201E-7D)
    {
      this.entity.aM = this.entity.yaw;
      this.entity.aO = a(this.entity.aM, this.entity.aO, 75.0F);
      this.c = this.entity.aO;
      this.b = 0;
      return;
    }
    

    float f = 75.0F;
    if (Math.abs(this.entity.aO - this.c) > 15.0F) {
      this.b = 0;
      this.c = this.entity.aO;
    } else {
      this.b += 1;
      int i = 10;
      if (this.b > 10) { f = Math.max(1.0F - (this.b - 10) / 10.0F, 0.0F) * 75.0F;
      }
    }
    this.entity.aM = a(this.entity.aO, this.entity.aM, f);
  }
  
  private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = MathHelper.g(paramFloat1 - paramFloat2);
    if (f < -paramFloat3) f = -paramFloat3;
    if (f >= paramFloat3) f = paramFloat3;
    return paramFloat1 - f;
  }
}
