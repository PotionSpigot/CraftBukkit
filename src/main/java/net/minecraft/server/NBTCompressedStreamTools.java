package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import net.minecraft.util.io.netty.buffer.ByteBufInputStream;
import org.spigotmc.LimitStream;
import org.spigotmc.SneakyThrow;

public class NBTCompressedStreamTools
{
  /* Error */
  public static NBTTagCompound a(InputStream inputstream)
  {
    // Byte code:
    //   0: new 17	java/io/DataInputStream
    //   3: dup
    //   4: new 19	java/io/BufferedInputStream
    //   7: dup
    //   8: new 21	java/util/zip/GZIPInputStream
    //   11: dup
    //   12: aload_0
    //   13: invokespecial 24	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   16: invokespecial 25	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   19: invokespecial 26	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   22: astore_1
    //   23: aload_1
    //   24: getstatic 31	net/minecraft/server/v1_7_R4/NBTReadLimiter:a	Lnet/minecraft/server/v1_7_R4/NBTReadLimiter;
    //   27: invokestatic 34	net/minecraft/server/v1_7_R4/NBTCompressedStreamTools:a	(Ljava/io/DataInput;Lnet/minecraft/server/v1_7_R4/NBTReadLimiter;)Lnet/minecraft/server/v1_7_R4/NBTTagCompound;
    //   30: astore_2
    //   31: aload_1
    //   32: invokevirtual 37	java/io/DataInputStream:close	()V
    //   35: goto +10 -> 45
    //   38: astore_3
    //   39: aload_1
    //   40: invokevirtual 37	java/io/DataInputStream:close	()V
    //   43: aload_3
    //   44: athrow
    //   45: aload_2
    //   46: areturn
    //   47: astore_1
    //   48: aload_1
    //   49: invokestatic 49	org/spigotmc/SneakyThrow:sneaky	(Ljava/lang/Throwable;)V
    //   52: aconst_null
    //   53: areturn
    // Line number table:
    //   Java source line #21	-> byte code offset #0
    //   Java source line #26	-> byte code offset #23
    //   Java source line #28	-> byte code offset #31
    //   Java source line #29	-> byte code offset #35
    //   Java source line #28	-> byte code offset #38
    //   Java source line #31	-> byte code offset #45
    //   Java source line #32	-> byte code offset #47
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	inputstream	InputStream
    //   22	18	1	datainputstream	DataInputStream
    //   47	2	1	ex	IOException
    //   30	2	2	nbttagcompound	NBTTagCompound
    //   45	1	2	nbttagcompound	NBTTagCompound
    //   38	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   23	31	38	finally
    //   0	46	47	java/io/IOException
  }
  
  /* Error */
  public static void a(NBTTagCompound nbttagcompound, java.io.OutputStream outputstream)
  {
    // Byte code:
    //   0: new 60	java/io/DataOutputStream
    //   3: dup
    //   4: new 62	java/io/BufferedOutputStream
    //   7: dup
    //   8: new 64	java/util/zip/GZIPOutputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 67	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   16: invokespecial 68	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   19: invokespecial 69	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   22: astore_2
    //   23: aload_0
    //   24: aload_2
    //   25: invokestatic 72	net/minecraft/server/v1_7_R4/NBTCompressedStreamTools:a	(Lnet/minecraft/server/v1_7_R4/NBTTagCompound;Ljava/io/DataOutput;)V
    //   28: aload_2
    //   29: invokevirtual 73	java/io/DataOutputStream:close	()V
    //   32: goto +10 -> 42
    //   35: astore_3
    //   36: aload_2
    //   37: invokevirtual 73	java/io/DataOutputStream:close	()V
    //   40: aload_3
    //   41: athrow
    //   42: goto +8 -> 50
    //   45: astore_2
    //   46: aload_2
    //   47: invokestatic 49	org/spigotmc/SneakyThrow:sneaky	(Ljava/lang/Throwable;)V
    //   50: return
    // Line number table:
    //   Java source line #37	-> byte code offset #0
    //   Java source line #40	-> byte code offset #23
    //   Java source line #42	-> byte code offset #28
    //   Java source line #43	-> byte code offset #32
    //   Java source line #42	-> byte code offset #35
    //   Java source line #44	-> byte code offset #42
    //   Java source line #45	-> byte code offset #50
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	nbttagcompound	NBTTagCompound
    //   0	51	1	outputstream	java.io.OutputStream
    //   22	15	2	dataoutputstream	java.io.DataOutputStream
    //   45	2	2	ex	IOException
    //   35	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   23	28	35	finally
    //   0	42	45	java/io/IOException
  }
  
  public static NBTTagCompound a(byte[] abyte, NBTReadLimiter nbtreadlimiter)
  {
    try
    {
      DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new LimitStream(new GZIPInputStream(new ByteArrayInputStream(abyte)), nbtreadlimiter)));
      

      try
      {
        nbttagcompound = a(datainputstream, nbtreadlimiter);
      } finally { NBTTagCompound nbttagcompound;
        datainputstream.close();
      }
      NBTTagCompound nbttagcompound;
      return nbttagcompound;
    } catch (IOException ex) { SneakyThrow.sneaky(ex); } return null;
  }
  
  /* Error */
  public static byte[] a(NBTTagCompound nbttagcompound)
  {
    // Byte code:
    //   0: new 97	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 98	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_1
    //   8: new 60	java/io/DataOutputStream
    //   11: dup
    //   12: new 64	java/util/zip/GZIPOutputStream
    //   15: dup
    //   16: aload_1
    //   17: invokespecial 67	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   20: invokespecial 69	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   23: astore_2
    //   24: aload_0
    //   25: aload_2
    //   26: invokestatic 72	net/minecraft/server/v1_7_R4/NBTCompressedStreamTools:a	(Lnet/minecraft/server/v1_7_R4/NBTTagCompound;Ljava/io/DataOutput;)V
    //   29: aload_2
    //   30: invokevirtual 73	java/io/DataOutputStream:close	()V
    //   33: goto +10 -> 43
    //   36: astore_3
    //   37: aload_2
    //   38: invokevirtual 73	java/io/DataOutputStream:close	()V
    //   41: aload_3
    //   42: athrow
    //   43: aload_1
    //   44: invokevirtual 102	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   47: areturn
    //   48: astore_1
    //   49: aload_1
    //   50: invokestatic 49	org/spigotmc/SneakyThrow:sneaky	(Ljava/lang/Throwable;)V
    //   53: aconst_null
    //   54: areturn
    // Line number table:
    //   Java source line #65	-> byte code offset #0
    //   Java source line #66	-> byte code offset #8
    //   Java source line #69	-> byte code offset #24
    //   Java source line #71	-> byte code offset #29
    //   Java source line #72	-> byte code offset #33
    //   Java source line #71	-> byte code offset #36
    //   Java source line #74	-> byte code offset #43
    //   Java source line #75	-> byte code offset #48
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	nbttagcompound	NBTTagCompound
    //   7	37	1	bytearrayoutputstream	java.io.ByteArrayOutputStream
    //   48	2	1	ex	IOException
    //   23	15	2	dataoutputstream	java.io.DataOutputStream
    //   36	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   24	29	36	finally
    //   0	47	48	java/io/IOException
  }
  
  public static NBTTagCompound a(DataInputStream datainputstream)
  {
    return a(datainputstream, NBTReadLimiter.a);
  }
  
  public static NBTTagCompound a(DataInput datainput, NBTReadLimiter nbtreadlimiter)
  {
    try {
      if ((datainput instanceof ByteBufInputStream))
      {
        datainput = new DataInputStream(new LimitStream((InputStream)datainput, nbtreadlimiter));
      }
      
      NBTBase nbtbase = a(datainput, 0, nbtreadlimiter);
      
      if ((nbtbase instanceof NBTTagCompound)) {
        return (NBTTagCompound)nbtbase;
      }
      throw new IOException("Root tag must be a named compound tag");
    } catch (IOException ex) {
      SneakyThrow.sneaky(ex); } return null;
  }
  
  public static void a(NBTTagCompound nbttagcompound, DataOutput dataoutput) {
    a(nbttagcompound, dataoutput);
  }
  
  private static void a(NBTBase nbtbase, DataOutput dataoutput) {
    try {
      dataoutput.writeByte(nbtbase.getTypeId());
      if (nbtbase.getTypeId() != 0) {
        dataoutput.writeUTF("");
        nbtbase.write(dataoutput);
      }
    } catch (IOException ex) { SneakyThrow.sneaky(ex);
    }
  }
  
  private static NBTBase a(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) {
    try { byte b0 = datainput.readByte();
      
      if (b0 == 0) {
        return new NBTTagEnd();
      }
      datainput.readUTF();
      NBTBase nbtbase = NBTBase.createTag(b0);
      try
      {
        nbtbase.load(datainput, i, nbtreadlimiter);
        return nbtbase;
      } catch (IOException ioexception) {
        CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");
        
        crashreportsystemdetails.a("Tag name", "[UNNAMED TAG]");
        crashreportsystemdetails.a("Tag type", Byte.valueOf(b0));
        throw new ReportedException(crashreport);
      }
      
      return null; } catch (IOException ex) { SneakyThrow.sneaky(ex);
    }
  }
}
