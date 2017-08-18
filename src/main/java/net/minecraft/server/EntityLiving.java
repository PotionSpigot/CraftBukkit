package net.minecraft.server;

import com.google.common.base.Function;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.potion.CraftPotionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionEffectExpireEvent;
import org.bukkit.plugin.PluginManager;
import org.spigotmc.CustomTimingsHandler;

public abstract class EntityLiving extends Entity
{
  private static final java.util.UUID b = java.util.UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
  private static final AttributeModifier c = new AttributeModifier(b, "Sprinting speed boost", 0.30000001192092896D, 2).a(false);
  private AttributeMapBase d;
  public CombatTracker combatTracker = new CombatTracker(this);
  public final HashMap effects = new HashMap();
  private final ItemStack[] g = new ItemStack[5];
  public boolean at;
  public int au;
  public int av;
  public float aw;
  public int hurtTicks;
  public int ay;
  public float az;
  public int deathTicks;
  public int attackTicks;
  public float aC;
  public float aD;
  public float aE;
  public float aF;
  public float aG;
  public int maxNoDamageTicks = 20;
  public float aI;
  public float aJ;
  public float aK;
  public float aL;
  public float aM;
  public float aN;
  public float aO;
  public float aP;
  public float aQ = 0.02F;
  public EntityHuman killer;
  protected int lastDamageByPlayerTime;
  protected boolean aT;
  protected int aU;
  protected float aV;
  protected float aW;
  protected float aX;
  protected float aY;
  protected float aZ;
  protected int ba;
  public float lastDamage;
  protected boolean bc;
  public float bd;
  public float be;
  protected float bf;
  protected int bg;
  protected double bh;
  protected double bi;
  protected double bj;
  protected double bk;
  protected double bl;
  public boolean updateEffects = true;
  
  public EntityLiving lastDamager;
  private int bm;
  private EntityLiving bn;
  private int bo;
  private float bp;
  private int bq;
  private float br;
  public int expToDrop;
  public int maxAirTicks = 300;
  java.util.ArrayList<org.bukkit.inventory.ItemStack> drops = null;
  

  public void inactiveTick()
  {
    super.inactiveTick();
    this.aU += 1;
  }
  
  public EntityLiving(World world)
  {
    super(world);
    aD();
    
    this.datawatcher.watch(6, Float.valueOf((float)getAttributeInstance(GenericAttributes.maxHealth).getValue()));
    this.k = true;
    this.aL = ((float)(Math.random() + 1.0D) * 0.01F);
    setPosition(this.locX, this.locY, this.locZ);
    this.aK = ((float)Math.random() * 12398.0F);
    this.yaw = ((float)(Math.random() * 3.1415927410125732D * 2.0D));
    this.aO = this.yaw;
    this.W = 0.5F;
  }
  
  protected void c() {
    this.datawatcher.a(7, Integer.valueOf(0));
    this.datawatcher.a(8, Byte.valueOf((byte)0));
    this.datawatcher.a(9, Byte.valueOf((byte)0));
    this.datawatcher.a(6, Float.valueOf(1.0F));
  }
  
  protected void aD() {
    getAttributeMap().b(GenericAttributes.maxHealth);
    getAttributeMap().b(GenericAttributes.c);
    getAttributeMap().b(GenericAttributes.d);
    if (!bk()) {
      getAttributeInstance(GenericAttributes.d).setValue(0.10000000149011612D);
    }
  }
  
  protected void a(double d0, boolean flag) {
    if (!M()) {
      N();
    }
    
    if ((flag) && (this.fallDistance > 0.0F)) {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
      int k = MathHelper.floor(this.locZ);
      Block block = this.world.getType(i, j, k);
      
      if (block.getMaterial() == Material.AIR) {
        int l = this.world.getType(i, j - 1, k).b();
        
        if ((l == 11) || (l == 32) || (l == 21)) {
          block = this.world.getType(i, j - 1, k);
        }
      } else if ((!this.world.isStatic) && (this.fallDistance > 3.0F))
      {
        if ((this instanceof EntityPlayer)) {
          this.world.a((EntityHuman)this, 2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F));
          
          ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutWorldEvent(2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F), false));
        } else {
          this.world.triggerEffect(2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F));
        }
      }
      

      block.a(this.world, i, j, k, this, this.fallDistance);
    }
    
    super.a(d0, flag);
  }
  
  public boolean aE() {
    return false;
  }
  
  public void C() {
    this.aC = this.aD;
    super.C();
    this.world.methodProfiler.a("livingEntityBaseTick");
    if ((isAlive()) && (inBlock())) {
      damageEntity(DamageSource.STUCK, 1.0F);
    }
    
    if ((isFireproof()) || (this.world.isStatic)) {
      extinguish();
    }
    
    boolean flag = ((this instanceof EntityHuman)) && (((EntityHuman)this).abilities.isInvulnerable);
    
    if ((isAlive()) && (a(Material.WATER))) {
      if ((!aE()) && (!hasEffect(MobEffectList.WATER_BREATHING.id)) && (!flag)) {
        setAirTicks(j(getAirTicks()));
        if (getAirTicks() == -20) {
          setAirTicks(0);
          
          for (int i = 0; i < 8; i++) {
            float f = this.random.nextFloat() - this.random.nextFloat();
            float f1 = this.random.nextFloat() - this.random.nextFloat();
            float f2 = this.random.nextFloat() - this.random.nextFloat();
            
            this.world.addParticle("bubble", this.locX + f, this.locY + f1, this.locZ + f2, this.motX, this.motY, this.motZ);
          }
          
          damageEntity(DamageSource.DROWN, 2.0F);
        }
      }
      
      if ((!this.world.isStatic) && (am()) && ((this.vehicle instanceof EntityLiving))) {
        mount((Entity)null);
      }
      
    }
    else if (getAirTicks() != 300) {
      setAirTicks(this.maxAirTicks);
    }
    


    if ((isAlive()) && (L())) {
      extinguish();
    }
    
    this.aI = this.aJ;
    if (this.attackTicks > 0) {
      this.attackTicks -= 1;
    }
    
    if (this.hurtTicks > 0) {
      this.hurtTicks -= 1;
    }
    
    if ((this.noDamageTicks > 0) && (!(this instanceof EntityPlayer))) {
      this.noDamageTicks -= 1;
    }
    
    if (getHealth() <= 0.0F) {
      aF();
    }
    
    if (this.lastDamageByPlayerTime > 0) {
      this.lastDamageByPlayerTime -= 1;
    } else {
      this.killer = null;
    }
    
    if ((this.bn != null) && (!this.bn.isAlive())) {
      this.bn = null;
    }
    
    if (this.lastDamager != null) {
      if (!this.lastDamager.isAlive()) {
        b((EntityLiving)null);
      } else if (this.ticksLived - this.bm > 100) {
        b((EntityLiving)null);
      }
    }
    
    aO();
    this.aY = this.aX;
    this.aN = this.aM;
    this.aP = this.aO;
    this.lastYaw = this.yaw;
    this.lastPitch = this.pitch;
    this.world.methodProfiler.b();
  }
  
  public int getExpReward()
  {
    int exp = getExpValue(this.killer);
    
    if ((!this.world.isStatic) && ((this.lastDamageByPlayerTime > 0) || (alwaysGivesExp())) && (aG()) && (this.world.getGameRules().getBoolean("doMobLoot"))) {
      return exp;
    }
    return 0;
  }
  

  public boolean isBaby()
  {
    return false;
  }
  
  protected void aF() {
    this.deathTicks += 1;
    if ((this.deathTicks >= 20) && (!this.dead))
    {


      int i = this.expToDrop;
      while (i > 0) {
        int j = EntityExperienceOrb.getOrbValue(i);
        
        i -= j;
        this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
      }
      this.expToDrop = 0;
      

      die();
      
      for (i = 0; i < 20; i++) {
        double d0 = this.random.nextGaussian() * 0.02D;
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        
        this.world.addParticle("explode", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
      }
    }
  }
  
  protected boolean aG() {
    return !isBaby();
  }
  
  protected int j(int i) {
    int j = EnchantmentManager.getOxygenEnchantmentLevel(this);
    
    return (j > 0) && (this.random.nextInt(j + 1) > 0) ? i : i - 1;
  }
  
  protected int getExpValue(EntityHuman entityhuman) {
    return 0;
  }
  
  protected boolean alwaysGivesExp() {
    return false;
  }
  
  public Random aI() {
    return this.random;
  }
  
  public EntityLiving getLastDamager() {
    return this.lastDamager;
  }
  
  public int aK() {
    return this.bm;
  }
  
  public void b(EntityLiving entityliving) {
    this.lastDamager = entityliving;
    this.bm = this.ticksLived;
  }
  
  public EntityLiving aL() {
    return this.bn;
  }
  
  public int aM() {
    return this.bo;
  }
  
  public void l(Entity entity) {
    if ((entity instanceof EntityLiving)) {
      this.bn = ((EntityLiving)entity);
    } else {
      this.bn = null;
    }
    
    this.bo = this.ticksLived;
  }
  
  public int aN() {
    return this.aU;
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setFloat("HealF", getHealth());
    nbttagcompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
    nbttagcompound.setShort("HurtTime", (short)this.hurtTicks);
    nbttagcompound.setShort("DeathTime", (short)this.deathTicks);
    nbttagcompound.setShort("AttackTime", (short)this.attackTicks);
    nbttagcompound.setFloat("AbsorptionAmount", getAbsorptionHearts());
    ItemStack[] aitemstack = getEquipment();
    int i = aitemstack.length;
    



    for (int j = 0; j < i; j++) {
      ItemStack itemstack = aitemstack[j];
      if (itemstack != null) {
        this.d.a(itemstack.D());
      }
    }
    
    nbttagcompound.set("Attributes", GenericAttributes.a(getAttributeMap()));
    aitemstack = getEquipment();
    i = aitemstack.length;
    
    for (j = 0; j < i; j++) {
      ItemStack itemstack = aitemstack[j];
      if (itemstack != null) {
        this.d.b(itemstack.D());
      }
    }
    
    if (!this.effects.isEmpty()) {
      NBTTagList nbttaglist = new NBTTagList();
      Iterator iterator = this.effects.values().iterator();
      
      while (iterator.hasNext()) {
        MobEffect mobeffect = (MobEffect)iterator.next();
        
        nbttaglist.add(mobeffect.a(new NBTTagCompound()));
      }
      
      nbttagcompound.set("ActiveEffects", nbttaglist);
    }
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    setAbsorptionHearts(nbttagcompound.getFloat("AbsorptionAmount"));
    if ((nbttagcompound.hasKeyOfType("Attributes", 9)) && (this.world != null) && (!this.world.isStatic)) {
      GenericAttributes.a(getAttributeMap(), nbttagcompound.getList("Attributes", 10));
    }
    
    if (nbttagcompound.hasKeyOfType("ActiveEffects", 9)) {
      NBTTagList nbttaglist = nbttagcompound.getList("ActiveEffects", 10);
      
      for (int i = 0; i < nbttaglist.size(); i++) {
        NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
        MobEffect mobeffect = MobEffect.b(nbttagcompound1);
        
        if (mobeffect != null) {
          this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
        }
      }
    }
    

    if (nbttagcompound.hasKey("Bukkit.MaxHealth")) {
      NBTBase nbtbase = nbttagcompound.get("Bukkit.MaxHealth");
      if (nbtbase.getTypeId() == 5) {
        getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagFloat)nbtbase).c());
      } else if (nbtbase.getTypeId() == 3) {
        getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagInt)nbtbase).d());
      }
    }
    

    if (nbttagcompound.hasKeyOfType("HealF", 99)) {
      setHealth(nbttagcompound.getFloat("HealF"));
    } else {
      NBTBase nbtbase = nbttagcompound.get("Health");
      
      if (nbtbase == null) {
        setHealth(getMaxHealth());
      } else if (nbtbase.getTypeId() == 5) {
        setHealth(((NBTTagFloat)nbtbase).h());
      } else if (nbtbase.getTypeId() == 2) {
        setHealth(((NBTTagShort)nbtbase).e());
      }
    }
    
    this.hurtTicks = nbttagcompound.getShort("HurtTime");
    this.deathTicks = nbttagcompound.getShort("DeathTime");
    this.attackTicks = nbttagcompound.getShort("AttackTime");
  }
  
  protected void aO()
  {
    for (com.google.common.collect.UnmodifiableIterator localUnmodifiableIterator = com.google.common.collect.ImmutableMap.copyOf(this.effects).entrySet().iterator(); localUnmodifiableIterator.hasNext();)
    {
      Map.Entry<Integer, MobEffect> entry = (Map.Entry)localUnmodifiableIterator.next();
      Integer integer = (Integer)entry.getKey();
      if (this.effects.keySet().contains(integer))
      {
        MobEffect mobeffect = (MobEffect)entry.getValue();
        if (!mobeffect.tick(this))
        {
          if (!this.world.isStatic)
          {
            PotionEffectExpireEvent event = new PotionEffectExpireEvent((LivingEntity)getBukkitEntity(), CraftPotionUtils.toBukkit(mobeffect));
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled())
            {
              CraftPotionUtils.extendDuration(mobeffect, event.getDuration());
            }
            else
            {
              this.effects.remove(integer);
              
              b(mobeffect);
            }
          }
        }
        else if (mobeffect.getDuration() % 600 == 0) {
          a(mobeffect, false);
        }
      }
    }
    if (this.updateEffects)
    {
      if (!this.world.isStatic) {
        if (this.effects.isEmpty())
        {
          this.datawatcher.watch(8, Byte.valueOf((byte)0));
          this.datawatcher.watch(7, Integer.valueOf(0));
          setInvisible(false);
        }
        else
        {
          int i = PotionBrewer.a(this.effects.values());
          this.datawatcher.watch(8, Byte.valueOf((byte)(PotionBrewer.b(this.effects.values()) ? 1 : 0)));
          this.datawatcher.watch(7, Integer.valueOf(i));
          setInvisible(hasEffect(MobEffectList.INVISIBILITY.id));
        }
      }
      this.updateEffects = false;
    }
    int i = this.datawatcher.getInt(7);
    boolean flag = this.datawatcher.getByte(8) > 0;
    if (i > 0)
    {
      boolean flag1 = false;
      if (!isInvisible()) {
        flag1 = this.random.nextBoolean();
      } else {
        flag1 = this.random.nextInt(15) == 0;
      }
      if (flag) {
        flag1 &= this.random.nextInt(5) == 0;
      }
      if ((flag1) && (i > 0))
      {
        double d0 = (i >> 16 & 0xFF) / 255.0D;
        double d1 = (i >> 8 & 0xFF) / 255.0D;
        double d2 = (i >> 0 & 0xFF) / 255.0D;
        
        this.world.addParticle(flag ? "mobSpellAmbient" : "mobSpell", this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length - this.height, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, d0, d1, d2);
      }
    }
  }
  
  public void removeAllEffects() {
    Iterator iterator = this.effects.keySet().iterator();
    
    while (iterator.hasNext()) {
      Integer integer = (Integer)iterator.next();
      MobEffect mobeffect = (MobEffect)this.effects.get(integer);
      
      if (!this.world.isStatic) {
        iterator.remove();
        b(mobeffect);
      }
    }
  }
  
  public java.util.Collection getEffects() {
    return this.effects.values();
  }
  
  public boolean hasEffect(int i)
  {
    return (this.effects.size() != 0) && (this.effects.containsKey(Integer.valueOf(i)));
  }
  
  public boolean hasEffect(MobEffectList mobeffectlist)
  {
    return (this.effects.size() != 0) && (this.effects.containsKey(Integer.valueOf(mobeffectlist.id)));
  }
  

  public MobEffect getEffect(MobEffectList mobeffectlist)
  {
    return (MobEffect)this.effects.get(Integer.valueOf(mobeffectlist.id));
  }
  
  public void addEffect(MobEffect mobeffect)
  {
    addEffect(mobeffect, org.bukkit.event.entity.PotionEffectAddEvent.EffectCause.UNKNOWN);
  }
  
  public void addEffect(MobEffect mobeffect, org.bukkit.event.entity.PotionEffectAddEvent.EffectCause cause)
  {
    if (d(mobeffect))
    {
      MobEffect current = (MobEffect)this.effects.get(Integer.valueOf(mobeffect.getEffectId()));
      if (current != null)
      {
        org.bukkit.event.entity.PotionEffectExtendEvent event = new org.bukkit.event.entity.PotionEffectExtendEvent((LivingEntity)getBukkitEntity(), CraftPotionUtils.toBukkit(mobeffect), CraftPotionUtils.toBukkit(current), cause);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
          return;
        }
        current.a(mobeffect);
        a(current, true);
      }
      else
      {
        org.bukkit.event.entity.PotionEffectAddEvent event = new org.bukkit.event.entity.PotionEffectAddEvent((LivingEntity)getBukkitEntity(), CraftPotionUtils.toBukkit(mobeffect), cause);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
          return;
        }
        this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
        a(mobeffect);
      }
    }
  }
  
  public boolean d(MobEffect mobeffect) { if (getMonsterType() == EnumMonsterType.UNDEAD) {
      int i = mobeffect.getEffectId();
      
      if ((i == MobEffectList.REGENERATION.id) || (i == MobEffectList.POISON.id)) {
        return false;
      }
    }
    
    return true;
  }
  
  public boolean aR() {
    return getMonsterType() == EnumMonsterType.UNDEAD;
  }
  
  public void removeEffect(int i)
  {
    MobEffect mobeffect = (MobEffect)this.effects.get(Integer.valueOf(i));
    if (mobeffect != null)
    {
      org.bukkit.event.entity.PotionEffectRemoveEvent event = new org.bukkit.event.entity.PotionEffectRemoveEvent((LivingEntity)getBukkitEntity(), CraftPotionUtils.toBukkit(mobeffect));
      this.world.getServer().getPluginManager().callEvent(event);
      if (event.isCancelled()) {
        return;
      }
      this.effects.remove(Integer.valueOf(i));
      
      b(mobeffect);
    }
  }
  
  protected void a(MobEffect mobeffect) {
    this.updateEffects = true;
    if (!this.world.isStatic) {
      MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
    }
  }
  
  protected void a(MobEffect mobeffect, boolean flag) {
    this.updateEffects = true;
    if ((flag) && (!this.world.isStatic)) {
      MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
      MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
    }
  }
  
  protected void b(MobEffect mobeffect) {
    this.updateEffects = true;
    if (!this.world.isStatic) {
      MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
    }
  }
  
  public void heal(float f)
  {
    heal(f, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.CUSTOM);
  }
  
  public void heal(float f, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason regainReason) {
    float f1 = getHealth();
    
    if (f1 > 0.0F) {
      org.bukkit.event.entity.EntityRegainHealthEvent event = new org.bukkit.event.entity.EntityRegainHealthEvent(getBukkitEntity(), f, regainReason);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (!event.isCancelled()) {
        setHealth((float)(getHealth() + event.getAmount()));
      }
    }
  }
  
  public final float getHealth()
  {
    if ((this instanceof EntityPlayer)) {
      return (float)((EntityPlayer)this).getBukkitEntity().getHealth();
    }
    
    return this.datawatcher.getFloat(6);
  }
  
  public void setHealth(float f)
  {
    if ((this instanceof EntityPlayer)) {
      CraftPlayer player = ((EntityPlayer)this).getBukkitEntity();
      
      if (f < 0.0F) {
        player.setRealHealth(0.0D);
      } else if (f > player.getMaxHealth()) {
        player.setRealHealth(player.getMaxHealth());
      } else {
        player.setRealHealth(f);
      }
      
      this.datawatcher.watch(6, Float.valueOf(player.getScaledHealth()));
      return;
    }
    
    this.datawatcher.watch(6, Float.valueOf(MathHelper.a(f, 0.0F, getMaxHealth())));
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable())
      return false;
    if (this.world.isStatic) {
      return false;
    }
    this.aU = 0;
    if (getHealth() <= 0.0F)
      return false;
    if ((damagesource.o()) && (hasEffect(MobEffectList.FIRE_RESISTANCE))) {
      return false;
    }
    





    this.aF = 1.5F;
    boolean flag = true;
    
    if (this.noDamageTicks > this.maxNoDamageTicks / 2.0F) {
      if (f <= this.lastDamage) {
        return false;
      }
      

      if (!d(damagesource, f - this.lastDamage)) {
        return false;
      }
      
      this.lastDamage = f;
      flag = false;
    }
    else {
      float previousHealth = getHealth();
      if (!d(damagesource, f)) {
        return false;
      }
      this.lastDamage = f;
      this.aw = previousHealth;
      this.noDamageTicks = this.maxNoDamageTicks;
      
      this.hurtTicks = (this.ay = 10);
    }
    
    this.az = 0.0F;
    Entity entity = damagesource.getEntity();
    
    if (entity != null) {
      if ((entity instanceof EntityLiving)) {
        b((EntityLiving)entity);
      }
      
      if ((entity instanceof EntityHuman)) {
        this.lastDamageByPlayerTime = 100;
        this.killer = ((EntityHuman)entity);
      } else if ((entity instanceof EntityWolf)) {
        EntityWolf entitywolf = (EntityWolf)entity;
        
        if (entitywolf.isTamed()) {
          this.lastDamageByPlayerTime = 100;
          this.killer = null;
        }
      }
    }
    

    boolean knockbackCancelled = false;
    if (flag) { if ((knockbackCancelled = (this.world.paperSpigotConfig.disableExplosionKnockback) && (damagesource.isExplosion()) && ((this instanceof EntityHuman)) ? 1 : 0) == 0)
      {
        this.world.broadcastEntityEffect(this, (byte)2);
        if (damagesource != DamageSource.DROWN) {
          Q();
        }
        
        if (entity != null) {
          double d0 = entity.locX - this.locX;
          


          for (double d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
            d0 = (Math.random() - Math.random()) * 0.01D;
          }
          
          this.az = ((float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - this.yaw);
          a(entity, f, d0, d1);
        } else {
          this.az = ((int)(Math.random() * 2.0D) * 180);
        }
      }
    }
    if (knockbackCancelled) { this.world.broadcastEntityEffect(this, (byte)2);
    }
    

    if (getHealth() <= 0.0F) {
      String s = aU();
      if ((flag) && (s != null)) {
        makeSound(s, bf(), bg());
      }
      
      die(damagesource);
    } else {
      String s = aT();
      if ((flag) && (s != null)) {
        makeSound(s, bf(), bg());
      }
    }
    
    return true;
  }
  

  public void a(ItemStack itemstack)
  {
    makeSound("random.break", 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
    
    for (int i = 0; i < 5; i++) {
      Vec3D vec3d = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
      
      vec3d.a(-this.pitch * 3.1415927F / 180.0F);
      vec3d.b(-this.yaw * 3.1415927F / 180.0F);
      Vec3D vec3d1 = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.3D, -this.random.nextFloat() * 0.6D - 0.3D, 0.6D);
      
      vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
      vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
      vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
      this.world.addParticle("iconcrack_" + Item.getId(itemstack.getItem()), vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c);
    }
  }
  
  public void die(DamageSource damagesource) {
    Entity entity = damagesource.getEntity();
    EntityLiving entityliving = aX();
    
    if ((this.ba >= 0) && (entityliving != null)) {
      entityliving.b(this, this.ba);
    }
    
    if (entity != null) {
      entity.a(this);
    }
    
    this.aT = true;
    aW().g();
    if (!this.world.isStatic) {
      int i = 0;
      
      if ((entity instanceof EntityHuman)) {
        i = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving)entity);
      }
      
      if ((aG()) && (this.world.getGameRules().getBoolean("doMobLoot"))) {
        this.drops = new java.util.ArrayList();
        
        dropDeathLoot(this.lastDamageByPlayerTime > 0, i);
        dropEquipment(this.lastDamageByPlayerTime > 0, i);
        if (this.lastDamageByPlayerTime > 0) {
          int j = this.random.nextInt(200) - i;
          
          if (j < 5) {
            getRareDrop(j <= 0 ? 1 : 0);
          }
        }
        

        org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityDeathEvent(this, this.drops);
        this.drops = null;
      } else {
        org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityDeathEvent(this);
      }
    }
    

    this.world.broadcastEntityEffect(this, (byte)3);
  }
  
  protected void dropEquipment(boolean flag, int i) {}
  
  public void a(Entity entity, float f, double d0, double d1) {
    if (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue()) {
      this.al = true;
      float f1 = MathHelper.sqrt(d0 * d0 + d1 * d1);
      float f2 = 0.4F;
      
      this.motX /= 2.0D;
      this.motY /= 2.0D;
      this.motZ /= 2.0D;
      this.motX -= d0 / f1 * f2;
      this.motY += f2;
      this.motZ -= d1 / f1 * f2;
      if (this.motY > 0.4000000059604645D) {
        this.motY = 0.4000000059604645D;
      }
    }
  }
  
  protected String aT() {
    return "game.neutral.hurt";
  }
  
  protected String aU() {
    return "game.neutral.die";
  }
  
  protected void getRareDrop(int i) {}
  
  protected void dropDeathLoot(boolean flag, int i) {}
  
  public boolean h_() {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.boundingBox.b);
    int k = MathHelper.floor(this.locZ);
    Block block = this.world.getType(i, j, k);
    
    return (block == Blocks.LADDER) || (block == Blocks.VINE);
  }
  
  public boolean isAlive() {
    return (!this.dead) && (getHealth() > 0.0F);
  }
  
  protected void b(float f) {
    super.b(f);
    MobEffect mobeffect = getEffect(MobEffectList.JUMP);
    float f1 = mobeffect != null ? mobeffect.getAmplifier() + 1 : 0.0F;
    int i = MathHelper.f(f - 3.0F - f1);
    
    if (i > 0)
    {
      if (!damageEntity(DamageSource.FALL, i)) {
        return;
      }
      
      makeSound(o(i), 1.0F, 1.0F);
      
      int j = MathHelper.floor(this.locX);
      int k = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
      int l = MathHelper.floor(this.locZ);
      Block block = this.world.getType(j, k, l);
      
      if (block.getMaterial() != Material.AIR) {
        StepSound stepsound = block.stepSound;
        
        makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.5F, stepsound.getVolume2() * 0.75F);
      }
    }
  }
  
  protected String o(int i) {
    return i > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
  }
  
  public int aV() {
    int i = 0;
    ItemStack[] aitemstack = getEquipment();
    int j = aitemstack.length;
    
    for (int k = 0; k < j; k++) {
      ItemStack itemstack = aitemstack[k];
      
      if ((itemstack != null) && ((itemstack.getItem() instanceof ItemArmor))) {
        int l = ((ItemArmor)itemstack.getItem()).c;
        
        i += l;
      }
    }
    
    return i;
  }
  
  protected void damageArmor(float f) {}
  
  protected float applyArmorModifier(DamageSource damagesource, float f) {
    if (!damagesource.ignoresArmor()) {
      int i = 25 - aV();
      float f1 = f * i;
      

      f = f1 / 25.0F;
    }
    
    return f;
  }
  
  protected float applyMagicModifier(DamageSource damagesource, float f) {
    if (damagesource.isStarvation()) {
      return f;
    }
    if ((this instanceof EntityZombie)) {
      f = f;
    }
    












    if (f <= 0.0F) {
      return 0.0F;
    }
    int i = EnchantmentManager.a(getEquipment(), damagesource);
    if (i > 20) {
      i = 20;
    }
    
    if ((i > 0) && (i <= 20)) {
      int j = 25 - i;
      float f1 = f * j;
      f = f1 / 25.0F;
    }
    
    return f;
  }
  


  protected boolean d(final DamageSource damagesource, float f)
  {
    if (!isInvulnerable()) {
      final boolean human = this instanceof EntityHuman;
      float originalDamage = f;
      Function<Double, Double> hardHat = new Function()
      {
        public Double apply(Double f) {
          if (((damagesource == DamageSource.ANVIL) || (damagesource == DamageSource.FALLING_BLOCK)) && (EntityLiving.this.getEquipment(4) != null)) {
            return Double.valueOf(-(f.doubleValue() - f.doubleValue() * 0.75D));
          }
          return Double.valueOf(-0.0D);
        }
      };
      float hardHatModifier = ((Double)hardHat.apply(Double.valueOf(f))).floatValue();
      f += hardHatModifier;
      
      Function<Double, Double> blocking = new Function()
      {
        public Double apply(Double f) {
          if ((human) && 
            (!damagesource.ignoresArmor()) && (((EntityHuman)EntityLiving.this).isBlocking()) && (f.doubleValue() > 0.0D)) {
            return Double.valueOf(-(f.doubleValue() - (1.0D + f.doubleValue()) * 0.5D));
          }
          
          return Double.valueOf(-0.0D);
        }
      };
      float blockingModifier = ((Double)blocking.apply(Double.valueOf(f))).floatValue();
      f += blockingModifier;
      
      Function<Double, Double> armor = new Function()
      {
        public Double apply(Double f) {
          return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyArmorModifier(damagesource, f.floatValue())));
        }
      };
      float armorModifier = ((Double)armor.apply(Double.valueOf(f))).floatValue();
      f += armorModifier;
      
      Function<Double, Double> resistance = new Function()
      {
        public Double apply(Double f) {
          if ((!damagesource.isStarvation()) && (EntityLiving.this.hasEffect(MobEffectList.RESISTANCE)) && (damagesource != DamageSource.OUT_OF_WORLD)) {
            int i = (EntityLiving.this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
            int j = 25 - i;
            float f1 = f.floatValue() * j;
            return Double.valueOf(-(f.doubleValue() - f1 / 25.0F));
          }
          return Double.valueOf(-0.0D);
        }
      };
      float resistanceModifier = ((Double)resistance.apply(Double.valueOf(f))).floatValue();
      f += resistanceModifier;
      
      Function<Double, Double> magic = new Function()
      {
        public Double apply(Double f) {
          return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyMagicModifier(damagesource, f.floatValue())));
        }
      };
      float magicModifier = ((Double)magic.apply(Double.valueOf(f))).floatValue();
      f += magicModifier;
      
      Function<Double, Double> absorption = new Function()
      {
        public Double apply(Double f) {
          return Double.valueOf(-Math.max(f.doubleValue() - Math.max(f.doubleValue() - EntityLiving.this.getAbsorptionHearts(), 0.0D), 0.0D));
        }
      };
      float absorptionModifier = ((Double)absorption.apply(Double.valueOf(f))).floatValue();
      
      EntityDamageEvent event = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleLivingEntityDamageEvent(this, damagesource, originalDamage, hardHatModifier, blockingModifier, armorModifier, resistanceModifier, magicModifier, absorptionModifier, hardHat, blocking, armor, resistance, magic, absorption);
      if (event.isCancelled()) {
        return false;
      }
      
      f = (float)event.getFinalDamage();
      

      if (((damagesource == DamageSource.ANVIL) || (damagesource == DamageSource.FALLING_BLOCK)) && (getEquipment(4) != null)) {
        getEquipment(4).damage((int)(event.getDamage() * 4.0D + this.random.nextFloat() * event.getDamage() * 2.0D), this);
      }
      

      if (!damagesource.ignoresArmor()) {
        float armorDamage = (float)(event.getDamage() + event.getDamage(org.bukkit.event.entity.EntityDamageEvent.DamageModifier.BLOCKING) + event.getDamage(org.bukkit.event.entity.EntityDamageEvent.DamageModifier.HARD_HAT));
        damageArmor(armorDamage);
      }
      
      absorptionModifier = (float)-event.getDamage(org.bukkit.event.entity.EntityDamageEvent.DamageModifier.ABSORPTION);
      setAbsorptionHearts(Math.max(getAbsorptionHearts() - absorptionModifier, 0.0F));
      if (f != 0.0F) {
        if (human) {
          ((EntityHuman)this).applyExhaustion(damagesource.getExhaustionCost());
        }
        
        float f2 = getHealth();
        
        setHealth(f2 - f);
        aW().a(damagesource, f2, f);
        
        if (human) {
          return true;
        }
        
        setAbsorptionHearts(getAbsorptionHearts() - f);
      }
      return true;
    }
    return false;
  }
  
  public CombatTracker aW() {
    return this.combatTracker;
  }
  
  public EntityLiving aX() {
    return this.lastDamager != null ? this.lastDamager : this.killer != null ? this.killer : this.combatTracker.c() != null ? this.combatTracker.c() : null;
  }
  
  public final float getMaxHealth() {
    return (float)getAttributeInstance(GenericAttributes.maxHealth).getValue();
  }
  
  public final int aZ() {
    return this.datawatcher.getByte(9);
  }
  
  public final void p(int i) {
    this.datawatcher.watch(9, Byte.valueOf((byte)i));
  }
  
  private int j() {
    return hasEffect(MobEffectList.SLOWER_DIG) ? 6 + (1 + getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2 : hasEffect(MobEffectList.FASTER_DIG) ? 6 - (1 + getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1 : 6;
  }
  
  public void ba() {
    if ((!this.at) || (this.au >= j() / 2) || (this.au < 0)) {
      this.au = -1;
      this.at = true;
      if ((this.world instanceof WorldServer)) {
        ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAnimation(this, 0));
      }
    }
  }
  
  protected void G() {
    damageEntity(DamageSource.OUT_OF_WORLD, 4.0F);
  }
  
  protected void bb() {
    int i = j();
    
    if (this.at) {
      this.au += 1;
      if (this.au >= i) {
        this.au = 0;
        this.at = false;
      }
    } else {
      this.au = 0;
    }
    
    this.aD = (this.au / i);
  }
  
  public AttributeInstance getAttributeInstance(IAttribute iattribute) {
    return getAttributeMap().a(iattribute);
  }
  
  public AttributeMapBase getAttributeMap() {
    if (this.d == null) {
      this.d = new AttributeMapServer();
    }
    
    return this.d;
  }
  
  public EnumMonsterType getMonsterType() {
    return EnumMonsterType.UNDEFINED;
  }
  
  public abstract ItemStack be();
  
  public abstract ItemStack getEquipment(int paramInt);
  
  public abstract void setEquipment(int paramInt, ItemStack paramItemStack);
  
  public void setSprinting(boolean flag) {
    super.setSprinting(flag);
    AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
    
    if (attributeinstance.a(b) != null) {
      attributeinstance.b(c);
    }
    
    if (flag) {
      attributeinstance.a(c);
    }
  }
  
  public abstract ItemStack[] getEquipment();
  
  protected float bf() {
    return 1.0F;
  }
  
  protected float bg() {
    return isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
  }
  
  protected boolean bh() {
    return getHealth() <= 0.0F;
  }
  
  public void enderTeleportTo(double d0, double d1, double d2) {
    setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
  }
  
  public void m(Entity entity) {
    double d0 = entity.locX;
    double d1 = entity.boundingBox.b + entity.length;
    double d2 = entity.locZ;
    byte b0 = 1;
    
    for (int i = -b0; i <= b0; i++) {
      for (int j = -b0; j < b0; j++) {
        if ((i != 0) || (j != 0)) {
          int k = (int)(this.locX + i);
          int l = (int)(this.locZ + j);
          AxisAlignedBB axisalignedbb = this.boundingBox.c(i, 1.0D, j);
          
          if (this.world.a(axisalignedbb).isEmpty()) {
            if (World.a(this.world, k, (int)this.locY, l)) {
              enderTeleportTo(this.locX + i, this.locY + 1.0D, this.locZ + j);
              return;
            }
            
            if ((World.a(this.world, k, (int)this.locY - 1, l)) || (this.world.getType(k, (int)this.locY - 1, l).getMaterial() == Material.WATER)) {
              d0 = this.locX + i;
              d1 = this.locY + 1.0D;
              d2 = this.locZ + j;
            }
          }
        }
      }
    }
    
    enderTeleportTo(d0, d1, d2);
  }
  
  protected void bj() {
    this.motY = 0.41999998688697815D;
    if (hasEffect(MobEffectList.JUMP)) {
      this.motY += (getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F;
    }
    
    if (isSprinting()) {
      float f = this.yaw * 0.017453292F;
      
      this.motX -= MathHelper.sin(f) * 0.2F;
      this.motZ += MathHelper.cos(f) * 0.2F;
    }
    
    this.al = true;
  }
  

  public void e(float f, float f1)
  {
    if ((M()) && ((!(this instanceof EntityHuman)) || (!((EntityHuman)this).abilities.isFlying))) {
      double d0 = this.locY;
      a(f, f1, bk() ? 0.04F : 0.02F);
      move(this.motX, this.motY, this.motZ);
      this.motX *= 0.800000011920929D;
      this.motY *= 0.800000011920929D;
      this.motZ *= 0.800000011920929D;
      this.motY -= 0.02D;
      if ((this.positionChanged) && (c(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ))) {
        this.motY = 0.30000001192092896D;
      }
    } else if ((P()) && ((!(this instanceof EntityHuman)) || (!((EntityHuman)this).abilities.isFlying))) {
      double d0 = this.locY;
      a(f, f1, 0.02F);
      move(this.motX, this.motY, this.motZ);
      this.motX *= 0.5D;
      this.motY *= 0.5D;
      this.motZ *= 0.5D;
      this.motY -= 0.02D;
      if ((this.positionChanged) && (c(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ))) {
        this.motY = 0.30000001192092896D;
      }
    } else {
      float f2 = 0.91F;
      
      if (this.onGround) {
        f2 = this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ)).frictionFactor * 0.91F;
      }
      
      float f3 = 0.16277136F / (f2 * f2 * f2);
      float f4;
      float f4;
      if (this.onGround) {
        f4 = bl() * f3;
      } else {
        f4 = this.aQ;
      }
      
      a(f, f1, f4);
      f2 = 0.91F;
      if (this.onGround) {
        f2 = this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ)).frictionFactor * 0.91F;
      }
      
      if (h_()) {
        float f5 = 0.15F;
        
        if (this.motX < -f5) {
          this.motX = (-f5);
        }
        
        if (this.motX > f5) {
          this.motX = f5;
        }
        
        if (this.motZ < -f5) {
          this.motZ = (-f5);
        }
        
        if (this.motZ > f5) {
          this.motZ = f5;
        }
        
        this.fallDistance = 0.0F;
        if (this.motY < -0.15D) {
          this.motY = -0.15D;
        }
        
        boolean flag = (isSneaking()) && ((this instanceof EntityHuman));
        
        if ((flag) && (this.motY < 0.0D)) {
          this.motY = 0.0D;
        }
      }
      
      move(this.motX, this.motY, this.motZ);
      if ((this.positionChanged) && (h_())) {
        this.motY = 0.2D;
      }
      
      if ((this.world.isStatic) && ((!this.world.isLoaded((int)this.locX, 0, (int)this.locZ)) || (!this.world.getChunkAtWorldCoords((int)this.locX, (int)this.locZ).d))) {
        if (this.locY > 0.0D) {
          this.motY = -0.1D;
        } else {
          this.motY = 0.0D;
        }
      } else {
        this.motY -= 0.08D;
      }
      
      this.motY *= 0.9800000190734863D;
      this.motX *= f2;
      this.motZ *= f2;
    }
    
    this.aE = this.aF;
    double d0 = this.locX - this.lastX;
    double d1 = this.locZ - this.lastZ;
    float f6 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
    
    if (f6 > 1.0F) {
      f6 = 1.0F;
    }
    
    this.aF += (f6 - this.aF) * 0.4F;
    this.aG += this.aF;
  }
  
  protected boolean bk() {
    return false;
  }
  
  public float bl() {
    return bk() ? this.bp : 0.1F;
  }
  
  public void i(float f) {
    this.bp = f;
  }
  
  public boolean n(Entity entity) {
    l(entity);
    return false;
  }
  
  public boolean isSleeping() {
    return false;
  }
  
  public void h() {
    SpigotTimings.timerEntityBaseTick.startTiming();
    super.h();
    if (!this.world.isStatic) {
      int i = aZ();
      
      if (i > 0) {
        if (this.av <= 0) {
          this.av = (20 * (30 - i));
        }
        
        this.av -= 1;
        if (this.av <= 0) {
          p(i - 1);
        }
      }
      
      for (int j = 0; j < 5; j++) {
        ItemStack itemstack = this.g[j];
        ItemStack itemstack1 = getEquipment(j);
        
        if (!ItemStack.matches(itemstack1, itemstack)) {
          ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutEntityEquipment(getId(), j, itemstack1));
          if (itemstack != null) {
            this.d.a(itemstack.D());
          }
          
          if (itemstack1 != null) {
            this.d.b(itemstack1.D());
          }
          
          this.g[j] = (itemstack1 == null ? null : itemstack1.cloneItemStack());
        }
      }
      
      if (this.ticksLived % 20 == 0) {
        aW().g();
      }
    }
    
    SpigotTimings.timerEntityBaseTick.stopTiming();
    e();
    SpigotTimings.timerEntityTickRest.startTiming();
    double d0 = this.locX - this.lastX;
    double d1 = this.locZ - this.lastZ;
    float f = (float)(d0 * d0 + d1 * d1);
    float f1 = this.aM;
    float f2 = 0.0F;
    
    this.aV = this.aW;
    float f3 = 0.0F;
    
    if (f > 0.0025000002F) {
      f3 = 1.0F;
      f2 = (float)Math.sqrt(f) * 3.0F;
      
      f1 = (float)org.bukkit.craftbukkit.v1_7_R4.TrigMath.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
    }
    
    if (this.aD > 0.0F) {
      f1 = this.yaw;
    }
    
    if (!this.onGround) {
      f3 = 0.0F;
    }
    
    this.aW += (f3 - this.aW) * 0.3F;
    this.world.methodProfiler.a("headTurn");
    f2 = f(f1, f2);
    this.world.methodProfiler.b();
    this.world.methodProfiler.a("rangeChecks");
    
    while (this.yaw - this.lastYaw < -180.0F) {
      this.lastYaw -= 360.0F;
    }
    
    while (this.yaw - this.lastYaw >= 180.0F) {
      this.lastYaw += 360.0F;
    }
    
    while (this.aM - this.aN < -180.0F) {
      this.aN -= 360.0F;
    }
    
    while (this.aM - this.aN >= 180.0F) {
      this.aN += 360.0F;
    }
    
    while (this.pitch - this.lastPitch < -180.0F) {
      this.lastPitch -= 360.0F;
    }
    
    while (this.pitch - this.lastPitch >= 180.0F) {
      this.lastPitch += 360.0F;
    }
    
    while (this.aO - this.aP < -180.0F) {
      this.aP -= 360.0F;
    }
    
    while (this.aO - this.aP >= 180.0F) {
      this.aP += 360.0F;
    }
    
    this.world.methodProfiler.b();
    this.aX += f2;
    SpigotTimings.timerEntityTickRest.stopTiming();
  }
  
  protected float f(float f, float f1) {
    float f2 = MathHelper.g(f - this.aM);
    
    this.aM += f2 * 0.3F;
    float f3 = MathHelper.g(this.yaw - this.aM);
    boolean flag = (f3 < -90.0F) || (f3 >= 90.0F);
    
    if (f3 < -75.0F) {
      f3 = -75.0F;
    }
    
    if (f3 >= 75.0F) {
      f3 = 75.0F;
    }
    
    this.aM = (this.yaw - f3);
    if (f3 * f3 > 2500.0F) {
      this.aM += f3 * 0.2F;
    }
    
    if (flag) {
      f1 *= -1.0F;
    }
    
    return f1;
  }
  
  public void e() {
    if (this.bq > 0) {
      this.bq -= 1;
    }
    
    if (this.bg > 0) {
      double d0 = this.locX + (this.bh - this.locX) / this.bg;
      double d1 = this.locY + (this.bi - this.locY) / this.bg;
      double d2 = this.locZ + (this.bj - this.locZ) / this.bg;
      double d3 = MathHelper.g(this.bk - this.yaw);
      
      this.yaw = ((float)(this.yaw + d3 / this.bg));
      this.pitch = ((float)(this.pitch + (this.bl - this.pitch) / this.bg));
      this.bg -= 1;
      setPosition(d0, d1, d2);
      b(this.yaw, this.pitch);
    } else if (!br()) {
      this.motX *= 0.98D;
      this.motY *= 0.98D;
      this.motZ *= 0.98D;
    }
    
    if (Math.abs(this.motX) < 0.005D) {
      this.motX = 0.0D;
    }
    
    if (Math.abs(this.motY) < 0.005D) {
      this.motY = 0.0D;
    }
    
    if (Math.abs(this.motZ) < 0.005D) {
      this.motZ = 0.0D;
    }
    
    this.world.methodProfiler.a("ai");
    SpigotTimings.timerEntityAI.startTiming();
    if (bh()) {
      this.bc = false;
      this.bd = 0.0F;
      this.be = 0.0F;
      this.bf = 0.0F;
    } else if (br()) {
      if (bk()) {
        this.world.methodProfiler.a("newAi");
        bn();
        this.world.methodProfiler.b();
      } else {
        this.world.methodProfiler.a("oldAi");
        bq();
        this.world.methodProfiler.b();
        this.aO = this.yaw;
      }
    }
    SpigotTimings.timerEntityAI.stopTiming();
    
    this.world.methodProfiler.b();
    this.world.methodProfiler.a("jump");
    if (this.bc) {
      if ((!M()) && (!P())) {
        if ((this.onGround) && (this.bq == 0)) {
          bj();
          this.bq = 10;
        }
      } else {
        this.motY += 0.03999999910593033D;
      }
    } else {
      this.bq = 0;
    }
    
    this.world.methodProfiler.b();
    this.world.methodProfiler.a("travel");
    this.bd *= 0.98F;
    this.be *= 0.98F;
    this.bf *= 0.9F;
    SpigotTimings.timerEntityAIMove.startTiming();
    e(this.bd, this.be);
    SpigotTimings.timerEntityAIMove.stopTiming();
    this.world.methodProfiler.b();
    this.world.methodProfiler.a("push");
    if (!this.world.isStatic) {
      SpigotTimings.timerEntityAICollision.startTiming();
      bo();
      SpigotTimings.timerEntityAICollision.stopTiming();
    }
    
    this.world.methodProfiler.b();
  }
  
  protected void bn() {}
  
  protected void bo() {
    List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
    
    if ((R()) && (list != null) && (!list.isEmpty())) {
      this.numCollisions -= this.world.spigotConfig.maxCollisionsPerEntity;
      for (int i = 0; i < list.size(); i++) {
        if (this.numCollisions > this.world.spigotConfig.maxCollisionsPerEntity) break;
        Entity entity = (Entity)list.get(i);
        


        if ((!(entity instanceof EntityLiving)) || ((this instanceof EntityPlayer)) || (this.ticksLived % 2 != 0))
        {



          if (entity.S()) {
            entity.numCollisions += 1;
            this.numCollisions += 1;
            o(entity);
          } }
      }
      this.numCollisions = 0;
    }
  }
  
  protected void o(Entity entity) {
    entity.collide(this);
  }
  
  public void ab() {
    super.ab();
    this.aV = this.aW;
    this.aW = 0.0F;
    this.fallDistance = 0.0F;
  }
  
  protected void bp() {}
  
  protected void bq() {
    this.aU += 1;
  }
  
  public void f(boolean flag) {
    this.bc = flag;
  }
  
  public void receive(Entity entity, int i) {
    if ((!entity.dead) && (!this.world.isStatic)) {
      EntityTracker entitytracker = ((WorldServer)this.world).getTracker();
      
      if ((entity instanceof EntityItem)) {
        entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
      }
      
      if ((entity instanceof EntityArrow)) {
        entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
      }
      
      if ((entity instanceof EntityExperienceOrb)) {
        entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
      }
    }
  }
  
  public boolean hasLineOfSight(Entity entity) {
    return this.world.a(Vec3D.a(this.locX, this.locY + getHeadHeight(), this.locZ), Vec3D.a(entity.locX, entity.locY + entity.getHeadHeight(), entity.locZ)) == null;
  }
  
  public Vec3D ag() {
    return j(1.0F);
  }
  




  public Vec3D j(float f)
  {
    if (f == 1.0F) {
      float f1 = MathHelper.cos(-this.yaw * 0.017453292F - 3.1415927F);
      float f2 = MathHelper.sin(-this.yaw * 0.017453292F - 3.1415927F);
      float f3 = -MathHelper.cos(-this.pitch * 0.017453292F);
      float f4 = MathHelper.sin(-this.pitch * 0.017453292F);
      return Vec3D.a(f2 * f3, f4, f1 * f3);
    }
    float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
    float f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;
    float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    
    return Vec3D.a(f4 * f5, f6, f3 * f5);
  }
  
  public boolean br()
  {
    return !this.world.isStatic;
  }
  
  public boolean R() {
    return !this.dead;
  }
  
  public boolean S() {
    return !this.dead;
  }
  
  public float getHeadHeight() {
    return this.length * 0.85F;
  }
  
  protected void Q() {
    this.velocityChanged = (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue());
  }
  
  public float getHeadRotation() {
    return this.aO;
  }
  
  public float getAbsorptionHearts() {
    return this.br;
  }
  
  public void setAbsorptionHearts(float f) {
    if (f < 0.0F) {
      f = 0.0F;
    }
    
    this.br = f;
  }
  
  public ScoreboardTeamBase getScoreboardTeam() {
    return null;
  }
  
  public boolean c(EntityLiving entityliving) {
    return a(entityliving.getScoreboardTeam());
  }
  
  public boolean a(ScoreboardTeamBase scoreboardteambase) {
    return getScoreboardTeam() != null ? getScoreboardTeam().isAlly(scoreboardteambase) : false;
  }
  
  public void bu() {}
  
  public void bv() {}
}
