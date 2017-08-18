package net.minecraft.server;

import java.util.List;
import java.util.Random;
import java.util.UUID;













public class EntityWitch
  extends EntityMonster
  implements IRangedEntity
{
  private static final UUID bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
  private static final AttributeModifier bq = new AttributeModifier(bp, "Drinking speed penalty", -0.25D, 0).a(false);
  

  private static final Item[] br = { Items.GLOWSTONE_DUST, Items.SUGAR, Items.REDSTONE, Items.SPIDER_EYE, Items.GLASS_BOTTLE, Items.SULPHUR, Items.STICK, Items.STICK };
  

  private int bs;
  

  public EntityWitch(World paramWorld)
  {
    super(paramWorld);
    
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
    this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
    this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
    
    this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
    this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
  }
  
  protected void c()
  {
    super.c();
    
    getDataWatcher().a(21, Byte.valueOf((byte)0));
  }
  
  protected String t()
  {
    return "mob.witch.idle";
  }
  
  protected String aT()
  {
    return "mob.witch.hurt";
  }
  
  protected String aU()
  {
    return "mob.witch.death";
  }
  
  public void a(boolean paramBoolean) {
    getDataWatcher().watch(21, Byte.valueOf((byte)(paramBoolean ? 1 : 0)));
  }
  
  public boolean bZ() {
    return getDataWatcher().getByte(21) == 1;
  }
  
  protected void aD()
  {
    super.aD();
    
    getAttributeInstance(GenericAttributes.maxHealth).setValue(26.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.25D);
  }
  
  public boolean bk()
  {
    return true;
  }
  
  public void e()
  {
    if (!this.world.isStatic) { Object localObject;
      if (bZ()) {
        if (this.bs-- <= 0) {
          a(false);
          ItemStack localItemStack = be();
          setEquipment(0, null);
          
          if ((localItemStack != null) && (localItemStack.getItem() == Items.POTION)) {
            localObject = Items.POTION.g(localItemStack);
            if (localObject != null) {
              for (MobEffect localMobEffect : (List)localObject) {
                addEffect(new MobEffect(localMobEffect));
              }
            }
          }
          
          getAttributeInstance(GenericAttributes.d).b(bq);
        }
      } else {
        int i = -1;
        
        if ((this.random.nextFloat() < 0.15F) && (a(Material.WATER)) && (!hasEffect(MobEffectList.WATER_BREATHING))) {
          i = 8237;
        } else if ((this.random.nextFloat() < 0.15F) && (isBurning()) && (!hasEffect(MobEffectList.FIRE_RESISTANCE))) {
          i = 16307;
        } else if ((this.random.nextFloat() < 0.05F) && (getHealth() < getMaxHealth())) {
          i = 16341;
        } else if ((this.random.nextFloat() < 0.25F) && (getGoalTarget() != null) && (!hasEffect(MobEffectList.FASTER_MOVEMENT)) && (getGoalTarget().f(this) > 121.0D)) {
          i = 16274;
        } else if ((this.random.nextFloat() < 0.25F) && (getGoalTarget() != null) && (!hasEffect(MobEffectList.FASTER_MOVEMENT)) && (getGoalTarget().f(this) > 121.0D)) {
          i = 16274;
        }
        
        if (i > -1) {
          setEquipment(0, new ItemStack(Items.POTION, 1, i));
          this.bs = be().n();
          a(true);
          localObject = getAttributeInstance(GenericAttributes.d);
          ((AttributeInstance)localObject).b(bq);
          ((AttributeInstance)localObject).a(bq);
        }
      }
      
      if (this.random.nextFloat() < 7.5E-4F) {
        this.world.broadcastEntityEffect(this, (byte)15);
      }
    }
    
    super.e();
  }
  











  protected float applyMagicModifier(DamageSource paramDamageSource, float paramFloat)
  {
    paramFloat = super.applyMagicModifier(paramDamageSource, paramFloat);
    
    if (paramDamageSource.getEntity() == this) paramFloat = 0.0F;
    if (paramDamageSource.isMagic()) { paramFloat = (float)(paramFloat * 0.15D);
    }
    return paramFloat;
  }
  
  protected void dropDeathLoot(boolean paramBoolean, int paramInt)
  {
    int i = this.random.nextInt(3) + 1;
    for (int j = 0; j < i; j++) {
      int k = this.random.nextInt(3);
      Item localItem = br[this.random.nextInt(br.length)];
      if (paramInt > 0) { k += this.random.nextInt(paramInt + 1);
      }
      for (int m = 0; m < k; m++) {
        a(localItem, 1);
      }
    }
  }
  
  public void a(EntityLiving paramEntityLiving, float paramFloat)
  {
    if (bZ()) { return;
    }
    EntityPotion localEntityPotion = new EntityPotion(this.world, this, 32732);
    localEntityPotion.pitch -= -20.0F;
    double d1 = paramEntityLiving.locX + paramEntityLiving.motX - this.locX;
    double d2 = paramEntityLiving.locY + paramEntityLiving.getHeadHeight() - 1.100000023841858D - this.locY;
    double d3 = paramEntityLiving.locZ + paramEntityLiving.motZ - this.locZ;
    float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
    
    if ((f >= 8.0F) && (!paramEntityLiving.hasEffect(MobEffectList.SLOWER_MOVEMENT))) {
      localEntityPotion.setPotionValue(32698);
    } else if ((paramEntityLiving.getHealth() >= 8.0F) && (!paramEntityLiving.hasEffect(MobEffectList.POISON))) {
      localEntityPotion.setPotionValue(32660);
    } else if ((f <= 3.0F) && (!paramEntityLiving.hasEffect(MobEffectList.WEAKNESS)) && (this.random.nextFloat() < 0.25F)) {
      localEntityPotion.setPotionValue(32696);
    }
    
    localEntityPotion.shoot(d1, d2 + f * 0.2F, d3, 0.75F, 8.0F);
    
    this.world.addEntity(localEntityPotion);
  }
}
