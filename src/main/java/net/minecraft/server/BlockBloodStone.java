package net.minecraft.server;

import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends Block
{
  public BlockBloodStone() {
    super(Material.STONE);
    a(CreativeModeTab.b);
  }
  
  public MaterialMapColor f(int i) {
    return MaterialMapColor.K;
  }
  
  public void doPhysics(World world, int i, int j, int k, int l)
  {
    if ((Block.getById(l) != null) && (Block.getById(l).isPowerSource())) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      int power = block.getBlockPower();
      
      BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
      world.getServer().getPluginManager().callEvent(event);
    }
  }
}
