package net.minecraft.server;

import java.io.File;
import java.net.SocketAddress;
import net.minecraft.util.com.google.gson.JsonObject;

public class IpBanList extends JsonList
{
  public IpBanList(File paramFile)
  {
    super(paramFile);
  }
  
  protected JsonListEntry a(JsonObject paramJsonObject)
  {
    return new IpBanEntry(paramJsonObject);
  }
  
  public boolean isBanned(SocketAddress paramSocketAddress) {
    String str = c(paramSocketAddress);
    return d(str);
  }
  
  public IpBanEntry get(SocketAddress paramSocketAddress) {
    String str = c(paramSocketAddress);
    return (IpBanEntry)get(str);
  }
  
  private String c(SocketAddress paramSocketAddress) {
    String str = paramSocketAddress.toString();
    if (str.contains("/")) {
      str = str.substring(str.indexOf('/') + 1);
    }
    if (str.contains(":")) {
      str = str.substring(0, str.indexOf(':'));
    }
    return str;
  }
}
