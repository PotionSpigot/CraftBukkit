package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockSkull
  extends BlockContainer
{
  protected BlockSkull()
  {
    super(Material.ORIENTABLE);
    a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
  }
  
  public int b() {
    return -1;
  }
  
  public boolean c() {
    return false;
  }
  
  public boolean d() {
    return false;
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    int l = iblockaccess.getData(i, j, k) & 0x7;
    
    switch (l) {
    case 1: 
    default: 
      a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
      break;
    
    case 2: 
      a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
      break;
    
    case 3: 
      a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
      break;
    
    case 4: 
      a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
      break;
    
    case 5: 
      a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
    }
  }
  
  public AxisAlignedBB a(World world, int i, int j, int k) {
    updateShape(world, i, j, k);
    return super.a(world, i, j, k);
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
    int l = MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 2.5D) & 0x3;
    
    world.setData(i, j, k, l, 2);
  }
  
  public TileEntity a(World world, int i) {
    return new TileEntitySkull();
  }
  
  public int getDropData(World world, int i, int j, int k) {
    TileEntity tileentity = world.getTileEntity(i, j, k);
    
    return (tileentity != null) && ((tileentity instanceof TileEntitySkull)) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDropData(world, i, j, k);
  }
  
  public int getDropData(int i) {
    return i;
  }
  
  public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1)
  {
    if (world.random.nextFloat() < f) {
      ItemStack itemstack = new ItemStack(Items.SKULL, 1, getDropData(world, i, j, k));
      

      TileEntity tileEntity = world.getTileEntity(i, j, k);
      if (!(tileEntity instanceof TileEntitySkull)) {
        return;
      }
      TileEntitySkull tileentityskull = (TileEntitySkull)tileEntity;
      

      if ((tileentityskull.getSkullType() == 3) && (tileentityskull.getGameProfile() != null)) {
        itemstack.setTag(new NBTTagCompound());
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        
        GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
        itemstack.getTag().set("SkullOwner", nbttagcompound);
      }
      
      a(world, i, j, k, itemstack);
    }
  }
  
  public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman)
  {
    if (entityhuman.abilities.canInstantlyBuild) {
      l |= 0x8;
      world.setData(i, j, k, l, 4);
    }
    
    super.a(world, i, j, k, l, entityhuman);
  }
  
  public void remove(World world, int i, int j, int k, Block block, int l) {
    if (!world.isStatic)
    {

















      super.remove(world, i, j, k, block, l);
    }
  }
  
  public Item getDropType(int i, Random random, int j) {
    return Items.SKULL;
  }
  
  public void a(World world, int i, int j, int k, TileEntitySkull tileentityskull) {
    if ((tileentityskull.getSkullType() == 1) && (j >= 2) && (world.difficulty != EnumDifficulty.PEACEFUL) && (!world.isStatic))
    {





      for (int l = -2; l <= 0; l++) {
        if ((world.getType(i, j - 1, k + l) == Blocks.SOUL_SAND) && (world.getType(i, j - 1, k + l + 1) == Blocks.SOUL_SAND) && (world.getType(i, j - 2, k + l + 1) == Blocks.SOUL_SAND) && (world.getType(i, j - 1, k + l + 2) == Blocks.SOUL_SAND) && (a(world, i, j, k + l, 1)) && (a(world, i, j, k + l + 1, 1)) && (a(world, i, j, k + l + 2, 1)))
        {
          BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
          
          world.setData(i, j, k + l, 8, 2);
          world.setData(i, j, k + l + 1, 8, 2);
          world.setData(i, j, k + l + 2, 8, 2);
          
          blockList.setTypeAndData(i, j, k + l, getById(0), 0, 2);
          blockList.setTypeAndData(i, j, k + l + 1, getById(0), 0, 2);
          blockList.setTypeAndData(i, j, k + l + 2, getById(0), 0, 2);
          blockList.setTypeAndData(i, j - 1, k + l, getById(0), 0, 2);
          blockList.setTypeAndData(i, j - 1, k + l + 1, getById(0), 0, 2);
          blockList.setTypeAndData(i, j - 1, k + l + 2, getById(0), 0, 2);
          blockList.setTypeAndData(i, j - 2, k + l + 1, getById(0), 0, 2);
          
          if (!world.isStatic) {
            EntityWither entitywither = new EntityWither(world);
            entitywither.setPositionRotation(i + 0.5D, j - 1.45D, k + l + 1.5D, 90.0F, 0.0F);
            entitywither.aM = 90.0F;
            entitywither.bZ();
            
            if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
              if (!world.isStatic) {
                Iterator iterator = world.a(EntityHuman.class, entitywither.boundingBox.grow(50.0D, 50.0D, 50.0D)).iterator();
                
                while (iterator.hasNext()) {
                  EntityHuman entityhuman = (EntityHuman)iterator.next();
                  entityhuman.a(AchievementList.I);
                }
              }
              
              blockList.updateList();
            }
          }
          
          for (int i1 = 0; i1 < 120; i1++) {
            world.addParticle("snowballpoof", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9D, k + l + 1 + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
          }
          
          return;
        }
      }
      
      for (l = -2; l <= 0; l++) {
        if ((world.getType(i + l, j - 1, k) == Blocks.SOUL_SAND) && (world.getType(i + l + 1, j - 1, k) == Blocks.SOUL_SAND) && (world.getType(i + l + 1, j - 2, k) == Blocks.SOUL_SAND) && (world.getType(i + l + 2, j - 1, k) == Blocks.SOUL_SAND) && (a(world, i + l, j, k, 1)) && (a(world, i + l + 1, j, k, 1)) && (a(world, i + l + 2, j, k, 1)))
        {
          BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
          
          world.setData(i + l, j, k, 8, 2);
          world.setData(i + l + 1, j, k, 8, 2);
          world.setData(i + l + 2, j, k, 8, 2);
          
          blockList.setTypeAndData(i + l, j, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l + 1, j, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l + 2, j, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l, j - 1, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l + 1, j - 1, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l + 2, j - 1, k, getById(0), 0, 2);
          blockList.setTypeAndData(i + l + 1, j - 2, k, getById(0), 0, 2);
          if (!world.isStatic) {
            EntityWither entitywither = new EntityWither(world);
            entitywither.setPositionRotation(i + l + 1.5D, j - 1.45D, k + 0.5D, 0.0F, 0.0F);
            entitywither.bZ();
            
            if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
              if (!world.isStatic) {
                Iterator iterator = world.a(EntityHuman.class, entitywither.boundingBox.grow(50.0D, 50.0D, 50.0D)).iterator();
                
                while (iterator.hasNext()) {
                  EntityHuman entityhuman = (EntityHuman)iterator.next();
                  entityhuman.a(AchievementList.I);
                }
              }
              blockList.updateList();
            }
          }
          
          for (int i1 = 0; i1 < 120; i1++) {
            world.addParticle("snowballpoof", i + l + 1 + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
          }
          

          return;
        }
      }
    }
  }
  
  private boolean a(World world, int i, int j, int k, int l) {
    if (world.getType(i, j, k) != this) {
      return false;
    }
    TileEntity tileentity = world.getTileEntity(i, j, k);
    
    return ((TileEntitySkull)tileentity).getSkullType() == l;
  }
}
