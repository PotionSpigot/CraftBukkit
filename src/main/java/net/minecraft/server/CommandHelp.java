package net.minecraft.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;





public class CommandHelp
  extends CommandAbstract
{
  public String getCommand()
  {
    return "help";
  }
  
  public int a()
  {
    return 0;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.help.usage";
  }
  
  public List b()
  {
    return Arrays.asList(new String[] { "?" });
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    List localList = d(paramICommandListener);
    int i = 7;
    int j = (localList.size() - 1) / i;
    int k = 0;
    try
    {
      k = paramArrayOfString.length == 0 ? 0 : a(paramICommandListener, paramArrayOfString[0], 1, j + 1) - 1;
    }
    catch (ExceptionInvalidNumber localExceptionInvalidNumber) {
      localObject = d();
      ICommand localICommand1 = (ICommand)((Map)localObject).get(paramArrayOfString[0]);
      
      if (localICommand1 != null)
      {
        throw new ExceptionUsage(localICommand1.c(paramICommandListener), new Object[0]); }
      if (MathHelper.a(paramArrayOfString[0], -1) != -1) {
        throw localExceptionInvalidNumber;
      }
      throw new ExceptionUnknownCommand();
    }
    


    int m = Math.min((k + 1) * i, localList.size());
    
    Object localObject = new ChatMessage("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
    ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
    paramICommandListener.sendMessage((IChatBaseComponent)localObject);
    
    for (int n = k * i; n < m; n++) {
      ICommand localICommand2 = (ICommand)localList.get(n);
      
      ChatMessage localChatMessage2 = new ChatMessage(localICommand2.c(paramICommandListener), new Object[0]);
      localChatMessage2.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/" + localICommand2.getCommand() + " "));
      paramICommandListener.sendMessage(localChatMessage2);
    }
    
    if ((k == 0) && ((paramICommandListener instanceof EntityHuman))) {
      ChatMessage localChatMessage1 = new ChatMessage("commands.help.footer", new Object[0]);
      localChatMessage1.getChatModifier().setColor(EnumChatFormat.GREEN);
      paramICommandListener.sendMessage(localChatMessage1);
    }
  }
  
  protected List d(ICommandListener paramICommandListener) {
    List localList = MinecraftServer.getServer().getCommandHandler().a(paramICommandListener);
    Collections.sort(localList);
    return localList;
  }
  
  protected Map d() {
    return MinecraftServer.getServer().getCommandHandler().a();
  }
}
