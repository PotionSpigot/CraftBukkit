package net.minecraft.server;

import java.util.Random;









public abstract class EntityWaterAnimal
  extends EntityCreature
  implements IAnimal
{
  public EntityWaterAnimal(World paramWorld)
  {
    super(paramWorld);
  }
  

  public boolean aE()
  {
    return true;
  }
  
  public boolean canSpawn()
  {
    return this.world.b(this.boundingBox);
  }
  
  public int q()
  {
    return 120;
  }
  
  protected boolean isTypeNotPersistent()
  {
    return true;
  }
  
  protected int getExpValue(EntityHuman paramEntityHuman)
  {
    return 1 + this.world.random.nextInt(3);
  }
  
  public void C()
  {
    int i = getAirTicks();
    
    super.C();
    
    if ((isAlive()) && (!M())) {
      setAirTicks(--i);
      if (getAirTicks() == -20) {
        setAirTicks(0);
        damageEntity(DamageSource.DROWN, 2.0F);
      }
    } else {
      setAirTicks(300);
    }
  }
}
