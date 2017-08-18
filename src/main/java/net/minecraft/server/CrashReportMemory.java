package net.minecraft.server;

import java.util.concurrent.Callable;























































class CrashReportMemory
  implements Callable
{
  CrashReportMemory(CrashReport paramCrashReport) {}
  
  public String a()
  {
    Runtime localRuntime = Runtime.getRuntime();
    long l1 = localRuntime.maxMemory();
    long l2 = localRuntime.totalMemory();
    long l3 = localRuntime.freeMemory();
    long l4 = l1 / 1024L / 1024L;
    long l5 = l2 / 1024L / 1024L;
    long l6 = l3 / 1024L / 1024L;
    
    return l3 + " bytes (" + l6 + " MB) / " + l2 + " bytes (" + l5 + " MB) up to " + l1 + " bytes (" + l4 + " MB)";
  }
}
