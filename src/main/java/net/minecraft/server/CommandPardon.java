package net.minecraft.server;

import java.util.List;
import net.minecraft.util.com.mojang.authlib.GameProfile;





public class CommandPardon
  extends CommandAbstract
{
  public String getCommand()
  {
    return "pardon";
  }
  
  public int a()
  {
    return 3;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.unban.usage";
  }
  
  public boolean canUse(ICommandListener paramICommandListener)
  {
    return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && (super.canUse(paramICommandListener));
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length == 1) && (paramArrayOfString[0].length() > 0)) {
      MinecraftServer localMinecraftServer = MinecraftServer.getServer();
      GameProfile localGameProfile = localMinecraftServer.getPlayerList().getProfileBans().a(paramArrayOfString[0]);
      if (localGameProfile == null) {
        throw new CommandException("commands.unban.failed", new Object[] { paramArrayOfString[0] });
      }
      
      localMinecraftServer.getPlayerList().getProfileBans().remove(localGameProfile);
      a(paramICommandListener, this, "commands.unban.success", new Object[] { paramArrayOfString[0] });
      return;
    }
    
    throw new ExceptionUsage("commands.unban.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries());
    }
    
    return null;
  }
}
