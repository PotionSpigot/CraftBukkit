package net.minecraft.server;





















































































































 enum EnumProtocol$4
{
  EnumProtocol$4(int paramInt1)
  {
    super(paramString, paramInt, paramInt1, null);
    b(0, PacketLoginOutDisconnect.class);
    b(1, PacketLoginOutEncryptionBegin.class);
    b(2, PacketLoginOutSuccess.class);
    
    a(0, PacketLoginInStart.class);
    a(1, PacketLoginInEncryptionBegin.class);
  }
}
