package net.minecraft.server;


public abstract class EntityAmbient
  extends EntityInsentient
  implements IAnimal
{
  public EntityAmbient(World paramWorld)
  {
    super(paramWorld);
  }
  
  public boolean bM()
  {
    return false;
  }
  
  protected boolean a(EntityHuman paramEntityHuman)
  {
    return false;
  }
}
