package net.minecraft.server;

import java.util.List;
import net.minecraft.util.com.google.gson.JsonParseException;
import net.minecraft.util.org.apache.commons.lang3.exception.ExceptionUtils;







public class CommandTellRaw
  extends CommandAbstract
{
  public String getCommand()
  {
    return "tellraw";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.tellraw.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 2) { throw new ExceptionUsage("commands.tellraw.usage", new Object[0]);
    }
    EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[0]);
    String str = b(paramICommandListener, paramArrayOfString, 1);
    try
    {
      IChatBaseComponent localIChatBaseComponent = ChatSerializer.a(str);
      localEntityPlayer.sendMessage(localIChatBaseComponent);
    } catch (JsonParseException localJsonParseException) {
      Throwable localThrowable = ExceptionUtils.getRootCause(localJsonParseException);
      throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[] { localThrowable == null ? "" : localThrowable.getMessage() });
    }
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
