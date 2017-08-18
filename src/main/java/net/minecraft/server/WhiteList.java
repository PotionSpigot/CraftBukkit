package net.minecraft.server;

import java.io.File;
import java.util.Map;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class WhiteList extends JsonList
{
  public WhiteList(File paramFile)
  {
    super(paramFile);
  }
  
  protected JsonListEntry a(JsonObject paramJsonObject)
  {
    return new WhiteListEntry(paramJsonObject);
  }
  
  public boolean isWhitelisted(GameProfile paramGameProfile) {
    return d(paramGameProfile);
  }
  
  public String[] getEntries()
  {
    String[] arrayOfString = new String[e().size()];
    int i = 0;
    for (WhiteListEntry localWhiteListEntry : e().values()) {
      arrayOfString[(i++)] = ((GameProfile)localWhiteListEntry.getKey()).getName();
    }
    return arrayOfString;
  }
  
  protected String b(GameProfile paramGameProfile)
  {
    return paramGameProfile.getId().toString();
  }
  
  public GameProfile a(String paramString) {
    for (WhiteListEntry localWhiteListEntry : e().values()) {
      if (paramString.equalsIgnoreCase(((GameProfile)localWhiteListEntry.getKey()).getName())) {
        return (GameProfile)localWhiteListEntry.getKey();
      }
    }
    return null;
  }
}
