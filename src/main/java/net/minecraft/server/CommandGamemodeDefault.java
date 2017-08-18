package net.minecraft.server;






public class CommandGamemodeDefault
  extends CommandGamemode
{
  public String getCommand()
  {
    return "defaultgamemode";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.defaultgamemode.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      EnumGamemode localEnumGamemode = h(paramICommandListener, paramArrayOfString[0]);
      a(localEnumGamemode);
      
      a(paramICommandListener, this, "commands.defaultgamemode.success", new Object[] { new ChatMessage("gameMode." + localEnumGamemode.b(), new Object[0]) });
      
      return;
    }
    
    throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
  }
  
  protected void a(EnumGamemode paramEnumGamemode) {
    MinecraftServer localMinecraftServer = MinecraftServer.getServer();
    localMinecraftServer.a(paramEnumGamemode);
    
    if (localMinecraftServer.getForceGamemode()) {
      for (EntityPlayer localEntityPlayer : MinecraftServer.getServer().getPlayerList().players) {
        localEntityPlayer.a(paramEnumGamemode);
        localEntityPlayer.fallDistance = 0.0F;
      }
    }
  }
}
