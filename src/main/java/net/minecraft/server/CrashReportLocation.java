package net.minecraft.server;

import java.util.concurrent.Callable;








































































































































































































































































































































































class CrashReportLocation
  implements Callable
{
  CrashReportLocation(Chunk paramChunk, int paramInt1, int paramInt2, int paramInt3) {}
  
  public String a()
  {
    return CrashReportSystemDetails.a(this.a, this.b, this.c);
  }
}
