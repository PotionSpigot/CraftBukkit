package net.minecraft.server;

import java.util.concurrent.Callable;



























































































































































































































































































































































































































class CrashReportLevelStorageVersion
  implements Callable
{
  CrashReportLevelStorageVersion(WorldData paramWorldData) {}
  
  public String a()
  {
    String str = "Unknown?";
    try
    {
      switch (WorldData.j(this.a)) {
      case 19133: 
        str = "Anvil";
        break;
      case 19132: 
        str = "McRegion";
      }
      
    }
    catch (Throwable localThrowable) {}
    

    return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldData.j(this.a)), str });
  }
}
