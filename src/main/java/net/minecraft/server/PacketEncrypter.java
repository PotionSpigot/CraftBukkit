package net.minecraft.server;

import javax.crypto.Cipher;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketEncrypter extends net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder
{
  private final PacketEncryptionHandler a;
  
  public PacketEncrypter(Cipher paramCipher)
  {
    this.a = new PacketEncryptionHandler(paramCipher);
  }
  
  protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
  {
    this.a.a(paramByteBuf1, paramByteBuf2);
  }
}
