package net.minecraft.server;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.projectiles.CraftBlockProjectileSource;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem
{
  public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    World world = isourceblock.k();
    IPosition iposition = BlockDispenser.a(isourceblock);
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    IProjectile iprojectile = a(world, iposition);
    

    ItemStack itemstack1 = itemstack.a(1);
    Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(enumfacing.getAdjacentX(), enumfacing.getAdjacentY() + 0.1F, enumfacing.getAdjacentZ()));
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
    
    iprojectile.shoot(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), b(), a());
    ((Entity)iprojectile).projectileSource = new CraftBlockProjectileSource((TileEntityDispenser)isourceblock.getTileEntity());
    

    world.addEntity((Entity)iprojectile);
    
    return itemstack;
  }
  
  protected void a(ISourceBlock isourceblock) {
    isourceblock.k().triggerEffect(1002, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
  }
  
  protected abstract IProjectile a(World paramWorld, IPosition paramIPosition);
  
  protected float a() {
    return 6.0F;
  }
  
  protected float b() {
    return 1.1F;
  }
}
