package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.com.google.common.collect.HashBiMap;
import net.minecraft.util.com.google.common.collect.Iterables;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.gnu.trove.map.TIntObjectMap;
import net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;








public enum EnumProtocol
{
  HANDSHAKING(-1), 
  

  PLAY(0), 
  


























































































  STATUS(1), 
  




  LOGIN(2);
  


  private static final TIntObjectMap e;
  

  private static final Map f;
  

  private final int g;
  
  private final BiMap h = HashBiMap.create();
  private final BiMap i = HashBiMap.create();
  

  private EnumProtocol(int paramInt1) { this.g = paramInt1; }
  
  protected EnumProtocol a(int paramInt, Class paramClass) {
    String str;
    if (this.h.containsKey(Integer.valueOf(paramInt))) {
      str = "Serverbound packet ID " + paramInt + " is already assigned to " + this.h.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass;
      LogManager.getLogger().fatal(str);
      throw new IllegalArgumentException(str);
    }
    if (this.h.containsValue(paramClass)) {
      str = "Serverbound packet " + paramClass + " is already assigned to ID " + this.h.inverse().get(paramClass) + "; cannot re-assign to " + paramInt;
      LogManager.getLogger().fatal(str);
      throw new IllegalArgumentException(str);
    }
    this.h.put(Integer.valueOf(paramInt), paramClass);
    return this;
  }
  
  protected EnumProtocol b(int paramInt, Class paramClass) { String str;
    if (this.i.containsKey(Integer.valueOf(paramInt))) {
      str = "Clientbound packet ID " + paramInt + " is already assigned to " + this.i.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass;
      LogManager.getLogger().fatal(str);
      throw new IllegalArgumentException(str);
    }
    if (this.i.containsValue(paramClass)) {
      str = "Clientbound packet " + paramClass + " is already assigned to ID " + this.i.inverse().get(paramClass) + "; cannot re-assign to " + paramInt;
      LogManager.getLogger().fatal(str);
      throw new IllegalArgumentException(str);
    }
    this.i.put(Integer.valueOf(paramInt), paramClass);
    return this;
  }
  
  public BiMap a() {
    return this.h;
  }
  
  public BiMap b() {
    return this.i;
  }
  
  public BiMap a(boolean paramBoolean) {
    return paramBoolean ? b() : a();
  }
  
  public BiMap b(boolean paramBoolean) {
    return paramBoolean ? a() : b();
  }
  
  public int c() {
    return this.g;
  }
  
  static
  {
    e = new TIntObjectHashMap();
    f = Maps.newHashMap();
    




























    EnumProtocol localEnumProtocol;
    




























    for (localEnumProtocol : values()) {
      e.put(localEnumProtocol.c(), localEnumProtocol);
      
      for (Class localClass : Iterables.concat(localEnumProtocol.b().values(), localEnumProtocol.a().values())) {
        if ((f.containsKey(localClass)) && (f.get(localClass) != localEnumProtocol)) {
          throw new Error("Packet " + localClass + " is already assigned to protocol " + f.get(localClass) + " - can't reassign to " + localEnumProtocol);
        }
        
        f.put(localClass, localEnumProtocol);
      }
    }
  }
  
  public static EnumProtocol a(int paramInt) {
    return (EnumProtocol)e.get(paramInt);
  }
  
  public static EnumProtocol a(Packet paramPacket) {
    return (EnumProtocol)f.get(paramPacket.getClass());
  }
}
