package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class EntitySlime extends EntityInsentient implements IMonster
{
  public float h;
  public float i;
  public float bm;
  private int jumpDelay;
  private Entity lastTarget;
  
  public EntitySlime(World world)
  {
    super(world);
    int i = 1 << this.random.nextInt(3);
    
    this.height = 0.0F;
    this.jumpDelay = (this.random.nextInt(20) + 10);
    setSize(i);
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(16, new Byte((byte)1));
  }
  
  public void setSize(int i)
  {
    this.datawatcher.watch(16, new Byte((byte)i));
    a(0.6F * i, 0.6F * i);
    setPosition(this.locX, this.locY, this.locZ);
    getAttributeInstance(GenericAttributes.maxHealth).setValue(i * i);
    setHealth(getMaxHealth());
    this.b = i;
  }
  
  public int getSize() {
    return this.datawatcher.getByte(16);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setInt("Size", getSize() - 1);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    int i = nbttagcompound.getInt("Size");
    
    if (i < 0) {
      i = 0;
    }
    
    setSize(i + 1);
  }
  
  protected String bP() {
    return "slime";
  }
  
  protected String bV() {
    return "mob.slime." + (getSize() > 1 ? "big" : "small");
  }
  
  public void h() {
    if ((!this.world.isStatic) && (this.world.difficulty == EnumDifficulty.PEACEFUL) && (getSize() > 0)) {
      this.dead = true;
    }
    
    this.i += (this.h - this.i) * 0.5F;
    this.bm = this.i;
    boolean flag = this.onGround;
    
    super.h();
    

    if ((this.onGround) && (!flag)) {
      int i = getSize();
      
      for (int j = 0; j < i * 8; j++) {
        float f = this.random.nextFloat() * 3.1415927F * 2.0F;
        float f1 = this.random.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.sin(f) * i * 0.5F * f1;
        float f3 = MathHelper.cos(f) * i * 0.5F * f1;
        
        this.world.addParticle(bP(), this.locX + f2, this.boundingBox.b, this.locZ + f3, 0.0D, 0.0D, 0.0D);
      }
      
      if (bW()) {
        makeSound(bV(), bf(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
      }
      
      this.h = -0.5F;
    } else if ((!this.onGround) && (flag)) {
      this.h = 1.0F;
    }
    
    bS();
    if (this.world.isStatic) {
      int i = getSize();
      a(0.6F * i, 0.6F * i);
    }
  }
  
  protected void bq() {
    w();
    
    Entity entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0D);
    EntityTargetEvent event = null;
    
    if ((entityhuman != null) && (!entityhuman.equals(this.lastTarget))) {
      event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
    } else if ((this.lastTarget != null) && (entityhuman == null)) {
      event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, org.bukkit.event.entity.EntityTargetEvent.TargetReason.FORGOT_TARGET);
    }
    
    if ((event != null) && (!event.isCancelled())) {
      entityhuman = event.getTarget() == null ? null : ((CraftEntity)event.getTarget()).getHandle();
    }
    
    this.lastTarget = entityhuman;
    

    if (entityhuman != null) {
      a(entityhuman, 10.0F, 20.0F);
    }
    
    if ((this.onGround) && (this.jumpDelay-- <= 0)) {
      this.jumpDelay = bR();
      if (entityhuman != null) {
        this.jumpDelay /= 3;
      }
      
      this.bc = true;
      if (bY()) {
        makeSound(bV(), bf(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }
      
      this.bd = (1.0F - this.random.nextFloat() * 2.0F);
      this.be = (1 * getSize());
    } else {
      this.bc = false;
      if (this.onGround) {
        this.bd = (this.be = 0.0F);
      }
    }
  }
  
  protected void bS() {
    this.h *= 0.6F;
  }
  
  protected int bR() {
    return this.random.nextInt(20) + 10;
  }
  
  protected EntitySlime bQ() {
    return new EntitySlime(this.world);
  }
  
  public void die() {
    int i = getSize();
    
    if ((!this.world.isStatic) && (i > 1) && (getHealth() <= 0.0F)) {
      int j = 2 + this.random.nextInt(3);
      

      SlimeSplitEvent event = new SlimeSplitEvent((org.bukkit.entity.Slime)getBukkitEntity(), j);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if ((!event.isCancelled()) && (event.getCount() > 0)) {
        j = event.getCount();
      } else {
        super.die();
        return;
      }
      

      for (int k = 0; k < j; k++) {
        float f = (k % 2 - 0.5F) * i / 4.0F;
        float f1 = (k / 2 - 0.5F) * i / 4.0F;
        EntitySlime entityslime = bQ();
        
        entityslime.setSize(i / 2);
        entityslime.setPositionRotation(this.locX + f, this.locY + 0.5D, this.locZ + f1, this.random.nextFloat() * 360.0F, 0.0F);
        this.world.addEntity(entityslime, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
      }
    }
    
    super.die();
  }
  
  public void b_(EntityHuman entityhuman) {
    if (bT()) {
      int i = getSize();
      
      if ((hasLineOfSight(entityhuman)) && (f(entityhuman) < 0.6D * i * 0.6D * i) && (entityhuman.damageEntity(DamageSource.mobAttack(this), bU()))) {
        makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
      }
    }
  }
  
  protected boolean bT() {
    return getSize() > 1;
  }
  
  protected int bU() {
    return getSize();
  }
  
  protected String aT() {
    return "mob.slime." + (getSize() > 1 ? "big" : "small");
  }
  
  protected String aU() {
    return "mob.slime." + (getSize() > 1 ? "big" : "small");
  }
  
  protected Item getLoot() {
    return getSize() == 1 ? Items.SLIME_BALL : Item.getById(0);
  }
  
  public boolean canSpawn() {
    Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
    
    if ((this.world.getWorldData().getType() == WorldType.FLAT) && (this.random.nextInt(4) != 1)) {
      return false;
    }
    if ((getSize() == 1) || (this.world.difficulty != EnumDifficulty.PEACEFUL)) {
      BiomeBase biomebase = this.world.getBiome(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
      
      if ((biomebase == BiomeBase.SWAMPLAND) && (this.locY > 50.0D) && (this.locY < 70.0D) && (this.random.nextFloat() < 0.5F) && (this.random.nextFloat() < this.world.y()) && (this.world.getLightLevel(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) <= this.random.nextInt(8))) {
        return super.canSpawn();
      }
      
      if ((this.random.nextInt(10) == 0) && (chunk.a(987234911L).nextInt(10) == 0) && (this.locY < 40.0D)) {
        return super.canSpawn();
      }
    }
    
    return false;
  }
  
  protected float bf()
  {
    return 0.4F * getSize();
  }
  
  public int x() {
    return 0;
  }
  
  protected boolean bY() {
    return getSize() > 0;
  }
  
  protected boolean bW() {
    return getSize() > 2;
  }
}
