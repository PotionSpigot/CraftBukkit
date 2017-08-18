package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityPigZombie extends EntityZombie
{
  private static final java.util.UUID bq = java.util.UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
  private static final AttributeModifier br = new AttributeModifier(bq, "Attacking speed boost", 0.45D, 0).a(false);
  public int angerLevel;
  private int soundDelay;
  private Entity bu;
  
  public EntityPigZombie(World world) {
    super(world);
    this.fireProof = true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(bp).setValue(0.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.5D);
    getAttributeInstance(GenericAttributes.e).setValue(5.0D);
  }
  
  protected boolean bk() {
    return false;
  }
  
  public void h() {
    if ((this.bu != this.target) && (!this.world.isStatic)) {
      AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
      
      attributeinstance.b(br);
      if (this.target != null) {
        attributeinstance.a(br);
      }
    }
    
    this.bu = this.target;
    if ((this.soundDelay > 0) && (--this.soundDelay == 0)) {
      makeSound("mob.zombiepig.zpigangry", bf() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
    }
    
    super.h();
  }
  
  public boolean canSpawn() {
    return (this.world.difficulty != EnumDifficulty.PEACEFUL) && (this.world.b(this.boundingBox)) && (this.world.getCubes(this, this.boundingBox).isEmpty()) && (!this.world.containsLiquid(this.boundingBox));
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setShort("Anger", (short)this.angerLevel);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    this.angerLevel = nbttagcompound.getShort("Anger");
  }
  
  protected Entity findTarget() {
    return this.angerLevel == 0 ? null : super.findTarget();
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    Entity entity = damagesource.getEntity();
    
    if ((entity instanceof EntityHuman)) {
      List list = this.world.getEntities(this, this.boundingBox.grow(32.0D, 32.0D, 32.0D));
      
      for (int i = 0; i < list.size(); i++) {
        Entity entity1 = (Entity)list.get(i);
        
        if ((entity1 instanceof EntityPigZombie)) {
          EntityPigZombie entitypigzombie = (EntityPigZombie)entity1;
          
          entitypigzombie.c(entity, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
        }
      }
      
      c(entity, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
    }
    
    return super.damageEntity(damagesource, f);
  }
  

  private void c(Entity entity, EntityTargetEvent.TargetReason reason)
  {
    EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), entity.getBukkitEntity(), reason);
    this.world.getServer().getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      return;
    }
    
    if (event.getTarget() == null) {
      this.target = null;
      return;
    }
    entity = ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity)event.getTarget()).getHandle();
    

    this.target = entity;
    this.angerLevel = (400 + this.random.nextInt(400));
    this.soundDelay = this.random.nextInt(40);
  }
  
  protected String t() {
    return "mob.zombiepig.zpig";
  }
  
  protected String aT() {
    return "mob.zombiepig.zpighurt";
  }
  
  protected String aU() {
    return "mob.zombiepig.zpigdeath";
  }
  
  protected void dropDeathLoot(boolean flag, int i) {
    int j = this.random.nextInt(2 + i);
    


    for (int k = 0; k < j; k++) {
      a(Items.ROTTEN_FLESH, 1);
    }
    
    j = this.random.nextInt(2 + i);
    
    for (k = 0; k < j; k++) {
      a(Items.GOLD_NUGGET, 1);
    }
  }
  
  public boolean a(EntityHuman entityhuman) {
    return false;
  }
  
  protected void getRareDrop(int i) {
    a(Items.GOLD_INGOT, 1);
  }
  
  protected void bC() {
    setEquipment(0, new ItemStack(Items.GOLD_SWORD));
  }
  
  public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
    super.prepare(groupdataentity);
    setVillager(false);
    return groupdataentity;
  }
}
