package net.minecraft.server;












public class EntitySelectorEquipable
  implements IEntitySelector
{
  private final ItemStack d;
  










  public EntitySelectorEquipable(ItemStack paramItemStack)
  {
    this.d = paramItemStack;
  }
  
  public boolean a(Entity paramEntity)
  {
    if (!paramEntity.isAlive()) return false;
    if (!(paramEntity instanceof EntityLiving)) return false;
    EntityLiving localEntityLiving = (EntityLiving)paramEntity;
    if (localEntityLiving.getEquipment(EntityInsentient.b(this.d)) != null) { return false;
    }
    if ((localEntityLiving instanceof EntityInsentient))
      return ((EntityInsentient)localEntityLiving).bJ();
    if ((localEntityLiving instanceof EntityHuman)) {
      return true;
    }
    
    return false;
  }
}
