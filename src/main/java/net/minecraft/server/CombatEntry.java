package net.minecraft.server;


public class CombatEntry
{
  private final DamageSource a;
  private final int b;
  private final float c;
  private final float d;
  private final String e;
  private final float f;
  
  public CombatEntry(DamageSource paramDamageSource, int paramInt, float paramFloat1, float paramFloat2, String paramString, float paramFloat3)
  {
    this.a = paramDamageSource;
    this.b = paramInt;
    this.c = paramFloat2;
    this.d = paramFloat1;
    this.e = paramString;
    this.f = paramFloat3;
  }
  
  public DamageSource a() {
    return this.a;
  }
  



  public float c()
  {
    return this.c;
  }
  







  public boolean f()
  {
    return this.a.getEntity() instanceof EntityLiving;
  }
  
  public String g() {
    return this.e;
  }
  
  public IChatBaseComponent h() {
    return a().getEntity() == null ? null : a().getEntity().getScoreboardDisplayName();
  }
  
  public float i() {
    if (this.a == DamageSource.OUT_OF_WORLD) return Float.MAX_VALUE;
    return this.f;
  }
}
