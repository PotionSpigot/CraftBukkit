package net.minecraft.server;

import java.util.List;






public class CommandMe
  extends CommandAbstract
{
  public String getCommand()
  {
    return "me";
  }
  
  public int a()
  {
    return 0;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.me.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      IChatBaseComponent localIChatBaseComponent = a(paramICommandListener, paramArrayOfString, 0, paramICommandListener.a(1, "me"));
      MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.emote", new Object[] { paramICommandListener.getScoreboardDisplayName(), localIChatBaseComponent }));
      return;
    }
    
    throw new ExceptionUsage("commands.me.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
  }
}
