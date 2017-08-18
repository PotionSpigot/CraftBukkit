package net.minecraft.server;

public abstract interface PacketHandshakingInListener
  extends PacketListener
{
  public abstract void a(PacketHandshakingInSetProtocol paramPacketHandshakingInSetProtocol);
}
