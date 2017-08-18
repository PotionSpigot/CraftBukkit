package net.minecraft.server;

import java.security.PrivateKey;
import javax.crypto.SecretKey;





public class PacketLoginInEncryptionBegin
  extends Packet
{
  private byte[] a = new byte[0];
  private byte[] b = new byte[0];
  









  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = a(paramPacketDataSerializer);
    this.b = a(paramPacketDataSerializer);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    a(paramPacketDataSerializer, this.a);
    a(paramPacketDataSerializer, this.b);
  }
  
  public void a(PacketLoginInListener paramPacketLoginInListener)
  {
    paramPacketLoginInListener.a(this);
  }
  
  public SecretKey a(PrivateKey paramPrivateKey) {
    return MinecraftEncryption.a(paramPrivateKey, this.a);
  }
  
  public byte[] b(PrivateKey paramPrivateKey) {
    if (paramPrivateKey == null) {
      return this.b;
    }
    return MinecraftEncryption.b(paramPrivateKey, this.b);
  }
}
