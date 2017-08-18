package net.minecraft.server;

import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;
import net.minecraft.util.com.mojang.authlib.GameProfile;































































public class ServerPingPlayerSampleSerializer
  implements JsonDeserializer, JsonSerializer
{
  public ServerPingPlayerSample a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
  {
    JsonObject localJsonObject1 = ChatDeserializer.l(paramJsonElement, "players");
    ServerPingPlayerSample localServerPingPlayerSample = new ServerPingPlayerSample(ChatDeserializer.m(localJsonObject1, "max"), ChatDeserializer.m(localJsonObject1, "online"));
    
    if (ChatDeserializer.d(localJsonObject1, "sample")) {
      JsonArray localJsonArray = ChatDeserializer.t(localJsonObject1, "sample");
      if (localJsonArray.size() > 0) {
        GameProfile[] arrayOfGameProfile = new GameProfile[localJsonArray.size()];
        for (int i = 0; i < arrayOfGameProfile.length; i++) {
          JsonObject localJsonObject2 = ChatDeserializer.l(localJsonArray.get(i), "player[" + i + "]");
          String str = ChatDeserializer.h(localJsonObject2, "id");
          arrayOfGameProfile[i] = new GameProfile(UUID.fromString(str), ChatDeserializer.h(localJsonObject2, "name"));
        }
        localServerPingPlayerSample.a(arrayOfGameProfile);
      }
    }
    
    return localServerPingPlayerSample;
  }
  
  public JsonElement a(ServerPingPlayerSample paramServerPingPlayerSample, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    JsonObject localJsonObject1 = new JsonObject();
    
    localJsonObject1.addProperty("max", Integer.valueOf(paramServerPingPlayerSample.a()));
    localJsonObject1.addProperty("online", Integer.valueOf(paramServerPingPlayerSample.b()));
    
    if ((paramServerPingPlayerSample.c() != null) && (paramServerPingPlayerSample.c().length > 0)) {
      JsonArray localJsonArray = new JsonArray();
      
      for (int i = 0; i < paramServerPingPlayerSample.c().length; i++) {
        JsonObject localJsonObject2 = new JsonObject();
        UUID localUUID = paramServerPingPlayerSample.c()[i].getId();
        localJsonObject2.addProperty("id", localUUID == null ? "" : localUUID.toString());
        localJsonObject2.addProperty("name", paramServerPingPlayerSample.c()[i].getName());
        localJsonArray.add(localJsonObject2);
      }
      
      localJsonObject1.add("sample", localJsonArray);
    }
    
    return localJsonObject1;
  }
}
