package net.minecraft.server;

import java.util.concurrent.Callable;









































class CrashReportJavaVersion
  implements Callable
{
  CrashReportJavaVersion(CrashReport paramCrashReport) {}
  
  public String a()
  {
    return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
  }
}
