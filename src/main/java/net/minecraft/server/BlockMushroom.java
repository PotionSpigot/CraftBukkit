package net.minecraft.server;

import java.util.Random;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.plugin.PluginManager;

public class BlockMushroom extends BlockPlant implements IBlockFragilePlantElement
{
  protected BlockMushroom()
  {
    float f = 0.2F;
    
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    a(true);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    int sourceX = i;int sourceY = j;int sourceZ = k;
    if (random.nextInt(Math.max(1, (int)world.growthOdds / world.spigotConfig.mushroomModifier * 25)) == 0) {
      byte b0 = 4;
      int l = 5;
      




      for (int i1 = i - b0; i1 <= i + b0; i1++) {
        for (int j1 = k - b0; j1 <= k + b0; j1++) {
          for (int k1 = j - 1; k1 <= j + 1; k1++) {
            if (world.getType(i1, k1, j1) == this) {
              l--;
              if (l <= 0) {
                return;
              }
            }
          }
        }
      }
      
      i1 = i + random.nextInt(3) - 1;
      int j1 = j + random.nextInt(2) - random.nextInt(2);
      int k1 = k + random.nextInt(3) - 1;
      
      for (int l1 = 0; l1 < 4; l1++) {
        if ((world.isEmpty(i1, j1, k1)) && (j(world, i1, j1, k1))) {
          i = i1;
          j = j1;
          k = k1;
        }
        
        i1 = i + random.nextInt(3) - 1;
        j1 = j + random.nextInt(2) - random.nextInt(2);
        k1 = k + random.nextInt(3) - 1;
      }
      
      if ((world.isEmpty(i1, j1, k1)) && (j(world, i1, j1, k1)))
      {
        org.bukkit.World bworld = world.getWorld();
        BlockState blockState = bworld.getBlockAt(i1, j1, k1).getState();
        blockState.setType(org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getMaterial(this));
        
        BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(sourceX, sourceY, sourceZ), blockState);
        world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          blockState.update(true);
        }
      }
    }
  }
  
  public boolean canPlace(World world, int i, int j, int k)
  {
    return (super.canPlace(world, i, j, k)) && (j(world, i, j, k));
  }
  
  protected boolean a(Block block) {
    return block.j();
  }
  
  public boolean j(World world, int i, int j, int k) {
    if ((j >= 0) && (j < 256)) {
      Block block = world.getType(i, j - 1, k);
      
      return (block == Blocks.MYCEL) || ((block == Blocks.DIRT) && (world.getData(i, j - 1, k) == 2)) || ((world.j(i, j, k) < 13) && (a(block)));
    }
    return false;
  }
  
  public boolean grow(World world, int i, int j, int k, Random random)
  {
    int l = world.getData(i, j, k);
    world.setAir(i, j, k);
    WorldGenHugeMushroom worldgenhugemushroom = null;
    
    if (this == Blocks.BROWN_MUSHROOM) {
      BlockSapling.treeType = TreeType.BROWN_MUSHROOM;
      worldgenhugemushroom = new WorldGenHugeMushroom(0);
    } else if (this == Blocks.RED_MUSHROOM) {
      BlockSapling.treeType = TreeType.RED_MUSHROOM;
      worldgenhugemushroom = new WorldGenHugeMushroom(1);
    }
    
    if ((worldgenhugemushroom != null) && (worldgenhugemushroom.generate(world, random, i, j, k))) {
      return true;
    }
    world.setTypeAndData(i, j, k, this, l, 3);
    return false;
  }
  
  public boolean a(World world, int i, int j, int k, boolean flag)
  {
    return true;
  }
  
  public boolean a(World world, Random random, int i, int j, int k) {
    return random.nextFloat() < 0.4D;
  }
  
  public void b(World world, Random random, int i, int j, int k) {
    grow(world, i, j, k, random);
  }
}
