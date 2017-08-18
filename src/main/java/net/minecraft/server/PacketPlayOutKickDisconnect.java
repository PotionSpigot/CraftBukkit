package net.minecraft.server;



public class PacketPlayOutKickDisconnect
  extends Packet
{
  private IChatBaseComponent a;
  


  public PacketPlayOutKickDisconnect() {}
  

  public PacketPlayOutKickDisconnect(IChatBaseComponent paramIChatBaseComponent)
  {
    this.a = paramIChatBaseComponent;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = ChatSerializer.a(paramPacketDataSerializer.c(32767));
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.a(ChatSerializer.a(this.a));
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public boolean a()
  {
    return true;
  }
}
