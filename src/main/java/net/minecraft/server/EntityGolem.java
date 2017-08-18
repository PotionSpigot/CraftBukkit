package net.minecraft.server;

public abstract class EntityGolem
  extends EntityCreature implements IAnimal
{
  public EntityGolem(World paramWorld)
  {
    super(paramWorld);
  }
  

  protected void b(float paramFloat) {}
  

  protected String t()
  {
    return "none";
  }
  
  protected String aT()
  {
    return "none";
  }
  
  protected String aU()
  {
    return "none";
  }
  
  public int q()
  {
    return 120;
  }
  
  protected boolean isTypeNotPersistent()
  {
    return false;
  }
}
