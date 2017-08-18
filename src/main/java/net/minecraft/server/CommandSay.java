package net.minecraft.server;

import java.util.List;






public class CommandSay
  extends CommandAbstract
{
  public String getCommand()
  {
    return "say";
  }
  
  public int a()
  {
    return 1;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.say.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length > 0) && (paramArrayOfString[0].length() > 0)) {
      IChatBaseComponent localIChatBaseComponent = a(paramICommandListener, paramArrayOfString, 0, true);
      MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.announcement", new Object[] { paramICommandListener.getName(), localIChatBaseComponent }));
      return;
    }
    
    throw new ExceptionUsage("commands.say.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
}
