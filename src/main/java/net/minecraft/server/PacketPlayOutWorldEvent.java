package net.minecraft.server;

public class PacketPlayOutWorldEvent extends Packet
{
  private int a;
  private int b;
  private int c;
  private int d;
  private int e;
  private boolean f;
  
  public PacketPlayOutWorldEvent() {}
  
  public PacketPlayOutWorldEvent(int i, int j, int k, int l, int i1, boolean flag) {
    this.a = i;
    this.c = j;
    this.d = k;
    this.e = l;
    this.b = i1;
    this.f = flag;
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readInt();
    this.c = packetdataserializer.readInt();
    this.d = (packetdataserializer.readByte() & 0xFF);
    this.e = packetdataserializer.readInt();
    this.b = packetdataserializer.readInt();
    this.f = packetdataserializer.readBoolean();
  }
  
  public void b(PacketDataSerializer packetdataserializer) {
    packetdataserializer.writeInt(this.a);
    

    if (packetdataserializer.version < 16) {
      packetdataserializer.writeInt(this.c);
      packetdataserializer.writeByte(this.d & 0xFF);
      packetdataserializer.writeInt(this.e);
    }
    else {
      packetdataserializer.writePosition(this.c, this.d, this.e);
    }
    

    packetdataserializer.writeInt(this.b);
    packetdataserializer.writeBoolean(this.f);
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener) {
    packetplayoutlistener.a(this);
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
