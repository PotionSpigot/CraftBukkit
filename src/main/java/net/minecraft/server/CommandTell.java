package net.minecraft.server;

import java.util.Arrays;
import java.util.List;









public class CommandTell
  extends CommandAbstract
{
  public List b()
  {
    return Arrays.asList(new String[] { "w", "msg" });
  }
  
  public String getCommand()
  {
    return "tell";
  }
  
  public int a()
  {
    return 0;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.message.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 2) { throw new ExceptionUsage("commands.message.usage", new Object[0]);
    }
    EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[0]);
    
    if (localEntityPlayer == null) throw new ExceptionPlayerNotFound();
    if (localEntityPlayer == paramICommandListener) { throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
    }
    IChatBaseComponent localIChatBaseComponent = a(paramICommandListener, paramArrayOfString, 1, !(paramICommandListener instanceof EntityHuman));
    ChatMessage localChatMessage1 = new ChatMessage("commands.message.display.incoming", new Object[] { paramICommandListener.getScoreboardDisplayName(), localIChatBaseComponent.f() });
    ChatMessage localChatMessage2 = new ChatMessage("commands.message.display.outgoing", new Object[] { localEntityPlayer.getScoreboardDisplayName(), localIChatBaseComponent.f() });
    localChatMessage1.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
    localChatMessage2.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
    localEntityPlayer.sendMessage(localChatMessage1);
    paramICommandListener.sendMessage(localChatMessage2);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
