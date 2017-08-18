package net.minecraft.server;

import java.util.Calendar;
import java.util.Random;












public class EntityBat
  extends EntityAmbient
{
  private ChunkCoordinates h;
  
  public EntityBat(World paramWorld)
  {
    super(paramWorld);
    
    a(0.5F, 0.9F);
    setAsleep(true);
  }
  
  protected void c()
  {
    super.c();
    
    this.datawatcher.a(16, new Byte((byte)0));
  }
  
  protected float bf()
  {
    return 0.1F;
  }
  
  protected float bg()
  {
    return super.bg() * 0.95F;
  }
  
  protected String t()
  {
    if ((isAsleep()) && (this.random.nextInt(4) != 0)) {
      return null;
    }
    return "mob.bat.idle";
  }
  
  protected String aT()
  {
    return "mob.bat.hurt";
  }
  
  protected String aU()
  {
    return "mob.bat.death";
  }
  

  public boolean S()
  {
    return false;
  }
  


  protected void o(Entity paramEntity) {}
  


  protected void bo() {}
  


  protected void aD()
  {
    super.aD();
    
    getAttributeInstance(GenericAttributes.maxHealth).setValue(6.0D);
  }
  
  public boolean isAsleep() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setAsleep(boolean paramBoolean) {
    int i = this.datawatcher.getByte(16);
    if (paramBoolean) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
    }
  }
  
  protected boolean bk()
  {
    return true;
  }
  

  public void h()
  {
    super.h();
    
    if (isAsleep()) {
      this.motX = (this.motY = this.motZ = 0.0D);
      this.locY = (MathHelper.floor(this.locY) + 1.0D - this.length);
    } else {
      this.motY *= 0.6000000238418579D;
    }
  }
  

  protected void bn()
  {
    super.bn();
    
    if (isAsleep()) {
      if (!this.world.getType(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ)).r()) {
        setAsleep(false);
        this.world.a(null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
      }
      else {
        if (this.random.nextInt(200) == 0) {
          this.aO = this.random.nextInt(360);
        }
        
        if (this.world.findNearbyPlayer(this, 4.0D) != null) {
          setAsleep(false);
          this.world.a(null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
        }
      }
    }
    else
    {
      if ((this.h != null) && ((!this.world.isEmpty(this.h.x, this.h.y, this.h.z)) || (this.h.y < 1))) {
        this.h = null;
      }
      if ((this.h == null) || (this.random.nextInt(30) == 0) || (this.h.e((int)this.locX, (int)this.locY, (int)this.locZ) < 4.0F)) {
        this.h = new ChunkCoordinates((int)this.locX + this.random.nextInt(7) - this.random.nextInt(7), (int)this.locY + this.random.nextInt(6) - 2, (int)this.locZ + this.random.nextInt(7) - this.random.nextInt(7));
      }
      
      double d1 = this.h.x + 0.5D - this.locX;
      double d2 = this.h.y + 0.1D - this.locY;
      double d3 = this.h.z + 0.5D - this.locZ;
      
      this.motX += (Math.signum(d1) * 0.5D - this.motX) * 0.10000000149011612D;
      this.motY += (Math.signum(d2) * 0.699999988079071D - this.motY) * 0.10000000149011612D;
      this.motZ += (Math.signum(d3) * 0.5D - this.motZ) * 0.10000000149011612D;
      
      float f1 = (float)(Math.atan2(this.motZ, this.motX) * 180.0D / 3.1415927410125732D) - 90.0F;
      float f2 = MathHelper.g(f1 - this.yaw);
      this.be = 0.5F;
      this.yaw += f2;
      
      if ((this.random.nextInt(100) == 0) && (this.world.getType(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ)).r())) {
        setAsleep(true);
      }
    }
  }
  

  protected boolean g_()
  {
    return false;
  }
  


  protected void b(float paramFloat) {}
  


  protected void a(double paramDouble, boolean paramBoolean) {}
  


  public boolean az()
  {
    return true;
  }
  

  public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
  {
    if (isInvulnerable()) return false;
    if ((!this.world.isStatic) && 
      (isAsleep())) {
      setAsleep(false);
    }
    

    return super.damageEntity(paramDamageSource, paramFloat);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    
    this.datawatcher.watch(16, Byte.valueOf(paramNBTTagCompound.getByte("BatFlags")));
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    
    paramNBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
  }
  

  public boolean canSpawn()
  {
    int i = MathHelper.floor(this.boundingBox.b);
    if (i >= 63) { return false;
    }
    int j = MathHelper.floor(this.locX);
    int k = MathHelper.floor(this.locZ);
    
    int m = this.world.getLightLevel(j, i, k);
    int n = 4;
    Calendar localCalendar = this.world.V();
    
    if (((localCalendar.get(2) + 1 == 10) && (localCalendar.get(5) >= 20)) || ((localCalendar.get(2) + 1 == 11) && (localCalendar.get(5) <= 3))) {
      n = 7;
    } else if (this.random.nextBoolean()) {
      return false;
    }
    
    if (m > this.random.nextInt(n)) { return false;
    }
    return super.canSpawn();
  }
}
