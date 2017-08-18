package net.minecraft.server;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.plugin.PluginManager;

public class BlockGrass
  extends Block
  implements IBlockFragilePlantElement
{
  private static final Logger a = ;
  
  protected BlockGrass() {
    super(Material.GRASS);
    a(true);
    a(CreativeModeTab.b);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      if ((world.getLightLevel(i, j + 1, k) < 4) && (world.getType(i, j + 1, k).k() > 2))
      {
        org.bukkit.World bworld = world.getWorld();
        BlockState blockState = bworld.getBlockAt(i, j, k).getState();
        blockState.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
        
        BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
        world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          blockState.update(true);
        }
      }
      else if (world.getLightLevel(i, j + 1, k) >= 9) {
        int numGrowth = Math.min(4, Math.max(20, (int)(400.0F / world.growthOdds)));
        for (int l = 0; l < numGrowth; l++) {
          int i1 = i + random.nextInt(3) - 1;
          int j1 = j + random.nextInt(5) - 3;
          int k1 = k + random.nextInt(3) - 1;
          Block block = world.getType(i1, j1 + 1, k1);
          
          if ((world.getType(i1, j1, k1) == Blocks.DIRT) && (world.getData(i1, j1, k1) == 0) && (world.getLightLevel(i1, j1 + 1, k1) >= 4) && (block.k() <= 2))
          {
            org.bukkit.World bworld = world.getWorld();
            BlockState blockState = bworld.getBlockAt(i1, j1, k1).getState();
            blockState.setType(CraftMagicNumbers.getMaterial(Blocks.GRASS));
            
            BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(i, j, k), blockState);
            world.getServer().getPluginManager().callEvent(event);
            
            if (!event.isCancelled()) {
              blockState.update(true);
            }
          }
        }
      }
    }
  }
  
  public Item getDropType(int i, Random random, int j)
  {
    return Blocks.DIRT.getDropType(0, random, j);
  }
  
  public boolean a(World world, int i, int j, int k, boolean flag) {
    return true;
  }
  
  public boolean a(World world, Random random, int i, int j, int k) {
    return true;
  }
  
  public void b(World world, Random random, int i, int j, int k) {
    int l = 0;
    
    while (l < 128) {
      int i1 = i;
      int j1 = j + 1;
      int k1 = k;
      int l1 = 0;
      

      while (l1 < l / 16) {
        i1 += random.nextInt(3) - 1;
        j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
        k1 += random.nextInt(3) - 1;
        if ((world.getType(i1, j1 - 1, k1) != Blocks.GRASS) || (world.getType(i1, j1, k1).r())) break label297;
        l1++;
      }
      
      if (world.getType(i1, j1, k1).material == Material.AIR) {
        if (random.nextInt(8) != 0) {
          if (Blocks.LONG_GRASS.j(world, i1, j1, k1)) {
            CraftEventFactory.handleBlockGrowEvent(world, i1, j1, k1, Blocks.LONG_GRASS, 1);
          }
        } else {
          String s = world.getBiome(i1, k1).a(random, i1, j1, k1);
          
          a.debug("Flower in " + world.getBiome(i1, k1).af + ": " + s);
          BlockFlowers blockflowers = BlockFlowers.e(s);
          
          if ((blockflowers != null) && (blockflowers.j(world, i1, j1, k1))) {
            int i2 = BlockFlowers.f(s);
            
            CraftEventFactory.handleBlockGrowEvent(world, i1, j1, k1, blockflowers, i2);
          }
        }
      }
      label297:
      l++;
    }
  }
}
