package net.minecraft.server;

import java.util.List;
import net.minecraft.util.com.mojang.authlib.GameProfile;






public class CommandWhitelist
  extends CommandAbstract
{
  public String getCommand()
  {
    return "whitelist";
  }
  
  public int a()
  {
    return 3;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.whitelist.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1) {
      MinecraftServer localMinecraftServer = MinecraftServer.getServer();
      if (paramArrayOfString[0].equals("on")) {
        localMinecraftServer.getPlayerList().setHasWhitelist(true);
        a(paramICommandListener, this, "commands.whitelist.enabled", new Object[0]);
        return; }
      if (paramArrayOfString[0].equals("off")) {
        localMinecraftServer.getPlayerList().setHasWhitelist(false);
        a(paramICommandListener, this, "commands.whitelist.disabled", new Object[0]); return; }
      Object localObject;
      if (paramArrayOfString[0].equals("list")) {
        paramICommandListener.sendMessage(new ChatMessage("commands.whitelist.list", new Object[] { Integer.valueOf(localMinecraftServer.getPlayerList().getWhitelisted().length), Integer.valueOf(localMinecraftServer.getPlayerList().getSeenPlayers().length) }));
        
        localObject = localMinecraftServer.getPlayerList().getWhitelisted();
        paramICommandListener.sendMessage(new ChatComponentText(a((Object[])localObject)));
        return; }
      if (paramArrayOfString[0].equals("add")) {
        if (paramArrayOfString.length < 2) {
          throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
        }
        
        localObject = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[1]);
        if (localObject == null) {
          throw new CommandException("commands.whitelist.add.failed", new Object[] { paramArrayOfString[1] });
        }
        localMinecraftServer.getPlayerList().addWhitelist((GameProfile)localObject);
        a(paramICommandListener, this, "commands.whitelist.add.success", new Object[] { paramArrayOfString[1] });
        return; }
      if (paramArrayOfString[0].equals("remove")) {
        if (paramArrayOfString.length < 2) {
          throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
        }
        
        localObject = localMinecraftServer.getPlayerList().getWhitelist().a(paramArrayOfString[1]);
        if (localObject == null) {
          throw new CommandException("commands.whitelist.remove.failed", new Object[] { paramArrayOfString[1] });
        }
        localMinecraftServer.getPlayerList().removeWhitelist((GameProfile)localObject);
        a(paramICommandListener, this, "commands.whitelist.remove.success", new Object[] { paramArrayOfString[1] });
        return; }
      if (paramArrayOfString[0].equals("reload")) {
        localMinecraftServer.getPlayerList().reloadWhitelist();
        a(paramICommandListener, this, "commands.whitelist.reloaded", new Object[0]);
        return;
      }
    }
    
    throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "on", "off", "list", "add", "remove", "reload" });
    }
    
    if (paramArrayOfString.length == 2) {
      if (paramArrayOfString[0].equals("remove"))
        return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getWhitelisted());
      if (paramArrayOfString[0].equals("add")) {
        return a(paramArrayOfString, MinecraftServer.getServer().getUserCache().a());
      }
    }
    
    return null;
  }
}
