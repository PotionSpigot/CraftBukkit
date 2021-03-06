package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutSpawnEntityPainting extends Packet
{
  private int a;
  private int b;
  private int c;
  private int d;
  private int e;
  private String f;
  
  public PacketPlayOutSpawnEntityPainting() {}
  
  public PacketPlayOutSpawnEntityPainting(EntityPainting entitypainting) {
    this.a = entitypainting.getId();
    this.b = entitypainting.x;
    this.c = entitypainting.y;
    this.d = entitypainting.z;
    this.e = entitypainting.direction;
    this.f = entitypainting.art.B;
  }
  
  public void a(PacketDataSerializer packetdataserializer) throws IOException {
    this.a = packetdataserializer.a();
    this.f = packetdataserializer.c(EnumArt.A);
    this.b = packetdataserializer.readInt();
    this.c = packetdataserializer.readInt();
    this.d = packetdataserializer.readInt();
    this.e = packetdataserializer.readInt();
  }
  
  public void b(PacketDataSerializer packetdataserializer) throws IOException {
    packetdataserializer.b(this.a);
    packetdataserializer.a(this.f);
    
    if (packetdataserializer.version >= 28)
    {




      switch (this.e) {
      case 0: 
        this.d += 1;
        break;
      case 1: 
        this.b -= 1;
        break;
      case 2: 
        this.d -= 1;
        break;
      case 3: 
        this.b += 1;
      }
      
    }
    if (packetdataserializer.version < 16)
    {
      packetdataserializer.writeInt(this.b);
      packetdataserializer.writeInt(this.c);
      packetdataserializer.writeInt(this.d);
      packetdataserializer.writeInt(this.e);
    }
    else {
      packetdataserializer.writePosition(this.b, this.c, this.d);
      packetdataserializer.writeByte(this.e);
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    return String.format("id=%d, type=%s, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(this.a), this.f, Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.d) });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
