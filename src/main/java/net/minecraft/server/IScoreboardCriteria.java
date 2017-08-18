package net.minecraft.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract interface IScoreboardCriteria
{
  public static final Map criteria = new HashMap();
  
  public static final IScoreboardCriteria b = new ScoreboardBaseCriteria("dummy");
  public static final IScoreboardCriteria c = new ScoreboardBaseCriteria("deathCount");
  public static final IScoreboardCriteria d = new ScoreboardBaseCriteria("playerKillCount");
  public static final IScoreboardCriteria e = new ScoreboardBaseCriteria("totalKillCount");
  public static final IScoreboardCriteria f = new ScoreboardHealthCriteria("health");
  
  public abstract String getName();
  
  public abstract int getScoreModifier(List paramList);
  
  public abstract boolean isReadOnly();
}
