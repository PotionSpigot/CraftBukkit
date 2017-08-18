package net.minecraft.server;




















public class PacketPlayOutGameStateChange
  extends Packet
{
  public static final String[] a = { "tile.bed.notValid", null, null, "gameMode.changed" };
  
  private int b;
  
  private float c;
  

  public PacketPlayOutGameStateChange() {}
  

  public PacketPlayOutGameStateChange(int paramInt, float paramFloat)
  {
    this.b = paramInt;
    this.c = paramFloat;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.b = paramPacketDataSerializer.readUnsignedByte();
    this.c = paramPacketDataSerializer.readFloat();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.b);
    paramPacketDataSerializer.writeFloat(this.c);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
