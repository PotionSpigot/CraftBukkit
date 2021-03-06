package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.event.entity.EntityPortalEnterEvent;

public class BlockEnderPortal extends BlockContainer
{
  public static boolean a;
  
  protected BlockEnderPortal(Material material)
  {
    super(material);
    a(1.0F);
  }
  
  public TileEntity a(World world, int i) {
    return new TileEntityEnderPortal();
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    float f = 0.0625F;
    
    a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
  }
  
  public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {}
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public int a(Random random) {
    return 0;
  }
  
  public void a(World world, int i, int j, int k, Entity entity) {
    if ((entity.vehicle == null) && (entity.passenger == null) && (!world.isStatic))
    {
      EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new org.bukkit.Location(world.getWorld(), i, j, k));
      world.getServer().getPluginManager().callEvent(event);
      
      entity.b(1);
    }
  }
  
  public int b() {
    return -1;
  }
  
  public void onPlace(World world, int i, int j, int k) {
    if ((!a) && 
      (world.worldProvider.dimension != 0)) {
      world.setAir(i, j, k);
    }
  }
  
  public MaterialMapColor f(int i)
  {
    return MaterialMapColor.J;
  }
}
