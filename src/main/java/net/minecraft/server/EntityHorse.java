package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.event.entity.HorseJumpEvent;

public class EntityHorse extends EntityAnimal implements IInventoryListener
{
  private static final IEntitySelector bu = new EntitySelectorHorse();
  public static final IAttribute attributeJumpStrength = new AttributeRanged("horse.jumpStrength", 0.7D, 0.0D, 2.0D).a("Jump Strength").a(true);
  private static final String[] bw = { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
  private static final String[] bx = { "", "meo", "goo", "dio" };
  private static final int[] by = { 0, 5, 7, 11 };
  private static final String[] bz = { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
  private static final String[] bA = { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
  private static final String[] bB = { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
  private static final String[] bC = { "", "wo_", "wmo", "wdo", "bdo" };
  private int bD;
  private int bE;
  private int bF;
  public int bp;
  public int bq;
  protected boolean br;
  public InventoryHorseChest inventoryChest;
  private boolean bH;
  protected int bs;
  protected float bt;
  private boolean bI;
  private float bJ;
  private float bK;
  private float bL;
  private float bM;
  private float bN;
  private float bO;
  private int bP;
  private String bQ;
  private String[] bR = new String[3];
  public int maxDomestication = 100;
  
  public EntityHorse(World world) {
    super(world);
    a(1.4F, 1.6F);
    this.fireProof = false;
    setHasChest(false);
    getNavigation().a(true);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));
    this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
    this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));
    this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    loadChest();
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(16, Integer.valueOf(0));
    this.datawatcher.a(19, Byte.valueOf((byte)0));
    this.datawatcher.a(20, Integer.valueOf(0));
    this.datawatcher.a(21, String.valueOf(""));
    this.datawatcher.a(22, Integer.valueOf(0));
  }
  
  public void setType(int i) {
    this.datawatcher.watch(19, Byte.valueOf((byte)i));
    cP();
  }
  
  public int getType() {
    return this.datawatcher.getByte(19);
  }
  
  public void setVariant(int i) {
    this.datawatcher.watch(20, Integer.valueOf(i));
    cP();
  }
  
  public int getVariant() {
    return this.datawatcher.getInt(20);
  }
  
  public String getName() {
    if (hasCustomName()) {
      return getCustomName();
    }
    int i = getType();
    
    switch (i) {
    case 0: 
    default: 
      return LocaleI18n.get("entity.horse.name");
    
    case 1: 
      return LocaleI18n.get("entity.donkey.name");
    
    case 2: 
      return LocaleI18n.get("entity.mule.name");
    
    case 3: 
      return LocaleI18n.get("entity.zombiehorse.name");
    }
    
    return LocaleI18n.get("entity.skeletonhorse.name");
  }
  

  private boolean x(int i)
  {
    return (this.datawatcher.getInt(16) & i) != 0;
  }
  
  private void b(int i, boolean flag) {
    int j = this.datawatcher.getInt(16);
    
    if (flag) {
      this.datawatcher.watch(16, Integer.valueOf(j | i));
    } else {
      this.datawatcher.watch(16, Integer.valueOf(j & (i ^ 0xFFFFFFFF)));
    }
  }
  
  public boolean cb() {
    return !isBaby();
  }
  
  public boolean isTame() {
    return x(2);
  }
  
  public boolean cg() {
    return cb();
  }
  
  public String getOwnerUUID() {
    return this.datawatcher.getString(21);
  }
  
  public void setOwnerUUID(String s) {
    this.datawatcher.watch(21, s);
  }
  
  public float ci() {
    int i = getAge();
    
    return i >= 0 ? 1.0F : 0.5F + (41536 - i) / -24000.0F * 0.5F;
  }
  
  public void a(boolean flag) {
    if (flag) {
      a(ci());
    } else {
      a(1.0F);
    }
  }
  
  public boolean cj() {
    return this.br;
  }
  
  public void setTame(boolean flag) {
    b(2, flag);
  }
  
  public void j(boolean flag) {
    this.br = flag;
  }
  
  public boolean bM()
  {
    if (this.world.paperSpigotConfig.allowUndeadHorseLeashing) {
      return super.bM();
    }
    return (!cE()) && (super.bM());
  }
  

  protected void o(float f)
  {
    if ((f > 6.0F) && (cm())) {
      o(false);
    }
  }
  
  public boolean hasChest() {
    return x(8);
  }
  
  public int cl() {
    return this.datawatcher.getInt(22);
  }
  
  private int e(ItemStack itemstack) {
    if (itemstack == null) {
      return 0;
    }
    Item item = itemstack.getItem();
    
    return item == Items.HORSE_ARMOR_DIAMOND ? 3 : item == Items.HORSE_ARMOR_GOLD ? 2 : item == Items.HORSE_ARMOR_IRON ? 1 : 0;
  }
  
  public boolean cm()
  {
    return x(32);
  }
  
  public boolean cn() {
    return x(64);
  }
  
  public boolean co() {
    return x(16);
  }
  
  public boolean cp() {
    return this.bH;
  }
  
  public void d(ItemStack itemstack) {
    this.datawatcher.watch(22, Integer.valueOf(e(itemstack)));
    cP();
  }
  
  public void k(boolean flag) {
    b(16, flag);
  }
  
  public void setHasChest(boolean flag) {
    b(8, flag);
  }
  
  public void m(boolean flag) {
    this.bH = flag;
  }
  
  public void n(boolean flag) {
    b(4, flag);
  }
  
  public int getTemper() {
    return this.bs;
  }
  
  public void setTemper(int i) {
    this.bs = i;
  }
  
  public int v(int i) {
    int j = MathHelper.a(getTemper() + i, 0, getMaxDomestication());
    
    setTemper(j);
    return j;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    Entity entity = damagesource.getEntity();
    
    return (this.passenger != null) && (this.passenger.equals(entity)) ? false : super.damageEntity(damagesource, f);
  }
  
  public int aV() {
    return by[cl()];
  }
  
  public boolean S() {
    return this.passenger == null;
  }
  
  public boolean cr() {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locZ);
    
    this.world.getBiome(i, j);
    return true;
  }
  
  public void cs() {
    if ((!this.world.isStatic) && (hasChest())) {
      a(Item.getItemOf(Blocks.CHEST), 1);
      setHasChest(false);
    }
  }
  
  private void cL() {
    cS();
    this.world.makeSound(this, "eating", 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
  }
  
  protected void b(float f) {
    if (f > 1.0F) {
      makeSound("mob.horse.land", 0.4F, 1.0F);
    }
    
    int i = MathHelper.f(f * 0.5F - 3.0F);
    
    if (i > 0) {
      damageEntity(DamageSource.FALL, i);
      if (this.passenger != null) {
        this.passenger.damageEntity(DamageSource.FALL, i);
      }
      
      Block block = this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.2D - this.lastYaw), MathHelper.floor(this.locZ));
      
      if (block.getMaterial() != Material.AIR) {
        StepSound stepsound = block.stepSound;
        
        this.world.makeSound(this, stepsound.getStepSound(), stepsound.getVolume1() * 0.5F, stepsound.getVolume2() * 0.75F);
      }
    }
  }
  
  private int cM() {
    int i = getType();
    
    return hasChest() ? 17 : 2;
  }
  
  public void loadChest() {
    InventoryHorseChest inventoryhorsechest = this.inventoryChest;
    
    this.inventoryChest = new InventoryHorseChest("HorseChest", cM(), this);
    this.inventoryChest.a(getName());
    if (inventoryhorsechest != null) {
      inventoryhorsechest.b(this);
      int i = Math.min(inventoryhorsechest.getSize(), this.inventoryChest.getSize());
      
      for (int j = 0; j < i; j++) {
        ItemStack itemstack = inventoryhorsechest.getItem(j);
        
        if (itemstack != null) {
          this.inventoryChest.setItem(j, itemstack.cloneItemStack());
        }
      }
      
      inventoryhorsechest = null;
    }
    
    this.inventoryChest.a(this);
    cO();
  }
  
  private void cO() {
    if (!this.world.isStatic) {
      n(this.inventoryChest.getItem(0) != null);
      if (cB()) {
        d(this.inventoryChest.getItem(1));
      }
    }
  }
  
  public void a(InventorySubcontainer inventorysubcontainer) {
    int i = cl();
    boolean flag = cu();
    
    cO();
    if (this.ticksLived > 20) {
      if ((i == 0) && (i != cl())) {
        makeSound("mob.horse.armor", 0.5F, 1.0F);
      } else if (i != cl()) {
        makeSound("mob.horse.armor", 0.5F, 1.0F);
      }
      
      if ((!flag) && (cu())) {
        makeSound("mob.horse.leather", 0.5F, 1.0F);
      }
    }
  }
  
  public boolean canSpawn() {
    cr();
    return super.canSpawn();
  }
  
  protected EntityHorse a(Entity entity, double d0) {
    double d1 = Double.MAX_VALUE;
    Entity entity1 = null;
    List list = this.world.getEntities(entity, entity.boundingBox.a(d0, d0, d0), bu);
    Iterator iterator = list.iterator();
    
    while (iterator.hasNext()) {
      Entity entity2 = (Entity)iterator.next();
      double d2 = entity2.e(entity.locX, entity.locY, entity.locZ);
      
      if (d2 < d1) {
        entity1 = entity2;
        d1 = d2;
      }
    }
    
    return (EntityHorse)entity1;
  }
  
  public double getJumpStrength() {
    return getAttributeInstance(attributeJumpStrength).getValue();
  }
  
  protected String aU() {
    cS();
    int i = getType();
    
    return (i != 1) && (i != 2) ? "mob.horse.death" : i == 4 ? "mob.horse.skeleton.death" : i == 3 ? "mob.horse.zombie.death" : "mob.horse.donkey.death";
  }
  
  protected Item getLoot() {
    boolean flag = this.random.nextInt(4) == 0;
    int i = getType();
    
    return i == 3 ? Items.ROTTEN_FLESH : flag ? Item.getById(0) : i == 4 ? Items.BONE : Items.LEATHER;
  }
  
  protected String aT() {
    cS();
    if (this.random.nextInt(3) == 0) {
      cU();
    }
    
    int i = getType();
    
    return (i != 1) && (i != 2) ? "mob.horse.hit" : i == 4 ? "mob.horse.skeleton.hit" : i == 3 ? "mob.horse.zombie.hit" : "mob.horse.donkey.hit";
  }
  
  public boolean cu() {
    return x(4);
  }
  
  protected String t() {
    cS();
    if ((this.random.nextInt(10) == 0) && (!bh())) {
      cU();
    }
    
    int i = getType();
    
    return (i != 1) && (i != 2) ? "mob.horse.idle" : i == 4 ? "mob.horse.skeleton.idle" : i == 3 ? "mob.horse.zombie.idle" : "mob.horse.donkey.idle";
  }
  
  protected String cv() {
    cS();
    cU();
    int i = getType();
    
    return (i != 3) && (i != 4) ? "mob.horse.donkey.angry" : (i != 1) && (i != 2) ? "mob.horse.angry" : null;
  }
  
  protected void a(int i, int j, int k, Block block) {
    StepSound stepsound = block.stepSound;
    
    if (this.world.getType(i, j + 1, k) == Blocks.SNOW) {
      stepsound = Blocks.SNOW.stepSound;
    }
    
    if (!block.getMaterial().isLiquid()) {
      int l = getType();
      
      if ((this.passenger != null) && (l != 1) && (l != 2)) {
        this.bP += 1;
        if ((this.bP > 5) && (this.bP % 3 == 0)) {
          makeSound("mob.horse.gallop", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
          if ((l == 0) && (this.random.nextInt(10) == 0)) {
            makeSound("mob.horse.breathe", stepsound.getVolume1() * 0.6F, stepsound.getVolume2());
          }
        } else if (this.bP <= 5) {
          makeSound("mob.horse.wood", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
        }
      } else if (stepsound == Block.f) {
        makeSound("mob.horse.wood", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
      } else {
        makeSound("mob.horse.soft", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
      }
    }
  }
  
  protected void aD() {
    super.aD();
    getAttributeMap().b(attributeJumpStrength);
    getAttributeInstance(GenericAttributes.maxHealth).setValue(53.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.22499999403953552D);
  }
  
  public int bB() {
    return 6;
  }
  
  public int getMaxDomestication() {
    return this.maxDomestication;
  }
  
  protected float bf() {
    return 0.8F;
  }
  
  public int q() {
    return 400;
  }
  
  private void cP() {
    this.bQ = null;
  }
  
  public void g(EntityHuman entityhuman) {
    if ((!this.world.isStatic) && ((this.passenger == null) || (this.passenger == entityhuman)) && (isTame())) {
      this.inventoryChest.a(getName());
      entityhuman.openHorseInventory(this, this.inventoryChest);
    }
  }
  
  public boolean a(EntityHuman entityhuman) {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && (itemstack.getItem() == Items.MONSTER_EGG))
      return super.a(entityhuman);
    if ((!isTame()) && (cE()))
      return false;
    if ((isTame()) && (cb()) && (entityhuman.isSneaking())) {
      g(entityhuman);
      return true; }
    if ((cg()) && (this.passenger != null)) {
      return super.a(entityhuman);
    }
    if (itemstack != null) {
      boolean flag = false;
      
      if (cB()) {
        byte b0 = -1;
        
        if (itemstack.getItem() == Items.HORSE_ARMOR_IRON) {
          b0 = 1;
        } else if (itemstack.getItem() == Items.HORSE_ARMOR_GOLD) {
          b0 = 2;
        } else if (itemstack.getItem() == Items.HORSE_ARMOR_DIAMOND) {
          b0 = 3;
        }
        
        if (b0 >= 0) {
          if (!isTame()) {
            cJ();
            return true;
          }
          
          g(entityhuman);
          return true;
        }
      }
      
      if ((!flag) && (!cE())) {
        float f = 0.0F;
        short short1 = 0;
        byte b1 = 0;
        
        if (itemstack.getItem() == Items.WHEAT) {
          f = 2.0F;
          short1 = 60;
          b1 = 3;
        } else if (itemstack.getItem() == Items.SUGAR) {
          f = 1.0F;
          short1 = 30;
          b1 = 3;
        } else if (itemstack.getItem() == Items.BREAD) {
          f = 7.0F;
          short1 = 180;
          b1 = 3;
        } else if (Block.a(itemstack.getItem()) == Blocks.HAY_BLOCK) {
          f = 20.0F;
          short1 = 180;
        } else if (itemstack.getItem() == Items.APPLE) {
          f = 3.0F;
          short1 = 60;
          b1 = 3;
        } else if (itemstack.getItem() == Items.CARROT_GOLDEN) {
          f = 4.0F;
          short1 = 60;
          b1 = 5;
          if ((isTame()) && (getAge() == 0)) {
            flag = true;
            f(entityhuman);
          }
        } else if (itemstack.getItem() == Items.GOLDEN_APPLE) {
          f = 10.0F;
          short1 = 240;
          b1 = 10;
          if ((isTame()) && (getAge() == 0)) {
            flag = true;
            f(entityhuman);
          }
        }
        
        if ((getHealth() < getMaxHealth()) && (f > 0.0F)) {
          heal(f, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.EATING);
          flag = true;
        }
        
        if ((!cb()) && (short1 > 0)) {
          a(short1);
          flag = true;
        }
        
        if ((b1 > 0) && ((flag) || (!isTame())) && (b1 < getMaxDomestication())) {
          flag = true;
          v(b1);
        }
        
        if (flag) {
          cL();
        }
      }
      
      if ((!isTame()) && (!flag)) {
        if ((itemstack != null) && (itemstack.a(entityhuman, this))) {
          return true;
        }
        
        cJ();
        return true;
      }
      
      if ((!flag) && (cC()) && (!hasChest()) && (itemstack.getItem() == Item.getItemOf(Blocks.CHEST))) {
        setHasChest(true);
        makeSound("mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        flag = true;
        loadChest();
      }
      
      if ((!flag) && (cg()) && (!cu()) && (itemstack.getItem() == Items.SADDLE)) {
        g(entityhuman);
        return true;
      }
      
      if (flag) {
        if (!entityhuman.abilities.canInstantlyBuild) { if (--itemstack.count == 0) {
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
          }
        }
        return true;
      }
    }
    
    if ((cg()) && (this.passenger == null)) {
      if ((itemstack != null) && (itemstack.a(entityhuman, this))) {
        return true;
      }
      i(entityhuman);
      return true;
    }
    
    return super.a(entityhuman);
  }
  

  private void i(EntityHuman entityhuman)
  {
    entityhuman.yaw = this.yaw;
    entityhuman.pitch = this.pitch;
    o(false);
    p(false);
    if (!this.world.isStatic) {
      entityhuman.mount(this);
    }
  }
  
  public boolean cB() {
    return getType() == 0;
  }
  
  public boolean cC() {
    int i = getType();
    
    return (i == 2) || (i == 1);
  }
  
  protected boolean bh() {
    return (this.passenger != null) && (cu());
  }
  
  public boolean cE() {
    int i = getType();
    
    return (i == 3) || (i == 4);
  }
  
  public boolean cF() {
    return (cE()) || (getType() == 2);
  }
  
  public boolean c(ItemStack itemstack) {
    return false;
  }
  
  private void cR() {
    this.bp = 1;
  }
  
  public void die(DamageSource damagesource) {
    super.die(damagesource);
  }
  





  protected void dropDeathLoot(boolean flag, int i)
  {
    super.dropDeathLoot(flag, i);
    

    if (!this.world.isStatic) {
      dropChest();
    }
  }
  
  public void e()
  {
    if (this.random.nextInt(200) == 0) {
      cR();
    }
    
    super.e();
    if (!this.world.isStatic) {
      if ((this.random.nextInt(900) == 0) && (this.deathTicks == 0)) {
        heal(1.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.REGEN);
      }
      
      if ((!cm()) && (this.passenger == null) && (this.random.nextInt(300) == 0) && (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY) - 1, MathHelper.floor(this.locZ)) == Blocks.GRASS)) {
        o(true);
      }
      
      if ((cm()) && (++this.bD > 50)) {
        this.bD = 0;
        o(false);
      }
      
      if ((co()) && (!cb()) && (!cm())) {
        EntityHorse entityhorse = a(this, 16.0D);
        
        if ((entityhorse != null) && (f(entityhorse) > 4.0D)) {
          PathEntity pathentity = this.world.findPath(this, entityhorse, 16.0F, true, false, false, true);
          
          setPathEntity(pathentity);
        }
      }
    }
  }
  
  public void h() {
    super.h();
    if ((this.world.isStatic) && (this.datawatcher.a())) {
      this.datawatcher.e();
      cP();
    }
    
    if ((this.bE > 0) && (++this.bE > 30)) {
      this.bE = 0;
      b(128, false);
    }
    
    if ((!this.world.isStatic) && (this.bF > 0) && (++this.bF > 20)) {
      this.bF = 0;
      p(false);
    }
    
    if ((this.bp > 0) && (++this.bp > 8)) {
      this.bp = 0;
    }
    
    if (this.bq > 0) {
      this.bq += 1;
      if (this.bq > 300) {
        this.bq = 0;
      }
    }
    
    this.bK = this.bJ;
    if (cm()) {
      this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
      if (this.bJ > 1.0F) {
        this.bJ = 1.0F;
      }
    } else {
      this.bJ += (0.0F - this.bJ) * 0.4F - 0.05F;
      if (this.bJ < 0.0F) {
        this.bJ = 0.0F;
      }
    }
    
    this.bM = this.bL;
    if (cn()) {
      this.bK = (this.bJ = 0.0F);
      this.bL += (1.0F - this.bL) * 0.4F + 0.05F;
      if (this.bL > 1.0F) {
        this.bL = 1.0F;
      }
    } else {
      this.bI = false;
      this.bL += (0.8F * this.bL * this.bL * this.bL - this.bL) * 0.6F - 0.05F;
      if (this.bL < 0.0F) {
        this.bL = 0.0F;
      }
    }
    
    this.bO = this.bN;
    if (x(128)) {
      this.bN += (1.0F - this.bN) * 0.7F + 0.05F;
      if (this.bN > 1.0F) {
        this.bN = 1.0F;
      }
    } else {
      this.bN += (0.0F - this.bN) * 0.7F - 0.05F;
      if (this.bN < 0.0F) {
        this.bN = 0.0F;
      }
    }
  }
  
  private void cS() {
    if (!this.world.isStatic) {
      this.bE = 1;
      b(128, true);
    }
  }
  
  private boolean cT() {
    return (this.passenger == null) && (this.vehicle == null) && (isTame()) && (cb()) && (!cF()) && (getHealth() >= getMaxHealth());
  }
  
  public void e(boolean flag) {
    b(32, flag);
  }
  
  public void o(boolean flag) {
    e(flag);
  }
  
  public void p(boolean flag) {
    if (flag) {
      o(false);
    }
    
    b(64, flag);
  }
  
  private void cU() {
    if (!this.world.isStatic) {
      this.bF = 1;
      p(true);
    }
  }
  
  public void cJ() {
    cU();
    String s = cv();
    
    if (s != null) {
      makeSound(s, bf(), bg());
    }
  }
  
  public void dropChest() {
    a(this, this.inventoryChest);
    cs();
  }
  
  private void a(Entity entity, InventoryHorseChest inventoryhorsechest) {
    if ((inventoryhorsechest != null) && (!this.world.isStatic)) {
      for (int i = 0; i < inventoryhorsechest.getSize(); i++) {
        ItemStack itemstack = inventoryhorsechest.getItem(i);
        
        if (itemstack != null) {
          a(itemstack, 0.0F);
        }
      }
    }
  }
  
  public boolean h(EntityHuman entityhuman) {
    setOwnerUUID(entityhuman.getUniqueID().toString());
    setTame(true);
    return true;
  }
  
  public void e(float f, float f1) {
    if ((this.passenger != null) && ((this.passenger instanceof EntityLiving)) && (cu())) {
      this.lastYaw = (this.yaw = this.passenger.yaw);
      this.pitch = (this.passenger.pitch * 0.5F);
      b(this.yaw, this.pitch);
      this.aO = (this.aM = this.yaw);
      f = ((EntityLiving)this.passenger).bd * 0.5F;
      f1 = ((EntityLiving)this.passenger).be;
      if (f1 <= 0.0F) {
        f1 *= 0.25F;
        this.bP = 0;
      }
      
      if ((this.onGround) && (this.bt == 0.0F) && (cn()) && (!this.bI)) {
        f = 0.0F;
        f1 = 0.0F;
      }
      
      if ((this.bt > 0.0F) && (!cj()) && (this.onGround)) {
        this.motY = (getJumpStrength() * this.bt);
        if (hasEffect(MobEffectList.JUMP)) {
          this.motY += (getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F;
        }
        
        j(true);
        this.al = true;
        if (f1 > 0.0F) {
          float f2 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
          float f3 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
          
          this.motX += -0.4F * f2 * this.bt;
          this.motZ += 0.4F * f3 * this.bt;
          makeSound("mob.horse.jump", 0.4F, 1.0F);
        }
        
        this.bt = 0.0F;
      }
      
      this.W = 1.0F;
      this.aQ = (bl() * 0.1F);
      if (!this.world.isStatic) {
        i((float)getAttributeInstance(GenericAttributes.d).getValue());
        super.e(f, f1);
      }
      
      if (this.onGround) {
        this.bt = 0.0F;
        j(false);
      }
      
      this.aE = this.aF;
      double d0 = this.locX - this.lastX;
      double d1 = this.locZ - this.lastZ;
      float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
      
      if (f4 > 1.0F) {
        f4 = 1.0F;
      }
      
      this.aF += (f4 - this.aF) * 0.4F;
      this.aG += this.aF;
    } else {
      this.W = 0.5F;
      this.aQ = 0.02F;
      super.e(f, f1);
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setBoolean("EatingHaystack", cm());
    nbttagcompound.setBoolean("ChestedHorse", hasChest());
    nbttagcompound.setBoolean("HasReproduced", cp());
    nbttagcompound.setBoolean("Bred", co());
    nbttagcompound.setInt("Type", getType());
    nbttagcompound.setInt("Variant", getVariant());
    nbttagcompound.setInt("Temper", getTemper());
    nbttagcompound.setBoolean("Tame", isTame());
    nbttagcompound.setString("OwnerUUID", getOwnerUUID());
    nbttagcompound.setInt("Bukkit.MaxDomestication", this.maxDomestication);
    if (hasChest()) {
      NBTTagList nbttaglist = new NBTTagList();
      
      for (int i = 2; i < this.inventoryChest.getSize(); i++) {
        ItemStack itemstack = this.inventoryChest.getItem(i);
        
        if (itemstack != null) {
          NBTTagCompound nbttagcompound1 = new NBTTagCompound();
          
          nbttagcompound1.setByte("Slot", (byte)i);
          itemstack.save(nbttagcompound1);
          nbttaglist.add(nbttagcompound1);
        }
      }
      
      nbttagcompound.set("Items", nbttaglist);
    }
    
    if (this.inventoryChest.getItem(1) != null) {
      nbttagcompound.set("ArmorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
    }
    
    if (this.inventoryChest.getItem(0) != null) {
      nbttagcompound.set("SaddleItem", this.inventoryChest.getItem(0).save(new NBTTagCompound()));
    }
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    o(nbttagcompound.getBoolean("EatingHaystack"));
    k(nbttagcompound.getBoolean("Bred"));
    setHasChest(nbttagcompound.getBoolean("ChestedHorse"));
    m(nbttagcompound.getBoolean("HasReproduced"));
    setType(nbttagcompound.getInt("Type"));
    setVariant(nbttagcompound.getInt("Variant"));
    setTemper(nbttagcompound.getInt("Temper"));
    setTame(nbttagcompound.getBoolean("Tame"));
    if (nbttagcompound.hasKeyOfType("OwnerUUID", 8)) {
      setOwnerUUID(nbttagcompound.getString("OwnerUUID"));

    }
    else if (nbttagcompound.hasKey("OwnerName")) {
      String owner = nbttagcompound.getString("OwnerName");
      if ((owner != null) && (!owner.isEmpty())) {
        setOwnerUUID(NameReferencingFileConverter.a(owner));
      }
    }
    

    if (nbttagcompound.hasKey("Bukkit.MaxDomestication")) {
      this.maxDomestication = nbttagcompound.getInt("Bukkit.MaxDomestication");
    }
    
    AttributeInstance attributeinstance = getAttributeMap().a("Speed");
    
    if (attributeinstance != null) {
      getAttributeInstance(GenericAttributes.d).setValue(attributeinstance.b() * 0.25D);
    }
    
    if (hasChest()) {
      NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
      
      loadChest();
      
      for (int i = 0; i < nbttaglist.size(); i++) {
        NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
        int j = nbttagcompound1.getByte("Slot") & 0xFF;
        
        if ((j >= 2) && (j < this.inventoryChest.getSize())) {
          this.inventoryChest.setItem(j, ItemStack.createStack(nbttagcompound1));
        }
      }
    }
    


    if (nbttagcompound.hasKeyOfType("ArmorItem", 10)) {
      ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("ArmorItem"));
      if ((itemstack != null) && (a(itemstack.getItem()))) {
        this.inventoryChest.setItem(1, itemstack);
      }
    }
    
    if (nbttagcompound.hasKeyOfType("SaddleItem", 10)) {
      ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("SaddleItem"));
      if ((itemstack != null) && (itemstack.getItem() == Items.SADDLE)) {
        this.inventoryChest.setItem(0, itemstack);
      }
    } else if (nbttagcompound.getBoolean("Saddle")) {
      this.inventoryChest.setItem(0, new ItemStack(Items.SADDLE));
    }
    
    cO();
  }
  
  public boolean mate(EntityAnimal entityanimal) {
    if (entityanimal == this)
      return false;
    if (entityanimal.getClass() != getClass()) {
      return false;
    }
    EntityHorse entityhorse = (EntityHorse)entityanimal;
    
    if ((cT()) && (entityhorse.cT())) {
      int i = getType();
      int j = entityhorse.getType();
      
      return (i == j) || ((i == 0) && (j == 1)) || ((i == 1) && (j == 0));
    }
    return false;
  }
  

  public EntityAgeable createChild(EntityAgeable entityageable)
  {
    EntityHorse entityhorse = (EntityHorse)entityageable;
    EntityHorse entityhorse1 = new EntityHorse(this.world);
    int i = getType();
    int j = entityhorse.getType();
    int k = 0;
    
    if (i == j) {
      k = i;
    } else if (((i == 0) && (j == 1)) || ((i == 1) && (j == 0))) {
      k = 2;
    }
    
    if (k == 0) {
      int l = this.random.nextInt(9);
      int i1;
      int i1;
      if (l < 4) {
        i1 = getVariant() & 0xFF; } else { int i1;
        if (l < 8) {
          i1 = entityhorse.getVariant() & 0xFF;
        } else {
          i1 = this.random.nextInt(7);
        }
      }
      int j1 = this.random.nextInt(5);
      
      if (j1 < 2) {
        i1 |= getVariant() & 0xFF00;
      } else if (j1 < 4) {
        i1 |= entityhorse.getVariant() & 0xFF00;
      } else {
        i1 |= this.random.nextInt(5) << 8 & 0xFF00;
      }
      
      entityhorse1.setVariant(i1);
    }
    
    entityhorse1.setType(k);
    double d0 = getAttributeInstance(GenericAttributes.maxHealth).b() + entityageable.getAttributeInstance(GenericAttributes.maxHealth).b() + cV();
    
    entityhorse1.getAttributeInstance(GenericAttributes.maxHealth).setValue(d0 / 3.0D);
    double d1 = getAttributeInstance(attributeJumpStrength).b() + entityageable.getAttributeInstance(attributeJumpStrength).b() + cW();
    
    entityhorse1.getAttributeInstance(attributeJumpStrength).setValue(d1 / 3.0D);
    double d2 = getAttributeInstance(GenericAttributes.d).b() + entityageable.getAttributeInstance(GenericAttributes.d).b() + cX();
    
    entityhorse1.getAttributeInstance(GenericAttributes.d).setValue(d2 / 3.0D);
    return entityhorse1;
  }
  
  public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
    Object object = super.prepare(groupdataentity);
    boolean flag = false;
    int i = 0;
    
    int j;
    if ((object instanceof GroupDataHorse)) {
      int j = ((GroupDataHorse)object).a;
      i = ((GroupDataHorse)object).b & 0xFF | this.random.nextInt(5) << 8;
    } else { int j;
      if (this.random.nextInt(10) == 0) {
        j = 1;
      } else {
        int k = this.random.nextInt(7);
        int l = this.random.nextInt(5);
        
        j = 0;
        i = k | l << 8;
      }
      
      object = new GroupDataHorse(j, i);
    }
    
    setType(j);
    setVariant(i);
    if (this.random.nextInt(5) == 0) {
      setAge(41536);
    }
    
    if ((j != 4) && (j != 3)) {
      getAttributeInstance(GenericAttributes.maxHealth).setValue(cV());
      if (j == 0) {
        getAttributeInstance(GenericAttributes.d).setValue(cX());
      } else {
        getAttributeInstance(GenericAttributes.d).setValue(0.17499999701976776D);
      }
    } else {
      getAttributeInstance(GenericAttributes.maxHealth).setValue(15.0D);
      getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
    }
    
    if ((j != 2) && (j != 1)) {
      getAttributeInstance(attributeJumpStrength).setValue(cW());
    } else {
      getAttributeInstance(attributeJumpStrength).setValue(0.5D);
    }
    
    setHealth(getMaxHealth());
    return (GroupDataEntity)object;
  }
  
  protected boolean bk() {
    return true;
  }
  
  public void w(int i) {
    if (cu())
    {
      if (i < 0) {
        i = 0;
      }
      float power;
      float power;
      if (i >= 90) {
        power = 1.0F;
      } else {
        power = 0.4F + 0.4F * i / 90.0F;
      }
      
      HorseJumpEvent event = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callHorseJumpEvent(this, power);
      if (!event.isCancelled()) {
        this.bI = true;
        cU();
        this.bt = event.getPower();
      }
    }
  }
  
  public void ac()
  {
    super.ac();
    if (this.bM > 0.0F) {
      float f = MathHelper.sin(this.aM * 3.1415927F / 180.0F);
      float f1 = MathHelper.cos(this.aM * 3.1415927F / 180.0F);
      float f2 = 0.7F * this.bM;
      float f3 = 0.15F * this.bM;
      
      this.passenger.setPosition(this.locX + f2 * f, this.locY + ad() + this.passenger.ad() + f3, this.locZ - f2 * f1);
      if ((this.passenger instanceof EntityLiving)) {
        ((EntityLiving)this.passenger).aM = this.aM;
      }
    }
  }
  
  private float cV() {
    return 15.0F + this.random.nextInt(8) + this.random.nextInt(9);
  }
  
  private double cW() {
    return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
  }
  
  private double cX() {
    return (0.44999998807907104D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
  }
  
  public static boolean a(Item item) {
    return (item == Items.HORSE_ARMOR_IRON) || (item == Items.HORSE_ARMOR_GOLD) || (item == Items.HORSE_ARMOR_DIAMOND);
  }
  
  public boolean h_() {
    return false;
  }
}
