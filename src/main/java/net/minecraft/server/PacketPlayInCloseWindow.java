package net.minecraft.server;

public class PacketPlayInCloseWindow extends Packet
{
  private int a;
  
  public PacketPlayInCloseWindow() {}
  
  public PacketPlayInCloseWindow(int id)
  {
    this.a = id;
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readByte();
  }
  
  public void b(PacketDataSerializer packetdataserializer) {
    packetdataserializer.writeByte(this.a);
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayInListener)packetlistener);
  }
}
