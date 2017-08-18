package net.minecraft.server;

import java.io.IOException;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class Packet
{
  private static final Logger a = ;
  public final long timestamp = System.currentTimeMillis();
  
  public static Packet a(BiMap bimap, int i)
  {
    try
    {
      Class oclass = (Class)bimap.get(Integer.valueOf(i));
      
      return oclass == null ? null : (Packet)oclass.newInstance();
    } catch (Exception exception) {
      a.error("Couldn't create packet " + i, exception); }
    return null;
  }
  

  public static void a(ByteBuf bytebuf, byte[] abyte)
  {
    if ((bytebuf instanceof PacketDataSerializer))
    {
      PacketDataSerializer packetDataSerializer = (PacketDataSerializer)bytebuf;
      if (packetDataSerializer.version >= 20) {
        packetDataSerializer.b(abyte.length);
      } else {
        bytebuf.writeShort(abyte.length);
      }
    }
    else {
      bytebuf.writeShort(abyte.length);
    }
    
    bytebuf.writeBytes(abyte);
  }
  
  public static byte[] a(ByteBuf bytebuf) throws IOException
  {
    short short1 = 0;
    if ((bytebuf instanceof PacketDataSerializer))
    {
      PacketDataSerializer packetDataSerializer = (PacketDataSerializer)bytebuf;
      if (packetDataSerializer.version >= 20) {
        short1 = (short)packetDataSerializer.a();
      } else {
        short1 = bytebuf.readShort();
      }
    }
    else {
      short1 = bytebuf.readShort();
    }
    

    if (short1 < 0) {
      throw new IOException("Key was smaller than nothing!  Weird key!");
    }
    byte[] abyte = new byte[short1];
    
    bytebuf.readBytes(abyte);
    return abyte;
  }
  
  public abstract void a(PacketDataSerializer paramPacketDataSerializer) throws IOException;
  
  public abstract void b(PacketDataSerializer paramPacketDataSerializer) throws IOException;
  
  public abstract void handle(PacketListener paramPacketListener);
  
  public boolean a()
  {
    return false;
  }
  
  public String toString() {
    return getClass().getSimpleName();
  }
  
  public String b() {
    return "";
  }
}
