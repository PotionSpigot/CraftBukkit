package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;


public class CrashReportSystemDetails
{
  private final CrashReport a;
  private final String b;
  private final List c = new ArrayList();
  private StackTraceElement[] d = new StackTraceElement[0];
  
  public CrashReportSystemDetails(CrashReport paramCrashReport, String paramString) {
    this.a = paramCrashReport;
    this.b = paramString;
  }
  



  public static String a(int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      localStringBuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
    } catch (Throwable localThrowable1) {
      localStringBuilder.append("(Error finding world loc)");
    }
    
    localStringBuilder.append(", ");
    int k;
    int m;
    int n; int i1; int i2; int i3; int i4; int i5; try { int i = paramInt1 >> 4;
      k = paramInt3 >> 4;
      m = paramInt1 & 0xF;
      n = paramInt2 >> 4;
      i1 = paramInt3 & 0xF;
      i2 = i << 4;
      i3 = k << 4;
      i4 = (i + 1 << 4) - 1;
      i5 = (k + 1 << 4) - 1;
      localStringBuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(m), Integer.valueOf(n), Integer.valueOf(i1), Integer.valueOf(i), Integer.valueOf(k), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5) }));
    } catch (Throwable localThrowable2) {
      localStringBuilder.append("(Error finding chunk loc)");
    }
    
    localStringBuilder.append(", ");
    try
    {
      int j = paramInt1 >> 9;
      k = paramInt3 >> 9;
      m = j << 5;
      n = k << 5;
      i1 = (j + 1 << 5) - 1;
      i2 = (k + 1 << 5) - 1;
      i3 = j << 9;
      i4 = k << 9;
      i5 = (j + 1 << 9) - 1;
      int i6 = (k + 1 << 9) - 1;
      localStringBuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m), Integer.valueOf(n), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6) }));
    } catch (Throwable localThrowable3) {
      localStringBuilder.append("(Error finding world loc)");
    }
    
    return localStringBuilder.toString();
  }
  
  public void a(String paramString, Callable paramCallable) {
    try {
      a(paramString, paramCallable.call());
    } catch (Throwable localThrowable) {
      a(paramString, localThrowable);
    }
  }
  
  public void a(String paramString, Object paramObject) {
    this.c.add(new CrashReportDetail(paramString, paramObject));
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    a(paramString, paramThrowable);
  }
  
  public int a(int paramInt) {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    

    if (arrayOfStackTraceElement.length <= 0) {
      return 0;
    }
    
    this.d = new StackTraceElement[arrayOfStackTraceElement.length - 3 - paramInt];
    System.arraycopy(arrayOfStackTraceElement, 3 + paramInt, this.d, 0, this.d.length);
    return this.d.length;
  }
  
  public boolean a(StackTraceElement paramStackTraceElement1, StackTraceElement paramStackTraceElement2) {
    if ((this.d.length == 0) || (paramStackTraceElement1 == null)) { return false;
    }
    StackTraceElement localStackTraceElement = this.d[0];
    

    if ((localStackTraceElement.isNativeMethod() != paramStackTraceElement1.isNativeMethod()) || (!localStackTraceElement.getClassName().equals(paramStackTraceElement1.getClassName())) || (!localStackTraceElement.getFileName().equals(paramStackTraceElement1.getFileName())) || (!localStackTraceElement.getMethodName().equals(paramStackTraceElement1.getMethodName())))
    {


      return false;
    }
    if ((paramStackTraceElement2 != null ? 1 : 0) != (this.d.length > 1 ? 1 : 0)) return false;
    if ((paramStackTraceElement2 != null) && (!this.d[1].equals(paramStackTraceElement2))) { return false;
    }
    this.d[0] = paramStackTraceElement1;
    
    return true;
  }
  
  public void b(int paramInt) {
    StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[this.d.length - paramInt];
    System.arraycopy(this.d, 0, arrayOfStackTraceElement, 0, arrayOfStackTraceElement.length);
    this.d = arrayOfStackTraceElement;
  }
  
  public void a(StringBuilder paramStringBuilder) {
    paramStringBuilder.append("-- ").append(this.b).append(" --\n");
    paramStringBuilder.append("Details:");
    
    for (Object localObject1 = this.c.iterator(); ((Iterator)localObject1).hasNext();) { CrashReportDetail localCrashReportDetail = (CrashReportDetail)((Iterator)localObject1).next();
      paramStringBuilder.append("\n\t");
      paramStringBuilder.append(localCrashReportDetail.a());
      paramStringBuilder.append(": ");
      paramStringBuilder.append(localCrashReportDetail.b());
    }
    
    if ((this.d != null) && (this.d.length > 0)) {
      paramStringBuilder.append("\nStacktrace:");
      
      for (Object localObject2 : this.d) {
        paramStringBuilder.append("\n\tat ");
        paramStringBuilder.append(((StackTraceElement)localObject2).toString());
      }
    }
  }
  
  public StackTraceElement[] a() {
    return this.d;
  }
  
  public static void a(CrashReportSystemDetails paramCrashReportSystemDetails, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
    int i = Block.getId(paramBlock);
    paramCrashReportSystemDetails.a("Block type", new CrashReportBlockType(i, paramBlock));
    









    paramCrashReportSystemDetails.a("Block data value", new CrashReportBlockDataValue(paramInt4));
    









    paramCrashReportSystemDetails.a("Block location", new CrashReportBlockLocation(paramInt1, paramInt2, paramInt3));
  }
}
