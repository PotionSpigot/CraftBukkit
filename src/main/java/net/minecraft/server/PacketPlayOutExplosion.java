package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;







public class PacketPlayOutExplosion
  extends Packet
{
  private double a;
  private double b;
  private double c;
  private float d;
  private List e;
  private float f;
  private float g;
  private float h;
  
  public PacketPlayOutExplosion() {}
  
  public PacketPlayOutExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, List paramList, Vec3D paramVec3D)
  {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
    this.d = paramFloat;
    this.e = new ArrayList(paramList);
    
    if (paramVec3D != null) {
      this.f = ((float)paramVec3D.a);
      this.g = ((float)paramVec3D.b);
      this.h = ((float)paramVec3D.c);
    }
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readFloat();
    this.b = paramPacketDataSerializer.readFloat();
    this.c = paramPacketDataSerializer.readFloat();
    this.d = paramPacketDataSerializer.readFloat();
    int i = paramPacketDataSerializer.readInt();
    
    this.e = new ArrayList(i);
    
    int j = (int)this.a;
    int k = (int)this.b;
    int m = (int)this.c;
    for (int n = 0; n < i; n++) {
      int i1 = paramPacketDataSerializer.readByte() + j;
      int i2 = paramPacketDataSerializer.readByte() + k;
      int i3 = paramPacketDataSerializer.readByte() + m;
      this.e.add(new ChunkPosition(i1, i2, i3));
    }
    
    this.f = paramPacketDataSerializer.readFloat();
    this.g = paramPacketDataSerializer.readFloat();
    this.h = paramPacketDataSerializer.readFloat();
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeFloat((float)this.a);
    paramPacketDataSerializer.writeFloat((float)this.b);
    paramPacketDataSerializer.writeFloat((float)this.c);
    paramPacketDataSerializer.writeFloat(this.d);
    paramPacketDataSerializer.writeInt(this.e.size());
    
    int i = (int)this.a;
    int j = (int)this.b;
    int k = (int)this.c;
    for (ChunkPosition localChunkPosition : this.e) {
      int m = localChunkPosition.x - i;
      int n = localChunkPosition.y - j;
      int i1 = localChunkPosition.z - k;
      paramPacketDataSerializer.writeByte(m);
      paramPacketDataSerializer.writeByte(n);
      paramPacketDataSerializer.writeByte(i1);
    }
    
    paramPacketDataSerializer.writeFloat(this.f);
    paramPacketDataSerializer.writeFloat(this.g);
    paramPacketDataSerializer.writeFloat(this.h);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
