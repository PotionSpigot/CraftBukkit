package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockMinecartDetector extends BlockMinecartTrackAbstract
{
  public BlockMinecartDetector()
  {
    super(true);
    a(true);
  }
  
  public int a(World world) {
    return 20;
  }
  
  public boolean isPowerSource() {
    return true;
  }
  
  public void a(World world, int i, int j, int k, Entity entity) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if ((l & 0x8) == 0) {
        a(world, i, j, k, l);
      }
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if ((l & 0x8) != 0) {
        a(world, i, j, k, l);
      }
    }
  }
  
  public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return (iblockaccess.getData(i, j, k) & 0x8) != 0 ? 15 : 0;
  }
  
  public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return l == 1 ? 15 : (iblockaccess.getData(i, j, k) & 0x8) == 0 ? 0 : 0;
  }
  
  private void a(World world, int i, int j, int k, int l) {
    boolean flag = (l & 0x8) != 0;
    boolean flag1 = false;
    float f = 0.125F;
    List list = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f));
    
    if (!list.isEmpty()) {
      flag1 = true;
    }
    

    if (flag != flag1) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
      world.getServer().getPluginManager().callEvent(eventRedstone);
      
      flag1 = eventRedstone.getNewCurrent() > 0;
    }
    

    if ((flag1) && (!flag)) {
      world.setData(i, j, k, l | 0x8, 3);
      world.applyPhysics(i, j, k, this);
      world.applyPhysics(i, j - 1, k, this);
      world.c(i, j, k, i, j, k);
    }
    
    if ((!flag1) && (flag)) {
      world.setData(i, j, k, l & 0x7, 3);
      world.applyPhysics(i, j, k, this);
      world.applyPhysics(i, j - 1, k, this);
      world.c(i, j, k, i, j, k);
    }
    
    if (flag1) {
      world.a(i, j, k, this, a(world));
    }
    
    world.updateAdjacentComparators(i, j, k, this);
  }
  
  public void onPlace(World world, int i, int j, int k) {
    super.onPlace(world, i, j, k);
    a(world, i, j, k, world.getData(i, j, k));
  }
  
  public boolean isComplexRedstone() {
    return true;
  }
  
  public int g(World world, int i, int j, int k, int l) {
    if ((world.getData(i, j, k) & 0x8) > 0) {
      float f = 0.125F;
      List list = world.a(EntityMinecartCommandBlock.class, AxisAlignedBB.a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f));
      
      if (list.size() > 0) {
        return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlock().g();
      }
      
      List list1 = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f), IEntitySelector.c);
      
      if (list1.size() > 0) {
        return Container.b((IInventory)list1.get(0));
      }
    }
    
    return 0;
  }
}
