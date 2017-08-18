package net.minecraft.server;

public abstract interface PacketLoginOutListener
  extends PacketListener
{
  public abstract void a(PacketLoginOutEncryptionBegin paramPacketLoginOutEncryptionBegin);
  
  public abstract void a(PacketLoginOutSuccess paramPacketLoginOutSuccess);
  
  public abstract void a(PacketLoginOutDisconnect paramPacketLoginOutDisconnect);
}
