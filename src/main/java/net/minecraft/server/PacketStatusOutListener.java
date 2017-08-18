package net.minecraft.server;

public abstract interface PacketStatusOutListener
  extends PacketListener
{
  public abstract void a(PacketStatusOutServerInfo paramPacketStatusOutServerInfo);
  
  public abstract void a(PacketStatusOutPong paramPacketStatusOutPong);
}
