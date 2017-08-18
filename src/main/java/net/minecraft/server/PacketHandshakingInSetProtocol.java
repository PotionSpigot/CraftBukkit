package net.minecraft.server;

import java.io.IOException;

public class PacketHandshakingInSetProtocol
  extends Packet
{
  private int a;
  public String b;
  public int c;
  private EnumProtocol d;
  
  public void a(PacketDataSerializer packetdataserializer) throws IOException
  {
    this.a = packetdataserializer.a();
    this.b = packetdataserializer.c(32767);
    this.c = packetdataserializer.readUnsignedShort();
    this.d = EnumProtocol.a(packetdataserializer.a());
  }
  
  public void b(PacketDataSerializer packetdataserializer) throws IOException {
    packetdataserializer.b(this.a);
    packetdataserializer.a(this.b);
    packetdataserializer.writeShort(this.c);
    packetdataserializer.b(this.d.c());
  }
  
  public void a(PacketHandshakingInListener packethandshakinginlistener) {
    packethandshakinginlistener.a(this);
  }
  
  public boolean a() {
    return true;
  }
  
  public EnumProtocol c() {
    return this.d;
  }
  
  public int d() {
    return this.a;
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketHandshakingInListener)packetlistener);
  }
}
