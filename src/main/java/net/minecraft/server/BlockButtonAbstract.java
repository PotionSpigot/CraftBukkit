package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public abstract class BlockButtonAbstract extends Block
{
  private final boolean a;
  
  protected BlockButtonAbstract(boolean flag)
  {
    super(Material.ORIENTABLE);
    a(true);
    a(CreativeModeTab.d);
    this.a = flag;
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    return null;
  }
  
  public int a(World world) {
    return this.a ? 30 : 20;
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean canPlace(World world, int i, int j, int k, int l) {
    return (l == 2) && (world.getType(i, j, k + 1).r());
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return world.getType(i, j, k - 1).r() ? true : world.getType(i + 1, j, k).r() ? true : world.getType(i - 1, j, k).r() ? true : world.getType(i, j, k + 1).r();
  }
  
  public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
    int j1 = world.getData(i, j, k);
    int k1 = j1 & 0x8;
    
    j1 &= 0x7;
    if ((l == 2) && (world.getType(i, j, k + 1).r())) {
      j1 = 4;
    } else if ((l == 3) && (world.getType(i, j, k - 1).r())) {
      j1 = 3;
    } else if ((l == 4) && (world.getType(i + 1, j, k).r())) {
      j1 = 2;
    } else if ((l == 5) && (world.getType(i - 1, j, k).r())) {
      j1 = 1;
    } else {
      j1 = e(world, i, j, k);
    }
    
    return j1 + k1;
  }
  
  private int e(World world, int i, int j, int k) {
    return world.getType(i, j, k + 1).r() ? 4 : world.getType(i, j, k - 1).r() ? 3 : world.getType(i + 1, j, k).r() ? 2 : world.getType(i - 1, j, k).r() ? 1 : 1;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    if (m(world, i, j, k)) {
      int l = world.getData(i, j, k) & 0x7;
      boolean flag = false;
      
      if ((!world.getType(i - 1, j, k).r()) && (l == 1)) {
        flag = true;
      }
      
      if ((!world.getType(i + 1, j, k).r()) && (l == 2)) {
        flag = true;
      }
      
      if ((!world.getType(i, j, k - 1).r()) && (l == 3)) {
        flag = true;
      }
      
      if ((!world.getType(i, j, k + 1).r()) && (l == 4)) {
        flag = true;
      }
      
      if (flag) {
        b(world, i, j, k, world.getData(i, j, k), 0);
        world.setAir(i, j, k);
      }
    }
  }
  
  private boolean m(World world, int i, int j, int k) {
    if (!canPlace(world, i, j, k)) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setAir(i, j, k);
      return false;
    }
    return true;
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k)
  {
    int l = iblockaccess.getData(i, j, k);
    
    b(l);
  }
  
  private void b(int i) {
    int j = i & 0x7;
    boolean flag = (i & 0x8) > 0;
    float f = 0.375F;
    float f1 = 0.625F;
    float f2 = 0.1875F;
    float f3 = 0.125F;
    
    if (flag) {
      f3 = 0.0625F;
    }
    
    if (j == 1) {
      a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
    } else if (j == 2) {
      a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
    } else if (j == 3) {
      a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
    } else if (j == 4) {
      a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
    }
  }
  
  public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    int i1 = world.getData(i, j, k);
    int j1 = i1 & 0x7;
    int k1 = 8 - (i1 & 0x8);
    
    if (k1 == 0) {
      return true;
    }
    
    org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
    int old = k1 != 8 ? 15 : 0;
    int current = k1 == 8 ? 15 : 0;
    
    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
    world.getServer().getPluginManager().callEvent(eventRedstone);
    
    if ((eventRedstone.getNewCurrent() > 0 ? 1 : 0) != (k1 == 8 ? 1 : 0)) {
      return true;
    }
    

    world.setData(i, j, k, j1 + k1, 3);
    world.c(i, j, k, i, j, k);
    world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
    a(world, i, j, k, j1);
    world.a(i, j, k, this, a(world));
    return true;
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l)
  {
    if ((l & 0x8) > 0) {
      int i1 = l & 0x7;
      
      a(world, i, j, k, i1);
    }
    
    super.remove(world, i, j, k, block, l);
  }
  
  public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return (iblockaccess.getData(i, j, k) & 0x8) > 0 ? 15 : 0;
  }
  
  public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    int i1 = iblockaccess.getData(i, j, k);
    
    if ((i1 & 0x8) == 0) {
      return 0;
    }
    int j1 = i1 & 0x7;
    
    return (j1 == 1) && (l == 5) ? 15 : (j1 == 2) && (l == 4) ? 15 : (j1 == 3) && (l == 3) ? 15 : (j1 == 4) && (l == 2) ? 15 : (j1 == 5) && (l == 1) ? 15 : 0;
  }
  
  public boolean isPowerSource()
  {
    return true;
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if ((l & 0x8) != 0) {
        if (this.a) {
          n(world, i, j, k);
        }
        else {
          org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
          
          BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
          world.getServer().getPluginManager().callEvent(eventRedstone);
          
          if (eventRedstone.getNewCurrent() > 0) {
            return;
          }
          
          world.setData(i, j, k, l & 0x7, 3);
          int i1 = l & 0x7;
          
          a(world, i, j, k, i1);
          world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.5F);
          world.c(i, j, k, i, j, k);
        }
      }
    }
  }
  
  public void g() {
    float f = 0.1875F;
    float f1 = 0.125F;
    float f2 = 0.125F;
    
    a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
  }
  
  public void a(World world, int i, int j, int k, Entity entity) {
    if ((!world.isStatic) && 
      (this.a) && 
      ((world.getData(i, j, k) & 0x8) == 0)) {
      n(world, i, j, k);
    }
  }
  

  private void n(World world, int i, int j, int k)
  {
    int l = world.getData(i, j, k);
    int i1 = l & 0x7;
    boolean flag = (l & 0x8) != 0;
    
    b(l);
    java.util.List list = world.a(EntityArrow.class, AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
    boolean flag1 = !list.isEmpty();
    

    if ((flag != flag1) && (flag1)) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      boolean allowed = false;
      

      for (Object object : list) {
        if (object != null) {
          EntityInteractEvent event = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
          world.getServer().getPluginManager().callEvent(event);
          
          if (!event.isCancelled()) {
            allowed = true;
            break;
          }
        }
      }
      
      if (!allowed) {
        return;
      }
    }
    

    if ((flag1) && (!flag))
    {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 0, 15);
      world.getServer().getPluginManager().callEvent(eventRedstone);
      
      if (eventRedstone.getNewCurrent() <= 0) {
        return;
      }
      
      world.setData(i, j, k, i1 | 0x8, 3);
      a(world, i, j, k, i1);
      world.c(i, j, k, i, j, k);
      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
    }
    
    if ((!flag1) && (flag))
    {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
      world.getServer().getPluginManager().callEvent(eventRedstone);
      
      if (eventRedstone.getNewCurrent() > 0) {
        return;
      }
      
      world.setData(i, j, k, i1, 3);
      a(world, i, j, k, i1);
      world.c(i, j, k, i, j, k);
      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.5F);
    }
    
    if (flag1) {
      world.a(i, j, k, this, a(world));
    }
  }
  
  private void a(World world, int i, int j, int k, int l) {
    world.applyPhysics(i, j, k, this);
    if (l == 1) {
      world.applyPhysics(i - 1, j, k, this);
    } else if (l == 2) {
      world.applyPhysics(i + 1, j, k, this);
    } else if (l == 3) {
      world.applyPhysics(i, j, k - 1, this);
    } else if (l == 4) {
      world.applyPhysics(i, j, k + 1, this);
    } else {
      world.applyPhysics(i, j - 1, k, this);
    }
  }
}
