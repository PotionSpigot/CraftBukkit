package net.minecraft.server;



public class PacketLoginOutDisconnect
  extends Packet
{
  private IChatBaseComponent a;
  


  public PacketLoginOutDisconnect() {}
  

  public PacketLoginOutDisconnect(IChatBaseComponent paramIChatBaseComponent)
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
  
  public void a(PacketLoginOutListener paramPacketLoginOutListener)
  {
    paramPacketLoginOutListener.a(this);
  }
  
  public boolean a()
  {
    return true;
  }
}
