package net.minecraft.server;


public class PacketPlayOutWindowData
  extends Packet
{
  private int a;
  
  private int b;
  
  private int c;
  

  public PacketPlayOutWindowData() {}
  

  public PacketPlayOutWindowData(int paramInt1, int paramInt2, int paramInt3)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readUnsignedByte();
    this.b = paramPacketDataSerializer.readShort();
    this.c = paramPacketDataSerializer.readShort();
  }
  

  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
    paramPacketDataSerializer.writeShort(this.b);
    paramPacketDataSerializer.writeShort(this.c);
  }
}
