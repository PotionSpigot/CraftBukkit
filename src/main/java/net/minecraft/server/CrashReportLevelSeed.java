package net.minecraft.server;

import java.util.concurrent.Callable;

















































































































































































































































































































































































class CrashReportLevelSeed
  implements Callable
{
  CrashReportLevelSeed(WorldData paramWorldData) {}
  
  public String a()
  {
    return String.valueOf(this.a.getSeed());
  }
}
