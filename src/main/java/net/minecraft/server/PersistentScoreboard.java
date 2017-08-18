package net.minecraft.server;

import java.util.Collection;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class PersistentScoreboard
  extends PersistentBase
{
  private static final Logger a = ;
  
  private Scoreboard b;
  private NBTTagCompound c;
  
  public PersistentScoreboard()
  {
    this("scoreboard");
  }
  
  public PersistentScoreboard(String paramString) {
    super(paramString);
  }
  
  public void a(Scoreboard paramScoreboard) {
    this.b = paramScoreboard;
    
    if (this.c != null) {
      a(this.c);
    }
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    if (this.b == null) {
      this.c = paramNBTTagCompound;
      return;
    }
    
    b(paramNBTTagCompound.getList("Objectives", 10));
    c(paramNBTTagCompound.getList("PlayerScores", 10));
    
    if (paramNBTTagCompound.hasKeyOfType("DisplaySlots", 10)) {
      c(paramNBTTagCompound.getCompound("DisplaySlots"));
    }
    
    if (paramNBTTagCompound.hasKeyOfType("Teams", 9)) {
      a(paramNBTTagCompound.getList("Teams", 10));
    }
  }
  
  protected void a(NBTTagList paramNBTTagList) {
    for (int i = 0; i < paramNBTTagList.size(); i++) {
      NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
      
      ScoreboardTeam localScoreboardTeam = this.b.createTeam(localNBTTagCompound.getString("Name"));
      localScoreboardTeam.setDisplayName(localNBTTagCompound.getString("DisplayName"));
      localScoreboardTeam.setPrefix(localNBTTagCompound.getString("Prefix"));
      localScoreboardTeam.setSuffix(localNBTTagCompound.getString("Suffix"));
      if (localNBTTagCompound.hasKeyOfType("AllowFriendlyFire", 99)) localScoreboardTeam.setAllowFriendlyFire(localNBTTagCompound.getBoolean("AllowFriendlyFire"));
      if (localNBTTagCompound.hasKeyOfType("SeeFriendlyInvisibles", 99)) { localScoreboardTeam.setCanSeeFriendlyInvisibles(localNBTTagCompound.getBoolean("SeeFriendlyInvisibles"));
      }
      a(localScoreboardTeam, localNBTTagCompound.getList("Players", 8));
    }
  }
  
  protected void a(ScoreboardTeam paramScoreboardTeam, NBTTagList paramNBTTagList) {
    for (int i = 0; i < paramNBTTagList.size(); i++) {
      this.b.addPlayerToTeam(paramNBTTagList.getString(i), paramScoreboardTeam.getName());
    }
  }
  
  protected void c(NBTTagCompound paramNBTTagCompound) {
    for (int i = 0; i < 3; i++) {
      if (paramNBTTagCompound.hasKeyOfType("slot_" + i, 8)) {
        String str = paramNBTTagCompound.getString("slot_" + i);
        ScoreboardObjective localScoreboardObjective = this.b.getObjective(str);
        this.b.setDisplaySlot(i, localScoreboardObjective);
      }
    }
  }
  
  protected void b(NBTTagList paramNBTTagList) {
    for (int i = 0; i < paramNBTTagList.size(); i++) {
      NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
      
      IScoreboardCriteria localIScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(localNBTTagCompound.getString("CriteriaName"));
      ScoreboardObjective localScoreboardObjective = this.b.registerObjective(localNBTTagCompound.getString("Name"), localIScoreboardCriteria);
      localScoreboardObjective.setDisplayName(localNBTTagCompound.getString("DisplayName"));
    }
  }
  
  protected void c(NBTTagList paramNBTTagList) {
    for (int i = 0; i < paramNBTTagList.size(); i++) {
      NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
      
      ScoreboardObjective localScoreboardObjective = this.b.getObjective(localNBTTagCompound.getString("Objective"));
      ScoreboardScore localScoreboardScore = this.b.getPlayerScoreForObjective(localNBTTagCompound.getString("Name"), localScoreboardObjective);
      localScoreboardScore.setScore(localNBTTagCompound.getInt("Score"));
    }
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    if (this.b == null) {
      a.warn("Tried to save scoreboard without having a scoreboard...");
      return;
    }
    
    paramNBTTagCompound.set("Objectives", b());
    paramNBTTagCompound.set("PlayerScores", e());
    paramNBTTagCompound.set("Teams", a());
    
    d(paramNBTTagCompound);
  }
  
  protected NBTTagList a() {
    NBTTagList localNBTTagList1 = new NBTTagList();
    Collection localCollection = this.b.getTeams();
    
    for (ScoreboardTeam localScoreboardTeam : localCollection) {
      NBTTagCompound localNBTTagCompound = new NBTTagCompound();
      
      localNBTTagCompound.setString("Name", localScoreboardTeam.getName());
      localNBTTagCompound.setString("DisplayName", localScoreboardTeam.getDisplayName());
      localNBTTagCompound.setString("Prefix", localScoreboardTeam.getPrefix());
      localNBTTagCompound.setString("Suffix", localScoreboardTeam.getSuffix());
      localNBTTagCompound.setBoolean("AllowFriendlyFire", localScoreboardTeam.allowFriendlyFire());
      localNBTTagCompound.setBoolean("SeeFriendlyInvisibles", localScoreboardTeam.canSeeFriendlyInvisibles());
      
      NBTTagList localNBTTagList2 = new NBTTagList();
      
      for (String str : localScoreboardTeam.getPlayerNameSet()) {
        localNBTTagList2.add(new NBTTagString(str));
      }
      
      localNBTTagCompound.set("Players", localNBTTagList2);
      
      localNBTTagList1.add(localNBTTagCompound);
    }
    
    return localNBTTagList1;
  }
  
  protected void d(NBTTagCompound paramNBTTagCompound) {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    int i = 0;
    
    for (int j = 0; j < 3; j++) {
      ScoreboardObjective localScoreboardObjective = this.b.getObjectiveForSlot(j);
      
      if (localScoreboardObjective != null) {
        localNBTTagCompound.setString("slot_" + j, localScoreboardObjective.getName());
        i = 1;
      }
    }
    
    if (i != 0) paramNBTTagCompound.set("DisplaySlots", localNBTTagCompound);
  }
  
  protected NBTTagList b() {
    NBTTagList localNBTTagList = new NBTTagList();
    Collection localCollection = this.b.getObjectives();
    
    for (ScoreboardObjective localScoreboardObjective : localCollection) {
      NBTTagCompound localNBTTagCompound = new NBTTagCompound();
      
      localNBTTagCompound.setString("Name", localScoreboardObjective.getName());
      localNBTTagCompound.setString("CriteriaName", localScoreboardObjective.getCriteria().getName());
      localNBTTagCompound.setString("DisplayName", localScoreboardObjective.getDisplayName());
      
      localNBTTagList.add(localNBTTagCompound);
    }
    
    return localNBTTagList;
  }
  
  protected NBTTagList e() {
    NBTTagList localNBTTagList = new NBTTagList();
    Collection localCollection = this.b.getScores();
    
    for (ScoreboardScore localScoreboardScore : localCollection) {
      NBTTagCompound localNBTTagCompound = new NBTTagCompound();
      
      localNBTTagCompound.setString("Name", localScoreboardScore.getPlayerName());
      localNBTTagCompound.setString("Objective", localScoreboardScore.getObjective().getName());
      localNBTTagCompound.setInt("Score", localScoreboardScore.getScore());
      
      localNBTTagList.add(localNBTTagCompound);
    }
    
    return localNBTTagList;
  }
}
