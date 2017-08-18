package net.minecraft.server;

import java.net.InetSocketAddress;
import net.minecraft.util.com.google.common.base.Charsets;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFutureListener;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.util.io.netty.channel.ChannelPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyPingHandler extends ChannelInboundHandlerAdapter
{
  private static final Logger a = ;
  
  private ServerConnection b;
  
  public LegacyPingHandler(ServerConnection paramServerConnection)
  {
    this.b = paramServerConnection;
  }
  
  public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
  {
    ByteBuf localByteBuf1 = (ByteBuf)paramObject;
    
    localByteBuf1.markReaderIndex();
    
    int i = 1;
    try {
      if (localByteBuf1.readUnsignedByte() != 254) {
        return;
      }
      
      InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramChannelHandlerContext.channel().remoteAddress();
      MinecraftServer localMinecraftServer = this.b.d();
      
      int j = localByteBuf1.readableBytes();
      String str1; switch (j) {
      case 0: 
        a.debug("Ping: (<1.3.x) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
        
        str1 = String.format("%s§%d§%d", new Object[] { localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.C()), Integer.valueOf(localMinecraftServer.D()) });
        a(paramChannelHandlerContext, a(str1));
        
        break;
      
      case 1: 
        if (localByteBuf1.readUnsignedByte() != 1) {
          return;
        }
        
        a.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
        
        str1 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), localMinecraftServer.getVersion(), localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.C()), Integer.valueOf(localMinecraftServer.D()) });
        a(paramChannelHandlerContext, a(str1));
        
        break;
      
      default: 
        boolean bool = localByteBuf1.readUnsignedByte() == 1;
        bool &= localByteBuf1.readUnsignedByte() == 250;
        bool &= "MC|PingHost".equals(new String(localByteBuf1.readBytes(localByteBuf1.readShort() * 2).array(), Charsets.UTF_16BE));
        int k = localByteBuf1.readUnsignedShort();
        bool &= localByteBuf1.readUnsignedByte() >= 73;
        bool &= 3 + localByteBuf1.readBytes(localByteBuf1.readShort() * 2).array().length + 4 == k;
        bool &= localByteBuf1.readInt() <= 65535;
        bool &= localByteBuf1.readableBytes() == 0;
        
        if (!bool) {
          return;
        }
        
        a.debug("Ping: (1.6) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
        
        String str2 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), localMinecraftServer.getVersion(), localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.C()), Integer.valueOf(localMinecraftServer.D()) });
        ByteBuf localByteBuf2 = a(str2);
        try {
          a(paramChannelHandlerContext, localByteBuf2);
        } finally {
          localByteBuf2.release();
        }
      }
      
      localByteBuf1.release();
      i = 0;
    }
    catch (RuntimeException localRuntimeException) {}finally {
      if (i != 0) {
        localByteBuf1.resetReaderIndex();
        paramChannelHandlerContext.channel().pipeline().remove("legacy_query");
        paramChannelHandlerContext.fireChannelRead(paramObject);
      }
    }
  }
  
  private void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
    paramChannelHandlerContext.pipeline().firstContext().writeAndFlush(paramByteBuf).addListener(ChannelFutureListener.CLOSE);
  }
  
  private ByteBuf a(String paramString) {
    ByteBuf localByteBuf = net.minecraft.util.io.netty.buffer.Unpooled.buffer();
    localByteBuf.writeByte(255);
    
    char[] arrayOfChar1 = paramString.toCharArray();
    localByteBuf.writeShort(arrayOfChar1.length);
    for (int k : arrayOfChar1) {
      localByteBuf.writeChar(k);
    }
    
    return localByteBuf;
  }
}
