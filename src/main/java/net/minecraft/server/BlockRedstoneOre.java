package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRedstoneOre extends Block
{
  private boolean a;
  
  public BlockRedstoneOre(boolean flag)
  {
    super(Material.STONE);
    if (flag) {
      a(true);
    }
    
    this.a = flag;
  }
  
  public int a(World world) {
    return 30;
  }
  
  public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
    e(world, i, j, k, entityhuman);
    super.attack(world, i, j, k, entityhuman);
  }
  
  public void b(World world, int i, int j, int k, Entity entity)
  {
    if ((entity instanceof EntityHuman)) {
      PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
      if (!event.isCancelled()) {
        e(world, i, j, k, entity);
        super.b(world, i, j, k, entity);
      }
    } else {
      EntityInteractEvent event = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
      world.getServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
        e(world, i, j, k, entity);
        super.b(world, i, j, k, entity);
      }
    }
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2)
  {
    e(world, i, j, k, entityhuman);
    return super.interact(world, i, j, k, entityhuman, l, f, f1, f2);
  }
  
  private void e(World world, int i, int j, int k, Entity entity) {
    m(world, i, j, k);
    if (this == Blocks.REDSTONE_ORE)
    {
      if (CraftEventFactory.callEntityChangeBlockEvent(entity, i, j, k, Blocks.GLOWING_REDSTONE_ORE, 0).isCancelled()) {
        return;
      }
      
      world.setTypeUpdate(i, j, k, Blocks.GLOWING_REDSTONE_ORE);
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (this == Blocks.GLOWING_REDSTONE_ORE)
    {
      if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Blocks.REDSTONE_ORE).isCancelled()) {
        return;
      }
      
      world.setTypeUpdate(i, j, k, Blocks.REDSTONE_ORE);
    }
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.REDSTONE;
  }
  
  public int getDropCount(int i, Random random) {
    return a(random) + random.nextInt(i + 1);
  }
  
  public int a(Random random) {
    return 4 + random.nextInt(2);
  }
  
  public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
    super.dropNaturally(world, i, j, k, l, f, i1);
  }
  






  public int getExpDrop(World world, int l, int i1)
  {
    if (getDropType(l, world.random, i1) != Item.getItemOf(this)) {
      int j1 = 1 + world.random.nextInt(5);
      
      return j1;
    }
    
    return 0;
  }
  
  private void m(World world, int i, int j, int k)
  {
    Random random = world.random;
    double d0 = 0.0625D;
    
    for (int l = 0; l < 6; l++) {
      double d1 = i + random.nextFloat();
      double d2 = j + random.nextFloat();
      double d3 = k + random.nextFloat();
      
      if ((l == 0) && (!world.getType(i, j + 1, k).c())) {
        d2 = j + 1 + d0;
      }
      
      if ((l == 1) && (!world.getType(i, j - 1, k).c())) {
        d2 = j + 0 - d0;
      }
      
      if ((l == 2) && (!world.getType(i, j, k + 1).c())) {
        d3 = k + 1 + d0;
      }
      
      if ((l == 3) && (!world.getType(i, j, k - 1).c())) {
        d3 = k + 0 - d0;
      }
      
      if ((l == 4) && (!world.getType(i + 1, j, k).c())) {
        d1 = i + 1 + d0;
      }
      
      if ((l == 5) && (!world.getType(i - 1, j, k).c())) {
        d1 = i + 0 - d0;
      }
      
      if ((d1 < i) || (d1 > i + 1) || (d2 < 0.0D) || (d2 > j + 1) || (d3 < k) || (d3 > k + 1)) {
        world.addParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  
  protected ItemStack j(int i) {
    return new ItemStack(Blocks.REDSTONE_ORE);
  }
}
