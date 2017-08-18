package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.PluginManager;

public class EntityBoat extends Entity
{
  private boolean a;
  private double b;
  private int c;
  private double d;
  private double e;
  private double f;
  private double g;
  private double h;
  public double maxSpeed = 0.4D;
  public double occupiedDeceleration = 0.2D;
  public double unoccupiedDeceleration = -1.0D;
  public boolean landBoats = false;
  
  public void collide(Entity entity)
  {
    org.bukkit.entity.Entity hitEntity = entity == null ? null : entity.getBukkitEntity();
    
    VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)getBukkitEntity(), hitEntity);
    this.world.getServer().getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      return;
    }
    
    super.collide(entity);
  }
  
  public EntityBoat(World world)
  {
    super(world);
    this.a = true;
    this.b = 0.07D;
    this.k = true;
    a(1.5F, 0.6F);
    this.height = (this.length / 2.0F);
  }
  
  protected boolean g_() {
    return false;
  }
  
  protected void c() {
    this.datawatcher.a(17, new Integer(0));
    this.datawatcher.a(18, new Integer(1));
    this.datawatcher.a(19, new Float(0.0F));
  }
  
  public AxisAlignedBB h(Entity entity) {
    return entity.boundingBox;
  }
  
  public AxisAlignedBB J() {
    return this.boundingBox;
  }
  
  public boolean S() {
    return true;
  }
  
  public EntityBoat(World world, double d0, double d1, double d2) {
    this(world);
    setPosition(d0, d1 + this.height, d2);
    this.motX = 0.0D;
    this.motY = 0.0D;
    this.motZ = 0.0D;
    this.lastX = d0;
    this.lastY = d1;
    this.lastZ = d2;
    
    this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle)getBukkitEntity()));
  }
  
  public double ae() {
    return this.length * 0.0D - 0.30000001192092896D;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable())
      return false;
    if ((!this.world.isStatic) && (!this.dead))
    {
      Vehicle vehicle = (Vehicle)getBukkitEntity();
      org.bukkit.entity.Entity attacker = damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity();
      
      VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, f);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (event.isCancelled()) {
        return true;
      }
      


      c(-i());
      a(10);
      setDamage(getDamage() + f * 10.0F);
      Q();
      boolean flag = ((damagesource.getEntity() instanceof EntityHuman)) && (((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
      
      if ((flag) || (getDamage() > 40.0F))
      {
        VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
        this.world.getServer().getPluginManager().callEvent(destroyEvent);
        
        if (destroyEvent.isCancelled()) {
          setDamage(40.0F);
          return true;
        }
        

        if (this.passenger != null) {
          this.passenger.mount(this);
        }
        
        if (!flag) {
          a(Items.BOAT, 1, 0.0F);
        }
        
        die();
      }
      
      return true;
    }
    return true;
  }
  
  public boolean R()
  {
    return !this.dead;
  }
  
  public void h()
  {
    double prevX = this.locX;
    double prevY = this.locY;
    double prevZ = this.locZ;
    float prevYaw = this.yaw;
    float prevPitch = this.pitch;
    

    super.h();
    if (f() > 0) {
      a(f() - 1);
    }
    
    if (getDamage() > 0.0F) {
      setDamage(getDamage() - 1.0F);
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    byte b0 = 5;
    double d0 = 0.0D;
    
    for (int i = 0; i < b0; i++) {
      double d1 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 0) / b0 - 0.125D;
      double d2 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 1) / b0 - 0.125D;
      AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.boundingBox.a, d1, this.boundingBox.c, this.boundingBox.d, d2, this.boundingBox.f);
      
      if (this.world.b(axisalignedbb, Material.WATER)) {
        d0 += 1.0D / b0;
      }
    }
    
    double d3 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    



    if (d3 > 0.26249999999999996D) {
      double d4 = Math.cos(this.yaw * 3.141592653589793D / 180.0D);
      double d5 = Math.sin(this.yaw * 3.141592653589793D / 180.0D);
      
      for (int j = 0; j < 1.0D + d3 * 60.0D; j++) {
        double d6 = this.random.nextFloat() * 2.0F - 1.0F;
        double d7 = (this.random.nextInt(2) * 2 - 1) * 0.7D;
        


        if (this.random.nextBoolean()) {
          double d8 = this.locX - d4 * d6 * 0.8D + d5 * d7;
          double d9 = this.locZ - d5 * d6 * 0.8D - d4 * d7;
          this.world.addParticle("splash", d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ);
        } else {
          double d8 = this.locX + d4 + d5 * d6 * 0.7D;
          double d9 = this.locZ + d5 - d4 * d6 * 0.7D;
          this.world.addParticle("splash", d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ);
        }
      }
    }
    



    if ((this.world.isStatic) && (this.a)) {
      if (this.c > 0) {
        double d4 = this.locX + (this.d - this.locX) / this.c;
        double d5 = this.locY + (this.e - this.locY) / this.c;
        double d10 = this.locZ + (this.f - this.locZ) / this.c;
        double d11 = MathHelper.g(this.g - this.yaw);
        this.yaw = ((float)(this.yaw + d11 / this.c));
        this.pitch = ((float)(this.pitch + (this.h - this.pitch) / this.c));
        this.c -= 1;
        setPosition(d4, d5, d10);
        b(this.yaw, this.pitch);
      } else {
        double d4 = this.locX + this.motX;
        double d5 = this.locY + this.motY;
        double d10 = this.locZ + this.motZ;
        setPosition(d4, d5, d10);
        if (this.onGround) {
          this.motX *= 0.5D;
          this.motY *= 0.5D;
          this.motZ *= 0.5D;
        }
        
        this.motX *= 0.9900000095367432D;
        this.motY *= 0.949999988079071D;
        this.motZ *= 0.9900000095367432D;
      }
    } else {
      if (d0 < 1.0D) {
        double d4 = d0 * 2.0D - 1.0D;
        this.motY += 0.03999999910593033D * d4;
      } else {
        if (this.motY < 0.0D) {
          this.motY /= 2.0D;
        }
        
        this.motY += 0.007000000216066837D;
      }
      
      if ((this.passenger != null) && ((this.passenger instanceof EntityLiving))) {
        EntityLiving entityliving = (EntityLiving)this.passenger;
        float f = this.passenger.yaw + -entityliving.bd * 90.0F;
        
        this.motX += -Math.sin(f * 3.1415927F / 180.0F) * this.b * entityliving.be * 0.05000000074505806D;
        this.motZ += Math.cos(f * 3.1415927F / 180.0F) * this.b * entityliving.be * 0.05000000074505806D;

      }
      else if (this.unoccupiedDeceleration >= 0.0D) {
        this.motX *= this.unoccupiedDeceleration;
        this.motZ *= this.unoccupiedDeceleration;
        
        if (this.motX <= 1.0E-5D) {
          this.motX = 0.0D;
        }
        if (this.motZ <= 1.0E-5D) {
          this.motZ = 0.0D;
        }
      }
      

      double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      if (d4 > 0.35D) {
        double d5 = 0.35D / d4;
        this.motX *= d5;
        this.motZ *= d5;
        d4 = 0.35D;
      }
      
      if ((d4 > d3) && (this.b < 0.35D)) {
        this.b += (0.35D - this.b) / 35.0D;
        if (this.b > 0.35D) {
          this.b = 0.35D;
        }
      } else {
        this.b -= (this.b - 0.07D) / 35.0D;
        if (this.b < 0.07D) {
          this.b = 0.07D;
        }
      }
      


      for (int k = 0; k < 4; k++) {
        int l = MathHelper.floor(this.locX + (k % 2 - 0.5D) * 0.8D);
        
        int j = MathHelper.floor(this.locZ + (k / 2 - 0.5D) * 0.8D);
        
        for (int i1 = 0; i1 < 2; i1++) {
          int j1 = MathHelper.floor(this.locY) + i1;
          Block block = this.world.getType(l, j1, j);
          
          if (block == Blocks.SNOW)
          {
            if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled())
            {


              this.world.setAir(l, j1, j);
              this.positionChanged = false;
            } } else if (block == Blocks.WATER_LILY)
          {
            if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled())
            {


              this.world.setAir(l, j1, j, true);
              this.positionChanged = false;
            }
          }
        }
      }
      if ((this.onGround) && (!this.landBoats)) {
        this.motX *= 0.5D;
        this.motY *= 0.5D;
        this.motZ *= 0.5D;
      }
      
      move(this.motX, this.motY, this.motZ);
      if ((this.positionChanged) && (d3 > 0.2D)) {
        if ((!this.world.isStatic) && (!this.dead))
        {
          Vehicle vehicle = (Vehicle)getBukkitEntity();
          VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
          this.world.getServer().getPluginManager().callEvent(destroyEvent);
          if (!destroyEvent.isCancelled()) {
            die();
            
            breakNaturally();
          }
        }
      }
      else {
        this.motX *= 0.9900000095367432D;
        this.motY *= 0.949999988079071D;
        this.motZ *= 0.9900000095367432D;
      }
      
      this.pitch = 0.0F;
      double d5 = this.yaw;
      double d10 = this.lastX - this.locX;
      double d11 = this.lastZ - this.locZ;
      if (d10 * d10 + d11 * d11 > 0.001D) {
        d5 = (float)(Math.atan2(d11, d10) * 180.0D / 3.141592653589793D);
      }
      
      double d12 = MathHelper.g(d5 - this.yaw);
      
      if (d12 > 20.0D) {
        d12 = 20.0D;
      }
      
      if (d12 < -20.0D) {
        d12 = -20.0D;
      }
      
      this.yaw = ((float)(this.yaw + d12));
      b(this.yaw, this.pitch);
      

      Server server = this.world.getServer();
      org.bukkit.World bworld = this.world.getWorld();
      
      Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
      Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      Vehicle vehicle = (Vehicle)getBukkitEntity();
      
      server.getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));
      
      if (!from.equals(to)) {
        VehicleMoveEvent event = new VehicleMoveEvent(vehicle, from, to);
        server.getPluginManager().callEvent(event);
      }
      

      if (!this.world.isStatic) {
        List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        
        if ((list != null) && (!list.isEmpty())) {
          for (int k1 = 0; k1 < list.size(); k1++) {
            Entity entity = (Entity)list.get(k1);
            
            if ((entity != this.passenger) && (entity.S()) && ((entity instanceof EntityBoat))) {
              entity.collide(this);
            }
          }
        }
        
        if ((this.passenger != null) && (this.passenger.dead)) {
          this.passenger.vehicle = null;
          this.passenger = null;
        }
      }
    }
  }
  
  public void ac() {
    if (this.passenger != null) {
      double d0 = Math.cos(this.yaw * 3.141592653589793D / 180.0D) * 0.4D;
      double d1 = Math.sin(this.yaw * 3.141592653589793D / 180.0D) * 0.4D;
      
      this.passenger.setPosition(this.locX + d0, this.locY + ae() + this.passenger.ad(), this.locZ + d1);
    }
  }
  
  protected void b(NBTTagCompound nbttagcompound) {}
  
  protected void a(NBTTagCompound nbttagcompound) {}
  
  public boolean c(EntityHuman entityhuman) {
    if ((this.passenger != null) && ((this.passenger instanceof EntityHuman)) && (this.passenger != entityhuman)) {
      return true;
    }
    if (!this.world.isStatic) {
      entityhuman.mount(this);
    }
    
    return true;
  }
  
  protected void a(double d0, boolean flag)
  {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locY);
    int k = MathHelper.floor(this.locZ);
    
    if (flag) {
      if (this.fallDistance > 3.0F) {
        b(this.fallDistance);
        if ((!this.world.isStatic) && (!this.dead))
        {
          Vehicle vehicle = (Vehicle)getBukkitEntity();
          VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
          this.world.getServer().getPluginManager().callEvent(destroyEvent);
          if (!destroyEvent.isCancelled()) {
            die();
            
            breakNaturally();
          }
        }
        

        this.fallDistance = 0.0F;
      }
    } else if ((this.world.getType(i, j - 1, k).getMaterial() != Material.WATER) && (d0 < 0.0D)) {
      this.fallDistance = ((float)(this.fallDistance - d0));
    }
  }
  
  public void setDamage(float f) {
    this.datawatcher.watch(19, Float.valueOf(f));
  }
  
  public float getDamage() {
    return this.datawatcher.getFloat(19);
  }
  
  public void a(int i) {
    this.datawatcher.watch(17, Integer.valueOf(i));
  }
  
  public int f() {
    return this.datawatcher.getInt(17);
  }
  
  public void c(int i) {
    this.datawatcher.watch(18, Integer.valueOf(i));
  }
  
  public int i() {
    return this.datawatcher.getInt(18);
  }
  


  public void breakNaturally()
  {
    if (this.world.paperSpigotConfig.boatsDropBoats) {
      a(Items.BOAT, 1, 0.0F);
    } else {
      for (int k = 0; k < 3; k++) {
        a(Item.getItemOf(Blocks.WOOD), 1, 0.0F);
      }
      
      for (int k = 0; k < 2; k++) {
        a(Items.STICK, 1, 0.0F);
      }
    }
  }
}
