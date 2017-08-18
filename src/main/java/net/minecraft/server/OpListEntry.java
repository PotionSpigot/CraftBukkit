package net.minecraft.server;

import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class OpListEntry extends JsonListEntry
{
  private final int a;
  
  public OpListEntry(GameProfile paramGameProfile, int paramInt)
  {
    super(paramGameProfile);
    this.a = paramInt;
  }
  
  public OpListEntry(JsonObject paramJsonObject) {
    super(b(paramJsonObject), paramJsonObject);
    this.a = (paramJsonObject.has("level") ? paramJsonObject.get("level").getAsInt() : 0);
  }
  
  public int a() {
    return this.a;
  }
  
  protected void a(JsonObject paramJsonObject)
  {
    if (getKey() == null) {
      return;
    }
    paramJsonObject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
    paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
    super.a(paramJsonObject);
    paramJsonObject.addProperty("level", Integer.valueOf(this.a));
  }
  
  private static GameProfile b(JsonObject paramJsonObject) {
    if ((!paramJsonObject.has("uuid")) || (!paramJsonObject.has("name"))) {
      return null;
    }
    String str = paramJsonObject.get("uuid").getAsString();
    java.util.UUID localUUID;
    try {
      localUUID = java.util.UUID.fromString(str);
    } catch (Throwable localThrowable) {
      return null;
    }
    return new GameProfile(localUUID, paramJsonObject.get("name").getAsString());
  }
}
