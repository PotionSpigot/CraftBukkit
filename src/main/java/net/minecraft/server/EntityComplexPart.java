package net.minecraft.server;


public class EntityComplexPart
  extends Entity
{
  public final IComplex owner;
  
  public final String b;
  
  public EntityComplexPart(IComplex paramIComplex, String paramString, float paramFloat1, float paramFloat2)
  {
    super(paramIComplex.a());
    a(paramFloat1, paramFloat2);
    this.owner = paramIComplex;
    this.b = paramString;
  }
  


  protected void c() {}
  


  protected void a(NBTTagCompound paramNBTTagCompound) {}
  

  protected void b(NBTTagCompound paramNBTTagCompound) {}
  

  public boolean R()
  {
    return true;
  }
  
  public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
  {
    if (isInvulnerable()) return false;
    return this.owner.a(this, paramDamageSource, paramFloat);
  }
  
  public boolean i(Entity paramEntity)
  {
    return (this == paramEntity) || (this.owner == paramEntity);
  }
}
