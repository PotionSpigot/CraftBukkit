package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.Maps;
import org.bukkit.event.Cancellable;

public class StatisticManager
{
  protected final Map a = Maps.newConcurrentMap();
  


  public boolean hasAchievement(Achievement achievement)
  {
    return getStatisticValue(achievement) > 0;
  }
  
  public boolean b(Achievement achievement) {
    return (achievement.c == null) || (hasAchievement(achievement.c));
  }
  
  public void b(EntityHuman entityhuman, Statistic statistic, int i) {
    if ((!statistic.d()) || (b((Achievement)statistic)))
    {
      Cancellable cancellable = org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleStatisticsIncrease(entityhuman, statistic, getStatisticValue(statistic), i);
      if ((cancellable != null) && (cancellable.isCancelled())) {
        return;
      }
      
      setStatistic(entityhuman, statistic, getStatisticValue(statistic) + i);
    }
  }
  
  public void setStatistic(EntityHuman entityhuman, Statistic statistic, int i) {
    StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
    
    if (statisticwrapper == null) {
      statisticwrapper = new StatisticWrapper();
      this.a.put(statistic, statisticwrapper);
    }
    
    statisticwrapper.a(i);
  }
  
  public int getStatisticValue(Statistic statistic) {
    StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
    
    return statisticwrapper == null ? 0 : statisticwrapper.a();
  }
  
  public IJsonStatistic b(Statistic statistic) {
    StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
    
    return statisticwrapper != null ? statisticwrapper.b() : null;
  }
  
  public IJsonStatistic a(Statistic statistic, IJsonStatistic ijsonstatistic) {
    StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
    
    if (statisticwrapper == null) {
      statisticwrapper = new StatisticWrapper();
      this.a.put(statistic, statisticwrapper);
    }
    
    statisticwrapper.a(ijsonstatistic);
    return ijsonstatistic;
  }
}
