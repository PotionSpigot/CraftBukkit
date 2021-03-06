package net.minecraft.server;

public class BlockAnvil extends BlockFalling
{
  public static final String[] a = { "intact", "slightlyDamaged", "veryDamaged" };
  private static final String[] N = { "anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2" };
  
  protected BlockAnvil() {
    super(Material.HEAVY);
    g(0);
    a(CreativeModeTab.c);
  }
  


  public AxisAlignedBB a(World world, int i, int j, int k)
  {
    updateShape(world, i, j, k);
    return super.a(world, i, j, k);
  }
  
  public boolean d()
  {
    return false;
  }
  
  public boolean c() {
    return false;
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    int l = MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    int i1 = world.getData(i, j, k) >> 2;
    
    l++;
    l %= 4;
    if (l == 0) {
      world.setData(i, j, k, 0x2 | i1 << 2, 2);
    }
    
    if (l == 1) {
      world.setData(i, j, k, 0x3 | i1 << 2, 2);
    }
    
    if (l == 2) {
      world.setData(i, j, k, 0x0 | i1 << 2, 2);
    }
    
    if (l == 3) {
      world.setData(i, j, k, 0x1 | i1 << 2, 2);
    }
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    if (world.isStatic) {
      return true;
    }
    entityhuman.openAnvil(i, j, k);
    return true;
  }
  
  public int b()
  {
    return 35;
  }
  
  public int getDropData(int i) {
    return i >> 2;
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    int l = iblockaccess.getData(i, j, k) & 0x3;
    
    if ((l != 3) && (l != 1)) {
      a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
    }
  }
  
  protected void a(EntityFallingBlock entityfallingblock) {
    entityfallingblock.a(true);
  }
  
  public void a(World world, int i, int j, int k, int l) {
    world.triggerEffect(1022, i, j, k, 0);
  }
}
