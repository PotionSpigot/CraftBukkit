package net.minecraft.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CommandPardonIP
  extends CommandAbstract
{
  public String getCommand()
  {
    return "pardon-ip";
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
    return "commands.unbanip.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length == 1) && (paramArrayOfString[0].length() > 1)) {
      Matcher localMatcher = CommandBanIp.a.matcher(paramArrayOfString[0]);
      
      if (localMatcher.matches()) {
        MinecraftServer.getServer().getPlayerList().getIPBans().remove(paramArrayOfString[0]);
        a(paramICommandListener, this, "commands.unbanip.success", new Object[] { paramArrayOfString[0] });
        return;
      }
      throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
    }
    

    throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getIPBans().getEntries());
    }
    
    return null;
  }
}
