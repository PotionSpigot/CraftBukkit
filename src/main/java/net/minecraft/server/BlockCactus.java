package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

public class BlockCactus extends Block
{
  protected BlockCactus()
  {
    super(Material.CACTUS);
    a(true);
    a(CreativeModeTab.c);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (world.isEmpty(i, j + 1, k))
    {

      for (int l = 1; world.getType(i, j - l, k) == this; l++) {}
      


      if (l < world.paperSpigotConfig.cactusMaxHeight) {
        int i1 = world.getData(i, j, k);
        
        if (i1 >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.cactusModifier * 15.0F + 0.5F, 15.0F)) {
          CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 0);
          world.setData(i, j, k, 0, 4);
          doPhysics(world, i, j + 1, k, this);
        } else {
          world.setData(i, j, k, i1 + 1, 4);
        }
      }
    }
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    float f = 0.0625F;
    
    return AxisAlignedBB.a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean c() {
    return false;
  }
  
  public int b() {
    return 13;
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return !super.canPlace(world, i, j, k) ? false : j(world, i, j, k);
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    if (!j(world, i, j, k)) {
      world.setAir(i, j, k, true);
    }
  }
  
  public boolean j(World world, int i, int j, int k) {
    if (world.getType(i - 1, j, k).getMaterial().isBuildable())
      return false;
    if (world.getType(i + 1, j, k).getMaterial().isBuildable())
      return false;
    if (world.getType(i, j, k - 1).getMaterial().isBuildable())
      return false;
    if (world.getType(i, j, k + 1).getMaterial().isBuildable()) {
      return false;
    }
    Block block = world.getType(i, j - 1, k);
    
    return (block == Blocks.CACTUS) || (block == Blocks.SAND);
  }
  
  public void a(World world, int i, int j, int k, Entity entity)
  {
    CraftEventFactory.blockDamage = world.getWorld().getBlockAt(i, j, k);
    entity.damageEntity(DamageSource.CACTUS, 1.0F);
    CraftEventFactory.blockDamage = null;
  }
}
