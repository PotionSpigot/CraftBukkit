package net.minecraft.server;

import java.util.List;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
import net.minecraft.util.io.netty.handler.codec.CorruptedFrameException;

public class PacketSplitter extends ByteToMessageDecoder
{
  protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List paramList)
  {
    paramByteBuf.markReaderIndex();
    
    byte[] arrayOfByte = new byte[3];
    for (int i = 0; i < arrayOfByte.length; i++) {
      if (!paramByteBuf.isReadable()) {
        paramByteBuf.resetReaderIndex();
        return;
      }
      
      arrayOfByte[i] = paramByteBuf.readByte();
      if (arrayOfByte[i] >= 0) {
        PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
        try {
          int j = localPacketDataSerializer.a();
          
          if (paramByteBuf.readableBytes() < j) {
            paramByteBuf.resetReaderIndex(); return;
          }
          
          paramList.add(paramByteBuf.readBytes(j)); return;
        }
        finally
        {
          localPacketDataSerializer.release();
        }
      }
    }
    
    throw new CorruptedFrameException("length wider than 21-bit");
  }
}
