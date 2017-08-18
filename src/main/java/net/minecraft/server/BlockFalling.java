package net.minecraft.server;

import java.util.Random;

public class BlockFalling extends Block
{
  public static boolean instaFall;
  
  public BlockFalling() {
    super(Material.SAND);
    a(CreativeModeTab.b);
  }
  
  public BlockFalling(Material material) {
    super(material);
  }
  
  public void onPlace(World world, int i, int j, int k) {
    world.a(i, j, k, this, a(world));
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    world.a(i, j, k, this, a(world));
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      m(world, i, j, k);
    }
  }
  
  private void m(World world, int i, int j, int k) {
    if ((canFall(world, i, j - 1, k)) && (j >= 0)) {
      byte b0 = 32;
      
      if ((!instaFall) && (world.b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0))) {
        if (!world.isStatic)
        {
          org.bukkit.Location loc = new org.bukkit.Location(world.getWorld(), i + 0.5F, j + 0.5F, k + 0.5F);
          EntityFallingBlock entityfallingblock = new EntityFallingBlock(loc, world, i + 0.5F, j + 0.5F, k + 0.5F, this, world.getData(i, j, k));
          

          a(entityfallingblock);
          world.addEntity(entityfallingblock);
        }
      } else {
        world.setAir(i, j, k);
        
        while ((canFall(world, i, j - 1, k)) && (j > 0)) {
          j--;
        }
        
        if (j > 0) {
          world.setTypeUpdate(i, j, k, this);
        }
      }
    }
  }
  
  protected void a(EntityFallingBlock entityfallingblock) {}
  
  public int a(World world) {
    return 2;
  }
  
  public static boolean canFall(World world, int i, int j, int k) {
    Block block = world.getType(i, j, k);
    
    if (block.material == Material.AIR)
      return true;
    if (block == Blocks.FIRE) {
      return true;
    }
    Material material = block.material;
    
    return material == Material.WATER;
  }
  
  public void a(World world, int i, int j, int k, int l) {}
}
