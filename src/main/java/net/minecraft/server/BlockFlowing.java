package net.minecraft.server;

import java.util.Random;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.PluginManager;
import org.github.paperspigot.PaperSpigotWorldConfig;

public class BlockFlowing extends BlockFluids
{
  int a;
  boolean[] b = new boolean[4];
  int[] M = new int[4];
  
  protected BlockFlowing(Material material) {
    super(material);
  }
  
  private void n(World world, int i, int j, int k) {
    int l = world.getData(i, j, k);
    
    world.setTypeAndData(i, j, k, Block.getById(Block.getId(this) + 1), l, 2);
  }
  
  public void a(World world, int i, int j, int k, Random random)
  {
    org.bukkit.World bworld = world.getWorld();
    Server server = world.getServer();
    org.bukkit.block.Block source = bworld == null ? null : bworld.getBlockAt(i, j, k);
    

    int l = e(world, i, j, k);
    byte b0 = 1;
    
    if ((this.material == Material.LAVA) && (!world.worldProvider.f)) {
      b0 = 2;
    }
    
    boolean flag = true;
    int i1 = getFlowSpeed(world, i, j, k);
    

    if (l > 0) {
      byte b1 = -100;
      
      this.a = 0;
      int k1 = a(world, i - 1, j, k, b1);
      
      k1 = a(world, i + 1, j, k, k1);
      k1 = a(world, i, j, k - 1, k1);
      k1 = a(world, i, j, k + 1, k1);
      int j1 = k1 + b0;
      if ((j1 >= 8) || (k1 < 0)) {
        j1 = -1;
      }
      
      if (e(world, i, j + 1, k) >= 0) {
        int l1 = e(world, i, j + 1, k);
        
        if (l1 >= 8) {
          j1 = l1;
        } else {
          j1 = l1 + 8;
        }
      }
      
      if ((this.a >= 2) && (this.material == Material.WATER)) {
        if (world.getType(i, j - 1, k).getMaterial().isBuildable()) {
          j1 = 0;
        } else if ((world.getType(i, j - 1, k).getMaterial() == this.material) && (world.getData(i, j - 1, k) == 0)) {
          j1 = 0;
        }
      }
      
      if ((!world.paperSpigotConfig.fastDrainLava) && (this.material == Material.LAVA) && (l < 8) && (j1 < 8) && (j1 > l) && (random.nextInt(4) != 0)) {
        i1 *= 4;
      }
      
      if (j1 == l) {
        if (flag) {
          n(world, i, j, k);
        }
      } else {
        l = j1;
        if ((j1 < 0) || (canFastDrain(world, i, j, k))) {
          world.setAir(i, j, k);
        } else {
          world.setData(i, j, k, j1, 2);
          world.a(i, j, k, this, i1);
          
          world.e(i - 1, j, k, this);
          world.e(i + 1, j, k, this);
          world.e(i, j + 1, k, this);
          world.e(i, j, k - 1, this);
          world.e(i, j, k + 1, this);
          world.spigotConfig.antiXrayInstance.updateNearbyBlocks(world, i, j, k);
        }
      }
    }
    else {
      n(world, i, j, k);
    }
    
    if (world.getType(i, j, k).getMaterial() != this.material) { return;
    }
    if (q(world, i, j - 1, k))
    {
      BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
      if (server != null) {
        server.getPluginManager().callEvent(event);
      }
      
      if (!event.isCancelled()) {
        if ((this.material == Material.LAVA) && (world.getType(i, j - 1, k).getMaterial() == Material.WATER)) {
          world.setTypeUpdate(i, j - 1, k, Blocks.STONE);
          fizz(world, i, j - 1, k);
          return;
        }
        
        if (l >= 8) {
          flow(world, i, j - 1, k, l);
        } else {
          flow(world, i, j - 1, k, l + 8);
        }
      }
    }
    else if ((l >= 0) && ((l == 0) || (p(world, i, j - 1, k)))) {
      boolean[] aboolean = o(world, i, j, k);
      
      int j1 = l + b0;
      if (l >= 8) {
        j1 = 1;
      }
      
      if (j1 >= 8) {
        return;
      }
      

      BlockFace[] faces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
      int index = 0;
      
      for (BlockFace currentFace : faces) {
        if (aboolean[index] != 0) {
          BlockFromToEvent event = new BlockFromToEvent(source, currentFace);
          
          if (server != null) {
            server.getPluginManager().callEvent(event);
          }
          
          if (!event.isCancelled()) {
            flow(world, i + currentFace.getModX(), j, k + currentFace.getModZ(), j1);
          }
        }
        index++;
      }
    }
  }
  
  private void flow(World world, int i, int j, int k, int l)
  {
    if (q(world, i, j, k)) {
      Block block = world.getType(i, j, k);
      
      if (this.material == Material.LAVA) {
        fizz(world, i, j, k);
      } else {
        block.b(world, i, j, k, world.getData(i, j, k), 0);
      }
      
      world.setTypeAndData(i, j, k, this, l, 3);
    }
  }
  
  private int c(World world, int i, int j, int k, int l, int i1) {
    int j1 = 1000;
    
    for (int k1 = 0; k1 < 4; k1++) {
      if (((k1 != 0) || (i1 != 1)) && ((k1 != 1) || (i1 != 0)) && ((k1 != 2) || (i1 != 3)) && ((k1 != 3) || (i1 != 2))) {
        int l1 = i;
        int i2 = k;
        
        if (k1 == 0) {
          l1 = i - 1;
        }
        
        if (k1 == 1) {
          l1++;
        }
        
        if (k1 == 2) {
          i2 = k - 1;
        }
        
        if (k1 == 3) {
          i2++;
        }
        
        if ((!p(world, l1, j, i2)) && ((world.getType(l1, j, i2).getMaterial() != this.material) || (world.getData(l1, j, i2) != 0))) {
          if (!p(world, l1, j - 1, i2)) {
            return l;
          }
          
          if (l < 4) {
            int j2 = c(world, l1, j, i2, l + 1, k1);
            
            if (j2 < j1) {
              j1 = j2;
            }
          }
        }
      }
    }
    
    return j1;
  }
  


  private boolean[] o(World world, int i, int j, int k)
  {
    for (int l = 0; l < 4; l++) {
      this.M[l] = 1000;
      int i1 = i;
      int j1 = k;
      
      if (l == 0) {
        i1 = i - 1;
      }
      
      if (l == 1) {
        i1++;
      }
      
      if (l == 2) {
        j1 = k - 1;
      }
      
      if (l == 3) {
        j1++;
      }
      
      if ((!p(world, i1, j, j1)) && ((world.getType(i1, j, j1).getMaterial() != this.material) || (world.getData(i1, j, j1) != 0))) {
        if (p(world, i1, j - 1, j1)) {
          this.M[l] = c(world, i1, j, j1, 1, l);
        } else {
          this.M[l] = 0;
        }
      }
    }
    
    l = this.M[0];
    
    for (int i1 = 1; i1 < 4; i1++) {
      if (this.M[i1] < l) {
        l = this.M[i1];
      }
    }
    
    for (i1 = 0; i1 < 4; i1++) {
      this.b[i1] = (this.M[i1] == l ? 1 : false);
    }
    
    return this.b;
  }
  
  private boolean p(World world, int i, int j, int k) {
    Block block = world.getType(i, j, k);
    
    return block.material == Material.PORTAL;
  }
  
  protected int a(World world, int i, int j, int k, int l) {
    int i1 = e(world, i, j, k);
    
    if (i1 < 0) {
      return l;
    }
    if (i1 == 0) {
      this.a += 1;
    }
    
    if (i1 >= 8) {
      i1 = 0;
    }
    
    return (l >= 0) && (i1 >= l) ? l : i1;
  }
  
  private boolean q(World world, int i, int j, int k)
  {
    Material material = world.getType(i, j, k).getMaterial();
    
    return material != this.material;
  }
  
  public void onPlace(World world, int i, int j, int k) {
    super.onPlace(world, i, j, k);
    if (world.getType(i, j, k) == this) {
      world.a(i, j, k, this, getFlowSpeed(world, i, j, k));
    }
  }
  
  public boolean L() {
    return true;
  }
  


  public int getFlowSpeed(World world, int x, int y, int z)
  {
    if (getMaterial() == Material.LAVA) {
      return world.worldProvider.g ? world.paperSpigotConfig.lavaFlowSpeedNether : world.paperSpigotConfig.lavaFlowSpeedNormal;
    }
    if ((getMaterial() == Material.WATER) && (
      (world.getType(x, y, z - 1).getMaterial() == Material.LAVA) || 
      (world.getType(x, y, z + 1).getMaterial() == Material.LAVA) || 
      (world.getType(x - 1, y, z).getMaterial() == Material.LAVA) || 
      (world.getType(x + 1, y, z).getMaterial() == Material.LAVA))) {
      return world.paperSpigotConfig.waterOverLavaFlowSpeed;
    }
    return super.a(world);
  }
  


  public int getData(World world, int x, int y, int z)
  {
    int data = e(world, x, y, z);
    return data < 8 ? data : 0;
  }
  


  public boolean canFastDrain(World world, int x, int y, int z)
  {
    boolean result = false;
    int data = getData(world, x, y, z);
    if (this.material == Material.WATER) {
      if (world.paperSpigotConfig.fastDrainWater) {
        result = true;
        if (getData(world, x, y - 1, z) < 0) {
          result = false;
        } else if ((world.getType(x, y, z - 1).getMaterial() == Material.WATER) && (getData(world, x, y, z - 1) < data)) {
          result = false;
        } else if ((world.getType(x, y, z + 1).getMaterial() == Material.WATER) && (getData(world, x, y, z + 1) < data)) {
          result = false;
        } else if ((world.getType(x - 1, y, z).getMaterial() == Material.WATER) && (getData(world, x - 1, y, z) < data)) {
          result = false;
        } else if ((world.getType(x + 1, y, z).getMaterial() == Material.WATER) && (getData(world, x + 1, y, z) < data)) {
          result = false;
        }
      }
    } else if ((this.material == Material.LAVA) && 
      (world.paperSpigotConfig.fastDrainLava)) {
      result = true;
      if ((getData(world, x, y - 1, z) < 0) || (world.getType(x, y + 1, z).getMaterial() != Material.AIR)) {
        result = false;
      } else if ((world.getType(x, y, z - 1).getMaterial() == Material.LAVA) && (getData(world, x, y, z - 1) < data)) {
        result = false;
      } else if ((world.getType(x, y, z + 1).getMaterial() == Material.LAVA) && (getData(world, x, y, z + 1) < data)) {
        result = false;
      } else if ((world.getType(x - 1, y, z).getMaterial() == Material.LAVA) && (getData(world, x - 1, y, z) < data)) {
        result = false;
      } else if ((world.getType(x + 1, y, z).getMaterial() == Material.LAVA) && (getData(world, x + 1, y, z) < data)) {
        result = false;
      }
    }
    
    return result;
  }
}
