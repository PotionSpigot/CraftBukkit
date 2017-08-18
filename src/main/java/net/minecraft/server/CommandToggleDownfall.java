package net.minecraft.server;



public class CommandToggleDownfall
  extends CommandAbstract
{
  public String getCommand()
  {
    return "toggledownfall";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.downfall.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    d();
    a(paramICommandListener, this, "commands.downfall.success", new Object[0]);
  }
  
  protected void d() {
    WorldData localWorldData = MinecraftServer.getServer().worldServer[0].getWorldData();
    
    localWorldData.setStorm(!localWorldData.hasStorm());
  }
}
