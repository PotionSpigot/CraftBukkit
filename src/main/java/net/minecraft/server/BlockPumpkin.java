package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockPumpkin extends BlockDirectional
{
  private boolean a;
  
  protected BlockPumpkin(boolean flag)
  {
    super(Material.PUMPKIN);
    a(true);
    this.a = flag;
    a(CreativeModeTab.b);
  }
  
  public void onPlace(World world, int i, int j, int k) {
    super.onPlace(world, i, j, k);
    if ((world.getType(i, j - 1, k) == Blocks.SNOW_BLOCK) && (world.getType(i, j - 2, k) == Blocks.SNOW_BLOCK)) {
      if (!world.isStatic)
      {
        BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
        
        blockList.setTypeId(i, j, k, 0);
        blockList.setTypeId(i, j - 1, k, 0);
        blockList.setTypeId(i, j - 2, k, 0);
        EntitySnowman entitysnowman = new EntitySnowman(world);
        
        entitysnowman.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
        if (world.addEntity(entitysnowman, CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
          blockList.updateList();
        }
      }
      

      for (int l = 0; l < 120; l++) {
        world.addParticle("snowshovel", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 2.5D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
      }
    } else if ((world.getType(i, j - 1, k) == Blocks.IRON_BLOCK) && (world.getType(i, j - 2, k) == Blocks.IRON_BLOCK)) {
      boolean flag = (world.getType(i - 1, j - 1, k) == Blocks.IRON_BLOCK) && (world.getType(i + 1, j - 1, k) == Blocks.IRON_BLOCK);
      boolean flag1 = (world.getType(i, j - 1, k - 1) == Blocks.IRON_BLOCK) && (world.getType(i, j - 1, k + 1) == Blocks.IRON_BLOCK);
      
      if ((flag) || (flag1))
      {
        BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
        
        blockList.setTypeId(i, j, k, 0);
        blockList.setTypeId(i, j - 1, k, 0);
        blockList.setTypeId(i, j - 2, k, 0);
        if (flag) {
          blockList.setTypeId(i - 1, j - 1, k, 0);
          blockList.setTypeId(i + 1, j - 1, k, 0);
        } else {
          blockList.setTypeId(i, j - 1, k - 1, 0);
          blockList.setTypeId(i, j - 1, k + 1, 0);
        }
        
        EntityIronGolem entityirongolem = new EntityIronGolem(world);
        
        entityirongolem.setPlayerCreated(true);
        entityirongolem.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
        if (world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) {
          for (int i1 = 0; i1 < 120; i1++) {
            world.addParticle("snowballpoof", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
          }
          
          blockList.updateList();
        }
      }
    }
  }
  
  public boolean canPlace(World world, int i, int j, int k)
  {
    return (world.getType(i, j, k).material.isReplaceable()) && (World.a(world, i, j - 1, k));
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    int l = MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 2.5D) & 0x3;
    
    world.setData(i, j, k, l, 2);
  }
  
  public void doPhysics(World world, int i, int j, int k, Block block)
  {
    if ((block != null) && (block.isPowerSource())) {
      org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(i, j, k);
      int power = bukkitBlock.getBlockPower();
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, power, power);
      world.getServer().getPluginManager().callEvent(eventRedstone);
    }
  }
}
