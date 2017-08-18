package net.minecraft.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import net.minecraft.util.gnu.trove.map.TIntObjectMap;
import net.minecraft.util.gnu.trove.map.TObjectIntMap;
import org.spigotmc.ProtocolData.DualByte;
import org.spigotmc.ProtocolData.DualInt;

public class DataWatcher
{
  private final Entity a;
  private boolean b = true;
  
  private static final TObjectIntMap classToId = new net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap(10, 0.5F, -1);
  private final TIntObjectMap dataValues = new net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap(10, 0.5F, -1);
  
  private static final java.util.Map c = net.minecraft.util.gnu.trove.TDecorators.wrap(classToId);
  private final java.util.Map d = net.minecraft.util.gnu.trove.TDecorators.wrap(this.dataValues);
  
  private boolean e;
  private ReadWriteLock f = new java.util.concurrent.locks.ReentrantReadWriteLock();
  
  public DataWatcher(Entity entity) {
    this.a = entity;
  }
  
  public void a(int i, Object object) {
    int integer = classToId.get(object.getClass());
    

    if (((object instanceof org.spigotmc.ProtocolData.ByteShort)) || ((object instanceof ProtocolData.DualByte)) || ((object instanceof org.spigotmc.ProtocolData.HiddenByte)))
    {


      integer = classToId.get(Byte.class);
    }
    if (((object instanceof org.spigotmc.ProtocolData.IntByte)) || ((object instanceof ProtocolData.DualInt)))
    {
      integer = classToId.get(Integer.class);
    }
    

    if (integer == -1)
      throw new IllegalArgumentException("Unknown data type: " + object.getClass());
    if (i > 31)
      throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 31 + ")");
    if (this.dataValues.containsKey(i)) {
      throw new IllegalArgumentException("Duplicate id value for " + i + "!");
    }
    WatchableObject watchableobject = new WatchableObject(integer, i, object);
    
    this.f.writeLock().lock();
    this.dataValues.put(i, watchableobject);
    this.f.writeLock().unlock();
    this.b = false;
  }
  
  public void add(int i, int j)
  {
    WatchableObject watchableobject = new WatchableObject(j, i, null);
    
    this.f.writeLock().lock();
    this.dataValues.put(i, watchableobject);
    this.f.writeLock().unlock();
    this.b = false;
  }
  
  public byte getByte(int i) {
    return ((Number)i(i).b()).byteValue();
  }
  
  public short getShort(int i) {
    return ((Number)i(i).b()).shortValue();
  }
  
  public int getInt(int i) {
    return ((Number)i(i).b()).intValue();
  }
  
  public float getFloat(int i) {
    return ((Number)i(i).b()).floatValue();
  }
  
  public String getString(int i) {
    return (String)i(i).b();
  }
  
  public ItemStack getItemStack(int i) {
    return (ItemStack)i(i).b();
  }
  
  public ProtocolData.DualByte getDualByte(int i)
  {
    return (ProtocolData.DualByte)i(i).b();
  }
  
  public org.spigotmc.ProtocolData.IntByte getIntByte(int i) { return (org.spigotmc.ProtocolData.IntByte)i(i).b(); }
  
  public ProtocolData.DualInt getDualInt(int i) {
    return (ProtocolData.DualInt)i(i).b();
  }
  
  private WatchableObject i(int i)
  {
    this.f.readLock().lock();
    

    try
    {
      watchableobject = (WatchableObject)this.dataValues.get(i);
    } catch (Throwable throwable) { WatchableObject watchableobject;
      CrashReport crashreport = CrashReport.a(throwable, "Getting synched entity data");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Synched entity data");
      
      crashreportsystemdetails.a("Data ID", Integer.valueOf(i));
      throw new ReportedException(crashreport);
    }
    WatchableObject watchableobject;
    this.f.readLock().unlock();
    return watchableobject;
  }
  
  public void watch(int i, Object object) {
    WatchableObject watchableobject = i(i);
    
    if (net.minecraft.util.org.apache.commons.lang3.ObjectUtils.notEqual(object, watchableobject.b())) {
      watchableobject.a(object);
      this.a.i(i);
      watchableobject.a(true);
      this.e = true;
    }
  }
  
  public void update(int i) {
    WatchableObject.a(i(i), true);
    this.e = true;
  }
  
  public boolean a() {
    return this.e;
  }
  
  public static void a(List list, PacketDataSerializer packetdataserializer)
  {
    a(list, packetdataserializer, 5);
  }
  
  public static void a(List list, PacketDataSerializer packetdataserializer, int version)
  {
    if (list != null) {
      Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        WatchableObject watchableobject = (WatchableObject)iterator.next();
        
        a(packetdataserializer, watchableobject, version);
      }
    }
    
    packetdataserializer.writeByte(127);
  }
  
  public List b() {
    ArrayList arraylist = null;
    
    if (this.e) {
      this.f.readLock().lock();
      Iterator iterator = this.dataValues.valueCollection().iterator();
      
      while (iterator.hasNext()) {
        WatchableObject watchableobject = (WatchableObject)iterator.next();
        
        if (watchableobject.d()) {
          watchableobject.a(false);
          if (arraylist == null) {
            arraylist = new ArrayList();
          }
          

          if ((watchableobject.b() instanceof ItemStack))
          {



            watchableobject = new WatchableObject(watchableobject.c(), watchableobject.a(), ((ItemStack)watchableobject.b()).cloneItemStack());
          }
          


          arraylist.add(watchableobject);
        }
      }
      
      this.f.readLock().unlock();
    }
    
    this.e = false;
    return arraylist;
  }
  
  public void a(PacketDataSerializer packetdataserializer)
  {
    a(packetdataserializer, 5);
  }
  
  public void a(PacketDataSerializer packetdataserializer, int version)
  {
    this.f.readLock().lock();
    Iterator iterator = this.dataValues.valueCollection().iterator();
    
    while (iterator.hasNext()) {
      WatchableObject watchableobject = (WatchableObject)iterator.next();
      
      a(packetdataserializer, watchableobject, version);
    }
    
    this.f.readLock().unlock();
    packetdataserializer.writeByte(127);
  }
  
  public List c() {
    ArrayList arraylist = new ArrayList();
    
    this.f.readLock().lock();
    
    arraylist.addAll(this.dataValues.valueCollection());
    
    for (int i = 0; i < arraylist.size(); i++)
    {
      WatchableObject watchableobject = (WatchableObject)arraylist.get(i);
      if ((watchableobject.b() instanceof ItemStack))
      {



        watchableobject = new WatchableObject(watchableobject.c(), watchableobject.a(), ((ItemStack)watchableobject.b()).cloneItemStack());
        
        arraylist.set(i, watchableobject);
      }
    }
    

    this.f.readLock().unlock();
    return arraylist;
  }
  
  private static void a(PacketDataSerializer packetdataserializer, WatchableObject watchableobject, int version)
  {
    int type = watchableobject.c();
    if (((watchableobject.b() instanceof org.spigotmc.ProtocolData.ByteShort)) && (version >= 16)) {
      type = 1;
    }
    if (((watchableobject.b() instanceof org.spigotmc.ProtocolData.IntByte)) && (version >= 28)) {
      type = 0;
    }
    if ((version < 16) && ((watchableobject.b() instanceof org.spigotmc.ProtocolData.HiddenByte))) { return;
    }
    int i = (type << 5 | watchableobject.a() & 0x1F) & 0xFF;
    
    packetdataserializer.writeByte(i);
    switch (type) {
    case 0: 
      if ((watchableobject.b() instanceof ProtocolData.DualByte))
      {
        ProtocolData.DualByte dualByte = (ProtocolData.DualByte)watchableobject.b();
        packetdataserializer.writeByte(version >= 16 ? dualByte.value2 : dualByte.value);
      }
      else {
        packetdataserializer.writeByte(((Number)watchableobject.b()).byteValue());
      }
      break;
    
    case 1: 
      packetdataserializer.writeShort(((Number)watchableobject.b()).shortValue());
      break;
    
    case 2: 
      int val = ((Number)watchableobject.b()).intValue();
      if (((watchableobject.b() instanceof ProtocolData.DualInt)) && (version >= 46)) {
        val = ((ProtocolData.DualInt)watchableobject.b()).value2;
      }
      packetdataserializer.writeInt(val);
      break;
    
    case 3: 
      packetdataserializer.writeFloat(((Number)watchableobject.b()).floatValue());
      break;
    case 4: 
      try
      {
        packetdataserializer.a((String)watchableobject.b());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    

    case 5: 
      ItemStack itemstack = (ItemStack)watchableobject.b();
      
      packetdataserializer.a(itemstack);
      break;
    
    case 6: 
      ChunkCoordinates chunkcoordinates = (ChunkCoordinates)watchableobject.b();
      
      packetdataserializer.writeInt(chunkcoordinates.x);
      packetdataserializer.writeInt(chunkcoordinates.y);
      packetdataserializer.writeInt(chunkcoordinates.z);
    }
  }
  
  public static List b(PacketDataSerializer packetdataserializer)
  {
    ArrayList arraylist = null;
    
    for (byte b0 = packetdataserializer.readByte(); b0 != Byte.MAX_VALUE; b0 = packetdataserializer.readByte()) {
      if (arraylist == null) {
        arraylist = new ArrayList();
      }
      
      int i = (b0 & 0xE0) >> 5;
      int j = b0 & 0x1F;
      WatchableObject watchableobject = null;
      
      switch (i) {
      case 0: 
        watchableobject = new WatchableObject(i, j, Byte.valueOf(packetdataserializer.readByte()));
        break;
      
      case 1: 
        watchableobject = new WatchableObject(i, j, Short.valueOf(packetdataserializer.readShort()));
        break;
      
      case 2: 
        watchableobject = new WatchableObject(i, j, Integer.valueOf(packetdataserializer.readInt()));
        break;
      
      case 3: 
        watchableobject = new WatchableObject(i, j, Float.valueOf(packetdataserializer.readFloat()));
        break;
      case 4: 
        try
        {
          watchableobject = new WatchableObject(i, j, packetdataserializer.c(32767));
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      

      case 5: 
        watchableobject = new WatchableObject(i, j, packetdataserializer.c());
        break;
      
      case 6: 
        int k = packetdataserializer.readInt();
        int l = packetdataserializer.readInt();
        int i1 = packetdataserializer.readInt();
        
        watchableobject = new WatchableObject(i, j, new ChunkCoordinates(k, l, i1));
      }
      
      arraylist.add(watchableobject);
    }
    
    return arraylist;
  }
  
  public boolean d() {
    return this.b;
  }
  
  public void e() {
    this.e = false;
  }
  
  static
  {
    classToId.put(Byte.class, 0);
    classToId.put(Short.class, 1);
    classToId.put(Integer.class, 2);
    classToId.put(Float.class, 3);
    classToId.put(String.class, 4);
    classToId.put(ItemStack.class, 5);
    classToId.put(ChunkCoordinates.class, 6);
  }
}
