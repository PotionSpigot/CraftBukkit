package net.minecraft.server;

import java.util.Random;







public class EntityBlaze
  extends EntityMonster
{
  private float bp = 0.5F;
  
  private int bq;
  private int br;
  
  public EntityBlaze(World paramWorld)
  {
    super(paramWorld);
    
    this.fireProof = true;
    this.b = 10;
  }
  
  protected void aD()
  {
    super.aD();
    getAttributeInstance(GenericAttributes.e).setValue(6.0D);
  }
  
  protected void c()
  {
    super.c();
    
    this.datawatcher.a(16, new Byte((byte)0));
  }
  
  protected String t()
  {
    return "mob.blaze.breathe";
  }
  
  protected String aT()
  {
    return "mob.blaze.hit";
  }
  
  protected String aU()
  {
    return "mob.blaze.death";
  }
  





  public float d(float paramFloat)
  {
    return 1.0F;
  }
  
  public void e()
  {
    if (!this.world.isStatic)
    {
      if (L()) {
        damageEntity(DamageSource.DROWN, 1.0F);
      }
      
      this.bq -= 1;
      if (this.bq <= 0) {
        this.bq = 100;
        this.bp = (0.5F + (float)this.random.nextGaussian() * 3.0F);
      }
      
      if ((bT() != null) && (bT().locY + bT().getHeadHeight() > this.locY + getHeadHeight() + this.bp)) {
        this.motY += (0.30000001192092896D - this.motY) * 0.30000001192092896D;
      }
    }
    

    if (this.random.nextInt(24) == 0) {
      this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.fire", 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
    }
    

    if ((!this.onGround) && (this.motY < 0.0D)) {
      this.motY *= 0.6D;
    }
    
    for (int i = 0; i < 2; i++) {
      this.world.addParticle("largesmoke", this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
    }
    
    super.e();
  }
  
  protected void a(Entity paramEntity, float paramFloat)
  {
    if ((this.attackTicks <= 0) && (paramFloat < 2.0F) && (paramEntity.boundingBox.e > this.boundingBox.b) && (paramEntity.boundingBox.b < this.boundingBox.e)) {
      this.attackTicks = 20;
      n(paramEntity);
    } else if (paramFloat < 30.0F) {
      double d1 = paramEntity.locX - this.locX;
      double d2 = paramEntity.boundingBox.b + paramEntity.length / 2.0F - (this.locY + this.length / 2.0F);
      double d3 = paramEntity.locZ - this.locZ;
      
      if (this.attackTicks == 0) {
        this.br += 1;
        if (this.br == 1) {
          this.attackTicks = 60;
          a(true);
        } else if (this.br <= 4) {
          this.attackTicks = 6;
        } else {
          this.attackTicks = 100;
          this.br = 0;
          a(false);
        }
        
        if (this.br > 1) {
          float f = MathHelper.c(paramFloat) * 0.5F;
          
          this.world.a(null, 1009, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
          for (int i = 0; i < 1; i++) {
            EntitySmallFireball localEntitySmallFireball = new EntitySmallFireball(this.world, this, d1 + this.random.nextGaussian() * f, d2, d3 + this.random.nextGaussian() * f);
            localEntitySmallFireball.locY = (this.locY + this.length / 2.0F + 0.5D);
            this.world.addEntity(localEntitySmallFireball);
          }
        }
      }
      
      this.yaw = ((float)(Math.atan2(d3, d1) * 180.0D / 3.1415927410125732D) - 90.0F);
      
      this.bn = true;
    }
  }
  

  protected void b(float paramFloat) {}
  

  protected Item getLoot()
  {
    return Items.BLAZE_ROD;
  }
  
  public boolean isBurning()
  {
    return bZ();
  }
  
  protected void dropDeathLoot(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean) {
      int i = this.random.nextInt(2 + paramInt);
      for (int j = 0; j < i; j++) {
        a(Items.BLAZE_ROD, 1);
      }
    }
  }
  
  public boolean bZ() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void a(boolean paramBoolean) {
    byte b = this.datawatcher.getByte(16);
    if (paramBoolean) {
      b = (byte)(b | 0x1);
    } else {
      b = (byte)(b & 0xFFFFFFFE);
    }
    this.datawatcher.watch(16, Byte.valueOf(b));
  }
  
  protected boolean j_()
  {
    return true;
  }
}
