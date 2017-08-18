package net.minecraft.server;





public class PacketPlayOutAnimation
  extends Packet
{
  private int a;
  


  private int b;
  



  public PacketPlayOutAnimation() {}
  



  public PacketPlayOutAnimation(Entity paramEntity, int paramInt)
  {
    this.a = paramEntity.getId();
    this.b = paramInt;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.a();
    this.b = paramPacketDataSerializer.readUnsignedByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.a);
    paramPacketDataSerializer.writeByte(this.b);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public String b()
  {
    return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
  }
}
