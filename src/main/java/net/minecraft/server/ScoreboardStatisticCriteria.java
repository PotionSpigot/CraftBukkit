package net.minecraft.server;

public class ScoreboardStatisticCriteria extends ScoreboardBaseCriteria
{
  private final Statistic g;
  
  public ScoreboardStatisticCriteria(Statistic paramStatistic)
  {
    super(paramStatistic.name);
    this.g = paramStatistic;
  }
}
