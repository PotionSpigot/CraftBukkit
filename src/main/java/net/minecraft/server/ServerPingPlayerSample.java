package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;














































public class ServerPingPlayerSample
{
  private final int a;
  private final int b;
  private GameProfile[] c;
  
  public ServerPingPlayerSample(int paramInt1, int paramInt2)
  {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public int a() {
    return this.a;
  }
  
  public int b() {
    return this.b;
  }
  
  public GameProfile[] c() {
    return this.c;
  }
  
  public void a(GameProfile[] paramArrayOfGameProfile) {
    this.c = paramArrayOfGameProfile;
  }
}
