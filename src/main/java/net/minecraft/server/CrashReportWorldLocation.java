package net.minecraft.server;

import java.util.concurrent.Callable;
















































































































class CrashReportWorldLocation
  implements Callable
{
  CrashReportWorldLocation(World paramWorld, int paramInt1, int paramInt2) {}
  
  public String a()
  {
    return CrashReportSystemDetails.a(this.a, 0, this.b);
  }
}