package net.minecraft.server;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.spigotmc.SpigotDebreakifier;

public class PacketPlayOutMapChunk extends Packet
{
  private int a;
  private int b;
  private int c;
  private int d;
  private byte[] e;
  private byte[] f;
  private boolean g;
  private int h;
  private static byte[] i = new byte[196864];
  
  private Chunk chunk;
  private int mask;
  
  public PacketPlayOutMapChunk() {}
  
  public PacketPlayOutMapChunk(Chunk chunk, boolean flag, int i, int version)
  {
    this.chunk = chunk;
    this.mask = i;
    this.a = chunk.locX;
    this.b = chunk.locZ;
    this.g = flag;
    ChunkMap chunkmap = chunk.getChunkMap(flag, i, version);
    
    this.d = chunkmap.c;
    this.c = chunkmap.b;
    
    this.f = chunkmap.a;
  }
  
  public static int c() {
    return 196864;
  }
  
  public void a(PacketDataSerializer packetdataserializer) throws IOException {
    this.a = packetdataserializer.readInt();
    this.b = packetdataserializer.readInt();
    this.g = packetdataserializer.readBoolean();
    this.c = packetdataserializer.readShort();
    this.d = packetdataserializer.readShort();
    this.h = packetdataserializer.readInt();
    if (i.length < this.h) {
      i = new byte[this.h];
    }
    
    packetdataserializer.readBytes(i, 0, this.h);
    int i = 0;
    


    for (int j = 0; j < 16; j++) {
      i += (this.c >> j & 0x1);
    }
    
    j = 12288 * i;
    if (this.g) {
      j += 256;
    }
    
    this.f = new byte[j];
    Inflater inflater = new Inflater();
    
    inflater.setInput(i, 0, this.h);
    try
    {
      inflater.inflate(this.f);
    } catch (DataFormatException dataformatexception) {
      throw new IOException("Bad compressed data format");
    } finally {
      inflater.end();
    }
  }
  
  /* Error */
  public void b(PacketDataSerializer packetdataserializer)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: getfield 38	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:a	I
    //   5: invokevirtual 125	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeInt	(I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   8: pop
    //   9: aload_1
    //   10: aload_0
    //   11: getfield 43	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:b	I
    //   14: invokevirtual 125	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeInt	(I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   17: pop
    //   18: aload_1
    //   19: aload_0
    //   20: getfield 45	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:g	Z
    //   23: invokevirtual 129	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeBoolean	(Z)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   26: pop
    //   27: aload_1
    //   28: aload_0
    //   29: getfield 57	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:c	I
    //   32: ldc -126
    //   34: iand
    //   35: i2s
    //   36: invokevirtual 133	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeShort	(I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   39: pop
    //   40: aload_1
    //   41: getfield 135	net/minecraft/server/v1_7_R4/PacketDataSerializer:version	I
    //   44: bipush 27
    //   46: if_icmpge +152 -> 198
    //   49: aload_0
    //   50: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   53: getfield 139	net/minecraft/server/v1_7_R4/Chunk:world	Lnet/minecraft/server/v1_7_R4/World;
    //   56: getfield 145	net/minecraft/server/v1_7_R4/World:spigotConfig	Lorg/spigotmc/SpigotWorldConfig;
    //   59: getfield 151	org/spigotmc/SpigotWorldConfig:antiXrayInstance	Lorg/spigotmc/AntiXray;
    //   62: aload_0
    //   63: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   66: getfield 36	net/minecraft/server/v1_7_R4/Chunk:locX	I
    //   69: aload_0
    //   70: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   73: getfield 41	net/minecraft/server/v1_7_R4/Chunk:locZ	I
    //   76: aload_0
    //   77: getfield 31	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:mask	I
    //   80: aload_0
    //   81: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   84: aload_0
    //   85: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   88: getfield 139	net/minecraft/server/v1_7_R4/Chunk:world	Lnet/minecraft/server/v1_7_R4/World;
    //   91: iconst_0
    //   92: invokevirtual 157	org/spigotmc/AntiXray:obfuscate	(III[BLnet/minecraft/server/v1_7_R4/World;Z)V
    //   95: new 159	java/util/zip/Deflater
    //   98: dup
    //   99: iconst_4
    //   100: invokespecial 162	java/util/zip/Deflater:<init>	(I)V
    //   103: astore_2
    //   104: aload_2
    //   105: aload_0
    //   106: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   109: iconst_0
    //   110: aload_0
    //   111: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   114: arraylength
    //   115: invokevirtual 163	java/util/zip/Deflater:setInput	([BII)V
    //   118: aload_2
    //   119: invokevirtual 166	java/util/zip/Deflater:finish	()V
    //   122: aload_0
    //   123: aload_0
    //   124: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   127: arraylength
    //   128: newarray <illegal type>
    //   130: putfield 168	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:e	[B
    //   133: aload_0
    //   134: aload_2
    //   135: aload_0
    //   136: getfield 168	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:e	[B
    //   139: invokevirtual 171	java/util/zip/Deflater:deflate	([B)I
    //   142: putfield 87	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:h	I
    //   145: aload_2
    //   146: invokevirtual 172	java/util/zip/Deflater:end	()V
    //   149: goto +10 -> 159
    //   152: astore_3
    //   153: aload_2
    //   154: invokevirtual 172	java/util/zip/Deflater:end	()V
    //   157: aload_3
    //   158: athrow
    //   159: aload_1
    //   160: aload_0
    //   161: getfield 55	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:d	I
    //   164: ldc -126
    //   166: iand
    //   167: i2s
    //   168: invokevirtual 133	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeShort	(I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   171: pop
    //   172: aload_1
    //   173: aload_0
    //   174: getfield 87	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:h	I
    //   177: invokevirtual 125	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeInt	(I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   180: pop
    //   181: aload_1
    //   182: aload_0
    //   183: getfield 168	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:e	[B
    //   186: iconst_0
    //   187: aload_0
    //   188: getfield 87	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:h	I
    //   191: invokevirtual 175	net/minecraft/server/v1_7_R4/PacketDataSerializer:writeBytes	([BII)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
    //   194: pop
    //   195: goto +57 -> 252
    //   198: aload_0
    //   199: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   202: getfield 139	net/minecraft/server/v1_7_R4/Chunk:world	Lnet/minecraft/server/v1_7_R4/World;
    //   205: getfield 145	net/minecraft/server/v1_7_R4/World:spigotConfig	Lorg/spigotmc/SpigotWorldConfig;
    //   208: getfield 151	org/spigotmc/SpigotWorldConfig:antiXrayInstance	Lorg/spigotmc/AntiXray;
    //   211: aload_0
    //   212: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   215: getfield 36	net/minecraft/server/v1_7_R4/Chunk:locX	I
    //   218: aload_0
    //   219: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   222: getfield 41	net/minecraft/server/v1_7_R4/Chunk:locZ	I
    //   225: aload_0
    //   226: getfield 31	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:mask	I
    //   229: aload_0
    //   230: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   233: aload_0
    //   234: getfield 29	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:chunk	Lnet/minecraft/server/v1_7_R4/Chunk;
    //   237: getfield 139	net/minecraft/server/v1_7_R4/Chunk:world	Lnet/minecraft/server/v1_7_R4/World;
    //   240: iconst_1
    //   241: invokevirtual 157	org/spigotmc/AntiXray:obfuscate	(III[BLnet/minecraft/server/v1_7_R4/World;Z)V
    //   244: aload_1
    //   245: aload_0
    //   246: getfield 61	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:f	[B
    //   249: invokestatic 178	net/minecraft/server/v1_7_R4/PacketPlayOutMapChunk:a	(Lnet/minecraft/util/io/netty/buffer/ByteBuf;[B)V
    //   252: return
    // Line number table:
    //   Java source line #84	-> byte code offset #0
    //   Java source line #85	-> byte code offset #9
    //   Java source line #86	-> byte code offset #18
    //   Java source line #87	-> byte code offset #27
    //   Java source line #89	-> byte code offset #40
    //   Java source line #91	-> byte code offset #49
    //   Java source line #92	-> byte code offset #95
    //   Java source line #94	-> byte code offset #104
    //   Java source line #95	-> byte code offset #118
    //   Java source line #96	-> byte code offset #122
    //   Java source line #97	-> byte code offset #133
    //   Java source line #99	-> byte code offset #145
    //   Java source line #100	-> byte code offset #149
    //   Java source line #99	-> byte code offset #152
    //   Java source line #101	-> byte code offset #159
    //   Java source line #102	-> byte code offset #172
    //   Java source line #103	-> byte code offset #181
    //   Java source line #104	-> byte code offset #195
    //   Java source line #106	-> byte code offset #198
    //   Java source line #107	-> byte code offset #244
    //   Java source line #110	-> byte code offset #252
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	253	0	this	PacketPlayOutMapChunk
    //   0	253	1	packetdataserializer	PacketDataSerializer
    //   103	51	2	deflater	java.util.zip.Deflater
    //   152	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   104	145	152	finally
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener)
  {
    packetplayoutlistener.a(this);
  }
  
  public String b() {
    return String.format("x=%d, z=%d, full=%b, sects=%d, add=%d, size=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b), Boolean.valueOf(this.g), Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.h) });
  }
  
  public static ChunkMap a(Chunk chunk, boolean flag, int i, int version)
  {
    int j = 0;
    ChunkSection[] achunksection = chunk.getSections();
    int k = 0;
    ChunkMap chunkmap = new ChunkMap();
    byte[] abyte = i;
    
    if (flag) {
      chunk.q = true;
    }
    


    for (int l = 0; l < achunksection.length; l++) {
      if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0)) {
        chunkmap.b |= 1 << l;
        if (achunksection[l].getExtendedIdArray() != null) {
          chunkmap.c |= 1 << l;
          k++;
        }
      }
    }
    
    if (version < 24)
    {
      for (l = 0; l < achunksection.length; l++)
      {
        if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0))
        {
          byte[] abyte1 = achunksection[l].getIdArray();
          
          System.arraycopy(abyte1, 0, abyte, j, abyte1.length);
          j += abyte1.length;
        }
      }
    }
    for (l = 0; l < achunksection.length; l++)
    {
      if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0))
      {
        byte[] abyte1 = achunksection[l].getIdArray();
        NibbleArray nibblearray = achunksection[l].getDataArray();
        for (int ind = 0; ind < abyte1.length; ind++)
        {
          int id = abyte1[ind] & 0xFF;
          int px = ind & 0xF;
          int py = ind >> 8 & 0xF;
          int pz = ind >> 4 & 0xF;
          int data = nibblearray.a(px, py, pz);
          if ((id == 90) && (data == 0))
          {
            Blocks.PORTAL.updateShape(chunk.world, (chunk.locX << 4) + px, (l << 4) + py, (chunk.locZ << 4) + pz);
          }
          else {
            data = SpigotDebreakifier.getCorrectedData(id, data);
          }
          char val = (char)(id << 4 | data);
          abyte[(j++)] = ((byte)(val & 0xFF));
          abyte[(j++)] = ((byte)(val >> '\b' & 0xFF));
        }
      }
    }
    



    if (version < 24)
    {
      for (l = 0; l < achunksection.length; l++)
      {
        if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0))
        {
          NibbleArray nibblearray = achunksection[l].getDataArray();
          System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
          j += nibblearray.a.length;
        }
      }
    }
    
    for (l = 0; l < achunksection.length; l++) {
      if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0)) {
        NibbleArray nibblearray = achunksection[l].getEmittedLightArray();
        System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
        j += nibblearray.a.length;
      }
    }
    
    if (!chunk.world.worldProvider.g) {
      for (l = 0; l < achunksection.length; l++) {
        if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && ((i & 1 << l) != 0)) {
          NibbleArray nibblearray = achunksection[l].getSkyLightArray();
          System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
          j += nibblearray.a.length;
        }
      }
    }
    
    if ((k > 0) && (version < 24)) {
      for (l = 0; l < achunksection.length; l++) {
        if ((achunksection[l] != null) && ((!flag) || (!achunksection[l].isEmpty())) && (achunksection[l].getExtendedIdArray() != null) && ((i & 1 << l) != 0)) {
          NibbleArray nibblearray = achunksection[l].getExtendedIdArray();
          System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
          j += nibblearray.a.length;
        }
      }
    }
    
    if (flag) {
      byte[] abyte2 = chunk.m();
      
      System.arraycopy(abyte2, 0, abyte, j, abyte2.length);
      j += abyte2.length;
    }
    
    chunkmap.a = new byte[j];
    System.arraycopy(abyte, 0, chunkmap.a, 0, j);
    return chunkmap;
  }
  

  public void handle(PacketListener packetlistener)
  {
    a((PacketPlayOutListener)packetlistener);
  }
}
