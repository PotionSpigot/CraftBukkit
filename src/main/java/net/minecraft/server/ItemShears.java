package net.minecraft.server;





public class ItemShears
  extends Item
{
  public ItemShears()
  {
    e(1);
    setMaxDurability(238);
    a(CreativeModeTab.i);
  }
  
  public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving)
  {
    if ((paramBlock.getMaterial() == Material.LEAVES) || (paramBlock == Blocks.WEB) || (paramBlock == Blocks.LONG_GRASS) || (paramBlock == Blocks.VINE) || (paramBlock == Blocks.TRIPWIRE)) {
      paramItemStack.damage(1, paramEntityLiving);
      return true;
    }
    return super.a(paramItemStack, paramWorld, paramBlock, paramInt1, paramInt2, paramInt3, paramEntityLiving);
  }
  
  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    return (paramBlock == Blocks.WEB) || (paramBlock == Blocks.REDSTONE_WIRE) || (paramBlock == Blocks.TRIPWIRE);
  }
  
  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    if ((paramBlock == Blocks.WEB) || (paramBlock.getMaterial() == Material.LEAVES)) {
      return 15.0F;
    }
    if (paramBlock == Blocks.WOOL) {
      return 5.0F;
    }
    return super.getDestroySpeed(paramItemStack, paramBlock);
  }
}
