package net.minecraft.server;

import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockCommand extends BlockContainer
{
  public BlockCommand()
  {
    super(Material.ORE);
  }
  
  public TileEntity a(World world, int i) {
    return new TileEntityCommand();
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    if (!world.isStatic) {
      boolean flag = world.isBlockIndirectlyPowered(i, j, k);
      int l = world.getData(i, j, k);
      boolean flag1 = (l & 0x1) != 0;
      

      org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(i, j, k);
      int old = flag1 ? 15 : 0;
      int current = flag ? 15 : 0;
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, old, current);
      world.getServer().getPluginManager().callEvent(eventRedstone);
      

      if ((eventRedstone.getNewCurrent() > 0) && (eventRedstone.getOldCurrent() <= 0)) {
        world.setData(i, j, k, l | 0x1, 4);
        world.a(i, j, k, this, a(world));
      } else if ((eventRedstone.getNewCurrent() <= 0) && (eventRedstone.getOldCurrent() > 0)) {
        world.setData(i, j, k, l & 0xFFFFFFFE, 4);
      }
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    TileEntity tileentity = world.getTileEntity(i, j, k);
    
    if ((tileentity != null) && ((tileentity instanceof TileEntityCommand))) {
      CommandBlockListenerAbstract commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();
      
      commandblocklistenerabstract.a(world);
      world.updateAdjacentComparators(i, j, k, this);
    }
  }
  
  public int a(World world) {
    return 1;
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
    
    if (tileentitycommand != null) {
      entityhuman.a(tileentitycommand);
    }
    
    return true;
  }
  
  public boolean isComplexRedstone() {
    return true;
  }
  
  public int g(World world, int i, int j, int k, int l) {
    TileEntity tileentity = world.getTileEntity(i, j, k);
    
    return (tileentity != null) && ((tileentity instanceof TileEntityCommand)) ? ((TileEntityCommand)tileentity).getCommandBlock().g() : 0;
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
    
    if (itemstack.hasName()) {
      tileentitycommand.getCommandBlock().setName(itemstack.getName());
    }
  }
  
  public int a(Random random) {
    return 0;
  }
}
