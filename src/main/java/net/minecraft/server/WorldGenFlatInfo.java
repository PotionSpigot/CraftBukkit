package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;












public class WorldGenFlatInfo
{
  private final List layers = new ArrayList();
  private final Map structures = new HashMap();
  private int c;
  
  public int a() {
    return this.c;
  }
  
  public void a(int paramInt) {
    this.c = paramInt;
  }
  
  public Map b() {
    return this.structures;
  }
  
  public List c() {
    return this.layers;
  }
  
  public void d() {
    int i = 0;
    
    for (WorldGenFlatLayerInfo localWorldGenFlatLayerInfo : this.layers) {
      localWorldGenFlatLayerInfo.c(i);
      i += localWorldGenFlatLayerInfo.a();
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    
    localStringBuilder.append(2);
    localStringBuilder.append(";");
    
    for (int i = 0; i < this.layers.size(); i++) {
      if (i > 0) localStringBuilder.append(",");
      localStringBuilder.append(((WorldGenFlatLayerInfo)this.layers.get(i)).toString());
    }
    
    localStringBuilder.append(";");
    localStringBuilder.append(this.c);
    
    if (!this.structures.isEmpty()) {
      localStringBuilder.append(";");
      i = 0;
      
      for (Entry localEntry1 : this.structures.entrySet()) {
        if (i++ > 0) localStringBuilder.append(",");
        localStringBuilder.append(((String)localEntry1.getKey()).toLowerCase());
        
        Map localMap = (Map)localEntry1.getValue();
        if (!localMap.isEmpty()) {
          localStringBuilder.append("(");
          int j = 0;
          
          for (Entry localEntry2 : localMap.entrySet()) {
            if (j++ > 0) localStringBuilder.append(" ");
            localStringBuilder.append((String)localEntry2.getKey());
            localStringBuilder.append("=");
            localStringBuilder.append((String)localEntry2.getValue());
          }
          
          localStringBuilder.append(")");
        }
      }
    } else {
      localStringBuilder.append(";");
    }
    
    return localStringBuilder.toString();
  }
  
  private static WorldGenFlatLayerInfo a(String paramString, int paramInt) {
    String[] arrayOfString = paramString.split("x", 2);
    int i = 1;
    
    int j = 0;
    
    if (arrayOfString.length == 2) {
      try {
        i = Integer.parseInt(arrayOfString[0]);
        if (paramInt + i >= 256) i = 256 - paramInt;
        if (i < 0) i = 0;
      } catch (Throwable localThrowable1) {
        return null;
      }
    }
    int k;
    try {
      String str = arrayOfString[(arrayOfString.length - 1)];
      arrayOfString = str.split(":", 2);
      k = Integer.parseInt(arrayOfString[0]);
      if (arrayOfString.length > 1) { j = Integer.parseInt(arrayOfString[1]);
      }
      if (Block.getById(k) == Blocks.AIR) {
        k = 0;
        j = 0;
      }
      
      if ((j < 0) || (j > 15)) j = 0;
    } catch (Throwable localThrowable2) {
      return null;
    }
    
    WorldGenFlatLayerInfo localWorldGenFlatLayerInfo = new WorldGenFlatLayerInfo(i, Block.getById(k), j);
    localWorldGenFlatLayerInfo.c(paramInt);
    return localWorldGenFlatLayerInfo;
  }
  
  private static List b(String paramString) {
    if ((paramString == null) || (paramString.length() < 1)) { return null;
    }
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString1 = paramString.split(",");
    int i = 0;
    
    for (String str : arrayOfString1) {
      WorldGenFlatLayerInfo localWorldGenFlatLayerInfo = a(str, i);
      if (localWorldGenFlatLayerInfo == null) return null;
      localArrayList.add(localWorldGenFlatLayerInfo);
      i += localWorldGenFlatLayerInfo.a();
    }
    
    return localArrayList;
  }
  
  public static WorldGenFlatInfo a(String paramString) {
    if (paramString == null) return e();
    String[] arrayOfString1 = paramString.split(";", -1);
    int i = arrayOfString1.length == 1 ? 0 : MathHelper.a(arrayOfString1[0], 0);
    if ((i < 0) || (i > 2)) { return e();
    }
    WorldGenFlatInfo localWorldGenFlatInfo = new WorldGenFlatInfo();
    int j = arrayOfString1.length == 1 ? 0 : 1;
    List localList = b(arrayOfString1[(j++)]);
    
    if ((localList == null) || (localList.isEmpty())) {
      return e();
    }
    
    localWorldGenFlatInfo.c().addAll(localList);
    localWorldGenFlatInfo.d();
    
    int k = BiomeBase.PLAINS.id;
    if ((i > 0) && (arrayOfString1.length > j)) k = MathHelper.a(arrayOfString1[(j++)], k);
    localWorldGenFlatInfo.a(k);
    
    if ((i > 0) && (arrayOfString1.length > j)) {
      String[] arrayOfString2 = arrayOfString1[(j++)].toLowerCase().split(",");
      
      for (String str : arrayOfString2) {
        String[] arrayOfString4 = str.split("\\(", 2);
        HashMap localHashMap = new HashMap();
        
        if (arrayOfString4[0].length() > 0) {
          localWorldGenFlatInfo.b().put(arrayOfString4[0], localHashMap);
          
          if ((arrayOfString4.length > 1) && (arrayOfString4[1].endsWith(")")) && (arrayOfString4[1].length() > 1)) {
            String[] arrayOfString5 = arrayOfString4[1].substring(0, arrayOfString4[1].length() - 1).split(" ");
            
            for (int i1 = 0; i1 < arrayOfString5.length; i1++) {
              String[] arrayOfString6 = arrayOfString5[i1].split("=", 2);
              if (arrayOfString6.length == 2) localHashMap.put(arrayOfString6[0], arrayOfString6[1]);
            }
          }
        }
      }
    } else {
      localWorldGenFlatInfo.b().put("village", new HashMap());
    }
    
    return localWorldGenFlatInfo;
  }
  
  public static WorldGenFlatInfo e() {
    WorldGenFlatInfo localWorldGenFlatInfo = new WorldGenFlatInfo();
    
    localWorldGenFlatInfo.a(BiomeBase.PLAINS.id);
    localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.BEDROCK));
    localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(2, Blocks.DIRT));
    localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.GRASS));
    localWorldGenFlatInfo.d();
    localWorldGenFlatInfo.b().put("village", new HashMap());
    
    return localWorldGenFlatInfo;
  }
}
