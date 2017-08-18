package net.minecraft.server;

import javax.crypto.Cipher;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketEncryptionHandler
{
  private final Cipher a;
  private byte[] b = new byte[0];
  private byte[] c = new byte[0];
  
  protected PacketEncryptionHandler(Cipher paramCipher) {
    this.a = paramCipher;
  }
  
  private byte[] a(ByteBuf paramByteBuf) {
    int i = paramByteBuf.readableBytes();
    if (this.b.length < i) {
      this.b = new byte[i];
    }
    paramByteBuf.readBytes(this.b, 0, i);
    return this.b;
  }
  
  protected ByteBuf a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
    int i = paramByteBuf.readableBytes();
    byte[] arrayOfByte = a(paramByteBuf);
    
    ByteBuf localByteBuf = paramChannelHandlerContext.alloc().heapBuffer(this.a.getOutputSize(i));
    localByteBuf.writerIndex(this.a.update(arrayOfByte, 0, i, localByteBuf.array(), localByteBuf.arrayOffset()));
    
    return localByteBuf;
  }
  
  protected void a(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) {
    int i = paramByteBuf1.readableBytes();
    byte[] arrayOfByte = a(paramByteBuf1);
    
    int j = this.a.getOutputSize(i);
    if (this.c.length < j) {
      this.c = new byte[j];
    }
    paramByteBuf2.writeBytes(this.c, 0, this.a.update(arrayOfByte, 0, i, this.c));
  }
}
