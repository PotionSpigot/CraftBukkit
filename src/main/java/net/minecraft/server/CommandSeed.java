package net.minecraft.server;





public class CommandSeed
  extends CommandAbstract
{
  public boolean canUse(ICommandListener paramICommandListener)
  {
    return (MinecraftServer.getServer().N()) || (super.canUse(paramICommandListener));
  }
  
  public String getCommand()
  {
    return "seed";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.seed.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    WorldServer localWorldServer = (paramICommandListener instanceof EntityHuman) ? ((EntityHuman)paramICommandListener).world : MinecraftServer.getServer().getWorldServer(0);
    paramICommandListener.sendMessage(new ChatMessage("commands.seed.success", new Object[] { Long.valueOf(localWorldServer.getSeed()) }));
  }
}
