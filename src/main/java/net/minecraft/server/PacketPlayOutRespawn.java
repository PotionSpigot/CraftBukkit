package net.minecraft.server;



public class PacketPlayOutRespawn
  extends Packet
{
  private int a;
  

  private EnumDifficulty b;
  

  private EnumGamemode c;
  
  private WorldType d;
  

  public PacketPlayOutRespawn() {}
  

  public PacketPlayOutRespawn(int paramInt, EnumDifficulty paramEnumDifficulty, WorldType paramWorldType, EnumGamemode paramEnumGamemode)
  {
    this.a = paramInt;
    this.b = paramEnumDifficulty;
    this.c = paramEnumGamemode;
    this.d = paramWorldType;
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readInt();
    this.b = EnumDifficulty.getById(paramPacketDataSerializer.readUnsignedByte());
    this.c = EnumGamemode.getById(paramPacketDataSerializer.readUnsignedByte());
    this.d = WorldType.getType(paramPacketDataSerializer.c(16));
    if (this.d == null) {
      this.d = WorldType.NORMAL;
    }
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeInt(this.a);
    paramPacketDataSerializer.writeByte(this.b.a());
    paramPacketDataSerializer.writeByte(this.c.getId());
    paramPacketDataSerializer.a(this.d.name());
  }
}
