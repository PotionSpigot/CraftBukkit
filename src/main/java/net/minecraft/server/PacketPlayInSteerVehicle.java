package net.minecraft.server;

public class PacketPlayInSteerVehicle
  extends Packet
{
  private float a;
  private float b;
  private boolean c;
  private boolean d;
  
  public void a(PacketDataSerializer packetdataserializer)
  {
    this.a = packetdataserializer.readFloat();
    this.b = packetdataserializer.readFloat();
    
    if (packetdataserializer.version < 16)
    {
      this.c = packetdataserializer.readBoolean();
      this.d = packetdataserializer.readBoolean();
    } else {
      int flags = packetdataserializer.readUnsignedByte();
      this.c = ((flags & 0x1) != 0);
      this.d = ((flags & 0x2) != 0);
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    packetdataserializer.writeFloat(this.a);
    packetdataserializer.writeFloat(this.b);
    packetdataserializer.writeBoolean(this.c);
    packetdataserializer.writeBoolean(this.d);
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public float c() {
    return this.a;
  }
  
  public float d() {
    return this.b;
  }
  
  public boolean e() {
    return this.c;
  }
  
  public boolean f() {
    return this.d;
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayInListener)packetlistener);
  }
}
