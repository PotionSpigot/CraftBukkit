package net.minecraft.server;

import java.util.List;

public class CounterStatistic extends Statistic
{
  public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter) {
    super(paramString, paramIChatBaseComponent, paramCounter);
  }
  
  public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
    super(paramString, paramIChatBaseComponent);
  }
  
  public Statistic h()
  {
    super.h();
    
    StatisticList.c.add(this);
    
    return this;
  }
}
