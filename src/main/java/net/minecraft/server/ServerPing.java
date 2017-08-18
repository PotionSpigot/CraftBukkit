package net.minecraft.server;



public class ServerPing
{
  private IChatBaseComponent a;
  

  private ServerPingPlayerSample b;
  

  private ServerPingServerData c;
  

  private String d;
  

  public IChatBaseComponent a()
  {
    return this.a;
  }
  
  public void setMOTD(IChatBaseComponent paramIChatBaseComponent) {
    this.a = paramIChatBaseComponent;
  }
  
  public ServerPingPlayerSample b() {
    return this.b;
  }
  
  public void setPlayerSample(ServerPingPlayerSample paramServerPingPlayerSample) {
    this.b = paramServerPingPlayerSample;
  }
  
  public ServerPingServerData c() {
    return this.c;
  }
  
  public void setServerInfo(ServerPingServerData paramServerPingServerData) {
    this.c = paramServerPingServerData;
  }
  
  public void setFavicon(String paramString) {
    this.d = paramString;
  }
  
  public String d() {
    return this.d;
  }
}
