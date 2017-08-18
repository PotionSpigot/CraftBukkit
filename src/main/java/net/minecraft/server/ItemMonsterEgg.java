package net.minecraft.server;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class ItemMonsterEgg extends Item {
  public ItemMonsterEgg() { a(true);
    a(CreativeModeTab.f);
  }
  
  public String n(ItemStack itemstack) {
    String s = ("" + LocaleI18n.get(new StringBuilder().append(getName()).append(".name").toString())).trim();
    String s1 = EntityTypes.b(itemstack.getData());
    
    if (s1 != null) {
      s = s + " " + LocaleI18n.get(new StringBuilder().append("entity.").append(s1).append(".name").toString());
    }
    
    return s;
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2)
  {
    if ((world.isStatic) || (itemstack.getData() == 48) || (itemstack.getData() == 49) || (itemstack.getData() == 63) || (itemstack.getData() == 64)) {
      return true;
    }
    Block block = world.getType(i, j, k);
    
    i += Facing.b[l];
    j += Facing.c[l];
    k += Facing.d[l];
    double d0 = 0.0D;
    
    if ((l == 1) && (block.b() == 11)) {
      d0 = 0.5D;
    }
    
    Entity entity = a(world, itemstack.getData(), i + 0.5D, j + d0, k + 0.5D);
    
    if (entity != null) {
      if (((entity instanceof EntityLiving)) && (itemstack.hasName())) {
        ((EntityInsentient)entity).setCustomName(itemstack.getName());
      }
      
      if (!entityhuman.abilities.canInstantlyBuild) {
        itemstack.count -= 1;
      }
    }
    
    return true;
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
  {
    if (world.isStatic) {
      return itemstack;
    }
    MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
    
    if (movingobjectposition == null) {
      return itemstack;
    }
    if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
      int i = movingobjectposition.b;
      int j = movingobjectposition.c;
      int k = movingobjectposition.d;
      
      if (!world.a(entityhuman, i, j, k)) {
        return itemstack;
      }
      
      if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
        return itemstack;
      }
      
      if ((world.getType(i, j, k) instanceof BlockFluids)) {
        Entity entity = a(world, itemstack.getData(), i, j, k);
        
        if (entity != null) {
          if (((entity instanceof EntityLiving)) && (itemstack.hasName())) {
            ((EntityInsentient)entity).setCustomName(itemstack.getName());
          }
          
          if (!entityhuman.abilities.canInstantlyBuild) {
            itemstack.count -= 1;
          }
        }
      }
    }
    
    return itemstack;
  }
  


  public static Entity a(World world, int i, double d0, double d1, double d2)
  {
    return spawnCreature(world, i, d0, d1, d2, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
  }
  
  public static Entity spawnCreature(World world, int i, double d0, double d1, double d2, CreatureSpawnEvent.SpawnReason spawnReason)
  {
    if (!EntityTypes.eggInfo.containsKey(Integer.valueOf(i))) {
      return null;
    }
    Entity entity = null;
    
    for (int j = 0; j < 1; j++) {
      entity = EntityTypes.a(i, world);
      if ((entity != null) && ((entity instanceof EntityLiving))) {
        EntityInsentient entityinsentient = (EntityInsentient)entity;
        
        entity.setPositionRotation(d0, d1, d2, MathHelper.g(world.random.nextFloat() * 360.0F), 0.0F);
        entityinsentient.aO = entityinsentient.yaw;
        entityinsentient.aM = entityinsentient.yaw;
        entityinsentient.prepare((GroupDataEntity)null);
        world.addEntity(entity, spawnReason);
        entityinsentient.r();
      }
    }
    
    return entity;
  }
}
