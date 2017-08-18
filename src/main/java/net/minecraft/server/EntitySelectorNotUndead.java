package net.minecraft.server;





































final class EntitySelectorNotUndead
  implements IEntitySelector
{
  public boolean a(Entity paramEntity)
  {
    return ((paramEntity instanceof EntityLiving)) && (((EntityLiving)paramEntity).getMonsterType() != EnumMonsterType.UNDEAD);
  }
}
