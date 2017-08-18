package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;





public class PacketLoginInStart
  extends Packet
{
  private GameProfile a;
  
  public PacketLoginInStart() {}
  
  public PacketLoginInStart(GameProfile paramGameProfile)
  {
    this.a = paramGameProfile;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = new GameProfile(null, paramPacketDataSerializer.c(16));
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.a(this.a.getName());
  }
  
  public void a(PacketLoginInListener paramPacketLoginInListener)
  {
    paramPacketLoginInListener.a(this);
  }
  
  public GameProfile c() {
    return this.a;
  }
}
