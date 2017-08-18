package net.minecraft.server;

import java.util.Random;

public class BlockReed extends Block
{
  protected BlockReed() {
    super(Material.PLANT);
    float f = 0.375F;
    
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
    a(true);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (((world.getType(i, j - 1, k) == Blocks.SUGAR_CANE_BLOCK) || (e(world, i, j, k))) && 
      (world.isEmpty(i, j + 1, k)))
    {

      for (int l = 1; world.getType(i, j - l, k) == this; l++) {}
      


      if (l < world.paperSpigotConfig.reedMaxHeight) {
        int i1 = world.getData(i, j, k);
        
        if (i1 >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.caneModifier * 15.0F + 0.5F, 15.0F)) {
          org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 0);
          world.setData(i, j, k, 0, 4);
        } else {
          world.setData(i, j, k, i1 + 1, 4);
        }
      }
    }
  }
  
  public boolean canPlace(World world, int i, int j, int k)
  {
    Block block = world.getType(i, j - 1, k);
    
    return block == this;
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    e(world, i, j, k);
  }
  
  protected final boolean e(World world, int i, int j, int k) {
    if (!j(world, i, j, k)) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setAir(i, j, k);
      return false;
    }
    return true;
  }
  
  public boolean j(World world, int i, int j, int k)
  {
    return canPlace(world, i, j, k);
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    return null;
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.SUGAR_CANE;
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public int b() {
    return 1;
  }
}
