package net.minecraft.server;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class EntityCow extends EntityAnimal
{
  public EntityCow(World world)
  {
    super(world);
    a(0.9F, 1.3F);
    getNavigation().a(true);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
    this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
    this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
  }
  
  public boolean bk() {
    return true;
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
  }
  
  protected String t() {
    return "mob.cow.say";
  }
  
  protected String aT() {
    return "mob.cow.hurt";
  }
  
  protected String aU() {
    return "mob.cow.hurt";
  }
  
  protected void a(int i, int j, int k, Block block) {
    makeSound("mob.cow.step", 0.15F, 1.0F);
  }
  
  protected float bf() {
    return 0.4F;
  }
  
  protected Item getLoot() {
    return Items.LEATHER;
  }
  
  protected void dropDeathLoot(boolean flag, int i) {
    int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
    


    for (int k = 0; k < j; k++) {
      a(Items.LEATHER, 1);
    }
    
    j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
    
    for (k = 0; k < j; k++) {
      if (isBurning()) {
        a(Items.COOKED_BEEF, 1);
      } else {
        a(Items.RAW_BEEF, 1);
      }
    }
  }
  
  public boolean a(EntityHuman entityhuman) {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && (itemstack.getItem() == Items.BUCKET) && (!entityhuman.abilities.canInstantlyBuild))
    {
      Location loc = getBukkitEntity().getLocation();
      PlayerBucketFillEvent event = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Items.MILK_BUCKET);
      
      if (event.isCancelled()) {
        return false;
      }
      
      if (--itemstack.count <= 0) {
        entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack.asNMSCopy(event.getItemStack()));
      } else if (!entityhuman.inventory.pickup(new ItemStack(Items.MILK_BUCKET))) {
        entityhuman.drop(org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack.asNMSCopy(event.getItemStack()), false);
      }
      

      return true;
    }
    return super.a(entityhuman);
  }
  
  public EntityCow b(EntityAgeable entityageable)
  {
    return new EntityCow(this.world);
  }
  
  public EntityAgeable createChild(EntityAgeable entityageable) {
    return b(entityageable);
  }
}
