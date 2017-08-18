package net.minecraft.server;

import java.util.Set;
import net.minecraft.util.com.google.common.collect.ForwardingSet;
import net.minecraft.util.com.google.common.collect.Sets;
import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonPrimitive;

public class AchievementSet extends ForwardingSet implements IJsonStatistic
{
  private final Set a = Sets.newHashSet();
  
  public void a(JsonElement paramJsonElement)
  {
    if (paramJsonElement.isJsonArray()) {
      for (JsonElement localJsonElement : paramJsonElement.getAsJsonArray()) {
        add(localJsonElement.getAsString());
      }
    }
  }
  
  public JsonElement a()
  {
    JsonArray localJsonArray = new JsonArray();
    
    for (String str : this) {
      localJsonArray.add(new JsonPrimitive(str));
    }
    
    return localJsonArray;
  }
  
  protected Set delegate()
  {
    return this.a;
  }
}
