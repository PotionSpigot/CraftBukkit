package net.minecraft.server;

import java.util.Random;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.plugin.PluginManager;

public class EntitySnowman extends EntityGolem implements IRangedEntity
{
  public EntitySnowman(World world)
  {
    super(world);
    a(0.4F, 1.8F);
    getNavigation().a(true);
    this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.25D, 20, 10.0F));
    this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
    this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, true, false, IMonster.a));
  }
  
  public boolean bk() {
    return true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
  }
  
  public void e() {
    super.e();
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locY);
    int k = MathHelper.floor(this.locZ);
    
    if (L()) {
      damageEntity(DamageSource.DROWN, 1.0F);
    }
    
    if (this.world.getBiome(i, k).a(i, j, k) > 1.0F) {
      damageEntity(org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.MELTING, 1.0F);
    }
    
    for (int l = 0; l < 4; l++) {
      i = MathHelper.floor(this.locX + (l % 2 * 2 - 1) * 0.25F);
      j = MathHelper.floor(this.locY);
      k = MathHelper.floor(this.locZ + (l / 2 % 2 * 2 - 1) * 0.25F);
      if ((this.world.getType(i, j, k).getMaterial() == Material.AIR) && (this.world.getBiome(i, k).a(i, j, k) < 0.8F) && (Blocks.SNOW.canPlace(this.world, i, j, k)))
      {
        BlockState blockState = this.world.getWorld().getBlockAt(i, j, k).getState();
        blockState.setType(org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getMaterial(Blocks.SNOW));
        
        EntityBlockFormEvent event = new EntityBlockFormEvent(getBukkitEntity(), blockState.getBlock(), blockState);
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          blockState.update(true);
        }
      }
    }
  }
  
  protected Item getLoot()
  {
    return Items.SNOW_BALL;
  }
  
  protected void dropDeathLoot(boolean flag, int i) {
    int j = this.random.nextInt(16);
    
    for (int k = 0; k < j; k++) {
      a(Items.SNOW_BALL, 1);
    }
  }
  
  public void a(EntityLiving entityliving, float f) {
    EntitySnowball entitysnowball = new EntitySnowball(this.world, this);
    double d0 = entityliving.locX - this.locX;
    double d1 = entityliving.locY + entityliving.getHeadHeight() - 1.100000023841858D - entitysnowball.locY;
    double d2 = entityliving.locZ - this.locZ;
    float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
    
    entitysnowball.shoot(d0, d1 + f1, d2, 1.6F, 12.0F);
    makeSound("random.bow", 1.0F, 1.0F / (aI().nextFloat() * 0.4F + 0.8F));
    this.world.addEntity(entitysnowball);
  }
}
