package net.minecraft.server;

import java.util.Set;
import java.util.TreeMap;













public class GameRules
{
  private TreeMap a = new TreeMap();
  
  public GameRules() {
    a("doFireTick", "true");
    a("mobGriefing", "true");
    a("keepInventory", "false");
    a("doMobSpawning", "true");
    a("doMobLoot", "true");
    a("doTileDrops", "true");
    a("commandBlockOutput", "true");
    a("naturalRegeneration", "true");
    a("doDaylightCycle", "true");
  }
  
  public void a(String paramString1, String paramString2) {
    this.a.put(paramString1, new GameRuleValue(paramString2));
  }
  
  public void set(String paramString1, String paramString2) {
    GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString1);
    if (localGameRuleValue != null) {
      localGameRuleValue.a(paramString2);
    } else {
      a(paramString1, paramString2);
    }
  }
  
  public String get(String paramString) {
    GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
    if (localGameRuleValue != null) {
      return localGameRuleValue.a();
    }
    return "";
  }
  
  public boolean getBoolean(String paramString) {
    GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
    if (localGameRuleValue != null) {
      return localGameRuleValue.b();
    }
    return false;
  }
  















  public NBTTagCompound a()
  {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    
    for (String str : this.a.keySet()) {
      GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(str);
      localNBTTagCompound.setString(str, localGameRuleValue.a());
    }
    
    return localNBTTagCompound;
  }
  
  public void a(NBTTagCompound paramNBTTagCompound) {
    Set localSet = paramNBTTagCompound.c();
    for (String str1 : localSet) {
      String str2 = str1;
      String str3 = paramNBTTagCompound.getString(str1);
      
      set(str2, str3);
    }
  }
  
  public String[] getGameRules() {
    return (String[])this.a.keySet().toArray(new String[0]);
  }
  
  public boolean contains(String paramString) {
    return this.a.containsKey(paramString);
  }
}
