package net.minecraft.server;





public class CommandSaveAll
  extends CommandAbstract
{
  public String getCommand()
  {
    return "save-all";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.save.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    MinecraftServer localMinecraftServer = MinecraftServer.getServer();
    
    paramICommandListener.sendMessage(new ChatMessage("commands.save.start", new Object[0]));
    
    if (localMinecraftServer.getPlayerList() != null)
      localMinecraftServer.getPlayerList().savePlayers();
    try {
      WorldServer localWorldServer;
      boolean bool;
      for (int i = 0; i < localMinecraftServer.worldServer.length; i++) {
        if (localMinecraftServer.worldServer[i] != null) {
          localWorldServer = localMinecraftServer.worldServer[i];
          bool = localWorldServer.savingDisabled;
          localWorldServer.savingDisabled = false;
          localWorldServer.save(true, null);
          localWorldServer.savingDisabled = bool;
        }
      }
      if ((paramArrayOfString.length > 0) && ("flush".equals(paramArrayOfString[0]))) {
        paramICommandListener.sendMessage(new ChatMessage("commands.save.flushStart", new Object[0]));
        for (i = 0; i < localMinecraftServer.worldServer.length; i++) {
          if (localMinecraftServer.worldServer[i] != null) {
            localWorldServer = localMinecraftServer.worldServer[i];
            bool = localWorldServer.savingDisabled;
            localWorldServer.savingDisabled = false;
            localWorldServer.flushSave();
            localWorldServer.savingDisabled = bool;
          }
        }
        paramICommandListener.sendMessage(new ChatMessage("commands.save.flushEnd", new Object[0]));
      }
    } catch (ExceptionWorldConflict localExceptionWorldConflict) {
      a(paramICommandListener, this, "commands.save.failed", new Object[] { localExceptionWorldConflict.getMessage() });
      return;
    }
    
    a(paramICommandListener, this, "commands.save.success", new Object[0]);
  }
}
