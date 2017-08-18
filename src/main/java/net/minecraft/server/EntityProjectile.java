package net.minecraft.server;

import java.util.Random;

public abstract class EntityProjectile extends Entity implements IProjectile
{
  private int blockX = -1;
  private int blockY = -1;
  private int blockZ = -1;
  private Block inBlockId;
  protected boolean inGround;
  public int shake;
  public EntityLiving shooter;
  public String shooterName;
  private int i;
  private int at;
  
  public EntityProjectile(World world) {
    super(world);
    a(0.25F, 0.25F);
  }
  
  protected void c() {}
  
  public EntityProjectile(World world, EntityLiving entityliving) {
    super(world);
    this.shooter = entityliving;
    this.projectileSource = ((org.bukkit.entity.LivingEntity)entityliving.getBukkitEntity());
    a(0.25F, 0.25F);
    setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.10000000149011612D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    float f = 0.4F;
    
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
    this.motY = (-MathHelper.sin((this.pitch + f()) / 180.0F * 3.1415927F) * f);
    shoot(this.motX, this.motY, this.motZ, e(), 1.0F);
  }
  
  public EntityProjectile(World world, double d0, double d1, double d2) {
    super(world);
    this.i = 0;
    a(0.25F, 0.25F);
    setPosition(d0, d1, d2);
    this.height = 0.0F;
  }
  
  protected float e() {
    return 1.5F;
  }
  
  protected float f() {
    return 0.0F;
  }
  
  public void shoot(double d0, double d1, double d2, float f, float f1) {
    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    
    d0 /= f2;
    d1 /= f2;
    d2 /= f2;
    d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d0 *= f;
    d1 *= f;
    d2 *= f;
    this.motX = d0;
    this.motY = d1;
    this.motZ = d2;
    float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    
    this.lastYaw = (this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D));
    this.i = 0;
  }
  
  public void h() {
    this.S = this.locX;
    this.T = this.locY;
    this.U = this.locZ;
    super.h();
    if (this.shake > 0) {
      this.shake -= 1;
    }
    
    if (this.inGround) {
      if (this.world.getType(this.blockX, this.blockY, this.blockZ) == this.inBlockId) {
        this.i += 1;
        if (this.i == 1200) {
          die();
        }
        
        return;
      }
      
      this.inGround = false;
      this.motX *= this.random.nextFloat() * 0.2F;
      this.motY *= this.random.nextFloat() * 0.2F;
      this.motZ *= this.random.nextFloat() * 0.2F;
      this.i = 0;
      this.at = 0;
    } else {
      this.at += 1;
    }
    
    Vec3D vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
    Vec3D vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
    
    vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
    vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    
    if (movingobjectposition != null) {
      vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
    }
    
    if (!this.world.isStatic) {
      Entity entity = null;
      java.util.List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double d0 = 0.0D;
      EntityLiving entityliving = getShooter();
      
      for (int i = 0; i < list.size(); i++) {
        Entity entity1 = (Entity)list.get(i);
        
        if ((entity1.R()) && ((entity1 != entityliving) || (this.at >= 5))) {
          float f = 0.3F;
          AxisAlignedBB axisalignedbb = entity1.boundingBox.grow(f, f, f);
          MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
          
          if (movingobjectposition1 != null) {
            double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
            
            if ((d1 < d0) || (d0 == 0.0D)) {
              entity = entity1;
              d0 = d1;
            }
          }
        }
      }
      
      if (entity != null) {
        movingobjectposition = new MovingObjectPosition(entity);
      }
    }
    

    if ((movingobjectposition != null) && ((movingobjectposition.entity instanceof EntityPlayer)) && (this.shooter != null) && ((this.shooter instanceof EntityPlayer)) && 
      (!((EntityPlayer)this.shooter).getBukkitEntity().canSee(((EntityPlayer)movingobjectposition.entity).getBukkitEntity()))) {
      movingobjectposition = null;
    }
    


    if (movingobjectposition != null) {
      if ((movingobjectposition.type == EnumMovingObjectType.BLOCK) && (this.world.getType(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d) == Blocks.PORTAL)) {
        ah();
      } else {
        a(movingobjectposition);
        
        if (this.dead) {
          org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callProjectileHitEvent(this);
        }
      }
    }
    

    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    
    this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
    
    for (this.pitch = ((float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
    


    while (this.pitch - this.lastPitch >= 180.0F) {
      this.lastPitch += 360.0F;
    }
    
    while (this.yaw - this.lastYaw < -180.0F) {
      this.lastYaw -= 360.0F;
    }
    
    while (this.yaw - this.lastYaw >= 180.0F) {
      this.lastYaw += 360.0F;
    }
    
    this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
    this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
    float f2 = 0.99F;
    float f3 = i();
    
    if (M()) {
      for (int j = 0; j < 4; j++) {
        float f4 = 0.25F;
        
        this.world.addParticle("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
      }
      
      f2 = 0.8F;
    }
    
    this.motX *= f2;
    this.motY *= f2;
    this.motZ *= f2;
    this.motY -= f3;
    setPosition(this.locX, this.locY, this.locZ);
  }
  
  protected float i() {
    return 0.03F;
  }
  
  protected abstract void a(MovingObjectPosition paramMovingObjectPosition);
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setShort("xTile", (short)this.blockX);
    nbttagcompound.setShort("yTile", (short)this.blockY);
    nbttagcompound.setShort("zTile", (short)this.blockZ);
    nbttagcompound.setByte("inTile", (byte)Block.getId(this.inBlockId));
    nbttagcompound.setByte("shake", (byte)this.shake);
    nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    if (((this.shooterName == null) || (this.shooterName.length() == 0)) && (this.shooter != null) && ((this.shooter instanceof EntityHuman))) {
      this.shooterName = this.shooter.getName();
    }
    
    nbttagcompound.setString("ownerName", this.shooterName == null ? "" : this.shooterName);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.blockX = nbttagcompound.getShort("xTile");
    this.blockY = nbttagcompound.getShort("yTile");
    this.blockZ = nbttagcompound.getShort("zTile");
    this.inBlockId = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
    this.shake = (nbttagcompound.getByte("shake") & 0xFF);
    this.inGround = (nbttagcompound.getByte("inGround") == 1);
    this.shooterName = nbttagcompound.getString("ownerName");
    if ((this.shooterName != null) && (this.shooterName.length() == 0)) {
      this.shooterName = null;
    }
  }
  
  public EntityLiving getShooter() {
    if ((this.shooter == null) && (this.shooterName != null) && (this.shooterName.length() > 0)) {
      this.shooter = this.world.a(this.shooterName);
    }
    
    return this.shooter;
  }
}
