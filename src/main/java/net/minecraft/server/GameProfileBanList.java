package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class GameProfileBanList extends JsonList
{
  public GameProfileBanList(java.io.File paramFile)
  {
    super(paramFile);
  }
  
  protected JsonListEntry a(JsonObject paramJsonObject)
  {
    return new GameProfileBanEntry(paramJsonObject);
  }
  
  public boolean isBanned(GameProfile paramGameProfile) {
    return d(paramGameProfile);
  }
  
  public String[] getEntries()
  {
    String[] arrayOfString = new String[e().size()];
    int i = 0;
    for (GameProfileBanEntry localGameProfileBanEntry : e().values()) {
      arrayOfString[(i++)] = ((GameProfile)localGameProfileBanEntry.getKey()).getName();
    }
    return arrayOfString;
  }
  
  protected String b(GameProfile paramGameProfile)
  {
    return paramGameProfile.getId().toString();
  }
  
  public GameProfile a(String paramString) {
    for (GameProfileBanEntry localGameProfileBanEntry : e().values()) {
      if (paramString.equalsIgnoreCase(((GameProfile)localGameProfileBanEntry.getKey()).getName())) {
        return (GameProfile)localGameProfileBanEntry.getKey();
      }
    }
    return null;
  }
}
