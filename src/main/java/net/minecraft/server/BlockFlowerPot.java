package net.minecraft.server;

import java.util.Random;

public class BlockFlowerPot extends BlockContainer
{
  public BlockFlowerPot() {
    super(Material.ORIENTABLE);
    g();
  }
  
  public void g() {
    float f = 0.375F;
    float f1 = f / 2.0F;
    
    a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
  }
  
  public boolean c() {
    return false;
  }
  
  public int b() {
    return 33;
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && ((itemstack.getItem() instanceof ItemBlock))) {
      TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
      
      if (tileentityflowerpot != null) {
        if (tileentityflowerpot.a() != null) {
          return false;
        }
        Block block = Block.a(itemstack.getItem());
        
        if (!a(block, itemstack.getData())) {
          return false;
        }
        tileentityflowerpot.a(itemstack.getItem(), itemstack.getData());
        tileentityflowerpot.update();
        if (!world.setData(i, j, k, itemstack.getData(), 2)) {
          world.notify(i, j, k);
        }
        
        if (!entityhuman.abilities.canInstantlyBuild) { if (--itemstack.count <= 0) {
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
          }
        }
        return true;
      }
      

      return false;
    }
    
    return false;
  }
  
  private boolean a(Block block, int i)
  {
    return (block == Blocks.LONG_GRASS) && (i == 2);
  }
  
  public int getDropData(World world, int i, int j, int k) {
    TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
    
    return (tileentityflowerpot != null) && (tileentityflowerpot.a() != null) ? tileentityflowerpot.b() : 0;
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return (super.canPlace(world, i, j, k)) && (World.a(world, i, j - 1, k));
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block) {
    if (!World.a(world, i, j - 1, k)) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setAir(i, j, k);
    }
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l) {
    TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
    
    if ((tileentityflowerpot != null) && (tileentityflowerpot.a() != null)) {
      a(world, i, j, k, new ItemStack(tileentityflowerpot.a(), 1, tileentityflowerpot.b()));
      tileentityflowerpot.a(null, 0);
    }
    
    super.remove(world, i, j, k, block, l);
  }
  
  public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
    super.a(world, i, j, k, l, entityhuman);
    if (entityhuman.abilities.canInstantlyBuild) {
      TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
      
      if (tileentityflowerpot != null) {
        tileentityflowerpot.a(Item.getById(0), 0);
      }
    }
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.FLOWER_POT;
  }
  
  private TileEntityFlowerPot e(World world, int i, int j, int k) {
    TileEntity tileentity = world.getTileEntity(i, j, k);
    
    return (tileentity != null) && ((tileentity instanceof TileEntityFlowerPot)) ? (TileEntityFlowerPot)tileentity : null;
  }
  
  public TileEntity a(World world, int i) {
    Object object = null;
    byte b0 = 0;
    
    switch (i) {
    case 1: 
      object = Blocks.RED_ROSE;
      b0 = 0;
      break;
    
    case 2: 
      object = Blocks.YELLOW_FLOWER;
      break;
    
    case 3: 
      object = Blocks.SAPLING;
      b0 = 0;
      break;
    
    case 4: 
      object = Blocks.SAPLING;
      b0 = 1;
      break;
    
    case 5: 
      object = Blocks.SAPLING;
      b0 = 2;
      break;
    
    case 6: 
      object = Blocks.SAPLING;
      b0 = 3;
      break;
    
    case 7: 
      object = Blocks.RED_MUSHROOM;
      break;
    
    case 8: 
      object = Blocks.BROWN_MUSHROOM;
      break;
    
    case 9: 
      object = Blocks.CACTUS;
      break;
    
    case 10: 
      object = Blocks.DEAD_BUSH;
      break;
    
    case 11: 
      object = Blocks.LONG_GRASS;
      b0 = 2;
      break;
    
    case 12: 
      object = Blocks.SAPLING;
      b0 = 4;
      break;
    
    case 13: 
      object = Blocks.SAPLING;
      b0 = 5;
    }
    
    return new TileEntityFlowerPot(Item.getItemOf((Block)object), b0);
  }
}
