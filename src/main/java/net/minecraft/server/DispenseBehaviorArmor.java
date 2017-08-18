package net.minecraft.server;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

final class DispenseBehaviorArmor extends DispenseBehaviorItem
{
  protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack)
  {
    EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
    int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
    int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
    int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
    AxisAlignedBB axisalignedbb = AxisAlignedBB.a(i, j, k, i + 1, j + 1, k + 1);
    List list = isourceblock.k().a(EntityLiving.class, axisalignedbb, new EntitySelectorEquipable(itemstack));
    
    if (list.size() > 0) {
      EntityLiving entityliving = (EntityLiving)list.get(0);
      int l = (entityliving instanceof EntityHuman) ? 1 : 0;
      int i1 = EntityInsentient.b(itemstack);
      

      ItemStack itemstack1 = itemstack.a(1);
      World world = isourceblock.k();
      Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
      
      BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
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
      

      itemstack1.count = 1;
      entityliving.setEquipment(i1 - l, itemstack1);
      if ((entityliving instanceof EntityInsentient)) {
        ((EntityInsentient)entityliving).a(i1, 2.0F);
      }
      

      return itemstack;
    }
    return super.b(isourceblock, itemstack);
  }
}
