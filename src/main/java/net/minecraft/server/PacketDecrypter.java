package net.minecraft.server;

import java.util.List;
import javax.crypto.Cipher;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketDecrypter extends net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder
{
  private final PacketEncryptionHandler a;
  
  public PacketDecrypter(Cipher paramCipher)
  {
    this.a = new PacketEncryptionHandler(paramCipher);
  }
  
  protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List paramList)
  {
    paramList.add(this.a.a(paramChannelHandlerContext, paramByteBuf));
  }
}
