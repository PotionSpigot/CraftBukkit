package net.minecraft.server;

import java.lang.reflect.Type;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;



































































































































public class ServerPingServerDataSerializer
  implements JsonDeserializer, JsonSerializer
{
  public ServerPingServerData a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
  {
    JsonObject localJsonObject = ChatDeserializer.l(paramJsonElement, "version");
    return new ServerPingServerData(ChatDeserializer.h(localJsonObject, "name"), ChatDeserializer.m(localJsonObject, "protocol"));
  }
  
  public JsonElement a(ServerPingServerData paramServerPingServerData, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    JsonObject localJsonObject = new JsonObject();
    localJsonObject.addProperty("name", paramServerPingServerData.a());
    localJsonObject.addProperty("protocol", Integer.valueOf(paramServerPingServerData.b()));
    return localJsonObject;
  }
}
