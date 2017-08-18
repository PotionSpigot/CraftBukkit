package net.minecraft.server;



public class PacketPlayInAbilities
  extends Packet
{
  private boolean a;
  
  private boolean b;
  
  private boolean c;
  
  private boolean d;
  
  private float e;
  
  private float f;
  

  public PacketPlayInAbilities() {}
  

  public PacketPlayInAbilities(PlayerAbilities paramPlayerAbilities)
  {
    a(paramPlayerAbilities.isInvulnerable);
    b(paramPlayerAbilities.isFlying);
    c(paramPlayerAbilities.canFly);
    d(paramPlayerAbilities.canInstantlyBuild);
    a(paramPlayerAbilities.a());
    b(paramPlayerAbilities.b());
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    int i = paramPacketDataSerializer.readByte();
    
    a((i & 0x1) > 0);
    b((i & 0x2) > 0);
    c((i & 0x4) > 0);
    d((i & 0x8) > 0);
    a(paramPacketDataSerializer.readFloat());
    b(paramPacketDataSerializer.readFloat());
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    int i = 0;
    
    if (c()) i = (byte)(i | 0x1);
    if (isFlying()) i = (byte)(i | 0x2);
    if (e()) i = (byte)(i | 0x4);
    if (f()) { i = (byte)(i | 0x8);
    }
    paramPacketDataSerializer.writeByte(i);
    paramPacketDataSerializer.writeFloat(this.e);
    paramPacketDataSerializer.writeFloat(this.f);
  }
  
  public void a(PacketPlayInListener paramPacketPlayInListener)
  {
    paramPacketPlayInListener.a(this);
  }
  
  public String b()
  {
    return String.format("invuln=%b, flying=%b, canfly=%b, instabuild=%b, flyspeed=%.4f, walkspped=%.4f", new Object[] { Boolean.valueOf(c()), Boolean.valueOf(isFlying()), Boolean.valueOf(e()), Boolean.valueOf(f()), Float.valueOf(g()), Float.valueOf(h()) });
  }
  
  public boolean c() {
    return this.a;
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean isFlying() {
    return this.b;
  }
  
  public void b(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean e() {
    return this.c;
  }
  
  public void c(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean f() {
    return this.d;
  }
  
  public void d(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public float g() {
    return this.e;
  }
  
  public void a(float paramFloat) {
    this.e = paramFloat;
  }
  
  public float h() {
    return this.f;
  }
  
  public void b(float paramFloat) {
    this.f = paramFloat;
  }
}
