package net.minecraft.server;

import java.util.concurrent.Callable;


























































































































































































































































class CrashReportType
  implements Callable
{
  CrashReportType(DedicatedServer paramDedicatedServer) {}
  
  public String a()
  {
    return "Dedicated Server (map_server.txt)";
  }
}
