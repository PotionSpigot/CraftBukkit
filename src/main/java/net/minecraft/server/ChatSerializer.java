package net.minecraft.server;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParseException;
import net.minecraft.util.com.google.gson.JsonPrimitive;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;








public class ChatSerializer
  implements JsonDeserializer, JsonSerializer
{
  private static final Gson a;
  
  static
  {
    GsonBuilder localGsonBuilder = new GsonBuilder();
    localGsonBuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer());
    localGsonBuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer());
    localGsonBuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
    a = localGsonBuilder.create();
  }
  
  public IChatBaseComponent a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext)
  {
    if (paramJsonElement.isJsonPrimitive())
    {
      return new ChatComponentText(paramJsonElement.getAsString()); }
    Object localObject1; Object localObject2; Object localObject3; Object localObject4; if (paramJsonElement.isJsonObject()) {
      localObject1 = paramJsonElement.getAsJsonObject();
      

      if (((JsonObject)localObject1).has("text")) {
        localObject2 = new ChatComponentText(((JsonObject)localObject1).get("text").getAsString());
      } else if (((JsonObject)localObject1).has("translate")) {
        localObject3 = ((JsonObject)localObject1).get("translate").getAsString();
        
        if (((JsonObject)localObject1).has("with")) {
          JsonArray localJsonArray = ((JsonObject)localObject1).getAsJsonArray("with");
          localObject4 = new Object[localJsonArray.size()];
          
          for (int j = 0; j < localObject4.length; j++) {
            localObject4[j] = a(localJsonArray.get(j), paramType, paramJsonDeserializationContext);
            
            if ((localObject4[j] instanceof ChatComponentText)) {
              ChatComponentText localChatComponentText = (ChatComponentText)localObject4[j];
              if ((localChatComponentText.getChatModifier().g()) && (localChatComponentText.a().isEmpty())) {
                localObject4[j] = localChatComponentText.g();
              }
            }
          }
          
          localObject2 = new ChatMessage((String)localObject3, (Object[])localObject4);
        } else {
          localObject2 = new ChatMessage((String)localObject3, new Object[0]);
        }
      } else {
        throw new JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
      }
      
      if (((JsonObject)localObject1).has("extra")) {
        localObject3 = ((JsonObject)localObject1).getAsJsonArray("extra");
        
        if (((JsonArray)localObject3).size() > 0) {
          for (int i = 0; i < ((JsonArray)localObject3).size(); i++) {
            ((IChatBaseComponent)localObject2).addSibling(a(((JsonArray)localObject3).get(i), paramType, paramJsonDeserializationContext));
          }
        } else {
          throw new JsonParseException("Unexpected empty array of components");
        }
      }
      
      ((IChatBaseComponent)localObject2).setChatModifier((ChatModifier)paramJsonDeserializationContext.deserialize(paramJsonElement, ChatModifier.class));
      
      return (IChatBaseComponent)localObject2; }
    if (paramJsonElement.isJsonArray())
    {
      localObject1 = paramJsonElement.getAsJsonArray();
      localObject2 = null;
      
      for (localObject3 = ((JsonArray)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { JsonElement localJsonElement = (JsonElement)((Iterator)localObject3).next();
        localObject4 = a(localJsonElement, localJsonElement.getClass(), paramJsonDeserializationContext);
        if (localObject2 == null) {
          localObject2 = localObject4;
        } else {
          ((IChatBaseComponent)localObject2).addSibling((IChatBaseComponent)localObject4);
        }
      }
      
      return (IChatBaseComponent)localObject2;
    }
    throw new JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
  }
  
  private void a(ChatModifier paramChatModifier, JsonObject paramJsonObject, JsonSerializationContext paramJsonSerializationContext)
  {
    JsonElement localJsonElement = paramJsonSerializationContext.serialize(paramChatModifier);
    
    if (localJsonElement.isJsonObject()) {
      JsonObject localJsonObject = (JsonObject)localJsonElement;
      for (Map.Entry localEntry : localJsonObject.entrySet()) {
        paramJsonObject.add((String)localEntry.getKey(), (JsonElement)localEntry.getValue());
      }
    }
  }
  
  public JsonElement a(IChatBaseComponent paramIChatBaseComponent, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    if (((paramIChatBaseComponent instanceof ChatComponentText)) && (paramIChatBaseComponent.getChatModifier().g()) && (paramIChatBaseComponent.a().isEmpty())) {
      return new JsonPrimitive(((ChatComponentText)paramIChatBaseComponent).g());
    }
    
    JsonObject localJsonObject = new JsonObject();
    
    if (!paramIChatBaseComponent.getChatModifier().g())
      a(paramIChatBaseComponent.getChatModifier(), localJsonObject, paramJsonSerializationContext);
    Object localObject1;
    Object localObject2;
    Object localObject3; if (!paramIChatBaseComponent.a().isEmpty()) {
      localObject1 = new JsonArray();
      
      for (localObject2 = paramIChatBaseComponent.a().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (IChatBaseComponent)((Iterator)localObject2).next();
        ((JsonArray)localObject1).add(a((IChatBaseComponent)localObject3, localObject3.getClass(), paramJsonSerializationContext));
      }
      
      localJsonObject.add("extra", (JsonElement)localObject1);
    }
    
    if ((paramIChatBaseComponent instanceof ChatComponentText)) {
      localJsonObject.addProperty("text", ((ChatComponentText)paramIChatBaseComponent).g());
    } else if ((paramIChatBaseComponent instanceof ChatMessage)) {
      localObject1 = (ChatMessage)paramIChatBaseComponent;
      localJsonObject.addProperty("translate", ((ChatMessage)localObject1).i());
      
      if ((((ChatMessage)localObject1).j() != null) && (((ChatMessage)localObject1).j().length > 0)) {
        localObject2 = new JsonArray();
        
        for (Object localObject4 : ((ChatMessage)localObject1).j()) {
          if ((localObject4 instanceof IChatBaseComponent)) {
            ((JsonArray)localObject2).add(a((IChatBaseComponent)localObject4, localObject4.getClass(), paramJsonSerializationContext));
          } else {
            ((JsonArray)localObject2).add(new JsonPrimitive(String.valueOf(localObject4)));
          }
        }
        
        localJsonObject.add("with", (JsonElement)localObject2);
      }
    } else {
      throw new IllegalArgumentException("Don't know how to serialize " + paramIChatBaseComponent + " as a Component");
    }
    
    return localJsonObject;
  }
  
  public static String a(IChatBaseComponent paramIChatBaseComponent) {
    return a.toJson(paramIChatBaseComponent);
  }
  
  public static IChatBaseComponent a(String paramString) {
    return (IChatBaseComponent)a.fromJson(paramString, IChatBaseComponent.class);
  }
}
