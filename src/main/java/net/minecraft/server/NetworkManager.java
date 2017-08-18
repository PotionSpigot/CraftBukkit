package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import java.net.SocketAddress;
import java.util.Queue;
import javax.crypto.SecretKey;
import net.minecraft.util.com.google.common.collect.Queues;
import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPipeline;
import net.minecraft.util.io.netty.channel.EventLoop;
import net.minecraft.util.io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.util.io.netty.channel.local.LocalChannel;
import net.minecraft.util.io.netty.channel.local.LocalServerChannel;
import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
import net.minecraft.util.io.netty.handler.timeout.TimeoutException;
import net.minecraft.util.io.netty.util.Attribute;
import net.minecraft.util.io.netty.util.AttributeKey;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.util.org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.spigotmc.SpigotCompressor;
import org.spigotmc.SpigotDecompressor;

public class NetworkManager extends SimpleChannelInboundHandler
{
  private static final Logger i = ;
  public static final Marker a = MarkerManager.getMarker("NETWORK");
  public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
  public static final Marker c = MarkerManager.getMarker("NETWORK_STAT", a);
  public static final AttributeKey d = new AttributeKey("protocol");
  public static final AttributeKey e = new AttributeKey("receivable_packets");
  public static final AttributeKey f = new AttributeKey("sendable_packets");
  public static final NioEventLoopGroup g = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
  public static final NetworkStatistics h = new NetworkStatistics();
  private final boolean j;
  private final Queue k = Queues.newConcurrentLinkedQueue();
  private final Queue l = Queues.newConcurrentLinkedQueue();
  
  private Channel m;
  public SocketAddress n;
  public java.util.UUID spoofedUUID;
  public net.minecraft.util.com.mojang.authlib.properties.Property[] spoofedProfile;
  public boolean preparing = true;
  
  private PacketListener o;
  
  private EnumProtocol p;
  private IChatBaseComponent q;
  private boolean r;
  public static final AttributeKey<Integer> protocolVersion = new AttributeKey("protocol_version");
  public static final ImmutableSet<Integer> SUPPORTED_VERSIONS = ImmutableSet.of(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(47));
  public static final int CURRENT_VERSION = 5;
  
  public static int getVersion(Channel attr) {
    Integer ver = (Integer)attr.attr(protocolVersion).get();
    return ver != null ? ver.intValue() : 5;
  }
  
  public int getVersion() {
    return getVersion(this.m);
  }
  
  public NetworkManager(boolean flag)
  {
    this.j = flag;
  }
  
  public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
    super.channelActive(channelhandlercontext);
    this.m = channelhandlercontext.channel();
    this.n = this.m.remoteAddress();
    
    this.preparing = false;
    
    a(EnumProtocol.HANDSHAKING);
  }
  
  public void a(EnumProtocol enumprotocol) {
    this.p = ((EnumProtocol)this.m.attr(d).getAndSet(enumprotocol));
    this.m.attr(e).set(enumprotocol.a(this.j));
    this.m.attr(f).set(enumprotocol.b(this.j));
    this.m.config().setAutoRead(true);
    i.debug("Enabled auto read");
  }
  
  public void channelInactive(ChannelHandlerContext channelhandlercontext) {
    close(new ChatMessage("disconnect.endOfStream", new Object[0]));
  }
  
  public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
    ChatMessage chatmessage;
    ChatMessage chatmessage;
    if ((throwable instanceof TimeoutException)) {
      chatmessage = new ChatMessage("disconnect.timeout", new Object[0]);
    } else {
      chatmessage = new ChatMessage("disconnect.genericReason", new Object[] { "Internal Exception: " + throwable });
    }
    
    close(chatmessage);
    if (MinecraftServer.getServer().isDebugging()) throwable.printStackTrace();
  }
  
  protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
    if (this.m.isOpen()) {
      if (packet.a()) {
        packet.handle(this.o);
      } else {
        this.k.add(packet);
      }
    }
  }
  
  public void a(PacketListener packetlistener) {
    Validate.notNull(packetlistener, "packetListener", new Object[0]);
    i.debug("Set listener of {} to {}", new Object[] { this, packetlistener });
    this.o = packetlistener;
  }
  
  public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
    if ((this.m != null) && (this.m.isOpen())) {
      i();
      b(packet, agenericfuturelistener);
    } else {
      this.l.add(new QueuedPacket(packet, agenericfuturelistener));
    }
  }
  
  private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
    EnumProtocol enumprotocol = EnumProtocol.a(packet);
    EnumProtocol enumprotocol1 = (EnumProtocol)this.m.attr(d).get();
    
    if (enumprotocol1 != enumprotocol) {
      i.debug("Disabled auto read");
      this.m.config().setAutoRead(false);
    }
    
    if (this.m.eventLoop().inEventLoop()) {
      if (enumprotocol != enumprotocol1) {
        a(enumprotocol);
      }
      
      this.m.writeAndFlush(packet).addListeners(agenericfuturelistener).addListener(net.minecraft.util.io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    } else {
      this.m.eventLoop().execute(new QueuedProtocolSwitch(this, enumprotocol, enumprotocol1, packet, agenericfuturelistener));
    }
  }
  
  private void i() {
    if ((this.m != null) && (this.m.isOpen()))
    {
      QueuedPacket queuedpacket;
      while ((queuedpacket = (QueuedPacket)this.l.poll()) != null) {
        b(QueuedPacket.a(queuedpacket), QueuedPacket.b(queuedpacket));
      }
    }
  }
  
  public void a()
  {
    i();
    EnumProtocol enumprotocol = (EnumProtocol)this.m.attr(d).get();
    
    if (this.p != enumprotocol) {
      if (this.p != null) {
        this.o.a(this.p, enumprotocol);
      }
      
      this.p = enumprotocol;
    }
    
    if (this.o != null)
    {
      Packet packet;
      for (int i = org.github.paperspigot.PaperSpigotConfig.maxPacketsPerPlayer; ((packet = (Packet)this.k.poll()) != null) && (i >= 0); i--)
      {


        if ((isConnected()) && (this.m.config().isAutoRead()))
        {


          packet.handle(this.o);
        }
      }
      this.o.a();
    }
    
    this.m.flush();
  }
  
  public SocketAddress getSocketAddress() {
    return this.n;
  }
  
  public void close(IChatBaseComponent ichatbasecomponent)
  {
    this.preparing = false;
    this.k.clear();
    this.l.clear();
    
    if (this.m.isOpen()) {
      this.m.close();
      this.q = ichatbasecomponent;
    }
  }
  
  public boolean c() {
    return ((this.m instanceof LocalChannel)) || ((this.m instanceof LocalServerChannel));
  }
  
  public void a(SecretKey secretkey) {
    this.m.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(MinecraftEncryption.a(2, secretkey)));
    this.m.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(MinecraftEncryption.a(1, secretkey)));
    this.r = true;
  }
  
  public boolean isConnected() {
    return (this.m != null) && (this.m.isOpen());
  }
  
  public PacketListener getPacketListener() {
    return this.o;
  }
  
  public IChatBaseComponent f() {
    return this.q;
  }
  
  public void g() {
    this.m.config().setAutoRead(false);
  }
  
  protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
    a(channelhandlercontext, (Packet)object);
  }
  
  static Channel a(NetworkManager networkmanager) {
    return networkmanager.m;
  }
  

  public SocketAddress getRawAddress()
  {
    return this.m.remoteAddress();
  }
  



  public void enableCompression()
  {
    if (this.m.pipeline().get("protocol_lib_decoder") != null) {
      this.m.pipeline().addBefore("protocol_lib_decoder", "decompress", new SpigotDecompressor());
    } else {
      this.m.pipeline().addBefore("decoder", "decompress", new SpigotDecompressor());
    }
    
    this.m.pipeline().addBefore("encoder", "compress", new SpigotCompressor());
  }
}
