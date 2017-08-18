package net.minecraft.server;

import java.util.Random;
import org.spigotmc.SpigotWorldConfig;

public class BlockHopper extends BlockContainer
{
  private final Random a = new Random();
  
  public BlockHopper() {
    super(Material.ORE);
    a(CreativeModeTab.d);
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, java.util.List list, Entity entity) {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
    super.a(world, i, j, k, axisalignedbb, list, entity);
    float f = 0.125F;
    
    a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
    super.a(world, i, j, k, axisalignedbb, list, entity);
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
    super.a(world, i, j, k, axisalignedbb, list, entity);
    a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    super.a(world, i, j, k, axisalignedbb, list, entity);
    a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
    super.a(world, i, j, k, axisalignedbb, list, entity);
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
    int j1 = Facing.OPPOSITE_FACING[l];
    
    if (j1 == 1) {
      j1 = 0;
    }
    
    return j1;
  }
  
  public TileEntity a(World world, int i) {
    return new TileEntityHopper();
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    super.postPlace(world, i, j, k, entityliving, itemstack);
    if (itemstack.hasName()) {
      TileEntityHopper tileentityhopper = e(world, i, j, k);
      
      tileentityhopper.a(itemstack.getName());
    }
  }
  
  public void onPlace(World world, int i, int j, int k) {
    super.onPlace(world, i, j, k);
    e(world, i, j, k);
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    if (world.isStatic) {
      return true;
    }
    TileEntityHopper tileentityhopper = e(world, i, j, k);
    
    if (tileentityhopper != null) {
      entityhuman.openHopper(tileentityhopper);
    }
    
    return true;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block)
  {
    e(world, i, j, k);
  }
  
  private void e(World world, int i, int j, int k) {
    int l = world.getData(i, j, k);
    int i1 = b(l);
    boolean flag = !world.isBlockIndirectlyPowered(i, j, k);
    boolean flag1 = c(l);
    
    if (flag != flag1) {
      world.setData(i, j, k, i1 | (flag ? 0 : 8), 4);
      


      if (world.spigotConfig.altHopperTicking)
      {
        TileEntityHopper hopper = e(world, i, j, k);
        if ((flag) && (hopper != null)) {
          hopper.makeTick();
        }
      }
    }
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l)
  {
    TileEntityHopper tileentityhopper = (TileEntityHopper)world.getTileEntity(i, j, k);
    
    if (tileentityhopper != null) {
      for (int i1 = 0; i1 < tileentityhopper.getSize(); i1++) {
        ItemStack itemstack = tileentityhopper.getItem(i1);
        
        if (itemstack != null) {
          float f = this.a.nextFloat() * 0.8F + 0.1F;
          float f1 = this.a.nextFloat() * 0.8F + 0.1F;
          float f2 = this.a.nextFloat() * 0.8F + 0.1F;
          
          while (itemstack.count > 0) {
            int j1 = this.a.nextInt(21) + 10;
            
            if (j1 > itemstack.count) {
              j1 = itemstack.count;
            }
            
            itemstack.count -= j1;
            EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getData()));
            
            if (itemstack.hasTag()) {
              entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
            }
            
            float f3 = 0.05F;
            
            entityitem.motX = ((float)this.a.nextGaussian() * f3);
            entityitem.motY = ((float)this.a.nextGaussian() * f3 + 0.2F);
            entityitem.motZ = ((float)this.a.nextGaussian() * f3);
            world.addEntity(entityitem);
          }
        }
      }
      
      world.updateAdjacentComparators(i, j, k, block);
    }
    
    super.remove(world, i, j, k, block, l);
  }
  
  public int b() {
    return 38;
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean c() {
    return false;
  }
  
  public static int b(int i) {
    return Math.min(i & 0x7, 5);
  }
  
  public static boolean c(int i) {
    return (i & 0x8) != 8;
  }
  
  public boolean isComplexRedstone() {
    return true;
  }
  
  public int g(World world, int i, int j, int k, int l) {
    return Container.b(e(world, i, j, k));
  }
  
  public static TileEntityHopper e(IBlockAccess iblockaccess, int i, int j, int k) {
    return (TileEntityHopper)iblockaccess.getTileEntity(i, j, k);
  }
  

  public void a(World world, int i, int j, int k, Random random)
  {
    if (world.spigotConfig.altHopperTicking)
    {
      TileEntityHopper hopper = e(world, i, j, k);
      if (hopper != null) {
        hopper.makeTick();
      }
    }
  }
}
