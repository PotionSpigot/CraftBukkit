package net.minecraft.server;



public class PacketPlayOutAttachEntity
  extends Packet
{
  private int a;
  

  private int b;
  

  private int c;
  

  public PacketPlayOutAttachEntity() {}
  

  public PacketPlayOutAttachEntity(int paramInt, Entity paramEntity1, Entity paramEntity2)
  {
    this.a = paramInt;
    this.b = paramEntity1.getId();
    this.c = (paramEntity2 != null ? paramEntity2.getId() : -1);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.b = paramPacketDataSerializer.readInt();
    this.c = paramPacketDataSerializer.readInt();
    this.a = paramPacketDataSerializer.readUnsignedByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeInt(this.b);
    paramPacketDataSerializer.writeInt(this.c);
    paramPacketDataSerializer.writeByte(this.a);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
