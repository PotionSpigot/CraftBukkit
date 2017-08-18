package net.minecraft.server;

import java.util.List;






public class CommandKick
  extends CommandAbstract
{
  public String getCommand()
  {
    return "kick";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.kick.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length > 0) && (paramArrayOfString[0].length() > 1)) {
      EntityPlayer localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
      String str = "Kicked by an operator.";
      int i = 0;
      
      if (localEntityPlayer == null) {
        throw new ExceptionPlayerNotFound();
      }
      
      if (paramArrayOfString.length >= 2) {
        str = a(paramICommandListener, paramArrayOfString, 1).c();
        i = 1;
      }
      
      localEntityPlayer.playerConnection.disconnect(str);
      
      if (i != 0) {
        a(paramICommandListener, this, "commands.kick.success.reason", new Object[] { localEntityPlayer.getName(), str });
      } else {
        a(paramICommandListener, this, "commands.kick.success", new Object[] { localEntityPlayer.getName() });
      }
      
      return;
    }
    
    throw new ExceptionUsage("commands.kick.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
}
