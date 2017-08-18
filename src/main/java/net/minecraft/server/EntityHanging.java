package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.entity.Hanging;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.plugin.PluginManager;

public abstract class EntityHanging extends Entity
{
  private int e;
  public int direction;
  public int x;
  public int y;
  public int z;
  
  public EntityHanging(World world)
  {
    super(world);
    this.height = 0.0F;
    a(0.5F, 0.5F);
  }
  
  public EntityHanging(World world, int i, int j, int k, int l) {
    this(world);
    this.x = i;
    this.y = j;
    this.z = k;
  }
  
  protected void c() {}
  
  public void setDirection(int i) {
    this.direction = i;
    this.lastYaw = (this.yaw = i * 90);
    float f = f();
    float f1 = i();
    float f2 = f();
    
    if ((i != 2) && (i != 0)) {
      f = 0.5F;
    } else {
      f2 = 0.5F;
      this.yaw = (this.lastYaw = Direction.f[i] * 90);
    }
    
    f /= 32.0F;
    f1 /= 32.0F;
    f2 /= 32.0F;
    float f3 = this.x + 0.5F;
    float f4 = this.y + 0.5F;
    float f5 = this.z + 0.5F;
    float f6 = 0.5625F;
    
    if (i == 2) {
      f5 -= f6;
    }
    
    if (i == 1) {
      f3 -= f6;
    }
    
    if (i == 0) {
      f5 += f6;
    }
    
    if (i == 3) {
      f3 += f6;
    }
    
    if (i == 2) {
      f3 -= c(f());
    }
    
    if (i == 1) {
      f5 += c(f());
    }
    
    if (i == 0) {
      f3 += c(f());
    }
    
    if (i == 3) {
      f5 -= c(f());
    }
    
    f4 += c(i());
    setPosition(f3, f4, f5);
    float f7 = -0.03125F;
    
    this.boundingBox.b(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
  }
  
  private float c(int i) {
    return i == 64 ? 0.5F : i == 32 ? 0.5F : 0.0F;
  }
  
  public void h() {
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    if ((this.e++ == this.world.spigotConfig.hangingTickFrequency) && (!this.world.isStatic)) {
      this.e = 0;
      if ((!this.dead) && (!survives()))
      {
        Material material = this.world.getType((int)this.locX, (int)this.locY, (int)this.locZ).getMaterial();
        HangingBreakEvent.RemoveCause cause;
        HangingBreakEvent.RemoveCause cause;
        if (!material.equals(Material.AIR))
        {
          cause = HangingBreakEvent.RemoveCause.OBSTRUCTION;
        } else {
          cause = HangingBreakEvent.RemoveCause.PHYSICS;
        }
        
        HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), cause);
        this.world.getServer().getPluginManager().callEvent(event);
        
        PaintingBreakEvent paintingEvent = null;
        if ((this instanceof EntityPainting))
        {
          paintingEvent = new PaintingBreakEvent((org.bukkit.entity.Painting)getBukkitEntity(), org.bukkit.event.painting.PaintingBreakEvent.RemoveCause.valueOf(cause.name()));
          paintingEvent.setCancelled(event.isCancelled());
          this.world.getServer().getPluginManager().callEvent(paintingEvent);
        }
        
        if ((this.dead) || (event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
          return;
        }
        

        die();
        b((Entity)null);
      }
    }
  }
  
  public boolean survives() {
    if (!this.world.getCubes(this, this.boundingBox).isEmpty()) {
      return false;
    }
    int i = Math.max(1, f() / 16);
    int j = Math.max(1, i() / 16);
    int k = this.x;
    int l = this.y;
    int i1 = this.z;
    
    if (this.direction == 2) {
      k = MathHelper.floor(this.locX - f() / 32.0F);
    }
    
    if (this.direction == 1) {
      i1 = MathHelper.floor(this.locZ - f() / 32.0F);
    }
    
    if (this.direction == 0) {
      k = MathHelper.floor(this.locX - f() / 32.0F);
    }
    
    if (this.direction == 3) {
      i1 = MathHelper.floor(this.locZ - f() / 32.0F);
    }
    
    l = MathHelper.floor(this.locY - i() / 32.0F);
    
    for (int j1 = 0; j1 < i; j1++) {
      for (int k1 = 0; k1 < j; k1++) {
        Material material;
        Material material;
        if ((this.direction != 2) && (this.direction != 0)) {
          material = this.world.getType(this.x, l + k1, i1 + j1).getMaterial();
        } else {
          material = this.world.getType(k + j1, l + k1, this.z).getMaterial();
        }
        
        if (!material.isBuildable()) {
          return false;
        }
      }
    }
    
    List list = this.world.getEntities(this, this.boundingBox);
    Iterator iterator = list.iterator();
    
    Entity entity;
    do
    {
      if (!iterator.hasNext()) {
        return true;
      }
      
      entity = (Entity)iterator.next();
    } while (!(entity instanceof EntityHanging));
    
    return false;
  }
  
  public boolean R()
  {
    return true;
  }
  
  public boolean j(Entity entity) {
    return (entity instanceof EntityHuman) ? damageEntity(DamageSource.playerAttack((EntityHuman)entity), 0.0F) : false;
  }
  
  public void i(int i) {
    this.world.X();
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    if ((!this.dead) && (!this.world.isStatic))
    {
      HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.DEFAULT);
      PaintingBreakEvent paintingEvent = null;
      if (damagesource.getEntity() != null) {
        event = new org.bukkit.event.hanging.HangingBreakByEntityEvent((Hanging)getBukkitEntity(), damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity());
        
        if ((this instanceof EntityPainting))
        {
          paintingEvent = new org.bukkit.event.painting.PaintingBreakByEntityEvent((org.bukkit.entity.Painting)getBukkitEntity(), damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity());
        }
      } else if (damagesource.isExplosion()) {
        event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.EXPLOSION);
      }
      
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (paintingEvent != null) {
        paintingEvent.setCancelled(event.isCancelled());
        this.world.getServer().getPluginManager().callEvent(paintingEvent);
      }
      
      if ((this.dead) || (event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
        return true;
      }
      

      die();
      Q();
      b(damagesource.getEntity());
    }
    
    return true;
  }
  
  public void move(double d0, double d1, double d2)
  {
    if ((!this.world.isStatic) && (!this.dead) && (d0 * d0 + d1 * d1 + d2 * d2 > 0.0D)) {
      if (this.dead) { return;
      }
      

      HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.PHYSICS);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if ((this.dead) || (event.isCancelled())) {
        return;
      }
      

      die();
      b((Entity)null);
    }
  }
  


  public void g(double d0, double d1, double d2) {}
  


  public void b(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setByte("Direction", (byte)this.direction);
    nbttagcompound.setInt("TileX", this.x);
    nbttagcompound.setInt("TileY", this.y);
    nbttagcompound.setInt("TileZ", this.z);
    switch (this.direction) {
    case 0: 
      nbttagcompound.setByte("Dir", (byte)2);
      break;
    
    case 1: 
      nbttagcompound.setByte("Dir", (byte)1);
      break;
    
    case 2: 
      nbttagcompound.setByte("Dir", (byte)0);
      break;
    
    case 3: 
      nbttagcompound.setByte("Dir", (byte)3);
    }
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    if (nbttagcompound.hasKeyOfType("Direction", 99)) {
      this.direction = nbttagcompound.getByte("Direction");
    } else {
      switch (nbttagcompound.getByte("Dir")) {
      case 0: 
        this.direction = 2;
        break;
      
      case 1: 
        this.direction = 1;
        break;
      
      case 2: 
        this.direction = 0;
        break;
      
      case 3: 
        this.direction = 3;
      }
      
    }
    this.x = nbttagcompound.getInt("TileX");
    this.y = nbttagcompound.getInt("TileY");
    this.z = nbttagcompound.getInt("TileZ");
    setDirection(this.direction);
  }
  
  public abstract int f();
  
  public abstract int i();
  
  public abstract void b(Entity paramEntity);
  
  protected boolean V() {
    return false;
  }
}
