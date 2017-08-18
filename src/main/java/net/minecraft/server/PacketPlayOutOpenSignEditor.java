package net.minecraft.server;

public class PacketPlayOutOpenSignEditor extends Packet
{
  private int a;
  private int b;
  private int c;
  
  public PacketPlayOutOpenSignEditor() {}
  
  public PacketPlayOutOpenSignEditor(int i, int j, int k) {
    this.a = i;
    this.b = j;
    this.c = k;
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener) {
    packetplayoutlistener.a(this);
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readInt();
    this.b = packetdataserializer.readInt();
    this.c = packetdataserializer.readInt();
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 16)
    {
      packetdataserializer.writeInt(this.a);
      packetdataserializer.writeInt(this.b);
      packetdataserializer.writeInt(this.c);
    }
    else {
      packetdataserializer.writePosition(this.a, this.b, this.c);
    }
  }
  
  public void handle(PacketListener packetlistener)
  {
    a((PacketPlayOutListener)packetlistener);
  }
}
