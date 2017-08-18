package net.minecraft.server;




public class PacketPlayInTransaction
  extends Packet
{
  private int a;
  


  private short b;
  


  private boolean c;
  



  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readByte();
    this.b = paramPacketDataSerializer.readShort();
    this.c = (paramPacketDataSerializer.readByte() != 0);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
    paramPacketDataSerializer.writeShort(this.b);
    paramPacketDataSerializer.writeByte(this.c ? 1 : 0);
  }
  
  public String b()
  {
    return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.a), Short.valueOf(this.b), Boolean.valueOf(this.c) });
  }
  
  public int c() {
    return this.a;
  }
  
  public short d() {
    return this.b;
  }
}
