package net.minecraft.server;



public class PacketPlayOutHeldItemSlot
  extends Packet
{
  private int a;
  


  public PacketPlayOutHeldItemSlot() {}
  

  public PacketPlayOutHeldItemSlot(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
