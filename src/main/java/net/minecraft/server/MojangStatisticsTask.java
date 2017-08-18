package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;



































class MojangStatisticsTask
  extends TimerTask
{
  MojangStatisticsTask(MojangStatisticsGenerator paramMojangStatisticsGenerator) {}
  
  public void run()
  {
    if (!MojangStatisticsGenerator.a(this.a).getSnooperEnabled())
      return;
    HashMap localHashMap;
    synchronized (MojangStatisticsGenerator.b(this.a)) {
      localHashMap = new HashMap(MojangStatisticsGenerator.c(this.a));
      if (MojangStatisticsGenerator.d(this.a) == 0) localHashMap.putAll(MojangStatisticsGenerator.e(this.a));
      localHashMap.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.f(this.a)));
      localHashMap.put("snooper_token", MojangStatisticsGenerator.g(this.a));
    }
    
    HttpUtilities.a(MojangStatisticsGenerator.h(this.a), localHashMap, true);
  }
}
