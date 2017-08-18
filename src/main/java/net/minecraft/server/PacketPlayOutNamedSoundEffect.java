package net.minecraft.server;

import net.minecraft.util.org.apache.commons.lang3.Validate;








public class PacketPlayOutNamedSoundEffect
  extends Packet
{
  private String a;
  private int b;
  private int c = Integer.MAX_VALUE;
  
  private int d;
  private float e;
  private int f;
  
  public PacketPlayOutNamedSoundEffect() {}
  
  public PacketPlayOutNamedSoundEffect(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2)
  {
    Validate.notNull(paramString, "name", new Object[0]);
    this.a = paramString;
    this.b = ((int)(paramDouble1 * 8.0D));
    this.c = ((int)(paramDouble2 * 8.0D));
    this.d = ((int)(paramDouble3 * 8.0D));
    this.e = paramFloat1;
    this.f = ((int)(paramFloat2 * 63.0F));
    
    if (this.f < 0) this.f = 0;
    if (this.f > 255) this.f = 255;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.c(256);
    this.b = paramPacketDataSerializer.readInt();
    this.c = paramPacketDataSerializer.readInt();
    this.d = paramPacketDataSerializer.readInt();
    this.e = paramPacketDataSerializer.readFloat();
    this.f = paramPacketDataSerializer.readUnsignedByte();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.a(this.a);
    paramPacketDataSerializer.writeInt(this.b);
    paramPacketDataSerializer.writeInt(this.c);
    paramPacketDataSerializer.writeInt(this.d);
    paramPacketDataSerializer.writeFloat(this.e);
    paramPacketDataSerializer.writeByte(this.f);
  }
  
























  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
