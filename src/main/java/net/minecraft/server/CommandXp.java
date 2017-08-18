package net.minecraft.server;

import java.util.List;





public class CommandXp
  extends CommandAbstract
{
  public String getCommand()
  {
    return "xp";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.xp.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0)
    {
      String str = paramArrayOfString[0];
      
      int i = (str.endsWith("l")) || (str.endsWith("L")) ? 1 : 0;
      if ((i != 0) && (str.length() > 1)) { str = str.substring(0, str.length() - 1);
      }
      int j = a(paramICommandListener, str);
      int k = j < 0 ? 1 : 0;
      
      if (k != 0) j *= -1;
      EntityPlayer localEntityPlayer;
      if (paramArrayOfString.length > 1) {
        localEntityPlayer = d(paramICommandListener, paramArrayOfString[1]);
      } else {
        localEntityPlayer = b(paramICommandListener);
      }
      
      if (i != 0) {
        if (k != 0) {
          localEntityPlayer.levelDown(-j);
          a(paramICommandListener, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
        } else {
          localEntityPlayer.levelDown(j);
          a(paramICommandListener, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
        }
      } else {
        if (k != 0) {
          throw new ExceptionUsage("commands.xp.failure.widthdrawXp", new Object[0]);
        }
        localEntityPlayer.giveExp(j);
        a(paramICommandListener, this, "commands.xp.success", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
      }
      

      return;
    }
    
    throw new ExceptionUsage("commands.xp.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 2) {
      return a(paramArrayOfString, d());
    }
    
    return null;
  }
  
  protected String[] d() {
    return MinecraftServer.getServer().getPlayers();
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 1;
  }
}
