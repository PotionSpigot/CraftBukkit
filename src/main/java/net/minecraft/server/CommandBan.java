package net.minecraft.server;

import java.util.List;
import net.minecraft.util.com.mojang.authlib.GameProfile;









public class CommandBan
  extends CommandAbstract
{
  public String getCommand()
  {
    return "ban";
  }
  
  public int a()
  {
    return 3;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.ban.usage";
  }
  
  public boolean canUse(ICommandListener paramICommandListener)
  {
    return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && (super.canUse(paramICommandListener));
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length >= 1) && (paramArrayOfString[0].length() > 0)) {
      MinecraftServer localMinecraftServer = MinecraftServer.getServer();
      GameProfile localGameProfile = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
      if (localGameProfile == null) {
        throw new CommandException("commands.ban.failed", new Object[] { paramArrayOfString[0] });
      }
      
      String str = null;
      if (paramArrayOfString.length >= 2) {
        str = a(paramICommandListener, paramArrayOfString, 1).c();
      }
      
      GameProfileBanEntry localGameProfileBanEntry = new GameProfileBanEntry(localGameProfile, null, paramICommandListener.getName(), null, str);
      localMinecraftServer.getPlayerList().getProfileBans().add(localGameProfileBanEntry);
      
      EntityPlayer localEntityPlayer = localMinecraftServer.getPlayerList().getPlayer(paramArrayOfString[0]);
      if (localEntityPlayer != null) {
        localEntityPlayer.playerConnection.disconnect("You are banned from this server.");
      }
      
      a(paramICommandListener, this, "commands.ban.success", new Object[] { paramArrayOfString[0] });
      return;
    }
    
    throw new ExceptionUsage("commands.ban.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
}
