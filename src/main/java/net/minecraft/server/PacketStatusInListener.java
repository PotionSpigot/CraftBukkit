package net.minecraft.server;

public abstract interface PacketStatusInListener
  extends PacketListener
{
  public abstract void a(PacketStatusInPing paramPacketStatusInPing);
  
  public abstract void a(PacketStatusInStart paramPacketStatusInStart);
}
