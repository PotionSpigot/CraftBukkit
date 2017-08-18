package net.minecraft.server;



public class PacketPlayOutCloseWindow
  extends Packet
{
  private int a;
  


  public PacketPlayOutCloseWindow() {}
  

  public PacketPlayOutCloseWindow(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readUnsignedByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
  }
}
