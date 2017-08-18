package net.minecraft.server;

import java.util.Arrays;

public class PacketPlayOutMap extends Packet
{
  private int a;
  private byte[] b;
  private byte scale;
  
  public PacketPlayOutMap() {}
  
  public PacketPlayOutMap(int i, byte[] abyte, byte scale)
  {
    this.scale = scale;
    
    this.a = i;
    this.b = abyte;
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.a();
    this.b = new byte[packetdataserializer.readUnsignedShort()];
    packetdataserializer.readBytes(this.b);
  }
  
  public void b(PacketDataSerializer packetdataserializer) {
    packetdataserializer.b(this.a);
    if (packetdataserializer.version < 27)
    {
      packetdataserializer.writeShort(this.b.length);
      packetdataserializer.writeBytes(this.b);
    } else {
      packetdataserializer.writeByte(this.scale);
      if (this.b[0] == 1) {
        int count = (this.b.length - 1) / 3;
        packetdataserializer.b(count);
        for (int i = 0; i < count; i++) {
          packetdataserializer.writeByte(this.b[(1 + i * 3)]);
          packetdataserializer.writeByte(this.b[(2 + i * 3)]);
          packetdataserializer.writeByte(this.b[(3 + i * 3)]);
        }
      } else {
        packetdataserializer.b(0);
      }
      
      if (this.b[0] == 0) {
        packetdataserializer.writeByte(1);
        int rows = this.b.length - 3;
        packetdataserializer.writeByte(rows);
        packetdataserializer.writeByte(this.b[1]);
        packetdataserializer.writeByte(this.b[2]);
        a(packetdataserializer, Arrays.copyOfRange(this.b, 3, this.b.length));
      } else {
        packetdataserializer.writeByte(0);
      }
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener) {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    return String.format("id=%d, length=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b.length) });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
