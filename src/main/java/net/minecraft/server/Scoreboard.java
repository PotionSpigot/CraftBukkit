package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Scoreboard
{
  private final Map objectivesByName = new HashMap();
  private final Map objectivesByCriteria = new HashMap();
  private final Map playerScores = new HashMap();
  private final ScoreboardObjective[] displaySlots = new ScoreboardObjective[3];
  private final Map teamsByName = new HashMap();
  private final Map teamsByPlayer = new HashMap();
  
  public ScoreboardObjective getObjective(String paramString) {
    return (ScoreboardObjective)this.objectivesByName.get(paramString);
  }
  
  public ScoreboardObjective registerObjective(String paramString, IScoreboardCriteria paramIScoreboardCriteria) {
    ScoreboardObjective localScoreboardObjective = getObjective(paramString);
    if (localScoreboardObjective != null) { throw new IllegalArgumentException("An objective with the name '" + paramString + "' already exists!");
    }
    localScoreboardObjective = new ScoreboardObjective(this, paramString, paramIScoreboardCriteria);
    
    Object localObject = (List)this.objectivesByCriteria.get(paramIScoreboardCriteria);
    
    if (localObject == null) {
      localObject = new ArrayList();
      this.objectivesByCriteria.put(paramIScoreboardCriteria, localObject);
    }
    
    ((List)localObject).add(localScoreboardObjective);
    this.objectivesByName.put(paramString, localScoreboardObjective);
    handleObjectiveAdded(localScoreboardObjective);
    
    return localScoreboardObjective;
  }
  
  public Collection getObjectivesForCriteria(IScoreboardCriteria paramIScoreboardCriteria) {
    Collection localCollection = (Collection)this.objectivesByCriteria.get(paramIScoreboardCriteria);
    
    return localCollection == null ? new ArrayList() : new ArrayList(localCollection);
  }
  
  public ScoreboardScore getPlayerScoreForObjective(String paramString, ScoreboardObjective paramScoreboardObjective) {
    Object localObject = (Map)this.playerScores.get(paramString);
    
    if (localObject == null) {
      localObject = new HashMap();
      this.playerScores.put(paramString, localObject);
    }
    
    ScoreboardScore localScoreboardScore = (ScoreboardScore)((Map)localObject).get(paramScoreboardObjective);
    
    if (localScoreboardScore == null) {
      localScoreboardScore = new ScoreboardScore(this, paramScoreboardObjective, paramString);
      ((Map)localObject).put(paramScoreboardObjective, localScoreboardScore);
    }
    
    return localScoreboardScore;
  }
  
  public Collection getScoresForObjective(ScoreboardObjective paramScoreboardObjective) {
    ArrayList localArrayList = new ArrayList();
    
    for (Map localMap : this.playerScores.values()) {
      ScoreboardScore localScoreboardScore = (ScoreboardScore)localMap.get(paramScoreboardObjective);
      if (localScoreboardScore != null) { localArrayList.add(localScoreboardScore);
      }
    }
    Collections.sort(localArrayList, ScoreboardScore.a);
    
    return localArrayList;
  }
  
  public Collection getObjectives() {
    return this.objectivesByName.values();
  }
  
  public Collection getPlayers() {
    return this.playerScores.keySet();
  }
  
  public void resetPlayerScores(String paramString) {
    Map localMap = (Map)this.playerScores.remove(paramString);
    
    if (localMap != null) {
      handlePlayerRemoved(paramString);
    }
  }
  
  public Collection getScores() {
    Collection localCollection = this.playerScores.values();
    ArrayList localArrayList = new ArrayList();
    
    for (Map localMap : localCollection) {
      localArrayList.addAll(localMap.values());
    }
    
    return localArrayList;
  }
  











  public Map getPlayerObjectives(String paramString)
  {
    Object localObject = (Map)this.playerScores.get(paramString);
    if (localObject == null) localObject = new HashMap();
    return (Map)localObject;
  }
  
  public void unregisterObjective(ScoreboardObjective paramScoreboardObjective) {
    this.objectivesByName.remove(paramScoreboardObjective.getName());
    
    for (int i = 0; i < 3; i++) {
      if (getObjectiveForSlot(i) == paramScoreboardObjective) { setDisplaySlot(i, null);
      }
    }
    List localList = (List)this.objectivesByCriteria.get(paramScoreboardObjective.getCriteria());
    if (localList != null) { localList.remove(paramScoreboardObjective);
    }
    for (Map localMap : this.playerScores.values()) {
      localMap.remove(paramScoreboardObjective);
    }
    
    handleObjectiveRemoved(paramScoreboardObjective);
  }
  
  public void setDisplaySlot(int paramInt, ScoreboardObjective paramScoreboardObjective) {
    this.displaySlots[paramInt] = paramScoreboardObjective;
  }
  
  public ScoreboardObjective getObjectiveForSlot(int paramInt) {
    return this.displaySlots[paramInt];
  }
  
  public ScoreboardTeam getTeam(String paramString) {
    return (ScoreboardTeam)this.teamsByName.get(paramString);
  }
  
  public ScoreboardTeam createTeam(String paramString) {
    ScoreboardTeam localScoreboardTeam = getTeam(paramString);
    if (localScoreboardTeam != null) { throw new IllegalArgumentException("A team with the name '" + paramString + "' already exists!");
    }
    localScoreboardTeam = new ScoreboardTeam(this, paramString);
    this.teamsByName.put(paramString, localScoreboardTeam);
    handleTeamAdded(localScoreboardTeam);
    
    return localScoreboardTeam;
  }
  
  public void removeTeam(ScoreboardTeam paramScoreboardTeam) {
    this.teamsByName.remove(paramScoreboardTeam.getName());
    


    for (String str : paramScoreboardTeam.getPlayerNameSet()) {
      this.teamsByPlayer.remove(str);
    }
    
    handleTeamRemoved(paramScoreboardTeam);
  }
  
  public boolean addPlayerToTeam(String paramString1, String paramString2) {
    if (!this.teamsByName.containsKey(paramString2)) return false;
    ScoreboardTeam localScoreboardTeam = getTeam(paramString2);
    
    if (getPlayerTeam(paramString1) != null) {
      removePlayerFromTeam(paramString1);
    }
    
    this.teamsByPlayer.put(paramString1, localScoreboardTeam);
    localScoreboardTeam.getPlayerNameSet().add(paramString1);
    return true;
  }
  
  public boolean removePlayerFromTeam(String paramString) {
    ScoreboardTeam localScoreboardTeam = getPlayerTeam(paramString);
    
    if (localScoreboardTeam != null) {
      removePlayerFromTeam(paramString, localScoreboardTeam);
      return true;
    }
    return false;
  }
  
  public void removePlayerFromTeam(String paramString, ScoreboardTeam paramScoreboardTeam)
  {
    if (getPlayerTeam(paramString) != paramScoreboardTeam) {
      throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + paramScoreboardTeam.getName() + "'.");
    }
    
    this.teamsByPlayer.remove(paramString);
    paramScoreboardTeam.getPlayerNameSet().remove(paramString);
  }
  
  public Collection getTeamNames() {
    return this.teamsByName.keySet();
  }
  
  public Collection getTeams() {
    return this.teamsByName.values();
  }
  



  public ScoreboardTeam getPlayerTeam(String paramString)
  {
    return (ScoreboardTeam)this.teamsByPlayer.get(paramString);
  }
  

  public void handleObjectiveAdded(ScoreboardObjective paramScoreboardObjective) {}
  

  public void handleObjectiveChanged(ScoreboardObjective paramScoreboardObjective) {}
  

  public void handleObjectiveRemoved(ScoreboardObjective paramScoreboardObjective) {}
  

  public void handleScoreChanged(ScoreboardScore paramScoreboardScore) {}
  

  public void handlePlayerRemoved(String paramString) {}
  

  public void handleTeamAdded(ScoreboardTeam paramScoreboardTeam) {}
  

  public void handleTeamChanged(ScoreboardTeam paramScoreboardTeam) {}
  
  public void handleTeamRemoved(ScoreboardTeam paramScoreboardTeam) {}
  
  public static String getSlotName(int paramInt)
  {
    switch (paramInt) {
    case 0: 
      return "list";
    case 1: 
      return "sidebar";
    case 2: 
      return "belowName";
    }
    return null;
  }
  
  public static int getSlotForName(String paramString)
  {
    if (paramString.equalsIgnoreCase("list"))
      return 0;
    if (paramString.equalsIgnoreCase("sidebar"))
      return 1;
    if (paramString.equalsIgnoreCase("belowName")) {
      return 2;
    }
    return -1;
  }
}
