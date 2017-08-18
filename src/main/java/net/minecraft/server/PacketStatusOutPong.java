package net.minecraft.server;



public class PacketStatusOutPong
  extends Packet
{
  private long a;
  

  public PacketStatusOutPong() {}
  

  public PacketStatusOutPong(long paramLong)
  {
    this.a = paramLong;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readLong();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeLong(this.a);
  }
  
  public void a(PacketStatusOutListener paramPacketStatusOutListener)
  {
    paramPacketStatusOutListener.a(this);
  }
  
  public boolean a()
  {
    return true;
  }
}
