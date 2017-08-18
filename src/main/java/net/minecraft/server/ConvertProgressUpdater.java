package net.minecraft.server;

import org.apache.logging.log4j.Logger;
































































































































public class ConvertProgressUpdater
  implements IProgressUpdate
{
  private long b = MinecraftServer.ar();
  


  public ConvertProgressUpdater(MinecraftServer paramMinecraftServer) {}
  

  public void a(String paramString) {}
  

  public void a(int paramInt)
  {
    if (MinecraftServer.ar() - this.b >= 1000L) {
      this.b = MinecraftServer.ar();
      MinecraftServer.getLogger().info("Converting... " + paramInt + "%");
    }
  }
  
  public void c(String paramString) {}
}
