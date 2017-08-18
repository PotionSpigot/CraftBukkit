package net.minecraft.server;

import java.util.Random;

public class BlockTallPlant extends BlockPlant implements IBlockFragilePlantElement
{
  public static final String[] a = { "sunflower", "syringa", "grass", "fern", "rose", "paeonia" };
  
  public BlockTallPlant() {
    super(Material.PLANT);
    c(0.0F);
    a(h);
    c("doublePlant");
  }
  
  public int b() {
    return 40;
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public int e(IBlockAccess iblockaccess, int i, int j, int k) {
    int l = iblockaccess.getData(i, j, k);
    
    return !c(l) ? l & 0x7 : iblockaccess.getData(i, j - 1, k) & 0x7;
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return (super.canPlace(world, i, j, k)) && (world.isEmpty(i, j + 1, k));
  }
  
  protected void e(World world, int i, int j, int k) {
    if (!j(world, i, j, k)) {
      int l = world.getData(i, j, k);
      
      if (!c(l)) {
        b(world, i, j, k, l, 0);
        if (world.getType(i, j + 1, k) == this) {
          world.setTypeAndData(i, j + 1, k, Blocks.AIR, 0, 2);
        }
      }
      
      world.setTypeAndData(i, j, k, Blocks.AIR, 0, 2);
    }
  }
  
  public boolean j(World world, int i, int j, int k) {
    int l = world.getData(i, j, k);
    
    return world.getType(i, j - 1, k) == this;
  }
  
  public Item getDropType(int i, Random random, int j) {
    if (c(i)) {
      return null;
    }
    int k = d(i);
    
    return (k != 3) && (k != 2) ? Item.getItemOf(this) : null;
  }
  
  public int getDropData(int i)
  {
    return c(i) ? 0 : i & 0x7;
  }
  
  public static boolean c(int i) {
    return (i & 0x8) != 0;
  }
  
  public static int d(int i) {
    return i & 0x7;
  }
  
  public void c(World world, int i, int j, int k, int l, int i1) {
    world.setTypeAndData(i, j, k, this, l, i1);
    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 8);
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    int l = ((MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
    
    world.setTypeAndData(i, j + 1, k, this, 0x8 | l, 2);
  }
  
  public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
    if ((world.isStatic) || (entityhuman.bF() == null) || (entityhuman.bF().getItem() != Items.SHEARS) || (c(l)) || (!b(world, i, j, k, l, entityhuman))) {
      super.a(world, entityhuman, i, j, k, l);
    }
  }
  
  public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
    if (c(l)) {
      if (world.getType(i, j - 1, k) == this) {
        if (!entityhuman.abilities.canInstantlyBuild) {
          int i1 = world.getData(i, j - 1, k);
          int j1 = d(i1);
          
          if ((j1 != 3) && (j1 != 2)) {
            world.setAir(i, j - 1, k, true);
          } else {
            if ((!world.isStatic) && (entityhuman.bF() != null) && (entityhuman.bF().getItem() == Items.SHEARS)) {
              b(world, i, j, k, i1, entityhuman);
            }
            
            world.setAir(i, j - 1, k);
          }
        } else {
          world.setAir(i, j - 1, k);
        }
      }
    } else if ((entityhuman.abilities.canInstantlyBuild) && (world.getType(i, j + 1, k) == this)) {
      world.setTypeAndData(i, j + 1, k, Blocks.AIR, 0, 2);
    }
    
    super.a(world, i, j, k, l, entityhuman);
  }
  
  private boolean b(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
    int i1 = d(l);
    
    if ((i1 != 3) && (i1 != 2)) {
      return false;
    }
    entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
    byte b0 = 1;
    
    if (i1 == 3) {
      b0 = 2;
    }
    
    a(world, i, j, k, new ItemStack(Blocks.LONG_GRASS, 2, b0));
    return true;
  }
  
  public int getDropData(World world, int i, int j, int k)
  {
    int l = world.getData(i, j, k);
    
    return c(l) ? d(world.getData(i, j - 1, k)) : d(l);
  }
  
  public boolean a(World world, int i, int j, int k, boolean flag) {
    int l = e(world, i, j, k);
    
    return (l != 2) && (l != 3);
  }
  
  public boolean a(World world, Random random, int i, int j, int k) {
    return true;
  }
  
  public void b(World world, Random random, int i, int j, int k) {
    int l = e(world, i, j, k);
    
    a(world, i, j, k, new ItemStack(this, 1, l));
  }
}
