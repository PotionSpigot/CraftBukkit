package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldLoaderServer
  extends WorldLoader
{
  private static final Logger b = ;
  
  public WorldLoaderServer(File paramFile) {
    super(paramFile);
  }
  




































  protected int c()
  {
    return 19133;
  }
  


  public void d() {}
  

  public IDataManager a(String paramString, boolean paramBoolean)
  {
    return new ServerNBTManager(this.a, paramString, paramBoolean);
  }
  












  public boolean isConvertable(String paramString)
  {
    WorldData localWorldData = c(paramString);
    if ((localWorldData == null) || (localWorldData.l() == c())) {
      return false;
    }
    return true;
  }
  

  public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate)
  {
    paramIProgressUpdate.a(0);
    
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    File localFile1 = new File(this.a, paramString);
    File localFile2 = new File(localFile1, "DIM-1");
    File localFile3 = new File(localFile1, "DIM1");
    
    b.info("Scanning folders...");
    

    a(localFile1, localArrayList1);
    

    if (localFile2.exists()) {
      a(localFile2, localArrayList2);
    }
    if (localFile3.exists()) {
      a(localFile3, localArrayList3);
    }
    
    int i = localArrayList1.size() + localArrayList2.size() + localArrayList3.size();
    b.info("Total conversion count is " + i);
    
    WorldData localWorldData = c(paramString);
    
    Object localObject = null;
    if (localWorldData.getType() == WorldType.FLAT) {
      localObject = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5F);
    } else {
      localObject = new WorldChunkManager(localWorldData.getSeed(), localWorldData.getType());
    }
    

    a(new File(localFile1, "region"), localArrayList1, (WorldChunkManager)localObject, 0, i, paramIProgressUpdate);
    
    a(new File(localFile2, "region"), localArrayList2, new WorldChunkManagerHell(BiomeBase.HELL, 0.0F), localArrayList1.size(), i, paramIProgressUpdate);
    
    a(new File(localFile3, "region"), localArrayList3, new WorldChunkManagerHell(BiomeBase.SKY, 0.0F), localArrayList1.size() + localArrayList2.size(), i, paramIProgressUpdate);
    
    localWorldData.e(19133);
    if (localWorldData.getType() == WorldType.NORMAL_1_1) {
      localWorldData.setType(WorldType.NORMAL);
    }
    
    g(paramString);
    
    IDataManager localIDataManager = a(paramString, false);
    localIDataManager.saveWorldData(localWorldData);
    
    return true;
  }
  
  private void g(String paramString) {
    File localFile1 = new File(this.a, paramString);
    if (!localFile1.exists()) {
      b.warn("Unable to create level.dat_mcr backup");
      return;
    }
    
    File localFile2 = new File(localFile1, "level.dat");
    if (!localFile2.exists()) {
      b.warn("Unable to create level.dat_mcr backup");
      return;
    }
    
    File localFile3 = new File(localFile1, "level.dat_mcr");
    if (!localFile2.renameTo(localFile3)) {
      b.warn("Unable to create level.dat_mcr backup");
    }
  }
  
  private void a(File paramFile, Iterable paramIterable, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate)
  {
    for (File localFile : paramIterable) {
      a(paramFile, localFile, paramWorldChunkManager, paramInt1, paramInt2, paramIProgressUpdate);
      
      paramInt1++;
      int i = (int)Math.round(100.0D * paramInt1 / paramInt2);
      paramIProgressUpdate.a(i);
    }
  }
  
  private void a(File paramFile1, File paramFile2, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate)
  {
    try
    {
      String str = paramFile2.getName();
      
      RegionFile localRegionFile1 = new RegionFile(paramFile2);
      RegionFile localRegionFile2 = new RegionFile(new File(paramFile1, str.substring(0, str.length() - ".mcr".length()) + ".mca"));
      
      for (int i = 0; i < 32; i++) {
        for (int j = 0; j < 32; j++)
          if ((localRegionFile1.c(i, j)) && (!localRegionFile2.c(i, j))) {
            DataInputStream localDataInputStream = localRegionFile1.a(i, j);
            if (localDataInputStream == null) {
              b.warn("Failed to fetch input stream");
            }
            else {
              NBTTagCompound localNBTTagCompound1 = NBTCompressedStreamTools.a(localDataInputStream);
              localDataInputStream.close();
              
              NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1.getCompound("Level");
              OldChunk localOldChunk = OldChunkLoader.a(localNBTTagCompound2);
              
              NBTTagCompound localNBTTagCompound3 = new NBTTagCompound();
              NBTTagCompound localNBTTagCompound4 = new NBTTagCompound();
              localNBTTagCompound3.set("Level", localNBTTagCompound4);
              OldChunkLoader.a(localOldChunk, localNBTTagCompound4, paramWorldChunkManager);
              
              DataOutputStream localDataOutputStream = localRegionFile2.b(i, j);
              NBTCompressedStreamTools.a(localNBTTagCompound3, localDataOutputStream);
              localDataOutputStream.close();
            }
          }
        j = (int)Math.round(100.0D * (paramInt1 * 1024) / (paramInt2 * 1024));
        int k = (int)Math.round(100.0D * ((i + 1) * 32 + paramInt1 * 1024) / (paramInt2 * 1024));
        if (k > j) {
          paramIProgressUpdate.a(k);
        }
      }
      
      localRegionFile1.c();
      localRegionFile2.c();
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
    }
  }
  
  private void a(File paramFile, Collection paramCollection)
  {
    File localFile = new File(paramFile, "region");
    File[] arrayOfFile = localFile.listFiles(new ChunkFilenameFilter(this));
    





    if (arrayOfFile != null) {
      Collections.addAll(paramCollection, arrayOfFile);
    }
  }
}
