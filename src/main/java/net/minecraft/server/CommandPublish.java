package net.minecraft.server;



public class CommandPublish
  extends CommandAbstract
{
  public String getCommand()
  {
    return "publish";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.publish.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    String str = MinecraftServer.getServer().a(EnumGamemode.SURVIVAL, false);
    
    if (str != null) {
      a(paramICommandListener, this, "commands.publish.started", new Object[] { str });
    } else {
      a(paramICommandListener, this, "commands.publish.failed", new Object[0]);
    }
  }
}
