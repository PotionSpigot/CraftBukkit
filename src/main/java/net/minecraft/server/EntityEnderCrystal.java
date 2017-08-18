package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityEnderCrystal extends Entity
{
  public int a;
  public int b;
  
  public EntityEnderCrystal(World world)
  {
    super(world);
    this.k = true;
    a(2.0F, 2.0F);
    this.height = (this.length / 2.0F);
    this.b = 5;
    this.a = this.random.nextInt(100000);
  }
  
  protected boolean g_() {
    return false;
  }
  
  protected void c() {
    this.datawatcher.a(8, Integer.valueOf(this.b));
  }
  
  public void h() {
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.a += 1;
    this.datawatcher.watch(8, Integer.valueOf(this.b));
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locY);
    int k = MathHelper.floor(this.locZ);
    
    if (((this.world.worldProvider instanceof WorldProviderTheEnd)) && (this.world.getType(i, j, k) != Blocks.FIRE))
    {
      if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
        this.world.setTypeUpdate(i, j, k, Blocks.FIRE);
      }
    }
  }
  
  protected void b(NBTTagCompound nbttagcompound) {}
  
  protected void a(NBTTagCompound nbttagcompound) {}
  
  public boolean R()
  {
    return true;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    if ((!this.dead) && (!this.world.isStatic))
    {
      if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
        return false;
      }
      

      this.b = 0;
      if (this.b <= 0) {
        die();
        if (!this.world.isStatic)
        {
          ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), 6.0F, false);
          this.world.getServer().getPluginManager().callEvent(event);
          if (event.isCancelled()) {
            this.dead = false;
            return false;
          }
          this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
        }
      }
    }
    

    return true;
  }
}
