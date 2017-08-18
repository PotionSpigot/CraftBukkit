package net.minecraft.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.spigotmc.SpigotConfig;

public class HandshakeListener implements PacketHandshakingInListener
{
  private static final Gson gson = new Gson();
  
  private static final HashMap<InetAddress, Long> throttleTracker = new HashMap();
  private static int throttleCounter = 0;
  
  private final MinecraftServer a;
  private final NetworkManager b;
  
  public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager)
  {
    this.a = minecraftserver;
    this.b = networkmanager;
  }
  
  public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol)
  {
    if (NetworkManager.SUPPORTED_VERSIONS.contains(Integer.valueOf(packethandshakinginsetprotocol.d())))
    {
      NetworkManager.a(this.b).attr(NetworkManager.protocolVersion).set(Integer.valueOf(packethandshakinginsetprotocol.d()));
    }
    
    switch (ProtocolOrdinalWrapper.a[packethandshakinginsetprotocol.c().ordinal()]) {
    case 1: 
      this.b.a(EnumProtocol.LOGIN);
      

      try
      {
        long currentTime = System.currentTimeMillis();
        long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
        InetAddress address = ((InetSocketAddress)this.b.getSocketAddress()).getAddress();
        
        synchronized (throttleTracker) {
          if ((throttleTracker.containsKey(address)) && (!"127.0.0.1".equals(address.getHostAddress())) && (currentTime - ((Long)throttleTracker.get(address)).longValue() < connectionThrottle)) {
            throttleTracker.put(address, Long.valueOf(currentTime));
            ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
            this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener[0]);
            this.b.close(chatcomponenttext);
            return;
          }
          
          throttleTracker.put(address, Long.valueOf(currentTime));
          throttleCounter += 1;
          if (throttleCounter > 200) {
            throttleCounter = 0;
            

            Iterator iter = throttleTracker.entrySet().iterator();
            while (iter.hasNext()) {
              Map.Entry<InetAddress, Long> entry = (Map.Entry)iter.next();
              if (((Long)entry.getValue()).longValue() > connectionThrottle) {
                iter.remove();
              }
            }
          }
        }
      } catch (Throwable t) {
        org.apache.logging.log4j.LogManager.getLogger().debug("Failed to check connection throttle", t);
      }
      

      if ((packethandshakinginsetprotocol.d() > 5) && (packethandshakinginsetprotocol.d() != 47)) {
        ChatComponentText chatcomponenttext = new ChatComponentText(java.text.MessageFormat.format(SpigotConfig.outdatedServerMessage, new Object[] { "1.7.10" }));
        this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener[0]);
        this.b.close(chatcomponenttext);
      } else if (packethandshakinginsetprotocol.d() < 4) {
        ChatComponentText chatcomponenttext = new ChatComponentText(java.text.MessageFormat.format(SpigotConfig.outdatedClientMessage, new Object[] { "1.7.10" }));
        this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener[0]);
        this.b.close(chatcomponenttext);
      } else {
        this.b.a(new LoginListener(this.a, this.b));
        
        if (SpigotConfig.bungee) {
          String[] split = packethandshakinginsetprotocol.b.split("\000");
          if ((split.length == 3) || (split.length == 4)) {
            packethandshakinginsetprotocol.b = split[0];
            this.b.n = new InetSocketAddress(split[1], ((InetSocketAddress)this.b.getSocketAddress()).getPort());
            this.b.spoofedUUID = net.minecraft.util.com.mojang.util.UUIDTypeAdapter.fromString(split[2]);
          }
          else {
            ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
            this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener[0]);
            this.b.close(chatcomponenttext);
            return;
          }
          if (split.length == 4)
          {
            this.b.spoofedProfile = ((net.minecraft.util.com.mojang.authlib.properties.Property[])gson.fromJson(split[3], net.minecraft.util.com.mojang.authlib.properties.Property[].class));
          }
        }
        
        ((LoginListener)this.b.getPacketListener()).hostname = (packethandshakinginsetprotocol.b + ":" + packethandshakinginsetprotocol.c);
      }
      break;
    
    case 2: 
      this.b.a(EnumProtocol.STATUS);
      this.b.a(new PacketStatusListener(this.a, this.b));
      break;
    
    default: 
      throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.c());
    }
  }
  
  public void a(IChatBaseComponent ichatbasecomponent) {}
  
  public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
    if ((enumprotocol1 != EnumProtocol.LOGIN) && (enumprotocol1 != EnumProtocol.STATUS)) {
      throw new UnsupportedOperationException("Invalid state " + enumprotocol1);
    }
  }
  
  public void a() {}
}
