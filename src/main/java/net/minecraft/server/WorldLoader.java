package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class WorldLoader
  implements Convertable
{
  private static final Logger b = ;
  protected final File a;
  
  public WorldLoader(File paramFile)
  {
    if (!paramFile.exists()) paramFile.mkdirs();
    this.a = paramFile;
  }
  













  public void d() {}
  












  public WorldData c(String paramString)
  {
    File localFile1 = new File(this.a, paramString);
    if (!localFile1.exists()) { return null;
    }
    File localFile2 = new File(localFile1, "level.dat");
    NBTTagCompound localNBTTagCompound3; if (localFile2.exists()) {
      try {
        NBTTagCompound localNBTTagCompound1 = NBTCompressedStreamTools.a(new FileInputStream(localFile2));
        localNBTTagCompound3 = localNBTTagCompound1.getCompound("Data");
        return new WorldData(localNBTTagCompound3);
      } catch (Exception localException1) {
        b.error("Exception reading " + localFile2, localException1);
      }
    }
    
    localFile2 = new File(localFile1, "level.dat_old");
    if (localFile2.exists()) {
      try {
        NBTTagCompound localNBTTagCompound2 = NBTCompressedStreamTools.a(new FileInputStream(localFile2));
        localNBTTagCompound3 = localNBTTagCompound2.getCompound("Data");
        return new WorldData(localNBTTagCompound3);
      } catch (Exception localException2) {
        b.error("Exception reading " + localFile2, localException2);
      }
    }
    return null;
  }
  






































  public boolean e(String paramString)
  {
    File localFile = new File(this.a, paramString);
    if (!localFile.exists()) { return true;
    }
    b.info("Deleting level " + paramString);
    
    for (int i = 1; i <= 5; i++) {
      b.info("Attempt " + i + "...");
      
      if (a(localFile.listFiles())) {
        break;
      }
      b.warn("Unsuccessful in deleting contents.");
      

      if (i < 5) {
        try {
          Thread.sleep(500L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
    
    return localFile.delete();
  }
  
  protected static boolean a(File[] paramArrayOfFile) {
    for (int i = 0; i < paramArrayOfFile.length; i++) {
      File localFile = paramArrayOfFile[i];
      b.debug("Deleting " + localFile);
      
      if ((localFile.isDirectory()) && 
        (!a(localFile.listFiles()))) {
        b.warn("Couldn't delete directory " + localFile);
        return false;
      }
      

      if (!localFile.delete()) {
        b.warn("Couldn't delete file " + localFile);
        return false;
      }
    }
    
    return true;
  }
  
  public IDataManager a(String paramString, boolean paramBoolean)
  {
    return new WorldNBTStorage(this.a, paramString, paramBoolean);
  }
  





  public boolean isConvertable(String paramString)
  {
    return false;
  }
  
  public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate)
  {
    return false;
  }
}
