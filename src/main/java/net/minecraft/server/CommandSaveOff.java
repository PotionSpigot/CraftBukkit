package net.minecraft.server;




public class CommandSaveOff
  extends CommandAbstract
{
  public String getCommand()
  {
    return "save-off";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.save-off.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    MinecraftServer localMinecraftServer = MinecraftServer.getServer();
    int i = 0;
    
    for (int j = 0; j < localMinecraftServer.worldServer.length; j++) {
      if (localMinecraftServer.worldServer[j] != null) {
        WorldServer localWorldServer = localMinecraftServer.worldServer[j];
        if (!localWorldServer.savingDisabled) {
          localWorldServer.savingDisabled = true;
          i = 1;
        }
      }
    }
    
    if (i != 0) {
      a(paramICommandListener, this, "commands.save.disabled", new Object[0]);
    } else {
      throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
    }
  }
}
