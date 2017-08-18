package net.minecraft.server;

import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTrapdoor extends Block
{
  protected BlockTrapdoor(Material material) {
    super(material);
    float f = 0.5F;
    float f1 = 1.0F;
    
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    a(CreativeModeTab.d);
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
    return !d(iblockaccess.getData(i, j, k));
  }
  
  public int b() {
    return 0;
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    updateShape(world, i, j, k);
    return super.a(world, i, j, k);
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    b(iblockaccess.getData(i, j, k));
  }
  
  public void g() {
    float f = 0.1875F;
    
    a(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
  }
  
  public void b(int i) {
    float f = 0.1875F;
    
    if ((i & 0x8) != 0) {
      a(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    if (d(i)) {
      if ((i & 0x3) == 0) {
        a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
      }
      
      if ((i & 0x3) == 1) {
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
      }
      
      if ((i & 0x3) == 2) {
        a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
      
      if ((i & 0x3) == 3) {
        a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
      }
    }
  }
  
  public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    if (this.material == Material.ORE) {
      return true;
    }
    int i1 = world.getData(i, j, k);
    
    world.setData(i, j, k, i1 ^ 0x4, 2);
    world.a(entityhuman, 1003, i, j, k, 0);
    return true;
  }
  
  public void setOpen(World world, int i, int j, int k, boolean flag)
  {
    int l = world.getData(i, j, k);
    boolean flag1 = (l & 0x4) > 0;
    
    if (flag1 != flag) {
      world.setData(i, j, k, l ^ 0x4, 2);
      world.a((EntityHuman)null, 1003, i, j, k, 0);
    }
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      int i1 = i;
      int j1 = k;
      
      if ((l & 0x3) == 0) {
        j1 = k + 1;
      }
      
      if ((l & 0x3) == 1) {
        j1--;
      }
      
      if ((l & 0x3) == 2) {
        i1 = i + 1;
      }
      
      if ((l & 0x3) == 3) {
        i1--;
      }
      
      if (!a(world.getType(i1, j, j1))) {
        world.setAir(i, j, k);
        b(world, i, j, k, l, 0);
      }
      
      boolean flag = world.isBlockIndirectlyPowered(i, j, k);
      
      if ((flag) || (block.isPowerSource()))
      {
        org.bukkit.World bworld = world.getWorld();
        org.bukkit.block.Block bblock = bworld.getBlockAt(i, j, k);
        
        int power = bblock.getBlockPower();
        int oldPower = (world.getData(i, j, k) & 0x4) > 0 ? 15 : 0;
        
        if ((((oldPower == 0 ? 1 : 0) ^ (power == 0 ? 1 : 0)) != 0) || (block.isPowerSource())) {
          BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bblock, oldPower, power);
          world.getServer().getPluginManager().callEvent(eventRedstone);
          flag = eventRedstone.getNewCurrent() > 0;
        }
        

        setOpen(world, i, j, k, flag);
      }
    }
  }
  
  public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
    updateShape(world, i, j, k);
    return super.a(world, i, j, k, vec3d, vec3d1);
  }
  
  public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
    int j1 = 0;
    
    if (l == 2) {
      j1 = 0;
    }
    
    if (l == 3) {
      j1 = 1;
    }
    
    if (l == 4) {
      j1 = 2;
    }
    
    if (l == 5) {
      j1 = 3;
    }
    
    if ((l != 1) && (l != 0) && (f1 > 0.5F)) {
      j1 |= 0x8;
    }
    
    return j1;
  }
  
  public boolean canPlace(World world, int i, int j, int k, int l) {
    if (l == 0)
      return false;
    if (l == 1) {
      return false;
    }
    if (l == 2) {
      k++;
    }
    
    if (l == 3) {
      k--;
    }
    
    if (l == 4) {
      i++;
    }
    
    if (l == 5) {
      i--;
    }
    
    return a(world.getType(i, j, k));
  }
  
  public static boolean d(int i)
  {
    return (i & 0x4) != 0;
  }
  
  private static boolean a(Block block) {
    return ((block.material.k()) && (block.d())) || (block == Blocks.GLOWSTONE) || ((block instanceof BlockStepAbstract)) || ((block instanceof BlockStairs));
  }
}
