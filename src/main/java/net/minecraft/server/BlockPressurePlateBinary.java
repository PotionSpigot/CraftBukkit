package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import org.bukkit.event.Cancellable;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract
{
  private EnumMobType a;
  
  protected BlockPressurePlateBinary(String s, Material material, EnumMobType enummobtype)
  {
    super(s, material);
    this.a = enummobtype;
  }
  
  protected int d(int i) {
    return i > 0 ? 1 : 0;
  }
  
  protected int c(int i) {
    return i == 1 ? 15 : 0;
  }
  
  protected int e(World world, int i, int j, int k) {
    List list = null;
    
    if (this.a == EnumMobType.EVERYTHING) {
      list = world.getEntities((Entity)null, a(i, j, k));
    }
    
    if (this.a == EnumMobType.MOBS) {
      list = world.a(EntityLiving.class, a(i, j, k));
    }
    
    if (this.a == EnumMobType.PLAYERS) {
      list = world.a(EntityHuman.class, a(i, j, k));
    }
    
    if ((list != null) && (!list.isEmpty())) {
      Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        Entity entity = (Entity)iterator.next();
        

        if (c(world.getData(i, j, k)) == 0) {
          org.bukkit.World bworld = world.getWorld();
          org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();
          Cancellable cancellable;
          Cancellable cancellable;
          if ((entity instanceof EntityHuman)) {
            cancellable = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
          } else {
            cancellable = new org.bukkit.event.entity.EntityInteractEvent(entity.getBukkitEntity(), bworld.getBlockAt(i, j, k));
            manager.callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
          }
          

          if (cancellable.isCancelled()) {}




        }
        else if (!entity.az()) {
          return 15;
        }
      }
    }
    
    return 0;
  }
}
