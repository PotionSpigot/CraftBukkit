package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.PluginManager;
import org.spigotmc.SpigotWorldConfig;

public class EntityWither extends EntityMonster implements IRangedEntity
{
  private float[] bp = new float[2];
  private float[] bq = new float[2];
  private float[] br = new float[2];
  private float[] bs = new float[2];
  private int[] bt = new int[2];
  private int[] bu = new int[2];
  private int bv;
  private static final IEntitySelector bw = new EntitySelectorNotUndead();
  
  public EntityWither(World world) {
    super(world);
    setHealth(getMaxHealth());
    a(0.9F, 4.0F);
    this.fireProof = true;
    getNavigation().e(true);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 40, 20.0F));
    this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
    this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, false, false, bw));
    this.b = 50;
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(17, new Integer(0));
    this.datawatcher.a(18, new Integer(0));
    this.datawatcher.a(19, new Integer(0));
    this.datawatcher.a(20, new Integer(0));
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setInt("Invul", ca());
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    s(nbttagcompound.getInt("Invul"));
  }
  
  protected String t() {
    return "mob.wither.idle";
  }
  
  protected String aT() {
    return "mob.wither.hurt";
  }
  
  protected String aU() {
    return "mob.wither.death";
  }
  
  public void e() {
    this.motY *= 0.6000000238418579D;
    



    if ((!this.world.isStatic) && (t(0) > 0)) {
      Entity entity = this.world.getEntity(t(0));
      
      if (entity != null) {
        if ((this.locY < entity.locY) || ((!cb()) && (this.locY < entity.locY + 5.0D))) {
          if (this.motY < 0.0D) {
            this.motY = 0.0D;
          }
          
          this.motY += (0.5D - this.motY) * 0.6000000238418579D;
        }
        
        double d3 = entity.locX - this.locX;
        
        double d0 = entity.locZ - this.locZ;
        double d1 = d3 * d3 + d0 * d0;
        if (d1 > 9.0D) {
          double d2 = MathHelper.sqrt(d1);
          this.motX += (d3 / d2 * 0.5D - this.motX) * 0.6000000238418579D;
          this.motZ += (d0 / d2 * 0.5D - this.motZ) * 0.6000000238418579D;
        }
      }
    }
    
    if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D) {
      this.yaw = ((float)Math.atan2(this.motZ, this.motX) * 57.295776F - 90.0F);
    }
    
    super.e();
    


    for (int i = 0; i < 2; i++) {
      this.bs[i] = this.bq[i];
      this.br[i] = this.bp[i];
    }
    


    for (i = 0; i < 2; i++) {
      int j = t(i + 1);
      Entity entity1 = null;
      
      if (j > 0) {
        entity1 = this.world.getEntity(j);
      }
      
      if (entity1 != null) {
        double d0 = u(i + 1);
        double d1 = v(i + 1);
        double d2 = w(i + 1);
        double d4 = entity1.locX - d0;
        double d5 = entity1.locY + entity1.getHeadHeight() - d1;
        double d6 = entity1.locZ - d2;
        double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
        float f = (float)(Math.atan2(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f1 = (float)-(Math.atan2(d5, d7) * 180.0D / 3.1415927410125732D);
        
        this.bp[i] = b(this.bp[i], f1, 40.0F);
        this.bq[i] = b(this.bq[i], f, 10.0F);
      } else {
        this.bq[i] = b(this.bq[i], this.aM, 10.0F);
      }
    }
    
    boolean flag = cb();
    
    for (int j = 0; j < 3; j++) {
      double d8 = u(j);
      double d9 = v(j);
      double d10 = w(j);
      
      this.world.addParticle("smoke", d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
      if ((flag) && (this.world.random.nextInt(4) == 0)) {
        this.world.addParticle("mobSpell", d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
      }
    }
    
    if (ca() > 0) {
      for (j = 0; j < 3; j++) {
        this.world.addParticle("mobSpell", this.locX + this.random.nextGaussian() * 1.0D, this.locY + this.random.nextFloat() * 3.3F, this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
      }
    }
  }
  

  protected void bn()
  {
    if (ca() > 0) {
      int i = ca() - 1;
      int viewDistance; if (i <= 0)
      {
        ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), 7.0F, false);
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          this.world.createExplosion(this, this.locX, this.locY + getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
        }
        

        this.world.createExplosion(this, this.locX, this.locY + getHeadHeight(), this.locZ, 7.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
        

        viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
        for (EntityPlayer player : this.world.players) {
          double deltaX = this.locX - player.locX;
          double deltaZ = this.locZ - player.locZ;
          double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
          if ((this.world.spigotConfig.witherSpawnSoundRadius <= 0) || (distanceSquared <= this.world.spigotConfig.witherSpawnSoundRadius * this.world.spigotConfig.witherSpawnSoundRadius)) {
            if (distanceSquared > viewDistance * viewDistance) {
              double deltaLength = Math.sqrt(distanceSquared);
              double relativeX = player.locX + deltaX / deltaLength * viewDistance;
              double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
              player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, (int)relativeX, (int)this.locY, (int)relativeZ, 0, true));
            } else {
              player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, (int)this.locX, (int)this.locY, (int)this.locZ, 0, true));
            }
          }
        }
      }
      
      s(i);
      
      if (this.ticksLived % 10 == 0) {
        heal(10.0F, EntityRegainHealthEvent.RegainReason.WITHER_SPAWN);
      }
    } else {
      super.bn();
      


      for (int i = 1; i < 3; i++) {
        if (this.ticksLived >= this.bt[(i - 1)]) {
          this.bt[(i - 1)] = (this.ticksLived + 10 + this.random.nextInt(10));
          if ((this.world.difficulty == EnumDifficulty.NORMAL) || (this.world.difficulty == EnumDifficulty.HARD)) {
            int i1001 = i - 1;
            int i1003 = this.bu[(i - 1)];
            
            this.bu[i1001] = (this.bu[(i - 1)] + 1);
            if (i1003 > 15) {
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.a(this.random, this.locX - f, this.locX + f);
              double d1 = MathHelper.a(this.random, this.locY - f1, this.locY + f1);
              double d2 = MathHelper.a(this.random, this.locZ - f, this.locZ + f);
              
              a(i + 1, d0, d1, d2, true);
              this.bu[(i - 1)] = 0;
            }
          }
          
          int j = t(i);
          if (j > 0) {
            Entity entity = this.world.getEntity(j);
            
            if ((entity != null) && (entity.isAlive()) && (f(entity) <= 900.0D) && (hasLineOfSight(entity))) {
              a(i + 1, (EntityLiving)entity);
              this.bt[(i - 1)] = (this.ticksLived + 40 + this.random.nextInt(20));
              this.bu[(i - 1)] = 0;
            } else {
              b(i, 0);
            }
          } else {
            List list = this.world.a(EntityLiving.class, this.boundingBox.grow(20.0D, 8.0D, 20.0D), bw);
            
            for (int i1 = 0; (i1 < 10) && (!list.isEmpty()); i1++) {
              EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));
              
              if ((entityliving != this) && (entityliving.isAlive()) && (hasLineOfSight(entityliving))) {
                if ((entityliving instanceof EntityHuman)) {
                  if (((EntityHuman)entityliving).abilities.isInvulnerable) break;
                  b(i, entityliving.getId()); break;
                }
                
                b(i, entityliving.getId());
                
                break;
              }
              
              list.remove(entityliving);
            }
          }
        }
      }
      
      if (getGoalTarget() != null) {
        b(0, getGoalTarget().getId());
      } else {
        b(0, 0);
      }
      
      if (this.bv > 0) {
        this.bv -= 1;
        if ((this.bv == 0) && (this.world.getGameRules().getBoolean("mobGriefing"))) {
          i = MathHelper.floor(this.locY);
          int j = MathHelper.floor(this.locX);
          int j1 = MathHelper.floor(this.locZ);
          boolean flag = false;
          
          for (int k1 = -1; k1 <= 1; k1++) {
            for (int l1 = -1; l1 <= 1; l1++) {
              for (int i2 = 0; i2 <= 3; i2++) {
                int j2 = j + k1;
                int k2 = i + i2;
                int l2 = j1 + l1;
                Block block = this.world.getType(j2, k2, l2);
                
                if ((block.getMaterial() != Material.AIR) && (block != Blocks.BEDROCK) && (block != Blocks.ENDER_PORTAL) && (block != Blocks.ENDER_PORTAL_FRAME) && (block != Blocks.COMMAND))
                {
                  if (!org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityChangeBlockEvent(this, j2, k2, l2, Blocks.AIR, 0).isCancelled())
                  {



                    flag = (this.world.setAir(j2, k2, l2, true)) || (flag);
                  }
                }
              }
            }
          }
          if (flag) {
            this.world.a((EntityHuman)null, 1012, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
          }
        }
      }
      
      if (this.ticksLived % 20 == 0) {
        heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
      }
    }
  }
  
  public void bZ() {
    s(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void as() {}
  
  public int aV() {
    return 4;
  }
  
  private double u(int i) {
    if (i <= 0) {
      return this.locX;
    }
    float f = (this.aM + 180 * (i - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    
    return this.locX + f1 * 1.3D;
  }
  
  private double v(int i)
  {
    return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
  }
  
  private double w(int i) {
    if (i <= 0) {
      return this.locZ;
    }
    float f = (this.aM + 180 * (i - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    
    return this.locZ + f1 * 1.3D;
  }
  
  private float b(float f, float f1, float f2)
  {
    float f3 = MathHelper.g(f1 - f);
    
    if (f3 > f2) {
      f3 = f2;
    }
    
    if (f3 < -f2) {
      f3 = -f2;
    }
    
    return f + f3;
  }
  
  private void a(int i, EntityLiving entityliving) {
    a(i, entityliving.locX, entityliving.locY + entityliving.getHeadHeight() * 0.5D, entityliving.locZ, (i == 0) && (this.random.nextFloat() < 0.001F));
  }
  
  private void a(int i, double d0, double d1, double d2, boolean flag) {
    this.world.a((EntityHuman)null, 1014, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
    double d3 = u(i);
    double d4 = v(i);
    double d5 = w(i);
    double d6 = d0 - d3;
    double d7 = d1 - d4;
    double d8 = d2 - d5;
    EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d6, d7, d8);
    
    if (flag) {
      entitywitherskull.setCharged(true);
    }
    
    entitywitherskull.locY = d4;
    entitywitherskull.locX = d3;
    entitywitherskull.locZ = d5;
    this.world.addEntity(entitywitherskull);
  }
  
  public void a(EntityLiving entityliving, float f) {
    a(0, entityliving);
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable())
      return false;
    if (damagesource == DamageSource.DROWN)
      return false;
    if (ca() > 0) {
      return false;
    }
    

    if (cb()) {
      Entity entity = damagesource.i();
      if ((entity instanceof EntityArrow)) {
        return false;
      }
    }
    
    Entity entity = damagesource.getEntity();
    if ((entity != null) && (!(entity instanceof EntityHuman)) && ((entity instanceof EntityLiving)) && (((EntityLiving)entity).getMonsterType() == getMonsterType())) {
      return false;
    }
    if (this.bv <= 0) {
      this.bv = 20;
    }
    
    for (int i = 0; i < this.bu.length; i++) {
      this.bu[i] += 3;
    }
    
    return super.damageEntity(damagesource, f);
  }
  

  protected void dropDeathLoot(boolean flag, int i)
  {
    a(Items.NETHER_STAR, 1);
    if (!this.world.isStatic) {
      Iterator iterator = this.world.a(EntityHuman.class, this.boundingBox.grow(50.0D, 100.0D, 50.0D)).iterator();
      
      while (iterator.hasNext()) {
        EntityHuman entityhuman = (EntityHuman)iterator.next();
        
        entityhuman.a(AchievementList.J);
      }
    }
  }
  
  protected void w() {
    this.aU = 0;
  }
  
  protected void b(float f) {}
  
  public void addEffect(MobEffect mobeffect) {}
  
  protected boolean bk() {
    return true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.6000000238418579D);
    getAttributeInstance(GenericAttributes.b).setValue(40.0D);
  }
  
  public int ca() {
    return this.datawatcher.getInt(20);
  }
  
  public void s(int i) {
    this.datawatcher.watch(20, Integer.valueOf(i));
  }
  
  public int t(int i) {
    return this.datawatcher.getInt(17 + i);
  }
  
  public void b(int i, int j) {
    this.datawatcher.watch(17 + i, Integer.valueOf(j));
  }
  
  public boolean cb() {
    return getHealth() <= getMaxHealth() / 2.0F;
  }
  
  public EnumMonsterType getMonsterType() {
    return EnumMonsterType.UNDEAD;
  }
  
  public void mount(Entity entity) {
    this.vehicle = null;
  }
}
