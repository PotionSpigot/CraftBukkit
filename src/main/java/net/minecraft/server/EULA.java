package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

public class EULA
{
  private static final Logger a = ;
  private final File b;
  private final boolean c;
  
  public EULA(File paramFile) {
    this.b = paramFile;
    this.c = a(paramFile);
  }
  
  private boolean a(File paramFile) {
    FileInputStream localFileInputStream = null;
    boolean bool = false;
    try {
      Properties localProperties = new Properties();
      localFileInputStream = new FileInputStream(paramFile);
      localProperties.load(localFileInputStream);
      bool = Boolean.parseBoolean(localProperties.getProperty("eula", "false"));
    } catch (Exception localException) {
      a.warn("Failed to load " + paramFile);
      b();
    } finally {
      net.minecraft.util.org.apache.commons.io.IOUtils.closeQuietly(localFileInputStream);
    }
    return bool;
  }
  
  public boolean a() {
    return this.c;
  }
  
  public void b() {
    FileOutputStream localFileOutputStream = null;
    try {
      Properties localProperties = new Properties();
      localFileOutputStream = new FileOutputStream(this.b);
      localProperties.setProperty("eula", "false");
      localProperties.store(localFileOutputStream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
    } catch (Exception localException) {
      a.warn("Failed to save " + this.b, localException);
    } finally {
      net.minecraft.util.org.apache.commons.io.IOUtils.closeQuietly(localFileOutputStream);
    }
  }
}
