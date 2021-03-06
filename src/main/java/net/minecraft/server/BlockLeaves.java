package net.minecraft.server;

import java.util.Random;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.plugin.PluginManager;

public abstract class BlockLeaves extends BlockTransparent
{
  int[] a;
  protected IIcon[][] M = new IIcon[2][];
  
  public BlockLeaves() {
    super(Material.LEAVES, false);
    a(true);
    a(CreativeModeTab.c);
    c(0.2F);
    g(1);
    a(h);
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l) {
    byte b0 = 1;
    int i1 = b0 + 1;
    
    if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
      for (int j1 = -b0; j1 <= b0; j1++) {
        for (int k1 = -b0; k1 <= b0; k1++) {
          for (int l1 = -b0; l1 <= b0; l1++) {
            if (world.getType(i + j1, j + k1, k + l1).getMaterial() == Material.LEAVES) {
              int i2 = world.getData(i + j1, j + k1, k + l1);
              
              world.setData(i + j1, j + k1, k + l1, i2 | 0x8, 4);
            }
          }
        }
      }
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if (((l & 0x8) != 0) && ((l & 0x4) == 0)) {
        byte b0 = 4;
        int i1 = b0 + 1;
        byte b1 = 32;
        int j1 = b1 * b1;
        int k1 = b1 / 2;
        
        if (this.a == null) {
          this.a = new int[b1 * b1 * b1];
        }
        


        if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1))
        {


          for (int l1 = -b0; l1 <= b0; l1++) {
            for (int i2 = -b0; i2 <= b0; i2++) {
              for (int j2 = -b0; j2 <= b0; j2++) {
                Block block = world.getType(i + l1, j + i2, k + j2);
                
                if ((block != Blocks.LOG) && (block != Blocks.LOG2)) {
                  if (block.getMaterial() == Material.LEAVES) {
                    this.a[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = -2;
                  } else {
                    this.a[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = -1;
                  }
                } else {
                  this.a[((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1)] = 0;
                }
              }
            }
          }
          
          for (l1 = 1; l1 <= 4; l1++) {
            for (int i2 = -b0; i2 <= b0; i2++) {
              for (int j2 = -b0; j2 <= b0; j2++) {
                for (int k2 = -b0; k2 <= b0; k2++) {
                  if (this.a[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1)] == l1 - 1) {
                    if (this.a[((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1)] == -2) {
                      this.a[((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1)] = l1;
                    }
                    
                    if (this.a[((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1)] == -2) {
                      this.a[((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1)] = l1;
                    }
                    
                    if (this.a[((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1)] == -2) {
                      this.a[((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1)] = l1;
                    }
                    
                    if (this.a[((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1)] == -2) {
                      this.a[((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1)] = l1;
                    }
                    
                    if (this.a[((i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1))] == -2) {
                      this.a[((i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1))] = l1;
                    }
                    
                    if (this.a[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1)] == -2) {
                      this.a[((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1)] = l1;
                    }
                  }
                }
              }
            }
          }
        }
        
        int l1 = this.a[(k1 * j1 + k1 * b1 + k1)];
        if (l1 >= 0) {
          world.setData(i, j, k, l & 0xFFFFFFF7, 4);
        } else {
          e(world, i, j, k);
        }
      }
    }
  }
  
  private void e(World world, int i, int j, int k)
  {
    LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
    world.getServer().getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      return;
    }
    

    b(world, i, j, k, world.getData(i, j, k), 0);
    world.setAir(i, j, k);
  }
  
  public int a(Random random) {
    return random.nextInt(20) == 0 ? 1 : 0;
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Item.getItemOf(Blocks.SAPLING);
  }
  
  public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
    if (!world.isStatic) {
      int j1 = b(l);
      
      if (i1 > 0) {
        j1 -= (2 << i1);
        if (j1 < 10) {
          j1 = 10;
        }
      }
      
      if (world.random.nextInt(j1) == 0) {
        Item item = getDropType(l, world.random, i1);
        
        a(world, i, j, k, new ItemStack(item, 1, getDropData(l)));
      }
      
      j1 = 200;
      if (i1 > 0) {
        j1 -= (10 << i1);
        if (j1 < 40) {
          j1 = 40;
        }
      }
      
      c(world, i, j, k, l, j1);
    }
  }
  
  protected void c(World world, int i, int j, int k, int l, int i1) {}
  
  protected int b(int i) {
    return 20;
  }
  
  public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
    if ((!world.isStatic) && (entityhuman.bF() != null) && (entityhuman.bF().getItem() == Items.SHEARS)) {
      entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
      a(world, i, j, k, new ItemStack(Item.getItemOf(this), 1, l & 0x3));
    } else {
      super.a(world, entityhuman, i, j, k, l);
    }
  }
  
  public int getDropData(int i) {
    return i & 0x3;
  }
  
  public boolean c() {
    return !this.P;
  }
  
  protected ItemStack j(int i) {
    return new ItemStack(Item.getItemOf(this), 1, i & 0x3);
  }
  
  public abstract String[] e();
}
