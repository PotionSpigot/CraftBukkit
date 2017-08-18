package net.minecraft.server;





public class PacketPlayInSetCreativeSlot
  extends Packet
{
  private int slot;
  



  private ItemStack b;
  




  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.slot = paramPacketDataSerializer.readShort();
    this.b = paramPacketDataSerializer.c();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeShort(this.slot);
    paramPacketDataSerializer.a(this.b);
  }
  
  public int c() {
    return this.slot;
  }
  
  public ItemStack getItemStack() {
    return this.b;
  }
}
