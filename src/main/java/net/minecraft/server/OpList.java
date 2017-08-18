package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class OpList extends JsonList
{
  public OpList(java.io.File paramFile)
  {
    super(paramFile);
  }
  
  protected JsonListEntry a(JsonObject paramJsonObject)
  {
    return new OpListEntry(paramJsonObject);
  }
  
  public String[] getEntries()
  {
    String[] arrayOfString = new String[e().size()];
    int i = 0;
    for (OpListEntry localOpListEntry : e().values()) {
      arrayOfString[(i++)] = ((GameProfile)localOpListEntry.getKey()).getName();
    }
    return arrayOfString;
  }
  








  protected String b(GameProfile paramGameProfile)
  {
    return paramGameProfile.getId().toString();
  }
  
  public GameProfile a(String paramString) {
    for (OpListEntry localOpListEntry : e().values()) {
      if (paramString.equalsIgnoreCase(((GameProfile)localOpListEntry.getKey()).getName())) {
        return (GameProfile)localOpListEntry.getKey();
      }
    }
    return null;
  }
}
