package net.minecraft.server;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;





public class PacketPlayOutTabComplete
  extends Packet
{
  private String[] a;
  
  public PacketPlayOutTabComplete() {}
  
  public PacketPlayOutTabComplete(String[] paramArrayOfString)
  {
    this.a = paramArrayOfString;
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = new String[paramPacketDataSerializer.a()];
    
    for (int i = 0; i < this.a.length; i++) {
      this.a[i] = paramPacketDataSerializer.c(32767);
    }
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.a.length);
    
    for (String str : this.a) {
      paramPacketDataSerializer.a(str);
    }
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  




  public String b()
  {
    return String.format("candidates='%s'", new Object[] { ArrayUtils.toString(this.a) });
  }
}
