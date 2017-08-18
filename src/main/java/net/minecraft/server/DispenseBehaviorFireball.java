package net.minecraft.server;

import java.util.Random;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.projectiles.CraftBlockProjectileSource;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

final class DispenseBehaviorFireball extends DispenseBehaviorItem
{
  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    IPosition iposition = BlockDispenser.a(isourceblock);
    double d0 = iposition.getX() + enumfacing.getAdjacentX() * 0.3F;
    double d1 = iposition.getY() + enumfacing.getAdjacentX() * 0.3F;
    double d2 = iposition.getZ() + enumfacing.getAdjacentZ() * 0.3F;
    World world = isourceblock.k();
    Random random = world.random;
    double d3 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentX();
    double d4 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentY();
    double d5 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentZ();
    

    ItemStack itemstack1 = itemstack.a(1);
    Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d3, d4, d5));
    if (!BlockDispenser.eventFired) {
      world.getServer().getPluginManager().callEvent(event);
    }
    
    if (event.isCancelled()) {
      itemstack.count += 1;
      return itemstack;
    }
    
    if (!event.getItem().equals(craftItem)) {
      itemstack.count += 1;
      
      ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
      IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
      if ((idispensebehavior != IDispenseBehavior.a) && (idispensebehavior != this)) {
        idispensebehavior.a(isourceblock, eventStack);
        return itemstack;
      }
    }
    
    EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
    entitysmallfireball.projectileSource = new CraftBlockProjectileSource((TileEntityDispenser)isourceblock.getTileEntity());
    
    world.addEntity(entitysmallfireball);
    


    return itemstack;
  }
  
  protected void a(ISourceBlock isourceblock) {
    isourceblock.k().triggerEffect(1009, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
  }
}
