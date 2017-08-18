package net.minecraft.server;

import java.util.Random;

public class EntityOcelot extends EntityTameableAnimal { public boolean spawnBonus = true;
  private PathfinderGoalTempt bq;
  
  public EntityOcelot(World world) {
    super(world);
    a(0.6F, 0.8F);
    getNavigation().a(true);
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
    this.goalSelector.a(2, this.bp);
    this.goalSelector.a(3, this.bq = new PathfinderGoalTempt(this, 0.6D, Items.RAW_FISH, true));
    this.goalSelector.a(4, new PathfinderGoalAvoidPlayer(this, EntityHuman.class, 16.0F, 0.8D, 1.33D));
    this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 5.0F));
    this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 1.33D));
    this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
    this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
    this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.8D));
    this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.8D));
    this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
    this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, 750, false));
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(18, Byte.valueOf((byte)0));
  }
  




  public void h()
  {
    if ((this.world.spigotConfig.altHopperTicking) && (isSitting())) {
      int xi = MathHelper.floor(this.boundingBox.a);
      int yi = MathHelper.floor(this.boundingBox.b) - 1;
      int zi = MathHelper.floor(this.boundingBox.c);
      int xf = MathHelper.floor(this.boundingBox.d);
      int yf = MathHelper.floor(this.boundingBox.e) - 1;
      int zf = MathHelper.floor(this.boundingBox.f);
      for (int a = xi; a <= xf; a++) {
        for (int c = zi; c <= zf; c++) {
          for (int b = yi; b <= yf; b++) {
            this.world.updateChestAndHoppers(a, b, c);
          }
        }
      }
    }
    super.h();
  }
  
  public void bp()
  {
    if (getControllerMove().a()) {
      double d0 = getControllerMove().b();
      
      if (d0 == 0.6D) {
        setSneaking(true);
        setSprinting(false);
      } else if (d0 == 1.33D) {
        setSneaking(false);
        setSprinting(true);
      } else {
        setSneaking(false);
        setSprinting(false);
      }
    } else {
      setSneaking(false);
      setSprinting(false);
    }
  }
  
  protected boolean isTypeNotPersistent() {
    return !isTamed();
  }
  
  public boolean bk() {
    return true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
  }
  
  protected void b(float f) {}
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setInt("CatType", getCatType());
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    setCatType(nbttagcompound.getInt("CatType"));
  }
  
  protected String t() {
    return isTamed() ? "mob.cat.meow" : this.random.nextInt(4) == 0 ? "mob.cat.purreow" : ce() ? "mob.cat.purr" : "";
  }
  
  protected String aT() {
    return "mob.cat.hitt";
  }
  
  protected String aU() {
    return "mob.cat.hitt";
  }
  
  protected float bf() {
    return 0.4F;
  }
  
  protected Item getLoot() {
    return Items.LEATHER;
  }
  
  public boolean n(Entity entity) {
    return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    this.bp.setSitting(false);
    return super.damageEntity(damagesource, f);
  }
  
  protected void dropDeathLoot(boolean flag, int i) {}
  
  public boolean a(EntityHuman entityhuman)
  {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if (isTamed()) {
      if ((e(entityhuman)) && (!this.world.isStatic) && (!c(itemstack))) {
        this.bp.setSitting(!isSitting());
      }
    } else if ((this.bq.f()) && (itemstack != null) && (itemstack.getItem() == Items.RAW_FISH) && (entityhuman.f(this) < 9.0D)) {
      if (!entityhuman.abilities.canInstantlyBuild) {
        itemstack.count -= 1;
      }
      
      if (itemstack.count <= 0) {
        entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
      }
      
      if (!this.world.isStatic)
      {
        if ((this.random.nextInt(3) == 0) && (!org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled())) {
          setTamed(true);
          setCatType(1 + this.world.random.nextInt(3));
          setOwnerUUID(entityhuman.getUniqueID().toString());
          i(true);
          this.bp.setSitting(true);
          this.world.broadcastEntityEffect(this, (byte)7);
        } else {
          i(false);
          this.world.broadcastEntityEffect(this, (byte)6);
        }
      }
      
      return true;
    }
    
    return super.a(entityhuman);
  }
  
  public EntityOcelot b(EntityAgeable entityageable) {
    EntityOcelot entityocelot = new EntityOcelot(this.world);
    
    if (isTamed()) {
      entityocelot.setOwnerUUID(getOwnerUUID());
      entityocelot.setTamed(true);
      entityocelot.setCatType(getCatType());
    }
    
    return entityocelot;
  }
  
  public boolean c(ItemStack itemstack) {
    return (itemstack != null) && (itemstack.getItem() == Items.RAW_FISH);
  }
  
  public boolean mate(EntityAnimal entityanimal) {
    if (entityanimal == this)
      return false;
    if (!isTamed())
      return false;
    if (!(entityanimal instanceof EntityOcelot)) {
      return false;
    }
    EntityOcelot entityocelot = (EntityOcelot)entityanimal;
    
    return entityocelot.isTamed();
  }
  
  public int getCatType()
  {
    return this.datawatcher.getByte(18);
  }
  
  public void setCatType(int i) {
    this.datawatcher.watch(18, Byte.valueOf((byte)i));
  }
  
  public boolean canSpawn() {
    if (this.world.random.nextInt(3) == 0) {
      return false;
    }
    if ((this.world.b(this.boundingBox)) && (this.world.getCubes(this, this.boundingBox).isEmpty()) && (!this.world.containsLiquid(this.boundingBox))) {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.boundingBox.b);
      int k = MathHelper.floor(this.locZ);
      
      if (j < 63) {
        return false;
      }
      
      Block block = this.world.getType(i, j - 1, k);
      
      if ((block == Blocks.GRASS) || (block.getMaterial() == Material.LEAVES)) {
        return true;
      }
    }
    
    return false;
  }
  
  public String getName()
  {
    return isTamed() ? LocaleI18n.get("entity.Cat.name") : hasCustomName() ? getCustomName() : super.getName();
  }
  
  public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
    groupdataentity = super.prepare(groupdataentity);
    if ((this.spawnBonus) && (this.world.random.nextInt(7) == 0)) {
      for (int i = 0; i < 2; i++) {
        EntityOcelot entityocelot = new EntityOcelot(this.world);
        
        entityocelot.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
        entityocelot.setAge(41536);
        this.world.addEntity(entityocelot, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.OCELOT_BABY);
      }
    }
    
    return groupdataentity;
  }
  
  public EntityAgeable createChild(EntityAgeable entityageable) {
    return b(entityageable);
  }
}
