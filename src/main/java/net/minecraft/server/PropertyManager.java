package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;


public class PropertyManager
{
  private static final Logger loggingAgent = ;
  public final Properties properties = new Properties();
  private final File c;
  
  public PropertyManager(File file1) {
    this.c = file1;
    if (file1.exists()) {
      FileInputStream fileinputstream = null;
      try
      {
        fileinputstream = new FileInputStream(file1);
        this.properties.load(fileinputstream);
      } catch (Exception exception) {
        loggingAgent.warn("Failed to load " + file1, exception);
        a();
      } finally {
        if (fileinputstream != null) {
          try {
            fileinputstream.close();
          }
          catch (IOException localIOException2) {}
        }
      }
    }
    
    loggingAgent.warn(file1 + " does not exist");
    a();
  }
  


  private OptionSet options = null;
  
  public PropertyManager(OptionSet options) {
    this((File)options.valueOf("config"));
    
    this.options = options;
  }
  
  private <T> T getOverride(String name, T value) {
    if ((this.options != null) && (this.options.has(name)) && (!name.equals("online-mode"))) {
      return (T)this.options.valueOf(name);
    }
    
    return value;
  }
  
  public void a()
  {
    loggingAgent.info("Generating new properties file");
    savePropertiesFile();
  }
  
  public void savePropertiesFile() {
    FileOutputStream fileoutputstream = null;
    
    try
    {
      if ((this.c.exists()) && (!this.c.canWrite())) {
        return;
      }
      
      fileoutputstream = new FileOutputStream(this.c);
      this.properties.store(fileoutputstream, "Minecraft server properties"); return;
    } catch (Exception exception) {
      loggingAgent.warn("Failed to save " + this.c, exception);
      a();
    } finally {
      if (fileoutputstream != null) {
        try {
          fileoutputstream.close();
        }
        catch (IOException localIOException3) {}
      }
    }
  }
  
  public File c()
  {
    return this.c;
  }
  
  public String getString(String s, String s1) {
    if (!this.properties.containsKey(s)) {
      this.properties.setProperty(s, s1);
      savePropertiesFile();
      savePropertiesFile();
    }
    
    return (String)getOverride(s, this.properties.getProperty(s, s1));
  }
  
  public int getInt(String s, int i) {
    try {
      return ((Integer)getOverride(s, Integer.valueOf(Integer.parseInt(getString(s, "" + i))))).intValue();
    } catch (Exception exception) {
      this.properties.setProperty(s, "" + i);
      savePropertiesFile(); }
    return ((Integer)getOverride(s, Integer.valueOf(i))).intValue();
  }
  
  public boolean getBoolean(String s, boolean flag)
  {
    try {
      return ((Boolean)getOverride(s, Boolean.valueOf(Boolean.parseBoolean(getString(s, "" + flag))))).booleanValue();
    } catch (Exception exception) {
      this.properties.setProperty(s, "" + flag);
      savePropertiesFile(); }
    return ((Boolean)getOverride(s, Boolean.valueOf(flag))).booleanValue();
  }
  
  public void setProperty(String s, Object object)
  {
    this.properties.setProperty(s, "" + object);
  }
}
