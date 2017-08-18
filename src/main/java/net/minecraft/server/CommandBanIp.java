package net.minecraft.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;









public class CommandBanIp
  extends CommandAbstract
{
  public static final Pattern a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
  

  public String getCommand()
  {
    return "ban-ip";
  }
  
  public int a()
  {
    return 3;
  }
  
  public boolean canUse(ICommandListener paramICommandListener)
  {
    return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled()) && (super.canUse(paramICommandListener));
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.banip.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length >= 1) && (paramArrayOfString[0].length() > 1)) {
      Matcher localMatcher = a.matcher(paramArrayOfString[0]);
      IChatBaseComponent localIChatBaseComponent = null;
      
      if (paramArrayOfString.length >= 2) {
        localIChatBaseComponent = a(paramICommandListener, paramArrayOfString, 1);
      }
      
      if (localMatcher.matches()) {
        a(paramICommandListener, paramArrayOfString[0], localIChatBaseComponent == null ? null : localIChatBaseComponent.c());
      } else {
        EntityPlayer localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
        
        if (localEntityPlayer == null) {
          throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
        }
        
        a(paramICommandListener, localEntityPlayer.s(), localIChatBaseComponent == null ? null : localIChatBaseComponent.c());
      }
      
      return;
    }
    
    throw new ExceptionUsage("commands.banip.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
  
  protected void a(ICommandListener paramICommandListener, String paramString1, String paramString2) {
    IpBanEntry localIpBanEntry = new IpBanEntry(paramString1, null, paramICommandListener.getName(), null, paramString2);
    MinecraftServer.getServer().getPlayerList().getIPBans().add(localIpBanEntry);
    
    List localList = MinecraftServer.getServer().getPlayerList().b(paramString1);
    String[] arrayOfString = new String[localList.size()];
    int i = 0;
    
    for (EntityPlayer localEntityPlayer : localList) {
      localEntityPlayer.playerConnection.disconnect("You have been IP banned.");
      arrayOfString[(i++)] = localEntityPlayer.getName();
    }
    
    if (localList.isEmpty()) {
      a(paramICommandListener, this, "commands.banip.success", new Object[] { paramString1 });
    } else {
      a(paramICommandListener, this, "commands.banip.success.players", new Object[] { paramString1, a(arrayOfString) });
    }
  }
}
