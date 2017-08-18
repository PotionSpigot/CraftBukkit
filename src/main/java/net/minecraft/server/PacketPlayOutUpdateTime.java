package net.minecraft.server;


public class PacketPlayOutUpdateTime
  extends Packet
{
  private long a;
  
  private long b;
  

  public PacketPlayOutUpdateTime() {}
  
  public PacketPlayOutUpdateTime(long paramLong1, long paramLong2, boolean paramBoolean)
  {
    this.a = paramLong1;
    this.b = paramLong2;
    
    if (!paramBoolean) {
      this.b = (-this.b);
      if (this.b == 0L) {
        this.b = -1L;
      }
    }
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readLong();
    this.b = paramPacketDataSerializer.readLong();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeLong(this.a);
    paramPacketDataSerializer.writeLong(this.b);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public String b()
  {
    return String.format("time=%d,dtime=%d", new Object[] { Long.valueOf(this.a), Long.valueOf(this.b) });
  }
}
