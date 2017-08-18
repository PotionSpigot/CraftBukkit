package net.minecraft.server;



public class PacketPlayOutEntityStatus
  extends Packet
{
  private int a;
  

  private byte b;
  


  public PacketPlayOutEntityStatus() {}
  

  public PacketPlayOutEntityStatus(Entity paramEntity, byte paramByte)
  {
    this.a = paramEntity.getId();
    this.b = paramByte;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readInt();
    this.b = paramPacketDataSerializer.readByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeInt(this.a);
    paramPacketDataSerializer.writeByte(this.b);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
