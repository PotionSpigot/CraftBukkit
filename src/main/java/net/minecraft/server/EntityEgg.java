package net.minecraft.server;

import java.util.Random;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class EntityEgg extends EntityProjectile
{
  public EntityEgg(World world)
  {
    super(world);
  }
  
  public EntityEgg(World world, EntityLiving entityliving) {
    super(world, entityliving);
  }
  
  public EntityEgg(World world, double d0, double d1, double d2) {
    super(world, d0, d1, d2);
  }
  
  protected void a(MovingObjectPosition movingobjectposition) {
    if (movingobjectposition.entity != null) {
      movingobjectposition.entity.damageEntity(DamageSource.projectile(this, getShooter()), 0.0F);
    }
    

    boolean hatching = (!this.world.isStatic) && (this.random.nextInt(8) == 0);
    int numHatching = this.random.nextInt(32) == 0 ? 4 : 1;
    if (!hatching) {
      numHatching = 0;
    }
    
    EntityType hatchingType = EntityType.CHICKEN;
    
    Entity shooter = getShooter();
    if ((shooter instanceof EntityPlayer)) {
      Player player = shooter == null ? null : (Player)shooter.getBukkitEntity();
      
      PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (org.bukkit.entity.Egg)getBukkitEntity(), hatching, (byte)numHatching, hatchingType);
      this.world.getServer().getPluginManager().callEvent(event);
      
      hatching = event.isHatching();
      numHatching = event.getNumHatches();
      hatchingType = event.getHatchingType();
    }
    
    if (hatching) {
      for (int k = 0; k < numHatching; k++) {
        org.bukkit.entity.Entity entity = this.world.getWorld().spawn(new org.bukkit.Location(this.world.getWorld(), this.locX, this.locY, this.locZ, this.yaw, 0.0F), hatchingType.getEntityClass(), org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.EGG);
        if ((entity instanceof Ageable)) {
          ((Ageable)entity).setBaby();
        }
      }
    }
    

    for (int j = 0; j < 8; j++) {
      this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
    }
    
    if (!this.world.isStatic) {
      die();
    }
  }
}
