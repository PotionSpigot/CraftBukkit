package net.minecraft.server;

import java.security.PublicKey;





public class PacketLoginOutEncryptionBegin
  extends Packet
{
  private String a;
  private PublicKey b;
  private byte[] c;
  
  public PacketLoginOutEncryptionBegin() {}
  
  public PacketLoginOutEncryptionBegin(String paramString, PublicKey paramPublicKey, byte[] paramArrayOfByte)
  {
    this.a = paramString;
    this.b = paramPublicKey;
    this.c = paramArrayOfByte;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.c(20);
    this.b = MinecraftEncryption.a(a(paramPacketDataSerializer));
    this.c = a(paramPacketDataSerializer);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.a(this.a);
    a(paramPacketDataSerializer, this.b.getEncoded());
    a(paramPacketDataSerializer, this.c);
  }
  
  public void a(PacketLoginOutListener paramPacketLoginOutListener)
  {
    paramPacketLoginOutListener.a(this);
  }
}
