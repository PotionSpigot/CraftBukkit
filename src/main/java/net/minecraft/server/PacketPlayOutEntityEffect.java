package net.minecraft.server;

public class PacketPlayOutEntityEffect extends Packet
{
  private int a;
  private byte b;
  private byte c;
  private short d;
  
  public PacketPlayOutEntityEffect() {}
  
  public PacketPlayOutEntityEffect(int i, MobEffect mobeffect) {
    this.a = i;
    this.b = ((byte)(mobeffect.getEffectId() & 0xFF));
    this.c = ((byte)(mobeffect.getAmplifier() & 0xFF));
    if (mobeffect.getDuration() > 32767) {
      this.d = Short.MAX_VALUE;
    } else {
      this.d = ((short)mobeffect.getDuration());
    }
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readInt();
    this.b = packetdataserializer.readByte();
    this.c = packetdataserializer.readByte();
    this.d = packetdataserializer.readShort();
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 16)
    {
      packetdataserializer.writeInt(this.a);
      packetdataserializer.writeByte(this.b);
      packetdataserializer.writeByte(this.c);
      packetdataserializer.writeShort(this.d);
    }
    else {
      packetdataserializer.b(this.a);
      packetdataserializer.writeByte(this.b);
      packetdataserializer.writeByte(this.c);
      packetdataserializer.b(this.d);
      packetdataserializer.writeBoolean(false);
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
