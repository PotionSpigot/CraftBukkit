package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutUpdateSign
  extends Packet
{
  private int x;
  private int y;
  private int z;
  private String[] lines;
  
  public PacketPlayOutUpdateSign() {}
  
  public PacketPlayOutUpdateSign(int i, int j, int k, String[] astring)
  {
    this.x = i;
    this.y = j;
    this.z = k;
    this.lines = new String[] { astring[0], astring[1], astring[2], astring[3] };
  }
  
  public void a(PacketDataSerializer packetdataserializer) throws IOException {
    this.x = packetdataserializer.readInt();
    this.y = packetdataserializer.readShort();
    this.z = packetdataserializer.readInt();
    this.lines = new String[4];
    
    for (int i = 0; i < 4; i++) {
      this.lines[i] = packetdataserializer.c(15);
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer) throws IOException
  {
    if (packetdataserializer.version < 16)
    {
      packetdataserializer.writeInt(this.x);
      packetdataserializer.writeShort(this.y);
      packetdataserializer.writeInt(this.z);
    }
    else {
      packetdataserializer.writePosition(this.x, this.y, this.z);
    }
    
    for (int i = 0; i < 4; i++) {
      if (packetdataserializer.version < 21)
      {
        packetdataserializer.a(this.lines[i]);
      }
      else {
        String line = ChatSerializer.a(org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage.fromString(this.lines[i])[0]);
        packetdataserializer.a(line);
      }
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
