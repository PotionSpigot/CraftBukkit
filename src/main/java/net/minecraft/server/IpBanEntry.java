package net.minecraft.server;

import net.minecraft.util.com.google.gson.JsonObject;

public class IpBanEntry extends ExpirableListEntry
{
  public IpBanEntry(String paramString)
  {
    this(paramString, null, null, null, null);
  }
  
  public IpBanEntry(String paramString1, java.util.Date paramDate1, String paramString2, java.util.Date paramDate2, String paramString3) {
    super(paramString1, paramDate1, paramString2, paramDate2, paramString3);
  }
  
  public IpBanEntry(JsonObject paramJsonObject) {
    super(b(paramJsonObject), paramJsonObject);
  }
  
  private static String b(JsonObject paramJsonObject) {
    return paramJsonObject.has("ip") ? paramJsonObject.get("ip").getAsString() : null;
  }
  
  protected void a(JsonObject paramJsonObject)
  {
    if (getKey() == null) {
      return;
    }
    paramJsonObject.addProperty("ip", (String)getKey());
    super.a(paramJsonObject);
  }
}
