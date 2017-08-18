package net.minecraft.server;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.com.google.common.collect.Maps;






public class PacketPlayOutStatistic
  extends Packet
{
  private Map a;
  
  public PacketPlayOutStatistic() {}
  
  public PacketPlayOutStatistic(Map paramMap)
  {
    this.a = paramMap;
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    int i = paramPacketDataSerializer.a();
    this.a = Maps.newHashMap();
    
    for (int j = 0; j < i; j++) {
      Statistic localStatistic = StatisticList.getStatistic(paramPacketDataSerializer.c(32767));
      int k = paramPacketDataSerializer.a();
      
      if (localStatistic != null) this.a.put(localStatistic, Integer.valueOf(k));
    }
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.a.size());
    
    for (Entry localEntry : this.a.entrySet()) {
      paramPacketDataSerializer.a(((Statistic)localEntry.getKey()).name);
      paramPacketDataSerializer.b(((Integer)localEntry.getValue()).intValue());
    }
  }
  
  public String b()
  {
    return String.format("count=%d", new Object[] { Integer.valueOf(this.a.size()) });
  }
}
