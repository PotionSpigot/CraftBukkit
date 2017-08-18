package net.minecraft.server;

import java.util.Date;
import java.util.UUID;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class GameProfileBanEntry extends ExpirableListEntry
{
  public GameProfileBanEntry(GameProfile gameprofile)
  {
    this(gameprofile, (Date)null, (String)null, (Date)null, (String)null);
  }
  
  public GameProfileBanEntry(GameProfile gameprofile, Date date, String s, Date date1, String s1) {
    super(gameprofile, date, s, date1, s1);
  }
  
  public GameProfileBanEntry(JsonObject jsonobject) {
    super(b(jsonobject), jsonobject);
  }
  
  protected void a(JsonObject jsonobject) {
    if (getKey() != null) {
      jsonobject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
      jsonobject.addProperty("name", ((GameProfile)getKey()).getName());
      super.a(jsonobject);
    }
  }
  

  private static GameProfile b(JsonObject jsonobject)
  {
    UUID uuid = null;
    String name = null;
    if (jsonobject.has("uuid")) {
      String s = jsonobject.get("uuid").getAsString();
      try
      {
        uuid = UUID.fromString(s);
      }
      catch (Throwable localThrowable) {}
    }
    
    if (jsonobject.has("name"))
    {
      name = jsonobject.get("name").getAsString();
    }
    if ((uuid != null) || (name != null))
    {
      return new GameProfile(uuid, name);
    }
    return null;
  }
}
