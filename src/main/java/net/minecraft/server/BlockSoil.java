package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockSoil extends Block
{
  protected BlockSoil()
  {
    super(Material.EARTH);
    a(true);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
    g(255);
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    return AxisAlignedBB.a(i + 0, j + 0, k + 0, i + 1, j + 1, k + 1);
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if ((!m(world, i, j, k)) && (!world.isRainingAt(i, j + 1, k))) {
      int l = world.getData(i, j, k);
      
      if (l > 0) {
        world.setData(i, j, k, l - 1, 2);
      } else if (!e(world, i, j, k))
      {
        org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        if (CraftEventFactory.callBlockFadeEvent(block, Blocks.DIRT).isCancelled()) {
          return;
        }
        

        world.setTypeUpdate(i, j, k, Blocks.DIRT);
      }
    } else {
      world.setData(i, j, k, 7, 2);
    }
  }
  
  public void a(World world, int i, int j, int k, Entity entity, float f) {
    if ((!world.isStatic) && (world.random.nextFloat() < f - 0.5F)) {
      if ((!(entity instanceof EntityHuman)) && (!world.getGameRules().getBoolean("mobGriefing"))) {
        return;
      }
      
      Cancellable cancellable;
      Cancellable cancellable;
      if ((entity instanceof EntityHuman)) {
        cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
      } else {
        cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
      }
      
      if (cancellable.isCancelled()) {
        return;
      }
      
      if (CraftEventFactory.callEntityChangeBlockEvent(entity, i, j, k, Blocks.DIRT, 0).isCancelled()) {
        return;
      }
      
      world.setTypeUpdate(i, j, k, Blocks.DIRT);
    }
  }
  
  private boolean e(World world, int i, int j, int k) {
    byte b0 = 0;
    
    for (int l = i - b0; l <= i + b0; l++) {
      for (int i1 = k - b0; i1 <= k + b0; i1++) {
        Block block = world.getType(l, j + 1, i1);
        
        if ((block == Blocks.CROPS) || (block == Blocks.MELON_STEM) || (block == Blocks.PUMPKIN_STEM) || (block == Blocks.POTATOES) || (block == Blocks.CARROTS)) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  private boolean m(World world, int i, int j, int k) {
    for (int l = i - 4; l <= i + 4; l++) {
      for (int i1 = j; i1 <= j + 1; i1++) {
        for (int j1 = k - 4; j1 <= k + 4; j1++) {
          if (world.getType(l, i1, j1).getMaterial() == Material.WATER) {
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    super.doPhysics(world, i, j, k, block);
    Material material = world.getType(i, j + 1, k).getMaterial();
    
    if (material.isBuildable()) {
      world.setTypeUpdate(i, j, k, Blocks.DIRT);
    }
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Blocks.DIRT.getDropType(0, random, j);
  }
}
