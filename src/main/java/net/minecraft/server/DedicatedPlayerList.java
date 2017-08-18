package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends PlayerList
{
  private static final Logger g = ;
  
  public DedicatedPlayerList(DedicatedServer paramDedicatedServer) {
    super(paramDedicatedServer);
    
    a(paramDedicatedServer.a("view-distance", 10));
    this.maxPlayers = paramDedicatedServer.a("max-players", 20);
    setHasWhitelist(paramDedicatedServer.a("white-list", false));
    
    if (!paramDedicatedServer.N()) {
      getProfileBans().a(true);
      getIPBans().a(true);
    }
    
    y();
    w();
    x();
    v();
    z();
    B();
    A();
    if (!getWhitelist().c().exists()) {
      C();
    }
  }
  
  public void setHasWhitelist(boolean paramBoolean)
  {
    super.setHasWhitelist(paramBoolean);
    getServer().a("white-list", Boolean.valueOf(paramBoolean));
    getServer().a();
  }
  
  public void addOp(GameProfile paramGameProfile)
  {
    super.addOp(paramGameProfile);
    A();
  }
  
  public void removeOp(GameProfile paramGameProfile)
  {
    super.removeOp(paramGameProfile);
    A();
  }
  
  public void removeWhitelist(GameProfile paramGameProfile)
  {
    super.removeWhitelist(paramGameProfile);
    C();
  }
  
  public void addWhitelist(GameProfile paramGameProfile)
  {
    super.addWhitelist(paramGameProfile);
    C();
  }
  
  public void reloadWhitelist()
  {
    B();
  }
  
  private void v() {
    try {
      getIPBans().save();
    } catch (IOException localIOException) {
      g.warn("Failed to save ip banlist: ", localIOException);
    }
  }
  
  private void w() {
    try {
      getProfileBans().save();
    } catch (IOException localIOException) {
      g.warn("Failed to save user banlist: ", localIOException);
    }
  }
  
  private void x() {
    try {
      getIPBans().load();
    } catch (IOException localIOException) {
      g.warn("Failed to load ip banlist: ", localIOException);
    }
  }
  
  private void y() {
    try {
      getProfileBans().load();
    } catch (IOException localIOException) {
      g.warn("Failed to load user banlist: ", localIOException);
    }
  }
  
  private void z() {
    try {
      getOPs().load();
    } catch (Exception localException) {
      g.warn("Failed to load operators list: ", localException);
    }
  }
  
  private void A() {
    try {
      getOPs().save();
    } catch (Exception localException) {
      g.warn("Failed to save operators list: ", localException);
    }
  }
  
  private void B() {
    try {
      getWhitelist().load();
    } catch (Exception localException) {
      g.warn("Failed to load white-list: ", localException);
    }
  }
  
  private void C() {
    try {
      getWhitelist().save();
    } catch (Exception localException) {
      g.warn("Failed to save white-list: ", localException);
    }
  }
  
  public boolean isWhitelisted(GameProfile paramGameProfile)
  {
    return (!getHasWhitelist()) || (isOp(paramGameProfile)) || (getWhitelist().isWhitelisted(paramGameProfile));
  }
  
  public DedicatedServer getServer()
  {
    return (DedicatedServer)super.getServer();
  }
}
