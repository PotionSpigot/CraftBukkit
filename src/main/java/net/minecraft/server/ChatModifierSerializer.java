package net.minecraft.server;

import java.lang.reflect.Type;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonPrimitive;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;










































































































































































































































public class ChatModifierSerializer
  implements JsonDeserializer, JsonSerializer
{
  public ChatModifier a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
  {
    if (paramJsonElement.isJsonObject()) {
      ChatModifier localChatModifier = new ChatModifier();
      JsonObject localJsonObject1 = paramJsonElement.getAsJsonObject();
      if (localJsonObject1 == null) {
        return null;
      }
      
      if (localJsonObject1.has("bold")) ChatModifier.a(localChatModifier, Boolean.valueOf(localJsonObject1.get("bold").getAsBoolean()));
      if (localJsonObject1.has("italic")) ChatModifier.b(localChatModifier, Boolean.valueOf(localJsonObject1.get("italic").getAsBoolean()));
      if (localJsonObject1.has("underlined")) ChatModifier.c(localChatModifier, Boolean.valueOf(localJsonObject1.get("underlined").getAsBoolean()));
      if (localJsonObject1.has("strikethrough")) ChatModifier.d(localChatModifier, Boolean.valueOf(localJsonObject1.get("strikethrough").getAsBoolean()));
      if (localJsonObject1.has("obfuscated")) ChatModifier.e(localChatModifier, Boolean.valueOf(localJsonObject1.get("obfuscated").getAsBoolean()));
      if (localJsonObject1.has("color")) ChatModifier.a(localChatModifier, (EnumChatFormat)paramJsonDeserializationContext.deserialize(localJsonObject1.get("color"), EnumChatFormat.class));
      JsonObject localJsonObject2;
      JsonPrimitive localJsonPrimitive; Object localObject1; Object localObject2; if (localJsonObject1.has("clickEvent")) {
        localJsonObject2 = localJsonObject1.getAsJsonObject("clickEvent");
        if (localJsonObject2 != null) {
          localJsonPrimitive = localJsonObject2.getAsJsonPrimitive("action");
          localObject1 = localJsonPrimitive == null ? null : EnumClickAction.a(localJsonPrimitive.getAsString());
          
          localObject2 = localJsonObject2.getAsJsonPrimitive("value");
          String str = localObject2 == null ? null : ((JsonPrimitive)localObject2).getAsString();
          
          if ((localObject1 != null) && (str != null) && (((EnumClickAction)localObject1).a())) {
            ChatModifier.a(localChatModifier, new ChatClickable((EnumClickAction)localObject1, str));
          }
        }
      }
      
      if (localJsonObject1.has("hoverEvent")) {
        localJsonObject2 = localJsonObject1.getAsJsonObject("hoverEvent");
        if (localJsonObject2 != null) {
          localJsonPrimitive = localJsonObject2.getAsJsonPrimitive("action");
          localObject1 = localJsonPrimitive == null ? null : EnumHoverAction.a(localJsonPrimitive.getAsString());
          
          localObject2 = (IChatBaseComponent)paramJsonDeserializationContext.deserialize(localJsonObject2.get("value"), IChatBaseComponent.class);
          
          if ((localObject1 != null) && (localObject2 != null) && (((EnumHoverAction)localObject1).a())) {
            ChatModifier.a(localChatModifier, new ChatHoverable((EnumHoverAction)localObject1, (IChatBaseComponent)localObject2));
          }
        }
      }
      
      return localChatModifier;
    }
    
    return null;
  }
  

  public JsonElement a(ChatModifier paramChatModifier, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    if (paramChatModifier.g()) return null;
    JsonObject localJsonObject1 = new JsonObject();
    
    if (ChatModifier.b(paramChatModifier) != null) localJsonObject1.addProperty("bold", ChatModifier.b(paramChatModifier));
    if (ChatModifier.c(paramChatModifier) != null) localJsonObject1.addProperty("italic", ChatModifier.c(paramChatModifier));
    if (ChatModifier.d(paramChatModifier) != null) localJsonObject1.addProperty("underlined", ChatModifier.d(paramChatModifier));
    if (ChatModifier.e(paramChatModifier) != null) localJsonObject1.addProperty("strikethrough", ChatModifier.e(paramChatModifier));
    if (ChatModifier.f(paramChatModifier) != null) localJsonObject1.addProperty("obfuscated", ChatModifier.f(paramChatModifier));
    if (ChatModifier.g(paramChatModifier) != null) localJsonObject1.add("color", paramJsonSerializationContext.serialize(ChatModifier.g(paramChatModifier)));
    JsonObject localJsonObject2;
    if (ChatModifier.h(paramChatModifier) != null) {
      localJsonObject2 = new JsonObject();
      localJsonObject2.addProperty("action", ChatModifier.h(paramChatModifier).a().b());
      localJsonObject2.addProperty("value", ChatModifier.h(paramChatModifier).b());
      localJsonObject1.add("clickEvent", localJsonObject2);
    }
    
    if (ChatModifier.i(paramChatModifier) != null) {
      localJsonObject2 = new JsonObject();
      localJsonObject2.addProperty("action", ChatModifier.i(paramChatModifier).a().b());
      localJsonObject2.add("value", paramJsonSerializationContext.serialize(ChatModifier.i(paramChatModifier).b()));
      localJsonObject1.add("hoverEvent", localJsonObject2);
    }
    
    return localJsonObject1;
  }
}
