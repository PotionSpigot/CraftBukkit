package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.event.Cancellable;

public class BlockTripwire extends Block
{
  public BlockTripwire()
  {
    super(Material.ORIENTABLE);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
    a(true);
  }
  
  public int a(World world) {
    return 10;
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    return null;
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public int b() {
    return 30;
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.STRING;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    int l = world.getData(i, j, k);
    boolean flag = (l & 0x2) == 2;
    boolean flag1 = !World.a(world, i, j - 1, k);
    
    if (flag != flag1) {
      b(world, i, j, k, l, 0);
      world.setAir(i, j, k);
    }
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    int l = iblockaccess.getData(i, j, k);
    boolean flag = (l & 0x4) == 4;
    boolean flag1 = (l & 0x2) == 2;
    
    if (!flag1) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
    } else if (!flag) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    } else {
      a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
    }
  }
  
  public void onPlace(World world, int i, int j, int k) {
    int l = World.a(world, i, j - 1, k) ? 0 : 2;
    
    world.setData(i, j, k, l, 3);
    a(world, i, j, k, l);
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l) {
    a(world, i, j, k, l | 0x1);
  }
  
  public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
    if ((!world.isStatic) && 
      (entityhuman.bF() != null) && (entityhuman.bF().getItem() == Items.SHEARS)) {
      world.setData(i, j, k, l | 0x8, 4);
    }
  }
  
  private void a(World world, int i, int j, int k, int l)
  {
    int i1 = 0;
    
    while (i1 < 2) {
      int j1 = 1;
      

      while (j1 < 42) {
        int k1 = i + Direction.a[i1] * j1;
        int l1 = k + Direction.b[i1] * j1;
        Block block = world.getType(k1, j, l1);
        
        if (block == Blocks.TRIPWIRE_SOURCE) {
          int i2 = world.getData(k1, j, l1) & 0x3;
          
          if (i2 == Direction.f[i1]) {
            Blocks.TRIPWIRE_SOURCE.a(world, k1, j, l1, false, world.getData(k1, j, l1), true, j1, l);
          }
          break; } if (block != Blocks.TRIPWIRE) break;
        j1++;
      }
      


      i1++;
    }
  }
  

  public void a(World world, int i, int j, int k, Entity entity)
  {
    if ((!world.isStatic) && 
      ((world.getData(i, j, k) & 0x1) != 1)) {
      e(world, i, j, k);
    }
  }
  
  public void a(World world, int i, int j, int k, Random random)
  {
    if ((!world.isStatic) && 
      ((world.getData(i, j, k) & 0x1) == 1)) {
      e(world, i, j, k);
    }
  }
  
  private void e(World world, int i, int j, int k)
  {
    int l = world.getData(i, j, k);
    boolean flag = (l & 0x1) == 1;
    boolean flag1 = false;
    List list = world.getEntities((Entity)null, AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
    
    if (!list.isEmpty()) {
      Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        Entity entity = (Entity)iterator.next();
        
        if (!entity.az()) {
          flag1 = true;
          break;
        }
      }
    }
    

    if ((flag != flag1) && (flag1) && ((world.getData(i, j, k) & 0x4) == 4)) {
      org.bukkit.World bworld = world.getWorld();
      org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();
      org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
      boolean allowed = false;
      

      for (Object object : list) {
        if (object != null) {
          Cancellable cancellable;
          Cancellable cancellable;
          if ((object instanceof EntityHuman)) {
            cancellable = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
          } else { if (!(object instanceof Entity)) continue;
            cancellable = new org.bukkit.event.entity.EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
            manager.callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
          }
          


          if (!cancellable.isCancelled()) {
            allowed = true;
            break;
          }
        }
      }
      
      if (!allowed) {
        return;
      }
    }
    

    if ((flag1) && (!flag)) {
      l |= 0x1;
    }
    
    if ((!flag1) && (flag)) {
      l &= 0xFFFFFFFE;
    }
    
    if (flag1 != flag) {
      world.setData(i, j, k, l, 3);
      a(world, i, j, k, l);
    }
    
    if (flag1) {
      world.a(i, j, k, this, a(world));
    }
  }
}
