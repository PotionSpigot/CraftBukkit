package net.minecraft.server;

import java.util.List;

public class ScoreboardBaseCriteria implements IScoreboardCriteria
{
  private final String g;
  
  public ScoreboardBaseCriteria(String paramString)
  {
    this.g = paramString;
    IScoreboardCriteria.criteria.put(paramString, this);
  }
  
  public String getName()
  {
    return this.g;
  }
  
  public int getScoreModifier(List paramList)
  {
    return 0;
  }
  
  public boolean isReadOnly()
  {
    return false;
  }
}
