package net.minecraft.server;









public class PacketPlayInClientCommand
  extends Packet
{
  private EnumClientCommand a;
  








  public PacketPlayInClientCommand() {}
  







  public PacketPlayInClientCommand(EnumClientCommand paramEnumClientCommand)
  {
    this.a = paramEnumClientCommand;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = EnumClientCommand.a()[(paramPacketDataSerializer.readByte() % EnumClientCommand.a().length)];
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(EnumClientCommand.a(this.a));
  }
  
  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public EnumClientCommand c() {
    return this.a;
  }
}
