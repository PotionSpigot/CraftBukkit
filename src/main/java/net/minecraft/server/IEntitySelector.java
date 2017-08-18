package net.minecraft.server;




public abstract interface IEntitySelector
{
  public static final IEntitySelector a = new EntitySelectorLiving();
  




  public static final IEntitySelector b = new EntitySelectorChickenJockey();
  




  public static final IEntitySelector c = new EntitySelectorContainer();
  
  public abstract boolean a(Entity paramEntity);
}
