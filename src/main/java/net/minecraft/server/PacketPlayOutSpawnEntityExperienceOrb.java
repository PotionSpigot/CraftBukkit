package net.minecraft.server;


public class PacketPlayOutSpawnEntityExperienceOrb
  extends Packet
{
  private int a;
  
  private int b;
  
  private int c;
  
  private int d;
  
  private int e;
  

  public PacketPlayOutSpawnEntityExperienceOrb() {}
  

  public PacketPlayOutSpawnEntityExperienceOrb(EntityExperienceOrb paramEntityExperienceOrb)
  {
    this.a = paramEntityExperienceOrb.getId();
    this.b = MathHelper.floor(paramEntityExperienceOrb.locX * 32.0D);
    this.c = MathHelper.floor(paramEntityExperienceOrb.locY * 32.0D);
    this.d = MathHelper.floor(paramEntityExperienceOrb.locZ * 32.0D);
    this.e = paramEntityExperienceOrb.e();
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.a();
    this.b = paramPacketDataSerializer.readInt();
    this.c = paramPacketDataSerializer.readInt();
    this.d = paramPacketDataSerializer.readInt();
    this.e = paramPacketDataSerializer.readShort();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.a);
    paramPacketDataSerializer.writeInt(this.b);
    paramPacketDataSerializer.writeInt(this.c);
    paramPacketDataSerializer.writeInt(this.d);
    paramPacketDataSerializer.writeShort(this.e);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public String b()
  {
    return String.format("id=%d, value=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.e), Float.valueOf(this.b / 32.0F), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F) });
  }
}
