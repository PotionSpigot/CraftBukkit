package net.minecraft.server;





























































class CrashReportDetail
{
  private final String a;
  



























































  private final String b;
  



























































  public CrashReportDetail(String paramString, Object paramObject)
  {
    this.a = paramString;
    
    if (paramObject == null) {
      this.b = "~~NULL~~";
    } else if ((paramObject instanceof Throwable)) {
      Throwable localThrowable = (Throwable)paramObject;
      this.b = ("~~ERROR~~ " + localThrowable.getClass().getSimpleName() + ": " + localThrowable.getMessage());
    } else {
      this.b = paramObject.toString();
    }
  }
  
  public String a() {
    return this.a;
  }
  
  public String b() {
    return this.b;
  }
}
