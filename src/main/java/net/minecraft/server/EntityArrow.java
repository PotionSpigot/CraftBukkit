package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.PluginManager;

public class EntityArrow extends Entity implements IProjectile
{
  private int d = -1;
  private int e = -1;
  private int f = -1;
  private Block g;
  private int h;
  public boolean inGround = false;
  public int fromPlayer;
  public int shake;
  public Entity shooter;
  private int at;
  private int au;
  private double damage = 2.0D;
  
  public int knockbackStrength;
  

  public void inactiveTick()
  {
    if (this.inGround)
    {
      this.at += 19;
    }
    super.inactiveTick();
  }
  
  public EntityArrow(World world)
  {
    super(world);
    this.j = 10.0D;
    a(0.5F, 0.5F);
  }
  
  public EntityArrow(World world, double d0, double d1, double d2) {
    super(world);
    this.j = 10.0D;
    a(0.5F, 0.5F);
    setPosition(d0, d1, d2);
    this.height = 0.0F;
  }
  
  public EntityArrow(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1) {
    super(world);
    this.j = 10.0D;
    this.shooter = entityliving;
    this.projectileSource = ((LivingEntity)entityliving.getBukkitEntity());
    if ((entityliving instanceof EntityHuman)) {
      this.fromPlayer = 1;
    }
    
    this.locY = (entityliving.locY + entityliving.getHeadHeight() - 0.10000000149011612D);
    double d0 = entityliving1.locX - entityliving.locX;
    double d1 = entityliving1.boundingBox.b + entityliving1.length / 3.0F - this.locY;
    double d2 = entityliving1.locZ - entityliving.locZ;
    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    
    if (d3 >= 1.0E-7D) {
      float f2 = (float)(Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
      float f3 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.1415927410125732D);
      double d4 = d0 / d3;
      double d5 = d2 / d3;
      
      setPositionRotation(entityliving.locX + d4, this.locY, entityliving.locZ + d5, f2, f3);
      this.height = 0.0F;
      float f4 = (float)d3 * 0.2F;
      
      shoot(d0, d1 + f4, d2, f, f1);
    }
  }
  
  public EntityArrow(World world, EntityLiving entityliving, float f) {
    super(world);
    this.j = 10.0D;
    this.shooter = entityliving;
    this.projectileSource = ((LivingEntity)entityliving.getBukkitEntity());
    if ((entityliving instanceof EntityHuman)) {
      this.fromPlayer = 1;
    }
    
    a(0.5F, 0.5F);
    setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.10000000149011612D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    shoot(this.motX, this.motY, this.motZ, f * 1.5F, 1.0F);
  }
  
  protected void c() {
    this.datawatcher.a(16, Byte.valueOf((byte)0));
  }
  
  public void shoot(double d0, double d1, double d2, float f, float f1) {
    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    
    d0 /= f2;
    d1 /= f2;
    d2 /= f2;
    d0 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
    d1 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
    d2 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
    d0 *= f;
    d1 *= f;
    d2 *= f;
    this.motX = d0;
    this.motY = d1;
    this.motZ = d2;
    float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    
    this.lastYaw = (this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D));
    this.at = 0;
  }
  
  public void h() {
    super.h();
    if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F)) {
      float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      
      this.lastYaw = (this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
      this.lastPitch = (this.pitch = (float)(Math.atan2(this.motY, f) * 180.0D / 3.1415927410125732D));
    }
    
    Block block = this.world.getType(this.d, this.e, this.f);
    
    if (block.getMaterial() != Material.AIR) {
      block.updateShape(this.world, this.d, this.e, this.f);
      AxisAlignedBB axisalignedbb = block.a(this.world, this.d, this.e, this.f);
      
      if ((axisalignedbb != null) && (axisalignedbb.a(Vec3D.a(this.locX, this.locY, this.locZ)))) {
        this.inGround = true;
      }
    }
    
    if (this.shake > 0) {
      this.shake -= 1;
    }
    
    if (this.inGround) {
      int i = this.world.getData(this.d, this.e, this.f);
      
      if ((block == this.g) && (i == this.h)) {
        this.at += 1;
        if (this.at >= this.world.spigotConfig.arrowDespawnRate) {
          die();
        }
      } else {
        this.inGround = false;
        this.motX *= this.random.nextFloat() * 0.2F;
        this.motY *= this.random.nextFloat() * 0.2F;
        this.motZ *= this.random.nextFloat() * 0.2F;
        this.at = 0;
        this.au = 0;
      }
    } else {
      this.au += 1;
      Vec3D vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
      Vec3D vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, false, true, false);
      
      vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
      vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      if (movingobjectposition != null) {
        vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
      }
      
      Entity entity = null;
      List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double d0 = 0.0D;
      



      for (int j = 0; j < list.size(); j++) {
        Entity entity1 = (Entity)list.get(j);
        
        if ((entity1.R()) && ((entity1 != this.shooter) || (this.au >= 5))) {
          float f1 = 0.3F;
          AxisAlignedBB axisalignedbb1 = entity1.boundingBox.grow(f1, f1, f1);
          MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
          
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
      
      if ((movingobjectposition != null) && (movingobjectposition.entity != null) && ((movingobjectposition.entity instanceof EntityHuman))) {
        EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
        
        if ((entityhuman.abilities.isInvulnerable) || (((this.shooter instanceof EntityHuman)) && (!((EntityHuman)this.shooter).a(entityhuman)))) {
          movingobjectposition = null;
        }
      }
      




      if ((movingobjectposition != null) && ((movingobjectposition.entity instanceof EntityPlayer)) && (this.shooter != null) && ((this.shooter instanceof EntityPlayer)) && 
        (!((EntityPlayer)this.shooter).getBukkitEntity().canSee(((EntityPlayer)movingobjectposition.entity).getBukkitEntity()))) {
        movingobjectposition = null;
      }
      


      if (movingobjectposition != null) {
        org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callProjectileHitEvent(this);
        
        if (movingobjectposition.entity != null) {
          float f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
          int k = MathHelper.f(f2 * this.damage);
          
          if (isCritical()) {
            k += this.random.nextInt(k / 2 + 2);
          }
          
          DamageSource damagesource = null;
          
          if (this.shooter == null) {
            damagesource = DamageSource.arrow(this, this);
          } else {
            damagesource = DamageSource.arrow(this, this.shooter);
          }
          

          if (movingobjectposition.entity.damageEntity(damagesource, k)) {
            if ((isBurning()) && (!(movingobjectposition.entity instanceof EntityEnderman)) && ((!(movingobjectposition.entity instanceof EntityPlayer)) || (!(this.shooter instanceof EntityPlayer)) || (this.world.pvpMode))) {
              EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 5);
              org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
              
              if (!combustEvent.isCancelled()) {
                movingobjectposition.entity.setOnFire(combustEvent.getDuration());
              }
            }
            


            if ((movingobjectposition.entity instanceof EntityLiving)) {
              EntityLiving entityliving = (EntityLiving)movingobjectposition.entity;
              
              if (!this.world.isStatic) {
                entityliving.p(entityliving.aZ() + 1);
              }
              
              if (this.knockbackStrength > 0) {
                float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                if (f3 > 0.0F) {
                  movingobjectposition.entity.g(this.motX * this.knockbackStrength * 0.6000000238418579D / f3, 0.1D, this.motZ * this.knockbackStrength * 0.6000000238418579D / f3);
                }
              }
              
              if ((this.shooter != null) && ((this.shooter instanceof EntityLiving))) {
                EnchantmentManager.a(entityliving, this.shooter);
                EnchantmentManager.b((EntityLiving)this.shooter, entityliving);
              }
              
              if ((this.shooter != null) && (movingobjectposition.entity != this.shooter) && ((movingobjectposition.entity instanceof EntityHuman)) && ((this.shooter instanceof EntityPlayer))) {
                ((EntityPlayer)this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
              }
            }
            
            makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (!(movingobjectposition.entity instanceof EntityEnderman)) {
              die();
            }
          } else {
            this.motX *= -0.10000000149011612D;
            this.motY *= -0.10000000149011612D;
            this.motZ *= -0.10000000149011612D;
            this.yaw += 180.0F;
            this.lastYaw += 180.0F;
            this.au = 0;
          }
        } else {
          this.d = movingobjectposition.b;
          this.e = movingobjectposition.c;
          this.f = movingobjectposition.d;
          this.g = this.world.getType(this.d, this.e, this.f);
          this.h = this.world.getData(this.d, this.e, this.f);
          this.motX = ((float)(movingobjectposition.pos.a - this.locX));
          this.motY = ((float)(movingobjectposition.pos.b - this.locY));
          this.motZ = ((float)(movingobjectposition.pos.c - this.locZ));
          float f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
          this.locX -= this.motX / f2 * 0.05000000074505806D;
          this.locY -= this.motY / f2 * 0.05000000074505806D;
          this.locZ -= this.motZ / f2 * 0.05000000074505806D;
          makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
          this.inGround = true;
          this.shake = 7;
          setCritical(false);
          if (this.g.getMaterial() != Material.AIR) {
            this.g.a(this.world, this.d, this.e, this.f, this);
          }
        }
      }
      
      if (isCritical()) {
        for (j = 0; j < 4; j++) {
          this.world.addParticle("crit", this.locX + this.motX * j / 4.0D, this.locY + this.motY * j / 4.0D, this.locZ + this.motZ * j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
        }
      }
      
      this.locX += this.motX;
      this.locY += this.motY;
      this.locZ += this.motZ;
      float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
      
      for (this.pitch = ((float)(Math.atan2(this.motY, f2) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
      


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
      float f4 = 0.99F;
      
      float f1 = 0.05F;
      if (M()) {
        for (int l = 0; l < 4; l++) {
          float f3 = 0.25F;
          this.world.addParticle("bubble", this.locX - this.motX * f3, this.locY - this.motY * f3, this.locZ - this.motZ * f3, this.motX, this.motY, this.motZ);
        }
        
        f4 = 0.8F;
      }
      
      if (L()) {
        extinguish();
      }
      
      this.motX *= f4;
      this.motY *= f4;
      this.motZ *= f4;
      this.motY -= f1;
      setPosition(this.locX, this.locY, this.locZ);
      I();
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setShort("xTile", (short)this.d);
    nbttagcompound.setShort("yTile", (short)this.e);
    nbttagcompound.setShort("zTile", (short)this.f);
    nbttagcompound.setShort("life", (short)this.at);
    nbttagcompound.setByte("inTile", (byte)Block.getId(this.g));
    nbttagcompound.setByte("inData", (byte)this.h);
    nbttagcompound.setByte("shake", (byte)this.shake);
    nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    nbttagcompound.setByte("pickup", (byte)this.fromPlayer);
    nbttagcompound.setDouble("damage", this.damage);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.d = nbttagcompound.getShort("xTile");
    this.e = nbttagcompound.getShort("yTile");
    this.f = nbttagcompound.getShort("zTile");
    this.at = nbttagcompound.getShort("life");
    this.g = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
    this.h = (nbttagcompound.getByte("inData") & 0xFF);
    this.shake = (nbttagcompound.getByte("shake") & 0xFF);
    this.inGround = (nbttagcompound.getByte("inGround") == 1);
    if (nbttagcompound.hasKeyOfType("damage", 99)) {
      this.damage = nbttagcompound.getDouble("damage");
    }
    
    if (nbttagcompound.hasKeyOfType("pickup", 99)) {
      this.fromPlayer = nbttagcompound.getByte("pickup");
    } else if (nbttagcompound.hasKeyOfType("player", 99)) {
      this.fromPlayer = (nbttagcompound.getBoolean("player") ? 1 : 0);
    }
  }
  
  public void b_(EntityHuman entityhuman) {
    if ((!this.world.isStatic) && (this.inGround) && (this.shake <= 0))
    {
      ItemStack itemstack = new ItemStack(Items.ARROW);
      if ((this.fromPlayer == 1) && (entityhuman.inventory.canHold(itemstack) > 0)) {
        EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
        
        PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new org.bukkit.craftbukkit.v1_7_R4.entity.CraftItem(this.world.getServer(), this, item), 0);
        
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (event.isCancelled()) {
          return;
        }
      }
      

      boolean flag = (this.fromPlayer == 1) || ((this.fromPlayer == 2) && (entityhuman.abilities.canInstantlyBuild));
      
      if ((this.fromPlayer == 1) && (!entityhuman.inventory.pickup(new ItemStack(Items.ARROW, 1)))) {
        flag = false;
      }
      
      if (flag) {
        makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        entityhuman.receive(this, 1);
        die();
      }
    }
  }
  
  protected boolean g_() {
    return false;
  }
  
  public void b(double d0) {
    this.damage = d0;
  }
  
  public double e() {
    return this.damage;
  }
  
  public void setKnockbackStrength(int i) {
    this.knockbackStrength = i;
  }
  
  public boolean av() {
    return false;
  }
  
  public void setCritical(boolean flag) {
    byte b0 = this.datawatcher.getByte(16);
    
    if (flag) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
    }
  }
  
  public boolean isCritical() {
    byte b0 = this.datawatcher.getByte(16);
    
    return (b0 & 0x1) != 0;
  }
  
  public boolean isInGround()
  {
    return this.inGround;
  }
}
