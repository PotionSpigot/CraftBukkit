package net.minecraft.server;

import org.bukkit.event.player.PlayerFishEvent;

public class ItemFishingRod extends Item
{
  public ItemFishingRod() {
    setMaxDurability(64);
    e(1);
    a(CreativeModeTab.i);
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
    if (entityhuman.hookedFish != null) {
      int i = entityhuman.hookedFish.e();
      
      itemstack.damage(i, entityhuman);
      entityhuman.ba();
    }
    else {
      EntityFishingHook hook = new EntityFishingHook(world, entityhuman);
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), null, (org.bukkit.entity.Fish)hook.getBukkitEntity(), org.bukkit.event.player.PlayerFishEvent.State.FISHING);
      world.getServer().getPluginManager().callEvent(playerFishEvent);
      
      if (playerFishEvent.isCancelled()) {
        entityhuman.hookedFish = null;
        return itemstack;
      }
      
      world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
      if (!world.isStatic) {
        world.addEntity(hook);
      }
      
      entityhuman.ba();
    }
    
    return itemstack;
  }
  
  public boolean e_(ItemStack itemstack) {
    return super.e_(itemstack);
  }
  
  public int c() {
    return 1;
  }
}
