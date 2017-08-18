package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class ItemLeash extends Item
{
  public ItemLeash()
  {
    a(CreativeModeTab.i);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
    Block block = world.getType(i, j, k);
    
    if (block.b() == 11) {
      if (world.isStatic) {
        return true;
      }
      a(entityhuman, world, i, j, k);
      return true;
    }
    
    return false;
  }
  
  public static boolean a(EntityHuman entityhuman, World world, int i, int j, int k)
  {
    EntityLeash entityleash = EntityLeash.b(world, i, j, k);
    boolean flag = false;
    double d0 = 7.0D;
    List list = world.a(EntityInsentient.class, AxisAlignedBB.a(i - d0, j - d0, k - d0, i + d0, j + d0, k + d0));
    
    if (list != null) {
      Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
        
        if ((entityinsentient.bN()) && (entityinsentient.getLeashHolder() == entityhuman)) {
          if (entityleash == null) {
            entityleash = EntityLeash.a(world, i, j, k);
            

            HangingPlaceEvent event = new HangingPlaceEvent((org.bukkit.entity.Hanging)entityleash.getBukkitEntity(), entityhuman != null ? (org.bukkit.entity.Player)entityhuman.getBukkitEntity() : null, world.getWorld().getBlockAt(i, j, k), org.bukkit.block.BlockFace.SELF);
            world.getServer().getPluginManager().callEvent(event);
            
            if (event.isCancelled()) {
              entityleash.die();
              return false;
            }
          }
          


          if (!org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, entityleash, entityhuman).isCancelled())
          {



            entityinsentient.setLeashHolder(entityleash, true);
            flag = true;
          }
        }
      }
    }
    return flag;
  }
}
