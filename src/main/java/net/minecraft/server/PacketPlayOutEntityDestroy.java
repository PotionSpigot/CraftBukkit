package net.minecraft.server;

public class PacketPlayOutEntityDestroy extends Packet
{
  private int[] a;
  
  public PacketPlayOutEntityDestroy() {}
  
  public PacketPlayOutEntityDestroy(int... aint) {
    this.a = aint;
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = new int[packetdataserializer.readByte()];
    
    for (int i = 0; i < this.a.length; i++) {
      this.a[i] = packetdataserializer.readInt();
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer) {
    int i;
    if (packetdataserializer.version < 16)
    {
      packetdataserializer.writeByte(this.a.length);
      
      for (i = 0; i < this.a.length; i++)
      {
        packetdataserializer.writeInt(this.a[i]);
      }
    } else {
      packetdataserializer.b(this.a.length);
      for (int i : this.a) {
        packetdataserializer.b(i);
      }
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    StringBuilder stringbuilder = new StringBuilder();
    
    for (int i = 0; i < this.a.length; i++) {
      if (i > 0) {
        stringbuilder.append(", ");
      }
      
      stringbuilder.append(this.a[i]);
    }
    
    return String.format("entities=%d[%s]", new Object[] { Integer.valueOf(this.a.length), stringbuilder });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
