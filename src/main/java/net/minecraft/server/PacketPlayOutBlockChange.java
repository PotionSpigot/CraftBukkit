package net.minecraft.server;

import org.spigotmc.SpigotDebreakifier;

public class PacketPlayOutBlockChange extends Packet {
  private int a;
  private int b;
  private int c;
  public Block block;
  public int data;
  
  public PacketPlayOutBlockChange() {}
  
  public PacketPlayOutBlockChange(int i, int j, int k, World world) { this.a = i;
    this.b = j;
    this.c = k;
    this.block = world.getType(i, j, k);
    this.data = world.getData(i, j, k);
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.a = packetdataserializer.readInt();
    this.b = packetdataserializer.readUnsignedByte();
    this.c = packetdataserializer.readInt();
    this.block = Block.getById(packetdataserializer.a());
    this.data = packetdataserializer.readUnsignedByte();
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 25)
    {
      packetdataserializer.writeInt(this.a);
      packetdataserializer.writeByte(this.b);
      packetdataserializer.writeInt(this.c);
      packetdataserializer.b(Block.getId(this.block));
      packetdataserializer.writeByte(this.data);
    }
    else {
      packetdataserializer.writePosition(this.a, this.b, this.c);
      int id = Block.getId(this.block);
      this.data = SpigotDebreakifier.getCorrectedData(id, this.data);
      packetdataserializer.b(id << 4 | this.data);
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    return String.format("type=%d, data=%d, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(Block.getId(this.block)), Integer.valueOf(this.data), Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c) });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
