package net.minecraft.server;

import java.util.UUID;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class WhiteListEntry extends JsonListEntry
{
  public WhiteListEntry(GameProfile paramGameProfile)
  {
    super(paramGameProfile);
  }
  
  public WhiteListEntry(JsonObject paramJsonObject) {
    super(b(paramJsonObject), paramJsonObject);
  }
  
  protected void a(JsonObject paramJsonObject)
  {
    if (getKey() == null) {
      return;
    }
    paramJsonObject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
    paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
    super.a(paramJsonObject);
  }
  
  private static GameProfile b(JsonObject paramJsonObject) {
    if ((!paramJsonObject.has("uuid")) || (!paramJsonObject.has("name"))) {
      return null;
    }
    String str = paramJsonObject.get("uuid").getAsString();
    UUID localUUID;
    try {
      localUUID = UUID.fromString(str);
    } catch (Throwable localThrowable) {
      return null;
    }
    return new GameProfile(localUUID, paramJsonObject.get("name").getAsString());
  }
}
