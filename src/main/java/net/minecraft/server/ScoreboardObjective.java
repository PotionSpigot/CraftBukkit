package net.minecraft.server;


public class ScoreboardObjective
{
  private final Scoreboard a;
  
  private final String b;
  
  private final IScoreboardCriteria c;
  private String d;
  
  public ScoreboardObjective(Scoreboard paramScoreboard, String paramString, IScoreboardCriteria paramIScoreboardCriteria)
  {
    this.a = paramScoreboard;
    this.b = paramString;
    this.c = paramIScoreboardCriteria;
    
    this.d = paramString;
  }
  



  public String getName()
  {
    return this.b;
  }
  
  public IScoreboardCriteria getCriteria() {
    return this.c;
  }
  
  public String getDisplayName() {
    return this.d;
  }
  
  public void setDisplayName(String paramString) {
    this.d = paramString;
    this.a.handleObjectiveChanged(this);
  }
}
