package net.minecraft.server;














public enum EnumChatVisibility
{
  private static final EnumChatVisibility[] d;
  












  private final int e;
  











  private final String f;
  












  private EnumChatVisibility(int paramInt1, String paramString1)
  {
    this.e = paramInt1;
    this.f = paramString1;
  }
  
  public int a() {
    return this.e;
  }
  
  public static EnumChatVisibility a(int paramInt) {
    return d[(paramInt % d.length)];
  }
  
  static
  {
    d = new EnumChatVisibility[values().length];
    
















    for (EnumChatVisibility localEnumChatVisibility : values()) {
      d[localEnumChatVisibility.e] = localEnumChatVisibility;
    }
  }
}
