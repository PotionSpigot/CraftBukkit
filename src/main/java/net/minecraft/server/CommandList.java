package net.minecraft.server;




public class CommandList
  extends CommandAbstract
{
  public String getCommand()
  {
    return "list";
  }
  
  public int a()
  {
    return 0;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.players.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    paramICommandListener.sendMessage(new ChatMessage("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().C()), Integer.valueOf(MinecraftServer.getServer().D()) }));
    paramICommandListener.sendMessage(new ChatComponentText(MinecraftServer.getServer().getPlayerList().b((paramArrayOfString.length > 0) && ("uuids".equalsIgnoreCase(paramArrayOfString[0])))));
  }
}
