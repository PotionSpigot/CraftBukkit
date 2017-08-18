package net.minecraft.server;


public class PacketPlayInArmAnimation
  extends Packet
{
  private int a;
  private int b;
  
  public void a(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 16)
    {
      this.a = packetdataserializer.readInt();
      this.b = packetdataserializer.readByte();
    } else {
      this.b = 1;
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    packetdataserializer.writeInt(this.a);
    packetdataserializer.writeByte(this.b);
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public String b() {
    return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
  }
  
  public int d() {
    return this.b;
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayInListener)packetlistener);
  }
}
