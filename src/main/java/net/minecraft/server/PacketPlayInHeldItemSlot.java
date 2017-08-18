package net.minecraft.server;






public class PacketPlayInHeldItemSlot
  extends Packet
{
  private int itemInHandIndex;
  





  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.itemInHandIndex = paramPacketDataSerializer.readShort();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeShort(this.itemInHandIndex);
  }
  
  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public int c() {
    return this.itemInHandIndex;
  }
}
