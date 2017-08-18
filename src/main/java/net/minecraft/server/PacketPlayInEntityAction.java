package net.minecraft.server;


public class PacketPlayInEntityAction
  extends Packet
{
  private int a;
  private int animation;
  private int c;
  
  public void a(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 16)
    {
      this.a = packetdataserializer.readInt();
      this.animation = packetdataserializer.readByte();
      this.c = packetdataserializer.readInt();
    }
    else {
      this.a = packetdataserializer.a();
      this.animation = (packetdataserializer.readUnsignedByte() + 1);
      this.c = packetdataserializer.a();
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    packetdataserializer.writeInt(this.a);
    packetdataserializer.writeByte(this.animation);
    packetdataserializer.writeInt(this.c);
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public int d() {
    return this.animation;
  }
  
  public int e() {
    return this.c;
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayInListener)packetlistener);
  }
}
