package net.minecraft.server;





public class PacketPlayInEnchantItem
  extends Packet
{
  private int a;
  



  private int b;
  



  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readByte();
    this.b = paramPacketDataSerializer.readByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
    paramPacketDataSerializer.writeByte(this.b);
  }
  
  public String b()
  {
    return String.format("id=%d, button=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
  }
  
  public int c() {
    return this.a;
  }
  
  public int d() {
    return this.b;
  }
}
