package net.minecraft.server;

public class PacketPlayOutRelEntityMove extends PacketPlayOutEntity
{
  private boolean onGround;
  
  public PacketPlayOutRelEntityMove() {}
  
  public PacketPlayOutRelEntityMove(int i, byte b0, byte b1, byte b2, boolean onGround) {
    super(i);
    this.b = b0;
    this.c = b1;
    this.d = b2;
    this.onGround = onGround;
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    super.a(packetdataserializer);
    this.b = packetdataserializer.readByte();
    this.c = packetdataserializer.readByte();
    this.d = packetdataserializer.readByte();
  }
  
  public void b(PacketDataSerializer packetdataserializer) {
    super.b(packetdataserializer);
    packetdataserializer.writeByte(this.b);
    packetdataserializer.writeByte(this.c);
    packetdataserializer.writeByte(this.d);
    
    if (packetdataserializer.version >= 22)
    {
      packetdataserializer.writeBoolean(this.onGround);
    }
  }
  
  public String b()
  {
    return super.b() + String.format(", xa=%d, ya=%d, za=%d", new Object[] { Byte.valueOf(this.b), Byte.valueOf(this.c), Byte.valueOf(this.d) });
  }
  
  public void handle(PacketListener packetlistener) {
    super.a((PacketPlayOutListener)packetlistener);
  }
}
