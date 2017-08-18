package net.minecraft.server;




public class CommandIdleTimeout
  extends CommandAbstract
{
  public String getCommand()
  {
    return "setidletimeout";
  }
  
  public int a()
  {
    return 3;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.setidletimeout.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      int i = a(paramICommandListener, paramArrayOfString[0], 0);
      MinecraftServer.getServer().setIdleTimeout(i);
      a(paramICommandListener, this, "commands.setidletimeout.success", new Object[] { Integer.valueOf(i) });
      return;
    }
    
    throw new ExceptionUsage("commands.setidletimeout.usage", new Object[0]);
  }
}
