package net.minecraft.server;


public class CommandStop
  extends CommandAbstract
{
  public String getCommand()
  {
    return "stop";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.stop.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (MinecraftServer.getServer().worldServer != null) {
      a(paramICommandListener, this, "commands.stop.start", new Object[0]);
    }
    MinecraftServer.getServer().safeShutdown();
  }
}
