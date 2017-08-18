package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotDebreakifier;

public class PacketPlayOutMultiBlockChange extends Packet
{
  private static final Logger a = ;
  
  private ChunkCoordIntPair b;
  
  private byte[] c;
  
  private int d;
  private short[] ashort;
  private int[] blocks;
  private Chunk chunk;
  
  public PacketPlayOutMultiBlockChange() {}
  
  public PacketPlayOutMultiBlockChange(int i, short[] ashort, Chunk chunk)
  {
    this.ashort = new short[ashort.length];
    System.arraycopy(ashort, 0, this.ashort, 0, ashort.length);
    
    this.chunk = chunk;
    
    this.b = new ChunkCoordIntPair(chunk.locX, chunk.locZ);
    this.d = i;
    int j = 4 * i;
    
    try
    {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(j);
      DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
      

      this.blocks = new int[i];
      for (int k = 0; k < i; k++) {
        int l = ashort[k] >> 12 & 0xF;
        int i1 = ashort[k] >> 8 & 0xF;
        int j1 = ashort[k] & 0xFF;
        
        dataoutputstream.writeShort(ashort[k]);
        int blockId = Block.getId(chunk.getType(l, j1, i1));
        int data = chunk.getData(l, j1, i1);
        data = SpigotDebreakifier.getCorrectedData(blockId, data);
        int id = (blockId & 0xFFF) << 4 | data & 0xF;
        dataoutputstream.writeShort((short)id);
        this.blocks[k] = id;
      }
      

      this.c = bytearrayoutputstream.toByteArray();
      if (this.c.length != j) {
        throw new RuntimeException("Expected length " + j + " doesn't match received length " + this.c.length);
      }
    } catch (IOException ioexception) {
      a.error("Couldn't create bulk block update packet", ioexception);
      this.c = null;
    }
  }
  
  public void a(PacketDataSerializer packetdataserializer) {
    this.b = new ChunkCoordIntPair(packetdataserializer.readInt(), packetdataserializer.readInt());
    this.d = (packetdataserializer.readShort() & 0xFFFF);
    int i = packetdataserializer.readInt();
    
    if (i > 0) {
      this.c = new byte[i];
      packetdataserializer.readBytes(this.c);
    }
  }
  
  public void b(PacketDataSerializer packetdataserializer)
  {
    if (packetdataserializer.version < 25)
    {
      packetdataserializer.writeInt(this.b.x);
      packetdataserializer.writeInt(this.b.z);
      packetdataserializer.writeShort((short)this.d);
      if (this.c != null)
      {
        packetdataserializer.writeInt(this.c.length);
        packetdataserializer.writeBytes(this.c);
      }
      else {
        packetdataserializer.writeInt(0);
      }
    } else {
      packetdataserializer.writeInt(this.b.x);
      packetdataserializer.writeInt(this.b.z);
      packetdataserializer.b(this.d);
      for (int i = 0; i < this.d; i++)
      {
        packetdataserializer.writeShort(this.ashort[i]);
        packetdataserializer.b(this.blocks[i]);
      }
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    return String.format("xc=%d, zc=%d, count=%d", new Object[] { Integer.valueOf(this.b.x), Integer.valueOf(this.b.z), Integer.valueOf(this.d) });
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
