package net.minecraft.server;

import java.util.Random;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class DispenseBehaviorItem implements IDispenseBehavior
{
  public final ItemStack a(ISourceBlock isourceblock, ItemStack itemstack)
  {
    ItemStack itemstack1 = b(isourceblock, itemstack);
    
    a(isourceblock);
    a(isourceblock, BlockDispenser.b(isourceblock.h()));
    return itemstack1;
  }
  
  protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    IPosition iposition = BlockDispenser.a(isourceblock);
    ItemStack itemstack1 = itemstack.a(1);
    

    if (!a(isourceblock.k(), itemstack1, 6, enumfacing, isourceblock)) {
      itemstack.count += 1;
    }
    

    return itemstack;
  }
  
  public static boolean a(World world, ItemStack itemstack, int i, EnumFacing enumfacing, ISourceBlock isourceblock)
  {
    IPosition iposition = BlockDispenser.a(isourceblock);
    
    double d0 = iposition.getX();
    double d1 = iposition.getY();
    double d2 = iposition.getZ();
    EntityItem entityitem = new EntityItem(world, d0, d1 - 0.3D, d2, itemstack);
    double d3 = world.random.nextDouble() * 0.1D + 0.2D;
    
    entityitem.motX = (enumfacing.getAdjacentX() * d3);
    entityitem.motY = 0.20000000298023224D;
    entityitem.motZ = (enumfacing.getAdjacentZ() * d3);
    entityitem.motX += world.random.nextGaussian() * 0.007499999832361937D * i;
    entityitem.motY += world.random.nextGaussian() * 0.007499999832361937D * i;
    entityitem.motZ += world.random.nextGaussian() * 0.007499999832361937D * i;
    

    Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
    CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
    
    BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(entityitem.motX, entityitem.motY, entityitem.motZ));
    if (!BlockDispenser.eventFired) {
      world.getServer().getPluginManager().callEvent(event);
    }
    
    if (event.isCancelled()) {
      return false;
    }
    
    entityitem.setItemStack(CraftItemStack.asNMSCopy(event.getItem()));
    entityitem.motX = event.getVelocity().getX();
    entityitem.motY = event.getVelocity().getY();
    entityitem.motZ = event.getVelocity().getZ();
    
    if (!event.getItem().equals(craftItem))
    {
      ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
      IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
      if ((idispensebehavior != IDispenseBehavior.a) && (idispensebehavior.getClass() != DispenseBehaviorItem.class)) {
        idispensebehavior.a(isourceblock, eventStack);
      } else {
        world.addEntity(entityitem);
      }
      return false;
    }
    
    world.addEntity(entityitem);
    
    return true;
  }
  
  protected void a(ISourceBlock isourceblock)
  {
    isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
  }
  
  protected void a(ISourceBlock isourceblock, EnumFacing enumfacing) {
    isourceblock.k().triggerEffect(2000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), a(enumfacing));
  }
  
  private int a(EnumFacing enumfacing) {
    return enumfacing.getAdjacentX() + 1 + (enumfacing.getAdjacentZ() + 1) * 3;
  }
}
