package net.minecraft.server;

import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;





public class PacketPrepender
  extends MessageToByteEncoder
{
  protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
  {
    int i = paramByteBuf1.readableBytes();
    int j = PacketDataSerializer.a(i);
    
    if (j > 3) {
      throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
    }
    
    PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(paramByteBuf2);
    
    localPacketDataSerializer.ensureWritable(j + i);
    
    localPacketDataSerializer.b(i);
    localPacketDataSerializer.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), i);
  }
}
