package net.minecraft.server;

import java.util.Comparator;
import java.util.List;


public class ScoreboardScore
{
  public static final Comparator a = new ScoreboardComparator();
  


  private final Scoreboard b;
  

  private final ScoreboardObjective c;
  

  private final String playerName;
  

  private int score;
  


  public ScoreboardScore(Scoreboard paramScoreboard, ScoreboardObjective paramScoreboardObjective, String paramString)
  {
    this.b = paramScoreboard;
    this.c = paramScoreboardObjective;
    this.playerName = paramString;
  }
  
  public void addScore(int paramInt) {
    if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
    setScore(getScore() + paramInt);
  }
  
  public void removeScore(int paramInt) {
    if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
    setScore(getScore() - paramInt);
  }
  
  public void incrementScore() {
    if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
    addScore(1);
  }
  




  public int getScore()
  {
    return this.score;
  }
  
  public void setScore(int paramInt) {
    int i = this.score;
    this.score = paramInt;
    if (i != paramInt) f().handleScoreChanged(this);
  }
  
  public ScoreboardObjective getObjective() {
    return this.c;
  }
  
  public String getPlayerName() {
    return this.playerName;
  }
  
  public Scoreboard f() {
    return this.b;
  }
  
  public void updateForList(List paramList) {
    setScore(this.c.getCriteria().getScoreModifier(paramList));
  }
}
