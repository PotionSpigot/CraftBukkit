package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.PluginManager;
import org.spigotmc.SpigotWorldConfig;

public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement
{
  public static final String[] a = { "oak", "spruce", "birch", "jungle", "acacia", "roofed_oak" };
  private static final IIcon[] b = new IIcon[a.length];
  public static TreeType treeType;
  
  protected BlockSapling() {
    float f = 0.4F;
    
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    a(CreativeModeTab.c);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      super.a(world, i, j, k, random);
      if ((world.getLightLevel(i, j + 1, k) >= 9) && (random.nextInt(Math.max(2, (int)(world.growthOdds / world.spigotConfig.saplingModifier * 7.0F + 0.5F))) == 0))
      {
        world.captureTreeGeneration = true;
        
        grow(world, i, j, k, random);
        
        world.captureTreeGeneration = false;
        if (world.capturedBlockStates.size() > 0) {
          TreeType treeType = treeType;
          treeType = null;
          Location location = new Location(world.getWorld(), i, j, k);
          List<BlockState> blocks = (List)world.capturedBlockStates.clone();
          world.capturedBlockStates.clear();
          StructureGrowEvent event = null;
          if (treeType != null) {
            event = new StructureGrowEvent(location, treeType, false, null, blocks);
            Bukkit.getPluginManager().callEvent(event);
          }
          if ((event == null) || (!event.isCancelled())) {
            for (BlockState blockstate : blocks) {
              blockstate.update(true);
            }
          }
        }
      }
    }
  }
  
  public void grow(World world, int i, int j, int k, Random random)
  {
    int l = world.getData(i, j, k);
    
    if ((l & 0x8) == 0) {
      world.setData(i, j, k, l | 0x8, 4);
    } else {
      d(world, i, j, k, random);
    }
  }
  
  public void d(World world, int i, int j, int k, Random random) {
    int l = world.getData(i, j, k) & 0x7;
    
    Object object;
    Object object;
    if (random.nextInt(10) == 0) {
      treeType = TreeType.BIG_TREE;
      object = new WorldGenBigTree(true);
    } else {
      treeType = TreeType.TREE;
      object = new WorldGenTrees(true);
    }
    
    int i1 = 0;
    int j1 = 0;
    boolean flag = false;
    
    switch (l)
    {
    case 0: 
    default: 
      break;
    
    case 1: 
      for (i1 = 0; i1 >= -1; i1--) {
        for (j1 = 0; j1 >= -1; j1--) {
          if ((a(world, i + i1, j, k + j1, 1)) && (a(world, i + i1 + 1, j, k + j1, 1)) && (a(world, i + i1, j, k + j1 + 1, 1)) && (a(world, i + i1 + 1, j, k + j1 + 1, 1))) {
            treeType = TreeType.MEGA_REDWOOD;
            object = new WorldGenMegaTree(false, random.nextBoolean());
            flag = true;
            
            break label252;
          }
        }
      }
      if (!flag) {
        j1 = 0;
        i1 = 0;
        treeType = TreeType.REDWOOD;
        object = new WorldGenTaiga2(true);
      }
      
      break;
    case 2: 
      treeType = TreeType.BIRCH;
      object = new WorldGenForest(true, false);
      break;
    

    case 3: 
      for (i1 = 0; i1 >= -1; i1--) {
        for (j1 = 0; j1 >= -1; j1--) {
          if ((a(world, i + i1, j, k + j1, 3)) && (a(world, i + i1 + 1, j, k + j1, 3)) && (a(world, i + i1, j, k + j1 + 1, 3)) && (a(world, i + i1 + 1, j, k + j1 + 1, 3))) {
            treeType = TreeType.JUNGLE;
            object = new WorldGenJungleTree(true, 10, 20, 3, 3);
            flag = true;
            
            break label444;
          }
        }
      }
      if (!flag) {
        j1 = 0;
        i1 = 0;
        treeType = TreeType.SMALL_JUNGLE;
        object = new WorldGenTrees(true, 4 + random.nextInt(7), 3, 3, false);
      }
      
      break;
    case 4: 
      treeType = TreeType.ACACIA;
      object = new WorldGenAcaciaTree(true);
      break;
    case 5: 
      label252:
      label444:
      for (i1 = 0; i1 >= -1; i1--) {
        for (j1 = 0; j1 >= -1; j1--) {
          if ((a(world, i + i1, j, k + j1, 5)) && (a(world, i + i1 + 1, j, k + j1, 5)) && (a(world, i + i1, j, k + j1 + 1, 5)) && (a(world, i + i1 + 1, j, k + j1 + 1, 5))) {
            object = new WorldGenForestTree(true);
            treeType = TreeType.DARK_OAK;
            flag = true;
            break label641;
          }
        }
      }
      label641:
      if (!flag) {
        return;
      }
      break;
    }
    Block block = Blocks.AIR;
    
    if (flag) {
      world.setTypeAndData(i + i1, j, k + j1, block, 0, 4);
      world.setTypeAndData(i + i1 + 1, j, k + j1, block, 0, 4);
      world.setTypeAndData(i + i1, j, k + j1 + 1, block, 0, 4);
      world.setTypeAndData(i + i1 + 1, j, k + j1 + 1, block, 0, 4);
    } else {
      world.setTypeAndData(i, j, k, block, 0, 4);
    }
    
    if (!((WorldGenerator)object).generate(world, random, i + i1, j, k + j1)) {
      if (flag) {
        world.setTypeAndData(i + i1, j, k + j1, this, l, 4);
        world.setTypeAndData(i + i1 + 1, j, k + j1, this, l, 4);
        world.setTypeAndData(i + i1, j, k + j1 + 1, this, l, 4);
        world.setTypeAndData(i + i1 + 1, j, k + j1 + 1, this, l, 4);
      } else {
        world.setTypeAndData(i, j, k, this, l, 4);
      }
    }
  }
  
  public boolean a(World world, int i, int j, int k, int l) {
    return (world.getType(i, j, k) == this) && ((world.getData(i, j, k) & 0x7) == l);
  }
  
  public int getDropData(int i) {
    return MathHelper.a(i & 0x7, 0, 5);
  }
  
  public boolean a(World world, int i, int j, int k, boolean flag) {
    return true;
  }
  
  public boolean a(World world, Random random, int i, int j, int k) {
    return world.random.nextFloat() < 0.45D;
  }
  
  public void b(World world, Random random, int i, int j, int k) {
    grow(world, i, j, k, random);
  }
}
