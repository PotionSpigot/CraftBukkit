package net.minecraft.server;

import java.util.List;






public class CommandClear
  extends CommandAbstract
{
  public String getCommand()
  {
    return "clear";
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.clear.usage";
  }
  
  public int a()
  {
    return 2;
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    EntityPlayer localEntityPlayer = paramArrayOfString.length == 0 ? b(paramICommandListener) : d(paramICommandListener, paramArrayOfString[0]);
    
    Item localItem = paramArrayOfString.length >= 2 ? f(paramICommandListener, paramArrayOfString[1]) : null;
    int i = paramArrayOfString.length >= 3 ? a(paramICommandListener, paramArrayOfString[2], 0) : -1;
    
    if ((paramArrayOfString.length >= 2) && (localItem == null)) {
      throw new CommandException("commands.clear.failure", new Object[] { localEntityPlayer.getName() });
    }
    
    int j = localEntityPlayer.inventory.a(localItem, i);
    localEntityPlayer.defaultContainer.b();
    if (!localEntityPlayer.abilities.canInstantlyBuild) { localEntityPlayer.broadcastCarriedItem();
    }
    if (j == 0) {
      throw new CommandException("commands.clear.failure", new Object[] { localEntityPlayer.getName() });
    }
    
    a(paramICommandListener, this, "commands.clear.success", new Object[] { localEntityPlayer.getName(), Integer.valueOf(j) });
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, d());
    }
    if (paramArrayOfString.length == 2) {
      return a(paramArrayOfString, Item.REGISTRY.keySet());
    }
    
    return null;
  }
  
  protected String[] d() {
    return MinecraftServer.getServer().getPlayers();
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
