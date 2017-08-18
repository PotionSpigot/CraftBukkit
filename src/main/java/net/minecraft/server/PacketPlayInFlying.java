package net.minecraft.server;













public class PacketPlayInFlying
  extends Packet
{
  protected double x;
  











  protected double y;
  











  protected double z;
  











  protected double stance;
  











  protected float yaw;
  











  protected float pitch;
  











  protected boolean g;
  











  protected boolean hasPos;
  











  protected boolean hasLook;
  











  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.g = (paramPacketDataSerializer.readUnsignedByte() != 0);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.g ? 1 : 0);
  }
  
  public double c() {
    return this.x;
  }
  
  public double d() {
    return this.y;
  }
  
  public double e() {
    return this.z;
  }
  
  public double f() {
    return this.stance;
  }
  
  public float g() {
    return this.yaw;
  }
  
  public float h() {
    return this.pitch;
  }
  
  public boolean i() {
    return this.g;
  }
  
  public boolean j() {
    return this.hasPos;
  }
  
  public boolean k() {
    return this.hasLook;
  }
  
  public void a(boolean paramBoolean) {
    this.hasPos = paramBoolean;
  }
}
