package net.minecraft.server;

import java.util.concurrent.Callable;

















































































































































final class CrashReportBlockType
  implements Callable
{
  CrashReportBlockType(int paramInt, Block paramBlock) {}
  
  public String a()
  {
    try
    {
      return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.a), this.b.a(), this.b.getClass().getCanonicalName() });
    } catch (Throwable localThrowable) {}
    return "ID #" + this.a;
  }
}
