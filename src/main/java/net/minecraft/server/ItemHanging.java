package net.minecraft.server;

import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.plugin.PluginManager;

public class ItemHanging extends Item
{
  private final Class a;
  
  public ItemHanging(Class oclass)
  {
    this.a = oclass;
    a(CreativeModeTab.c);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
    if (l == 0)
      return false;
    if (l == 1) {
      return false;
    }
    int i1 = Direction.e[l];
    EntityHanging entityhanging = a(world, i, j, k, i1);
    
    if (!entityhuman.a(i, j, k, l, itemstack)) {
      return false;
    }
    if ((entityhanging != null) && (entityhanging.survives())) {
      if (!world.isStatic)
      {
        Player who = entityhuman == null ? null : (Player)entityhuman.getBukkitEntity();
        org.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(i, j, k);
        org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock.notchToBlockFace(l);
        
        HangingPlaceEvent event = new HangingPlaceEvent((org.bukkit.entity.Hanging)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
        world.getServer().getPluginManager().callEvent(event);
        
        PaintingPlaceEvent paintingEvent = null;
        if ((entityhanging instanceof EntityPainting))
        {
          paintingEvent = new PaintingPlaceEvent((org.bukkit.entity.Painting)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
          paintingEvent.setCancelled(event.isCancelled());
          world.getServer().getPluginManager().callEvent(paintingEvent);
        }
        
        if ((event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
          return false;
        }
        

        world.addEntity(entityhanging);
      }
      
      itemstack.count -= 1;
    }
    
    return true;
  }
  

  private EntityHanging a(World world, int i, int j, int k, int l)
  {
    return this.a == EntityItemFrame.class ? new EntityItemFrame(world, i, j, k, l) : this.a == EntityPainting.class ? new EntityPainting(world, i, j, k, l) : null;
  }
}
