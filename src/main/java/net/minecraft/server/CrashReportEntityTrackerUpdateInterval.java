package net.minecraft.server;

import java.util.concurrent.Callable;
















































































class CrashReportEntityTrackerUpdateInterval
  implements Callable
{
  CrashReportEntityTrackerUpdateInterval(EntityTracker paramEntityTracker, int paramInt) {}
  
  public String a()
  {
    String str = "Once per " + this.a + " ticks";
    if (this.a == Integer.MAX_VALUE) str = "Maximum (" + str + ")";
    return str;
  }
}
