package net.minecraft.server;

public class PacketPlayOutKeepAlive extends Packet
{
  private int a;
  
  public PacketPlayOutKeepAlive() {}
  
  public PacketPlayOutKeepAlive(int i) {
    this.a = i;
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener) {
    packetplayoutlistener.a(this);
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readInt();
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version >= 32)
    {
      packetdataserializer.b(this.a);
    }
    else {
      packetdataserializer.writeInt(this.a);
    }
  }
  
  public boolean a()
  {
    return true;
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
