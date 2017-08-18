package net.minecraft.server;








public abstract interface IMonster
  extends IAnimal
{
  public static final IEntitySelector a = new EntitySelectorMonster();
}
