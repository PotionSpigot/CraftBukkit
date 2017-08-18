package net.minecraft.server;

import java.io.PrintStream;









public class MobEffect
{
  private int effectId;
  private int duration;
  private int amplification;
  private boolean splash;
  private boolean ambient;
  
  public MobEffect(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, 0);
  }
  
  public MobEffect(int paramInt1, int paramInt2, int paramInt3) {
    this(paramInt1, paramInt2, paramInt3, false);
  }
  
  public MobEffect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    this.effectId = paramInt1;
    this.duration = paramInt2;
    this.amplification = paramInt3;
    this.ambient = paramBoolean;
  }
  
  public MobEffect(MobEffect paramMobEffect) {
    this.effectId = paramMobEffect.effectId;
    this.duration = paramMobEffect.duration;
    this.amplification = paramMobEffect.amplification;
  }
  
  public void a(MobEffect paramMobEffect) {
    if (this.effectId != paramMobEffect.effectId) {
      System.err.println("This method should only be called for matching effects!");
    }
    if (paramMobEffect.amplification > this.amplification) {
      this.amplification = paramMobEffect.amplification;
      this.duration = paramMobEffect.duration;
    } else if ((paramMobEffect.amplification == this.amplification) && (this.duration < paramMobEffect.duration)) {
      this.duration = paramMobEffect.duration;
    } else if ((!paramMobEffect.ambient) && (this.ambient)) {
      this.ambient = paramMobEffect.ambient;
    }
  }
  
  public int getEffectId() {
    return this.effectId;
  }
  
  public int getDuration() {
    return this.duration;
  }
  
  public int getAmplifier() {
    return this.amplification;
  }
  



  public void setSplash(boolean paramBoolean)
  {
    this.splash = paramBoolean;
  }
  
  public boolean isAmbient() {
    return this.ambient;
  }
  





  public boolean tick(EntityLiving paramEntityLiving)
  {
    if (this.duration > 0) {
      if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification)) {
        b(paramEntityLiving);
      }
      h();
    }
    return this.duration > 0;
  }
  
  private int h() {
    return --this.duration;
  }
  
  public void b(EntityLiving paramEntityLiving) {
    if (this.duration > 0) {
      MobEffectList.byId[this.effectId].tick(paramEntityLiving, this.amplification);
    }
  }
  
  public String f() {
    return MobEffectList.byId[this.effectId].a();
  }
  
  public int hashCode()
  {
    return this.effectId;
  }
  
  public String toString()
  {
    String str = "";
    if (getAmplifier() > 0) {
      str = f() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
    } else {
      str = f() + ", Duration: " + getDuration();
    }
    if (this.splash) {
      str = str + ", Splash: true";
    }
    if (MobEffectList.byId[this.effectId].i()) {
      return "(" + str + ")";
    }
    return str;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof MobEffect)) {
      return false;
    }
    MobEffect localMobEffect = (MobEffect)paramObject;
    return (this.effectId == localMobEffect.effectId) && (this.amplification == localMobEffect.amplification) && (this.duration == localMobEffect.duration) && (this.splash == localMobEffect.splash) && (this.ambient == localMobEffect.ambient);
  }
  
  public NBTTagCompound a(NBTTagCompound paramNBTTagCompound) {
    paramNBTTagCompound.setByte("Id", (byte)getEffectId());
    paramNBTTagCompound.setByte("Amplifier", (byte)getAmplifier());
    paramNBTTagCompound.setInt("Duration", getDuration());
    paramNBTTagCompound.setBoolean("Ambient", isAmbient());
    return paramNBTTagCompound;
  }
  
  public static MobEffect b(NBTTagCompound paramNBTTagCompound) {
    int i = paramNBTTagCompound.getByte("Id");
    if ((i < 0) || (i >= MobEffectList.byId.length) || (MobEffectList.byId[i] == null)) {
      return null;
    }
    int j = paramNBTTagCompound.getByte("Amplifier");
    int k = paramNBTTagCompound.getInt("Duration");
    boolean bool = paramNBTTagCompound.getBoolean("Ambient");
    return new MobEffect(i, k, j, bool);
  }
}
