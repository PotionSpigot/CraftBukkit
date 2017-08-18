package net.minecraft.server;

import java.util.Random;

public class BlockIce extends BlockHalfTransparent
{
  public BlockIce() {
    super("ice", Material.ICE, false);
    this.frictionFactor = 0.98F;
    a(true);
    a(CreativeModeTab.b);
  }
  
  public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
    entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
    entityhuman.applyExhaustion(0.025F);
    if ((E()) && (EnchantmentManager.hasSilkTouchEnchantment(entityhuman))) {
      ItemStack itemstack = j(l);
      
      if (itemstack != null) {
        a(world, i, j, k, itemstack);
      }
    } else {
      if (world.worldProvider.f) {
        world.setAir(i, j, k);
        return;
      }
      
      int i1 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
      
      b(world, i, j, k, l, i1);
      Material material = world.getType(i, j - 1, k).getMaterial();
      
      if ((material.isSolid()) || (material.isLiquid())) {
        world.setTypeUpdate(i, j, k, Blocks.WATER);
      }
    }
  }
  
  public int a(Random random) {
    return 0;
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11 - k())
    {
      if (org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), world.worldProvider.f ? Blocks.AIR : Blocks.STATIONARY_WATER).isCancelled()) {
        return;
      }
      

      if (world.worldProvider.f) {
        world.setAir(i, j, k);
        return;
      }
      
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setTypeUpdate(i, j, k, Blocks.STATIONARY_WATER);
    }
  }
  
  public int h() {
    return 0;
  }
}
