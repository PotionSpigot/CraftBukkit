package net.minecraft.server;

import java.util.Random;

public class EntityChicken extends EntityAnimal { public float bp;
  public float bq;
  public float br;
  public float bs;
  public float bt = 1.0F;
  public int bu;
  public boolean bv;
  
  public EntityChicken(World world) {
    super(world);
    a(0.3F, 0.7F);
    this.bu = (this.random.nextInt(6000) + 6000);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.4D));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
    this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D, Items.SEEDS, false));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
    this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
  }
  
  public boolean bk() {
    return true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.25D);
  }
  
  public void e()
  {
    if (isChickenJockey()) {
      this.persistent = (!isTypeNotPersistent());
    }
    
    super.e();
    this.bs = this.bp;
    this.br = this.bq;
    this.bq = ((float)(this.bq + (this.onGround ? -1 : 4) * 0.3D));
    if (this.bq < 0.0F) {
      this.bq = 0.0F;
    }
    
    if (this.bq > 1.0F) {
      this.bq = 1.0F;
    }
    
    if ((!this.onGround) && (this.bt < 1.0F)) {
      this.bt = 1.0F;
    }
    
    this.bt = ((float)(this.bt * 0.9D));
    if ((!this.onGround) && (this.motY < 0.0D)) {
      this.motY *= 0.6D;
    }
    
    this.bp += this.bt * 2.0F;
    if ((!this.world.isStatic) && (!isBaby()) && (!isChickenJockey()) && (--this.bu <= 0)) {
      makeSound("mob.chicken.plop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
      a(Items.EGG, 1);
      this.bu = (this.random.nextInt(6000) + 6000);
    }
  }
  
  protected void b(float f) {}
  
  protected String t() {
    return "mob.chicken.say";
  }
  
  protected String aT() {
    return "mob.chicken.hurt";
  }
  
  protected String aU() {
    return "mob.chicken.hurt";
  }
  
  protected void a(int i, int j, int k, Block block) {
    makeSound("mob.chicken.step", 0.15F, 1.0F);
  }
  
  protected Item getLoot() {
    return Items.FEATHER;
  }
  
  protected void dropDeathLoot(boolean flag, int i) {
    int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
    
    for (int k = 0; k < j; k++) {
      a(Items.FEATHER, 1);
    }
    
    if (isBurning()) {
      a(Items.COOKED_CHICKEN, 1);
    } else {
      a(Items.RAW_CHICKEN, 1);
    }
  }
  
  public EntityChicken b(EntityAgeable entityageable) {
    return new EntityChicken(this.world);
  }
  
  public boolean c(ItemStack itemstack) {
    return (itemstack != null) && ((itemstack.getItem() instanceof ItemSeeds));
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    this.bv = nbttagcompound.getBoolean("IsChickenJockey");
  }
  
  protected int getExpValue(EntityHuman entityhuman) {
    return isChickenJockey() ? 10 : super.getExpValue(entityhuman);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setBoolean("IsChickenJockey", this.bv);
  }
  
  protected boolean isTypeNotPersistent() {
    return (isChickenJockey()) && (this.passenger == null);
  }
  
  public void ac() {
    super.ac();
    float f = MathHelper.sin(this.aM * 3.1415927F / 180.0F);
    float f1 = MathHelper.cos(this.aM * 3.1415927F / 180.0F);
    float f2 = 0.1F;
    float f3 = 0.0F;
    
    this.passenger.setPosition(this.locX + f2 * f, this.locY + this.length * 0.5F + this.passenger.ad() + f3, this.locZ - f2 * f1);
    if ((this.passenger instanceof EntityLiving)) {
      ((EntityLiving)this.passenger).aM = this.aM;
    }
  }
  
  public boolean isChickenJockey() {
    return this.bv;
  }
  
  public void i(boolean flag) {
    this.bv = flag;
  }
  
  public EntityAgeable createChild(EntityAgeable entityageable) {
    return b(entityageable);
  }
}
