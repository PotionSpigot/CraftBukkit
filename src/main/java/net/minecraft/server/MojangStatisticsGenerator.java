package net.minecraft.server;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import net.minecraft.util.com.google.common.collect.Maps;


public class MojangStatisticsGenerator
{
  private final Map a = Maps.newHashMap();
  private final Map b = Maps.newHashMap();
  
  private final String c = UUID.randomUUID().toString();
  private final URL d;
  private final IMojangStatistics e;
  private final Timer f = new Timer("Snooper Timer", true);
  private final Object g = new Object();
  private final long h;
  private boolean i;
  private int j;
  
  public MojangStatisticsGenerator(String paramString, IMojangStatistics paramIMojangStatistics, long paramLong) {
    try {
      this.d = new URL("http://snoop.minecraft.net/" + paramString + "?version=" + 2);
    } catch (MalformedURLException localMalformedURLException) {
      throw new IllegalArgumentException();
    }
    
    this.e = paramIMojangStatistics;
    this.h = paramLong;
  }
  
  public void a() {
    if (this.i) return;
    this.i = true;
    
    h();
    
    this.f.schedule(new MojangStatisticsTask(this), 0L, 900000L);
  }
  














  private void h()
  {
    i();
    
    a("snooper_token", this.c);
    b("snooper_token", this.c);
    b("os_name", System.getProperty("os.name"));
    b("os_version", System.getProperty("os.version"));
    b("os_architecture", System.getProperty("os.arch"));
    b("java_version", System.getProperty("java.version"));
    b("version", "1.7.10");
    
    this.e.b(this);
  }
  
  private void i() {
    RuntimeMXBean localRuntimeMXBean = ManagementFactory.getRuntimeMXBean();
    List localList = localRuntimeMXBean.getInputArguments();
    int k = 0;
    
    for (String str : localList) {
      if (str.startsWith("-X")) {
        a("jvm_arg[" + k++ + "]", str);
      }
    }
    
    a("jvm_args", Integer.valueOf(k));
  }
  
  public void b() {
    b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
    b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
    b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
    b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
    
    this.e.a(this);
  }
  
  public void a(String paramString, Object paramObject) {
    synchronized (this.g) {
      this.b.put(paramString, paramObject);
    }
  }
  
  public void b(String paramString, Object paramObject) {
    synchronized (this.g) {
      this.a.put(paramString, paramObject);
    }
  }
  

















  public boolean d()
  {
    return this.i;
  }
  
  public void e() {
    this.f.cancel();
  }
  



  public long g()
  {
    return this.h;
  }
}
