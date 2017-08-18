package net.minecraft.server;


public enum EnumDifficulty
{
  private static final EnumDifficulty[] e;
  
  private final int f;
  
  private final String g;
  
  private EnumDifficulty(int paramInt1, String paramString1)
  {
    this.f = paramInt1;
    this.g = paramString1;
  }
  
  public int a() {
    return this.f;
  }
  
  public static EnumDifficulty getById(int paramInt) {
    return e[(paramInt % e.length)];
  }
  
  static
  {
    e = new EnumDifficulty[values().length];
    
















    for (EnumDifficulty localEnumDifficulty : values()) {
      e[localEnumDifficulty.f] = localEnumDifficulty;
    }
  }
  
  public String b() {
    return this.g;
  }
}
