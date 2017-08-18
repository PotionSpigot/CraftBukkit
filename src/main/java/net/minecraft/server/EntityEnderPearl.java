package net.minecraft.server;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.event.entity.EnderpearlLandEvent.Reason;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.github.paperspigot.PaperSpigotWorldConfig;

public class EntityEnderPearl extends EntityProjectile
{
  public EntityEnderPearl(World world)
  {
    super(world);
    this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
  }
  
  public EntityEnderPearl(World world, EntityLiving entityliving) {
    super(world, entityliving);
    this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
  }
  
  protected void a(MovingObjectPosition movingobjectposition) {
    if (movingobjectposition.entity != null) {
      movingobjectposition.entity.damageEntity(DamageSource.projectile(this, getShooter()), 0.0F);
    }
    

    if ((this.inUnloadedChunk) && (this.world.paperSpigotConfig.removeUnloadedEnderPearls)) {
      die();
    }
    

    for (int i = 0; i < 32; i++) {
      this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
    }
    
    if (!this.world.isStatic) {
      if ((getShooter() != null) && ((getShooter() instanceof EntityPlayer))) {
        EntityPlayer entityplayer = (EntityPlayer)getShooter();
        
        if ((entityplayer.playerConnection.b().isConnected()) && (entityplayer.world == this.world))
        {
          CraftPlayer player = entityplayer.getBukkitEntity();
          
          EnderpearlLandEvent.Reason reason = movingobjectposition.entity != null ? EnderpearlLandEvent.Reason.ENTITY : EnderpearlLandEvent.Reason.BLOCK;
          org.bukkit.event.entity.EnderpearlLandEvent landEvent = new org.bukkit.event.entity.EnderpearlLandEvent((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEnderPearl)getBukkitEntity(), reason);
          org.bukkit.Bukkit.getPluginManager().callEvent(landEvent);
          
          Location location = getBukkitEntity().getLocation();
          location.setPitch(player.getLocation().getPitch());
          location.setYaw(player.getLocation().getYaw());
          
          PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
          org.bukkit.Bukkit.getPluginManager().callEvent(teleEvent);
          
          if ((!teleEvent.isCancelled()) && (!entityplayer.playerConnection.isDisconnected())) {
            if (getShooter().am()) {
              getShooter().mount((Entity)null);
            }
            
            entityplayer.playerConnection.teleport(teleEvent.getTo());
            getShooter().fallDistance = 0.0F;
            org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = this;
            getShooter().damageEntity(DamageSource.FALL, 5.0F);
            org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = null;
          }
        }
      }
      

      die();
    }
  }
}
