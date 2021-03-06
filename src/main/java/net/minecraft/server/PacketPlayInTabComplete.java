package net.minecraft.server;

import java.io.IOException;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class PacketPlayInTabComplete extends Packet
{
  private String a;
  
  public PacketPlayInTabComplete() {}
  
  public PacketPlayInTabComplete(String s)
  {
    this.a = s;
  }
  
  public void a(PacketDataSerializer packetdataserializer) throws IOException {
    this.a = packetdataserializer.c(32767);
    
    if (packetdataserializer.version >= 37)
    {
      if (packetdataserializer.readBoolean()) {
        long l = packetdataserializer.readLong();
      }
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer) throws IOException
  {
    packetdataserializer.a(StringUtils.substring(this.a, 0, 32767));
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public String c() {
    return this.a;
  }
  
  public String b() {
    return String.format("message='%s'", new Object[] { this.a });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayInListener)packetlistener);
  }
}
