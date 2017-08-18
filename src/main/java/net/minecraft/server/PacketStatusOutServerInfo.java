package net.minecraft.server;

import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;





public class PacketStatusOutServerInfo
  extends Packet
{
  private static final Gson a = new GsonBuilder().registerTypeAdapter(ServerPingServerData.class, new ServerPingServerDataSerializer()).registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSampleSerializer()).registerTypeAdapter(ServerPing.class, new ServerPingSerializer()).registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer()).registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer()).registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
  


  private ServerPing b;
  



  public PacketStatusOutServerInfo() {}
  



  public PacketStatusOutServerInfo(ServerPing paramServerPing)
  {
    this.b = paramServerPing;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.b = ((ServerPing)a.fromJson(paramPacketDataSerializer.c(32767), ServerPing.class));
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.a(a.toJson(this.b));
  }
  
  public void a(PacketStatusOutListener paramPacketStatusOutListener)
  {
    paramPacketStatusOutListener.a(this);
  }
  




  public boolean a()
  {
    return true;
  }
}
