package net.minecraft.server;


public abstract class ScoreboardTeamBase
{
  public boolean isAlly(ScoreboardTeamBase paramScoreboardTeamBase)
  {
    if (paramScoreboardTeamBase == null) {
      return false;
    }
    if (this == paramScoreboardTeamBase) {
      return true;
    }
    return false;
  }
  
  public abstract String getName();
  
  public abstract String getFormattedName(String paramString);
  
  public abstract boolean allowFriendlyFire();
}
