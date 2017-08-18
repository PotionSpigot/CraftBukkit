package net.minecraft.server;

import java.util.Collection;

public class AttributeSnapshot
{
  private final String b;
  private final double c;
  private final Collection d;
  
  public AttributeSnapshot(PacketPlayOutUpdateAttributes paramPacketPlayOutUpdateAttributes, String paramString, double paramDouble, Collection paramCollection)
  {
    this.b = paramString;
    this.c = paramDouble;
    this.d = paramCollection;
  }
  
  public String a() {
    return this.b;
  }
  
  public double b() {
    return this.c;
  }
  
  public Collection c() {
    return this.d;
  }
}
