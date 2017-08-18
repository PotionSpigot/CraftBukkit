package net.minecraft.server;

public class ItemDoor extends Item
{
  private Material a;
  
  public ItemDoor(Material material) {
    this.a = material;
    this.maxStackSize = 1;
    a(CreativeModeTab.d);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
    if (l != 1) {
      return false;
    }
    j++;
    Block block;
    Block block;
    if (this.a == Material.WOOD) {
      block = Blocks.WOODEN_DOOR;
    } else {
      block = Blocks.IRON_DOOR_BLOCK;
    }
    
    if ((entityhuman.a(i, j, k, l, itemstack)) && (entityhuman.a(i, j + 1, k, l, itemstack))) {
      if (!block.canPlace(world, i, j, k)) {
        return false;
      }
      int i1 = MathHelper.floor((entityhuman.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3;
      
      place(world, i, j, k, i1, block);
      itemstack.count -= 1;
      return true;
    }
    
    return false;
  }
  

  public static void place(World world, int i, int j, int k, int l, Block block)
  {
    byte b0 = 0;
    byte b1 = 0;
    
    if (l == 0) {
      b1 = 1;
    }
    
    if (l == 1) {
      b0 = -1;
    }
    
    if (l == 2) {
      b1 = -1;
    }
    
    if (l == 3) {
      b0 = 1;
    }
    
    int i1 = (world.getType(i - b0, j, k - b1).r() ? 1 : 0) + (world.getType(i - b0, j + 1, k - b1).r() ? 1 : 0);
    int j1 = (world.getType(i + b0, j, k + b1).r() ? 1 : 0) + (world.getType(i + b0, j + 1, k + b1).r() ? 1 : 0);
    boolean flag = (world.getType(i - b0, j, k - b1) == block) || (world.getType(i - b0, j + 1, k - b1) == block);
    boolean flag1 = (world.getType(i + b0, j, k + b1) == block) || (world.getType(i + b0, j + 1, k + b1) == block);
    boolean flag2 = false;
    
    if ((flag) && (!flag1)) {
      flag2 = true;
    } else if (j1 > i1) {
      flag2 = true;
    }
    

    world.setTypeAndData(i, j, k, block, l, 3);
    world.setTypeAndData(i, j + 1, k, block, 0x8 | (flag2 ? 1 : 0), 3);
  }
}
