package net.minecraft.server;



public class EntityMinecartRideable
  extends EntityMinecartAbstract
{
  public EntityMinecartRideable(World paramWorld)
  {
    super(paramWorld);
  }
  
  public EntityMinecartRideable(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
    super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
  }
  
  public boolean c(EntityHuman paramEntityHuman)
  {
    if ((this.passenger != null) && ((this.passenger instanceof EntityHuman)) && (this.passenger != paramEntityHuman)) return true;
    if ((this.passenger != null) && (this.passenger != paramEntityHuman)) return false;
    if (!this.world.isStatic) {
      paramEntityHuman.mount(this);
    }
    
    return true;
  }
  
  public int m()
  {
    return 0;
  }
}
