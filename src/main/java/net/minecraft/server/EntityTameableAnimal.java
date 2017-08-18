package net.minecraft.server;

import java.util.Random;
import java.util.UUID;











public abstract class EntityTameableAnimal
  extends EntityAnimal
  implements EntityOwnable
{
  protected PathfinderGoalSit bp = new PathfinderGoalSit(this);
  
  public EntityTameableAnimal(World paramWorld) {
    super(paramWorld);
  }
  
  protected void c()
  {
    super.c();
    this.datawatcher.a(16, Byte.valueOf((byte)0));
    this.datawatcher.a(17, "");
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    if (getOwnerUUID() == null) {
      paramNBTTagCompound.setString("OwnerUUID", "");
    } else {
      paramNBTTagCompound.setString("OwnerUUID", getOwnerUUID());
    }
    paramNBTTagCompound.setBoolean("Sitting", isSitting());
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    String str1 = "";
    if (paramNBTTagCompound.hasKeyOfType("OwnerUUID", 8)) {
      str1 = paramNBTTagCompound.getString("OwnerUUID");
    } else {
      String str2 = paramNBTTagCompound.getString("Owner");
      str1 = NameReferencingFileConverter.a(str2);
    }
    if (str1.length() > 0) {
      setOwnerUUID(str1);
      setTamed(true);
    }
    this.bp.setSitting(paramNBTTagCompound.getBoolean("Sitting"));
    setSitting(paramNBTTagCompound.getBoolean("Sitting"));
  }
  
  protected void i(boolean paramBoolean) {
    String str = "heart";
    if (!paramBoolean) {
      str = "smoke";
    }
    for (int i = 0; i < 7; i++) {
      double d1 = this.random.nextGaussian() * 0.02D;
      double d2 = this.random.nextGaussian() * 0.02D;
      double d3 = this.random.nextGaussian() * 0.02D;
      this.world.addParticle(str, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
    }
  }
  










  public boolean isTamed()
  {
    return (this.datawatcher.getByte(16) & 0x4) != 0;
  }
  
  public void setTamed(boolean paramBoolean) {
    int i = this.datawatcher.getByte(16);
    if (paramBoolean) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x4)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFB)));
    }
  }
  
  public boolean isSitting() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setSitting(boolean paramBoolean) {
    int i = this.datawatcher.getByte(16);
    if (paramBoolean) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
    }
  }
  
  public String getOwnerUUID()
  {
    return this.datawatcher.getString(17);
  }
  
  public void setOwnerUUID(String paramString) {
    this.datawatcher.watch(17, paramString);
  }
  
  public EntityLiving getOwner()
  {
    try {
      UUID localUUID = UUID.fromString(getOwnerUUID());
      if (localUUID == null) {
        return null;
      }
      return this.world.a(localUUID);
    } catch (IllegalArgumentException localIllegalArgumentException) {}
    return null;
  }
  
  public boolean e(EntityLiving paramEntityLiving)
  {
    return paramEntityLiving == getOwner();
  }
  
  public PathfinderGoalSit getGoalSit() {
    return this.bp;
  }
  
  public boolean a(EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
    return true;
  }
  
  public ScoreboardTeamBase getScoreboardTeam()
  {
    if (isTamed()) {
      EntityLiving localEntityLiving = getOwner();
      if (localEntityLiving != null) {
        return localEntityLiving.getScoreboardTeam();
      }
    }
    return super.getScoreboardTeam();
  }
  
  public boolean c(EntityLiving paramEntityLiving)
  {
    if (isTamed()) {
      EntityLiving localEntityLiving = getOwner();
      if (paramEntityLiving == localEntityLiving) {
        return true;
      }
      if (localEntityLiving != null) {
        return localEntityLiving.c(paramEntityLiving);
      }
    }
    return super.c(paramEntityLiving);
  }
}
