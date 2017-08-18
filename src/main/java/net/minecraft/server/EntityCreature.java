package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.plugin.PluginManager;

public abstract class EntityCreature extends EntityInsentient
{
  public static final java.util.UUID h = java.util.UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
  public static final AttributeModifier i = new AttributeModifier(h, "Fleeing speed bonus", 2.0D, 2).a(false);
  public PathEntity pathEntity;
  public Entity target;
  protected boolean bn;
  protected int bo;
  private ChunkCoordinates bq = new ChunkCoordinates(0, 0, 0);
  private float br = -1.0F;
  private PathfinderGoal bs = new PathfinderGoalMoveTowardsRestriction(this, 1.0D);
  private boolean bt;
  
  public EntityCreature(World world) {
    super(world);
  }
  
  protected boolean bP() {
    return false;
  }
  
  protected void bq() {
    this.world.methodProfiler.a("ai");
    if ((this.bo > 0) && (--this.bo == 0)) {
      AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
      
      attributeinstance.b(i);
    }
    
    this.bn = bP();
    float f11 = 16.0F;
    
    if (this.target == null)
    {
      Entity target = findTarget();
      if (target != null) {
        EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          if (event.getTarget() == null) {
            this.target = null;
          } else {
            this.target = ((CraftEntity)event.getTarget()).getHandle();
          }
        }
      }
      

      if (this.target != null) {
        this.pathEntity = this.world.findPath(this, this.target, f11, true, false, false, true);
      }
    } else if (this.target.isAlive()) {
      float f1 = this.target.e(this);
      
      if (hasLineOfSight(this.target)) {
        a(this.target, f1);
      }
    }
    else {
      EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (!event.isCancelled()) {
        if (event.getTarget() == null) {
          this.target = null;
        } else {
          this.target = ((CraftEntity)event.getTarget()).getHandle();
        }
      }
    }
    

    if (((this.target instanceof EntityPlayer)) && (((EntityPlayer)this.target).playerInteractManager.isCreative())) {
      this.target = null;
    }
    
    this.world.methodProfiler.b();
    if ((!this.bn) && (this.target != null) && ((this.pathEntity == null) || (this.random.nextInt(20) == 0))) {
      this.pathEntity = this.world.findPath(this, this.target, f11, true, false, false, true);
    } else if ((!this.bn) && (((this.pathEntity == null) && (this.random.nextInt(180) == 0)) || (((this.random.nextInt(120) == 0) || (this.bo > 0)) && (this.aU < 100)))) {
      bQ();
    }
    
    int i = MathHelper.floor(this.boundingBox.b + 0.5D);
    boolean flag = M();
    boolean flag1 = P();
    
    this.pitch = 0.0F;
    if ((this.pathEntity != null) && (this.random.nextInt(100) != 0)) {
      this.world.methodProfiler.a("followpath");
      Vec3D vec3d = this.pathEntity.a(this);
      double d0 = this.width * 2.0F;
      
      while ((vec3d != null) && (vec3d.d(this.locX, vec3d.b, this.locZ) < d0 * d0)) {
        this.pathEntity.a();
        if (this.pathEntity.b()) {
          vec3d = null;
          this.pathEntity = null;
        } else {
          vec3d = this.pathEntity.a(this);
        }
      }
      
      this.bc = false;
      if (vec3d != null) {
        double d1 = vec3d.a - this.locX;
        double d2 = vec3d.c - this.locZ;
        double d3 = vec3d.b - i;
        
        float f2 = (float)(org.bukkit.craftbukkit.v1_7_R4.TrigMath.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f3 = MathHelper.g(f2 - this.yaw);
        
        this.be = ((float)getAttributeInstance(GenericAttributes.d).getValue());
        if (f3 > 30.0F) {
          f3 = 30.0F;
        }
        
        if (f3 < -30.0F) {
          f3 = -30.0F;
        }
        
        this.yaw += f3;
        if ((this.bn) && (this.target != null)) {
          double d4 = this.target.locX - this.locX;
          double d5 = this.target.locZ - this.locZ;
          float f4 = this.yaw;
          
          this.yaw = ((float)(Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F);
          f3 = (f4 - this.yaw + 90.0F) * 3.1415927F / 180.0F;
          this.bd = (-MathHelper.sin(f3) * this.be * 1.0F);
          this.be = (MathHelper.cos(f3) * this.be * 1.0F);
        }
        
        if (d3 > 0.0D) {
          this.bc = true;
        }
      }
      
      if (this.target != null) {
        a(this.target, 30.0F, 30.0F);
      }
      
      if ((this.positionChanged) && (!bS())) {
        this.bc = true;
      }
      
      if ((this.random.nextFloat() < 0.8F) && ((flag) || (flag1))) {
        this.bc = true;
      }
      
      this.world.methodProfiler.b();
    } else {
      super.bq();
      this.pathEntity = null;
    }
  }
  
  protected void bQ() {
    this.world.methodProfiler.a("stroll");
    boolean flag = false;
    int i = -1;
    int j = -1;
    int k = -1;
    float f = -99999.0F;
    
    for (int l = 0; l < 10; l++) {
      int i1 = MathHelper.floor(this.locX + this.random.nextInt(13) - 6.0D);
      int j1 = MathHelper.floor(this.locY + this.random.nextInt(7) - 3.0D);
      int k1 = MathHelper.floor(this.locZ + this.random.nextInt(13) - 6.0D);
      float f1 = a(i1, j1, k1);
      
      if (f1 > f) {
        f = f1;
        i = i1;
        j = j1;
        k = k1;
        flag = true;
      }
    }
    
    if (flag) {
      this.pathEntity = this.world.a(this, i, j, k, 10.0F, true, false, false, true);
    }
    
    this.world.methodProfiler.b();
  }
  
  protected void a(Entity entity, float f) {}
  
  public float a(int i, int j, int k) {
    return 0.0F;
  }
  
  protected Entity findTarget() {
    return null;
  }
  
  public boolean canSpawn() {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.boundingBox.b);
    int k = MathHelper.floor(this.locZ);
    
    return (super.canSpawn()) && (a(i, j, k) >= 0.0F);
  }
  
  public boolean bS() {
    return this.pathEntity != null;
  }
  
  public void setPathEntity(PathEntity pathentity) {
    this.pathEntity = pathentity;
  }
  
  public Entity bT() {
    return this.target;
  }
  
  public void setTarget(Entity entity) {
    this.target = entity;
  }
  
  public boolean bU() {
    return b(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
  }
  
  public boolean b(int i, int j, int k) {
    return this.br == -1.0F;
  }
  
  public void a(int i, int j, int k, int l) {
    this.bq.b(i, j, k);
    this.br = l;
  }
  
  public ChunkCoordinates bV() {
    return this.bq;
  }
  
  public float bW() {
    return this.br;
  }
  
  public void bX() {
    this.br = -1.0F;
  }
  
  public boolean bY() {
    return this.br != -1.0F;
  }
  
  protected void bL() {
    super.bL();
    if ((bN()) && (getLeashHolder() != null) && (getLeashHolder().world == this.world)) {
      Entity entity = getLeashHolder();
      
      a((int)entity.locX, (int)entity.locY, (int)entity.locZ, 5);
      float f = e(entity);
      
      if (((this instanceof EntityTameableAnimal)) && (((EntityTameableAnimal)this).isSitting())) {
        if (f > 10.0F) {
          this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.entity.EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
          unleash(true, true);
        }
        
        return;
      }
      
      if (!this.bt) {
        this.goalSelector.a(2, this.bs);
        getNavigation().a(false);
        this.bt = true;
      }
      
      o(f);
      if (f > 4.0F) {
        getNavigation().a(entity, 1.0D);
      }
      
      if (f > 6.0F) {
        double d0 = (entity.locX - this.locX) / f;
        double d1 = (entity.locY - this.locY) / f;
        double d2 = (entity.locZ - this.locZ) / f;
        
        this.motX += d0 * Math.abs(d0) * 0.4D;
        this.motY += d1 * Math.abs(d1) * 0.4D;
        this.motZ += d2 * Math.abs(d2) * 0.4D;
      }
      
      if (f > 10.0F) {
        this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.entity.EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
        unleash(true, true);
      }
    } else if ((!bN()) && (this.bt)) {
      this.bt = false;
      this.goalSelector.a(this.bs);
      getNavigation().a(true);
      bX();
    }
  }
  
  protected void o(float f) {}
}
