package net.minecraft.server;






























































































public class PacketPlayInLook
  extends PacketPlayInFlying
{
  public PacketPlayInLook()
  {
    this.hasLook = true;
  }
  







  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.yaw = paramPacketDataSerializer.readFloat();
    this.pitch = paramPacketDataSerializer.readFloat();
    super.a(paramPacketDataSerializer);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeFloat(this.yaw);
    paramPacketDataSerializer.writeFloat(this.pitch);
    super.b(paramPacketDataSerializer);
  }
}
