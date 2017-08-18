package net.minecraft.server;

import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.ChannelException;
import net.minecraft.util.io.netty.channel.ChannelPipeline;

class ServerConnectionChannel extends net.minecraft.util.io.netty.channel.ChannelInitializer
{
  final ServerConnection a;
  
  ServerConnectionChannel(ServerConnection serverconnection)
  {
    this.a = serverconnection;
  }
  
  protected void initChannel(Channel channel) {
    try {
      channel.config().setOption(net.minecraft.util.io.netty.channel.ChannelOption.IP_TOS, Integer.valueOf(24));
    }
    catch (ChannelException localChannelException) {}
    
    try
    {
      channel.config().setOption(net.minecraft.util.io.netty.channel.ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
    }
    catch (ChannelException localChannelException1) {}
    

    channel.pipeline().addLast("timeout", new net.minecraft.util.io.netty.handler.timeout.ReadTimeoutHandler(30)).addLast("legacy_query", new LegacyPingHandler(this.a)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(NetworkManager.h)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(NetworkManager.h));
    NetworkManager networkmanager = new NetworkManager(false);
    
    ServerConnection.a(this.a).add(networkmanager);
    channel.pipeline().addLast("packet_handler", networkmanager);
    networkmanager.a(new HandshakeListener(ServerConnection.b(this.a), networkmanager));
  }
}
