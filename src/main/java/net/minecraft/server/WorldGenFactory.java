package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;








public class WorldGenFactory
{
  private static final Logger a = ;
  private static Map b = new HashMap();
  private static Map c = new HashMap();
  
  private static Map d = new HashMap();
  private static Map e = new HashMap();
  
  private static void b(Class paramClass, String paramString) {
    b.put(paramString, paramClass);
    c.put(paramClass, paramString);
  }
  
  static void a(Class paramClass, String paramString) {
    d.put(paramString, paramClass);
    e.put(paramClass, paramString);
  }
  
  static {
    b(WorldGenMineshaftStart.class, "Mineshaft");
    b(WorldGenVillageStart.class, "Village");
    b(WorldGenNetherStart.class, "Fortress");
    b(WorldGenStronghold2Start.class, "Stronghold");
    b(WorldGenLargeFeatureStart.class, "Temple");
    
    WorldGenMineshaftPieces.a();
    WorldGenVillagePieces.a();
    WorldGenNetherPieces.a();
    WorldGenStrongholdPieces.a();
    WorldGenRegistration.a();
  }
  
  public static String a(StructureStart paramStructureStart) {
    return (String)c.get(paramStructureStart.getClass());
  }
  
  public static String a(StructurePiece paramStructurePiece) {
    return (String)e.get(paramStructurePiece.getClass());
  }
  
  public static StructureStart a(NBTTagCompound paramNBTTagCompound, World paramWorld)
  {
    StructureStart localStructureStart = null;
    try
    {
      Class localClass = (Class)b.get(paramNBTTagCompound.getString("id"));
      if (localClass != null) localStructureStart = (StructureStart)localClass.newInstance();
    }
    catch (Exception localException) {
      a.warn("Failed Start with id " + paramNBTTagCompound.getString("id"));
      localException.printStackTrace();
    }
    if (localStructureStart != null) {
      localStructureStart.a(paramWorld, paramNBTTagCompound);
    } else {
      a.warn("Skipping Structure with id " + paramNBTTagCompound.getString("id"));
    }
    return localStructureStart;
  }
  
  public static StructurePiece b(NBTTagCompound paramNBTTagCompound, World paramWorld) {
    StructurePiece localStructurePiece = null;
    try
    {
      Class localClass = (Class)d.get(paramNBTTagCompound.getString("id"));
      if (localClass != null) localStructurePiece = (StructurePiece)localClass.newInstance();
    }
    catch (Exception localException) {
      a.warn("Failed Piece with id " + paramNBTTagCompound.getString("id"));
      localException.printStackTrace();
    }
    if (localStructurePiece != null) {
      localStructurePiece.a(paramWorld, paramNBTTagCompound);
    } else {
      a.warn("Skipping Piece with id " + paramNBTTagCompound.getString("id"));
    }
    return localStructurePiece;
  }
}
