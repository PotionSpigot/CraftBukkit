package net.minecraft.server;

import net.minecraft.util.com.google.gson.JsonElement;

public abstract interface IJsonStatistic
{
  public abstract void a(JsonElement paramJsonElement);
  
  public abstract JsonElement a();
}
