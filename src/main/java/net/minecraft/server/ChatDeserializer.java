package net.minecraft.server;

import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonPrimitive;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;








































public class ChatDeserializer
{
  public static boolean d(JsonObject paramJsonObject, String paramString)
  {
    if (!g(paramJsonObject, paramString)) {
      return false;
    }
    if (!paramJsonObject.get(paramString).isJsonArray()) {
      return false;
    }
    return true;
  }
  



















  public static boolean g(JsonObject paramJsonObject, String paramString)
  {
    if (paramJsonObject == null) {
      return false;
    }
    if (paramJsonObject.get(paramString) == null) {
      return false;
    }
    return true;
  }
  
  public static String a(JsonElement paramJsonElement, String paramString) {
    if (paramJsonElement.isJsonPrimitive()) {
      return paramJsonElement.getAsString();
    }
    throw new JsonSyntaxException("Expected " + paramString + " to be a string, was " + d(paramJsonElement));
  }
  
  public static String h(JsonObject paramJsonObject, String paramString)
  {
    if (paramJsonObject.has(paramString)) {
      return a(paramJsonObject.get(paramString), paramString);
    }
    throw new JsonSyntaxException("Missing " + paramString + ", expected to find a string");
  }
  








































































































  public static int f(JsonElement paramJsonElement, String paramString)
  {
    if ((paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isNumber())) {
      return paramJsonElement.getAsInt();
    }
    throw new JsonSyntaxException("Expected " + paramString + " to be a Int, was " + d(paramJsonElement));
  }
  
  public static int m(JsonObject paramJsonObject, String paramString)
  {
    if (paramJsonObject.has(paramString)) {
      return f(paramJsonObject.get(paramString), paramString);
    }
    throw new JsonSyntaxException("Missing " + paramString + ", expected to find a Int");
  }
  
































































































































  public static JsonObject l(JsonElement paramJsonElement, String paramString)
  {
    if (paramJsonElement.isJsonObject()) {
      return paramJsonElement.getAsJsonObject();
    }
    throw new JsonSyntaxException("Expected " + paramString + " to be a JsonObject, was " + d(paramJsonElement));
  }
  
















  public static JsonArray m(JsonElement paramJsonElement, String paramString)
  {
    if (paramJsonElement.isJsonArray()) {
      return paramJsonElement.getAsJsonArray();
    }
    throw new JsonSyntaxException("Expected " + paramString + " to be a JsonArray, was " + d(paramJsonElement));
  }
  
  public static JsonArray t(JsonObject paramJsonObject, String paramString)
  {
    if (paramJsonObject.has(paramString)) {
      return m(paramJsonObject.get(paramString), paramString);
    }
    throw new JsonSyntaxException("Missing " + paramString + ", expected to find a JsonArray");
  }
  








  public static String d(JsonElement paramJsonElement)
  {
    String str = StringUtils.abbreviateMiddle(String.valueOf(paramJsonElement), "...", 10);
    if (paramJsonElement == null) return "null (missing)";
    if (paramJsonElement.isJsonNull()) return "null (json)";
    if (paramJsonElement.isJsonArray()) return "an array (" + str + ")";
    if (paramJsonElement.isJsonObject()) return "an object (" + str + ")";
    if (paramJsonElement.isJsonPrimitive()) {
      JsonPrimitive localJsonPrimitive = paramJsonElement.getAsJsonPrimitive();
      if (localJsonPrimitive.isNumber()) return "a number (" + str + ")";
      if (localJsonPrimitive.isBoolean()) return "a boolean (" + str + ")";
    }
    return str;
  }
}
