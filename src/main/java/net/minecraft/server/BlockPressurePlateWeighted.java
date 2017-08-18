package net.minecraft.server;

import java.util.Iterator;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {
  private final int a;
  
  protected BlockPressurePlateWeighted(String s, Material material, int i) {
    super(s, material);
    this.a = i;
  }
  
  protected int e(World world, int i, int j, int k)
  {
    int l = 0;
    Iterator iterator = world.a(Entity.class, a(i, j, k)).iterator();
    
    while (iterator.hasNext()) {
      Entity entity = (Entity)iterator.next();
      
      org.bukkit.event.Cancellable cancellable;
      org.bukkit.event.Cancellable cancellable;
      if ((entity instanceof EntityHuman)) {
        cancellable = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
      } else {
        cancellable = new org.bukkit.event.entity.EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
      }
      

      if (!cancellable.isCancelled()) {
        l++;
      }
    }
    
    l = Math.min(l, this.a);
    

    if (l <= 0) {
      return 0;
    }
    
    float f = Math.min(this.a, l) / this.a;
    return MathHelper.f(f * 15.0F);
  }
  
  protected int c(int i) {
    return i;
  }
  
  protected int d(int i) {
    return i;
  }
  
  public int a(World world) {
    return 10;
  }
}
