package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;






public class WorldNBTStorage
  implements IDataManager, IPlayerFileData
{
  private static final org.apache.logging.log4j.Logger a = ;
  private final File baseDir;
  private final File playerDir;
  private final File dataDir;
  private final long sessionId = MinecraftServer.ar();
  private final String f;
  private UUID uuid = null;
  
  public WorldNBTStorage(File file1, String s, boolean flag) {
    this.baseDir = new File(file1, s);
    this.baseDir.mkdirs();
    this.playerDir = new File(this.baseDir, "playerdata");
    this.dataDir = new File(this.baseDir, "data");
    this.dataDir.mkdirs();
    this.f = s;
    if (flag) {
      this.playerDir.mkdirs();
    }
    
    h();
  }
  
  /* Error */
  private void h()
  {
    // Byte code:
    //   0: new 38	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 43	net/minecraft/server/v1_7_R4/WorldNBTStorage:baseDir	Ljava/io/File;
    //   8: ldc 72
    //   10: invokespecial 41	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   13: astore_1
    //   14: new 74	java/io/DataOutputStream
    //   17: dup
    //   18: new 76	java/io/FileOutputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 79	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   26: invokespecial 82	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   29: astore_2
    //   30: aload_2
    //   31: aload_0
    //   32: getfield 34	net/minecraft/server/v1_7_R4/WorldNBTStorage:sessionId	J
    //   35: invokevirtual 86	java/io/DataOutputStream:writeLong	(J)V
    //   38: aload_2
    //   39: invokevirtual 89	java/io/DataOutputStream:close	()V
    //   42: goto +10 -> 52
    //   45: astore_3
    //   46: aload_2
    //   47: invokevirtual 89	java/io/DataOutputStream:close	()V
    //   50: aload_3
    //   51: athrow
    //   52: goto +43 -> 95
    //   55: astore_1
    //   56: aload_1
    //   57: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   60: new 96	java/lang/RuntimeException
    //   63: dup
    //   64: new 98	java/lang/StringBuilder
    //   67: dup
    //   68: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   71: ldc 101
    //   73: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_0
    //   77: getfield 43	net/minecraft/server/v1_7_R4/WorldNBTStorage:baseDir	Ljava/io/File;
    //   80: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   83: ldc 110
    //   85: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokespecial 117	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   94: athrow
    //   95: return
    // Line number table:
    //   Java source line #47	-> byte code offset #0
    //   Java source line #48	-> byte code offset #14
    //   Java source line #51	-> byte code offset #30
    //   Java source line #53	-> byte code offset #38
    //   Java source line #54	-> byte code offset #42
    //   Java source line #53	-> byte code offset #45
    //   Java source line #58	-> byte code offset #52
    //   Java source line #55	-> byte code offset #55
    //   Java source line #56	-> byte code offset #56
    //   Java source line #57	-> byte code offset #60
    //   Java source line #59	-> byte code offset #95
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	WorldNBTStorage
    //   13	10	1	file1	File
    //   55	2	1	ioexception	IOException
    //   29	18	2	dataoutputstream	DataOutputStream
    //   45	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   30	38	45	finally
    //   0	52	55	java/io/IOException
  }
  
  public File getDirectory()
  {
    return this.baseDir;
  }
  
  /* Error */
  public void checkSession()
    throws ExceptionWorldConflict
  {
    // Byte code:
    //   0: new 38	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 43	net/minecraft/server/v1_7_R4/WorldNBTStorage:baseDir	Ljava/io/File;
    //   8: ldc 72
    //   10: invokespecial 41	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   13: astore_1
    //   14: new 128	java/io/DataInputStream
    //   17: dup
    //   18: new 130	java/io/FileInputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 131	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   26: invokespecial 134	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   29: astore_2
    //   30: aload_2
    //   31: invokevirtual 137	java/io/DataInputStream:readLong	()J
    //   34: aload_0
    //   35: getfield 34	net/minecraft/server/v1_7_R4/WorldNBTStorage:sessionId	J
    //   38: lcmp
    //   39: ifeq +38 -> 77
    //   42: new 126	net/minecraft/server/v1_7_R4/ExceptionWorldConflict
    //   45: dup
    //   46: new 98	java/lang/StringBuilder
    //   49: dup
    //   50: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   53: ldc -117
    //   55: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: aload_0
    //   59: getfield 43	net/minecraft/server/v1_7_R4/WorldNBTStorage:baseDir	Ljava/io/File;
    //   62: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   65: ldc -115
    //   67: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: invokespecial 142	net/minecraft/server/v1_7_R4/ExceptionWorldConflict:<init>	(Ljava/lang/String;)V
    //   76: athrow
    //   77: aload_2
    //   78: invokevirtual 143	java/io/DataInputStream:close	()V
    //   81: goto +10 -> 91
    //   84: astore_3
    //   85: aload_2
    //   86: invokevirtual 143	java/io/DataInputStream:close	()V
    //   89: aload_3
    //   90: athrow
    //   91: goto +39 -> 130
    //   94: astore_1
    //   95: new 126	net/minecraft/server/v1_7_R4/ExceptionWorldConflict
    //   98: dup
    //   99: new 98	java/lang/StringBuilder
    //   102: dup
    //   103: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   106: ldc 101
    //   108: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: aload_0
    //   112: getfield 43	net/minecraft/server/v1_7_R4/WorldNBTStorage:baseDir	Ljava/io/File;
    //   115: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   118: ldc 110
    //   120: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: invokespecial 142	net/minecraft/server/v1_7_R4/ExceptionWorldConflict:<init>	(Ljava/lang/String;)V
    //   129: athrow
    //   130: return
    // Line number table:
    //   Java source line #67	-> byte code offset #0
    //   Java source line #68	-> byte code offset #14
    //   Java source line #71	-> byte code offset #30
    //   Java source line #72	-> byte code offset #42
    //   Java source line #75	-> byte code offset #77
    //   Java source line #76	-> byte code offset #81
    //   Java source line #75	-> byte code offset #84
    //   Java source line #79	-> byte code offset #91
    //   Java source line #77	-> byte code offset #94
    //   Java source line #78	-> byte code offset #95
    //   Java source line #80	-> byte code offset #130
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	this	WorldNBTStorage
    //   13	10	1	file1	File
    //   94	2	1	ioexception	IOException
    //   29	57	2	datainputstream	DataInputStream
    //   84	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   30	77	84	finally
    //   0	91	94	java/io/IOException
  }
  
  public IChunkLoader createChunkLoader(WorldProvider worldprovider)
  {
    throw new RuntimeException("Old Chunk Storage is no longer supported.");
  }
  
  public WorldData getWorldData() {
    File file1 = new File(this.baseDir, "level.dat");
    


    if (file1.exists()) {
      try {
        NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
        return new WorldData(nbttagcompound1);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    
    file1 = new File(this.baseDir, "level.dat_old");
    if (file1.exists()) {
      try {
        NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
        return new WorldData(nbttagcompound1);
      } catch (Exception exception1) {
        exception1.printStackTrace();
      }
    }
    
    return null;
  }
  
  public void saveWorldData(WorldData worlddata, NBTTagCompound nbttagcompound) {
    NBTTagCompound nbttagcompound1 = worlddata.a(nbttagcompound);
    NBTTagCompound nbttagcompound2 = new NBTTagCompound();
    
    nbttagcompound2.set("Data", nbttagcompound1);
    try
    {
      File file1 = new File(this.baseDir, "level.dat_new");
      File file2 = new File(this.baseDir, "level.dat_old");
      File file3 = new File(this.baseDir, "level.dat");
      
      NBTCompressedStreamTools.a(nbttagcompound2, new FileOutputStream(file1));
      if (file2.exists()) {
        file2.delete();
      }
      
      file3.renameTo(file2);
      if (file3.exists()) {
        file3.delete();
      }
      
      file1.renameTo(file3);
      if (file1.exists()) {
        file1.delete();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
  
  public void saveWorldData(WorldData worlddata) {
    NBTTagCompound nbttagcompound = worlddata.a();
    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
    
    nbttagcompound1.set("Data", nbttagcompound);
    try
    {
      File file1 = new File(this.baseDir, "level.dat_new");
      File file2 = new File(this.baseDir, "level.dat_old");
      File file3 = new File(this.baseDir, "level.dat");
      
      NBTCompressedStreamTools.a(nbttagcompound1, new FileOutputStream(file1));
      if (file2.exists()) {
        file2.delete();
      }
      
      file3.renameTo(file2);
      if (file3.exists()) {
        file3.delete();
      }
      
      file1.renameTo(file3);
      if (file1.exists()) {
        file1.delete();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
  
  public void save(EntityHuman entityhuman) {
    try {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      
      entityhuman.e(nbttagcompound);
      File file1 = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat.tmp");
      File file2 = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat");
      
      NBTCompressedStreamTools.a(nbttagcompound, new FileOutputStream(file1));
      if (file2.exists()) {
        file2.delete();
      }
      
      file1.renameTo(file2);
    } catch (Exception exception) {
      a.warn("Failed to save player data for " + entityhuman.getName());
    }
  }
  
  public NBTTagCompound load(EntityHuman entityhuman) {
    NBTTagCompound nbttagcompound = null;
    try
    {
      File file1 = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat");
      
      boolean usingWrongFile = false;
      if (!file1.exists())
      {
        file1 = new File(this.playerDir, UUID.nameUUIDFromBytes(new StringBuilder().append("OfflinePlayer:").append(entityhuman.getName()).toString().getBytes("UTF-8")).toString() + ".dat");
        if (file1.exists())
        {
          usingWrongFile = true;
          Bukkit.getServer().getLogger().warning("Using offline mode UUID file for player " + entityhuman.getName() + " as it is the only copy we can find.");
        }
      }
      

      if ((file1.exists()) && (file1.isFile())) {
        nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
      }
      
      if (usingWrongFile)
      {
        file1.renameTo(new File(file1.getPath() + ".offline-read"));
      }
    }
    catch (Exception exception) {
      a.warn("Failed to load player data for " + entityhuman.getName());
    }
    
    if (nbttagcompound != null)
    {
      if ((entityhuman instanceof EntityPlayer)) {
        CraftPlayer player = (CraftPlayer)entityhuman.bukkitEntity;
        
        long modified = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat").lastModified();
        if (modified < player.getFirstPlayed()) {
          player.setFirstPlayed(modified);
        }
      }
      

      entityhuman.f(nbttagcompound);
    }
    
    return nbttagcompound;
  }
  
  public NBTTagCompound getPlayerData(String s) {
    try {
      File file1 = new File(this.playerDir, s + ".dat");
      
      if (file1.exists()) {
        return NBTCompressedStreamTools.a(new FileInputStream(file1));
      }
    } catch (Exception exception) {
      a.warn("Failed to load player data for " + s);
    }
    
    return null;
  }
  
  public IPlayerFileData getPlayerFileData() {
    return this;
  }
  
  public String[] getSeenPlayers() {
    String[] astring = this.playerDir.list();
    
    for (int i = 0; i < astring.length; i++) {
      if (astring[i].endsWith(".dat")) {
        astring[i] = astring[i].substring(0, astring[i].length() - 4);
      }
    }
    
    return astring;
  }
  
  public void a() {}
  
  public File getDataFile(String s) {
    return new File(this.dataDir, s + ".dat");
  }
  
  public String g() {
    return this.f;
  }
  
  public UUID getUUID()
  {
    if (this.uuid != null) return this.uuid;
    File file1 = new File(this.baseDir, "uid.dat");
    if (file1.exists()) {
      DataInputStream dis = null;
      try {
        dis = new DataInputStream(new FileInputStream(file1));
        return this.uuid = new UUID(dis.readLong(), dis.readLong());
      } catch (IOException ex) {
        a.warn("Failed to read " + file1 + ", generating new random UUID", ex);
      } finally {
        if (dis != null) {
          try {
            dis.close();
          }
          catch (IOException localIOException3) {}
        }
      }
    }
    
    this.uuid = UUID.randomUUID();
    DataOutputStream dos = null;
    try {
      dos = new DataOutputStream(new FileOutputStream(file1));
      dos.writeLong(this.uuid.getMostSignificantBits());
      dos.writeLong(this.uuid.getLeastSignificantBits());
      










      return this.uuid;
    }
    catch (IOException ex)
    {
      a.warn("Failed to write " + file1, ex);
    } finally {
      if (dos != null) {
        try {
          dos.close();
        }
        catch (IOException localIOException6) {}
      }
    }
  }
  

  public File getPlayerDir()
  {
    return this.playerDir;
  }
}
