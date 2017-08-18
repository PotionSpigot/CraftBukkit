package net.minecraft.server;







public class PacketStatusInPing
  extends Packet
{
  private long a;
  





  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readLong();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeLong(this.a);
  }
  
  public void a(PacketStatusInListener paramPacketStatusInListener)
  {
    paramPacketStatusInListener.a(this);
  }
  
  public boolean a()
  {
    return true;
  }
  
  public long c() {
    return this.a;
  }
}
