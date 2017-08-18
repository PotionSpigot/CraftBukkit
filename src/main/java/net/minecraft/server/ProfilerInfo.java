package net.minecraft.server;




public final class ProfilerInfo
  implements Comparable
{
  public double a;
  


  public double b;
  

  public String c;
  


  public ProfilerInfo(String paramString, double paramDouble1, double paramDouble2)
  {
    this.c = paramString;
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  
  public int a(ProfilerInfo paramProfilerInfo)
  {
    if (paramProfilerInfo.a < this.a) return -1;
    if (paramProfilerInfo.a > this.a) return 1;
    return paramProfilerInfo.c.compareTo(this.c);
  }
}
