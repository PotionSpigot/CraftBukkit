package net.minecraft.server;

import java.lang.reflect.Type;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;




















































































































































public class ServerPingSerializer
  implements JsonDeserializer, JsonSerializer
{
  public ServerPing a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
  {
    JsonObject localJsonObject = ChatDeserializer.l(paramJsonElement, "status");
    ServerPing localServerPing = new ServerPing();
    
    if (localJsonObject.has("description")) {
      localServerPing.setMOTD((IChatBaseComponent)paramJsonDeserializationContext.deserialize(localJsonObject.get("description"), IChatBaseComponent.class));
    }
    
    if (localJsonObject.has("players")) {
      localServerPing.setPlayerSample((ServerPingPlayerSample)paramJsonDeserializationContext.deserialize(localJsonObject.get("players"), ServerPingPlayerSample.class));
    }
    
    if (localJsonObject.has("version")) {
      localServerPing.setServerInfo((ServerPingServerData)paramJsonDeserializationContext.deserialize(localJsonObject.get("version"), ServerPingServerData.class));
    }
    
    if (localJsonObject.has("favicon")) {
      localServerPing.setFavicon(ChatDeserializer.h(localJsonObject, "favicon"));
    }
    
    return localServerPing;
  }
  
  public JsonElement a(ServerPing paramServerPing, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    JsonObject localJsonObject = new JsonObject();
    
    if (paramServerPing.a() != null) {
      localJsonObject.add("description", paramJsonSerializationContext.serialize(paramServerPing.a()));
    }
    
    if (paramServerPing.b() != null) {
      localJsonObject.add("players", paramJsonSerializationContext.serialize(paramServerPing.b()));
    }
    
    if (paramServerPing.c() != null) {
      localJsonObject.add("version", paramJsonSerializationContext.serialize(paramServerPing.c()));
    }
    
    if (paramServerPing.d() != null) {
      localJsonObject.addProperty("favicon", paramServerPing.d());
    }
    
    return localJsonObject;
  }
}
