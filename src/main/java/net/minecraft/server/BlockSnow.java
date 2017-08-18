package net.minecraft.server;

import java.util.Random;

public class BlockSnow extends Block
{
  protected BlockSnow() {
    super(Material.PACKED_ICE);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    a(true);
    a(CreativeModeTab.c);
    b(0);
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    int l = world.getData(i, j, k) & 0x7;
    float f = 0.125F;
    
    return AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + l * f, k + this.maxZ);
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public void g() {
    b(0);
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    b(iblockaccess.getData(i, j, k));
  }
  
  protected void b(int i) {
    int j = i & 0x7;
    float f = 2 * (1 + j) / 16.0F;
    
    a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    Block block = world.getType(i, j - 1, k);
    
    return block.getMaterial() == Material.LEAVES;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    m(world, i, j, k);
  }
  
  private boolean m(World world, int i, int j, int k) {
    if (!canPlace(world, i, j, k)) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setAir(i, j, k);
      return false;
    }
    return true;
  }
  
  public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l)
  {
    int i1 = l & 0x7;
    
    a(world, i, j, k, new ItemStack(Items.SNOW_BALL, i1 + 1, 0));
    world.setAir(i, j, k);
    entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.SNOW_BALL;
  }
  
  public int a(Random random) {
    return 0;
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11)
    {
      if (org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Blocks.AIR).isCancelled()) {
        return;
      }
      

      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setAir(i, j, k);
    }
  }
}
