package net.minecraft.server;



public enum EnumGamemode
{
  int e;
  

  String f;
  

  private EnumGamemode(int paramInt1, String paramString1)
  {
    this.e = paramInt1;
    this.f = paramString1;
  }
  
  public int getId() {
    return this.e;
  }
  
  public String b() {
    return this.f;
  }
  
  public void a(PlayerAbilities paramPlayerAbilities) {
    if (this == CREATIVE) {
      paramPlayerAbilities.canFly = true;
      paramPlayerAbilities.canInstantlyBuild = true;
      paramPlayerAbilities.isInvulnerable = true;
    } else {
      paramPlayerAbilities.canFly = false;
      paramPlayerAbilities.canInstantlyBuild = false;
      paramPlayerAbilities.isInvulnerable = false;
      paramPlayerAbilities.isFlying = false;
    }
    paramPlayerAbilities.mayBuild = (!isAdventure());
  }
  
  public boolean isAdventure() {
    return this == ADVENTURE;
  }
  
  public boolean d() {
    return this == CREATIVE;
  }
  



  public static EnumGamemode getById(int paramInt)
  {
    for (EnumGamemode localEnumGamemode : ) {
      if (localEnumGamemode.e == paramInt) {
        return localEnumGamemode;
      }
    }
    return SURVIVAL;
  }
}
