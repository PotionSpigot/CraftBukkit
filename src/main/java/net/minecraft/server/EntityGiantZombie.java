package net.minecraft.server;

public class EntityGiantZombie extends EntityMonster
{
  public EntityGiantZombie(World paramWorld)
  {
    super(paramWorld);
    this.height *= 6.0F;
    a(this.width * 6.0F, this.length * 6.0F);
  }
  
  protected void aD()
  {
    super.aD();
    
    getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.5D);
    getAttributeInstance(GenericAttributes.e).setValue(50.0D);
  }
  
  public float a(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F;
  }
}
