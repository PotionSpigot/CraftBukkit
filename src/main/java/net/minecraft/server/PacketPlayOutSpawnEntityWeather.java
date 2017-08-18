package net.minecraft.server;



public class PacketPlayOutSpawnEntityWeather
  extends Packet
{
  private int a;
  

  private int b;
  

  private int c;
  
  private int d;
  
  private int e;
  

  public PacketPlayOutSpawnEntityWeather() {}
  

  public PacketPlayOutSpawnEntityWeather(Entity paramEntity)
  {
    this.a = paramEntity.getId();
    this.b = MathHelper.floor(paramEntity.locX * 32.0D);
    this.c = MathHelper.floor(paramEntity.locY * 32.0D);
    this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
    if ((paramEntity instanceof EntityLightning)) {
      this.e = 1;
    }
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.a();
    this.e = paramPacketDataSerializer.readByte();
    this.b = paramPacketDataSerializer.readInt();
    this.c = paramPacketDataSerializer.readInt();
    this.d = paramPacketDataSerializer.readInt();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.a);
    paramPacketDataSerializer.writeByte(this.e);
    paramPacketDataSerializer.writeInt(this.b);
    paramPacketDataSerializer.writeInt(this.c);
    paramPacketDataSerializer.writeInt(this.d);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public String b()
  {
    return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.e), Float.valueOf(this.b / 32.0F), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F) });
  }
}
