package net.minecraft.server;

import java.util.concurrent.Callable;




























































































class CrashReportAABBPoolSize
  implements Callable
{
  CrashReportAABBPoolSize(CrashReport paramCrashReport) {}
  
  public String a()
  {
    int i = 0;
    int j = 56 * i;
    int k = j / 1024 / 1024;
    int m = 0;
    int n = 56 * m;
    int i1 = n / 1024 / 1024;
    
    return i + " (" + j + " bytes; " + k + " MB) allocated, " + m + " (" + n + " bytes; " + i1 + " MB) used";
  }
}
