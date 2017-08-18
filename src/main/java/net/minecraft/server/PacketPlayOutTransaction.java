package net.minecraft.server;


public class PacketPlayOutTransaction
  extends Packet
{
  private int a;
  
  private short b;
  
  private boolean c;
  

  public PacketPlayOutTransaction() {}
  

  public PacketPlayOutTransaction(int paramInt, short paramShort, boolean paramBoolean)
  {
    this.a = paramInt;
    this.b = paramShort;
    this.c = paramBoolean;
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readUnsignedByte();
    this.b = paramPacketDataSerializer.readShort();
    this.c = paramPacketDataSerializer.readBoolean();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
    paramPacketDataSerializer.writeShort(this.b);
    paramPacketDataSerializer.writeBoolean(this.c);
  }
  
  public String b()
  {
    return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.a), Short.valueOf(this.b), Boolean.valueOf(this.c) });
  }
}
