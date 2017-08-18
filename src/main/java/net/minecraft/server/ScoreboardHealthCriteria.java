package net.minecraft.server;

import java.util.List;

public class ScoreboardHealthCriteria
  extends ScoreboardBaseCriteria
{
  public ScoreboardHealthCriteria(String paramString)
  {
    super(paramString);
  }
  
  public int getScoreModifier(List paramList)
  {
    float f = 0.0F;
    
    for (EntityHuman localEntityHuman : paramList) {
      f += localEntityHuman.getHealth() + localEntityHuman.getAbsorptionHearts();
    }
    
    if (paramList.size() > 0) { f /= paramList.size();
    }
    return MathHelper.f(f);
  }
  
  public boolean isReadOnly()
  {
    return true;
  }
}
