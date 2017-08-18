package net.minecraft.server;

import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelFutureListener;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;






























































































































class QueuedProtocolSwitch
  implements Runnable
{
  QueuedProtocolSwitch(NetworkManager paramNetworkManager, EnumProtocol paramEnumProtocol1, EnumProtocol paramEnumProtocol2, Packet paramPacket, GenericFutureListener[] paramArrayOfGenericFutureListener) {}
  
  public void run()
  {
    if (this.a != this.b) {
      this.e.a(this.a);
    }
    NetworkManager.a(this.e).writeAndFlush(this.c).addListeners(this.d).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
  }
}
