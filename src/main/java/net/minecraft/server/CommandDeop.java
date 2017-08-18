package net.minecraft.server;

import java.util.List;
import net.minecraft.util.com.mojang.authlib.GameProfile;





public class CommandDeop
  extends CommandAbstract
{
  public String getCommand()
  {
    return "deop";
  }
  
  public int a()
  {
    return 3;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.deop.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length == 1) && (paramArrayOfString[0].length() > 0)) {
      MinecraftServer localMinecraftServer = MinecraftServer.getServer();
      GameProfile localGameProfile = localMinecraftServer.getPlayerList().getOPs().a(paramArrayOfString[0]);
      if (localGameProfile == null) {
        throw new CommandException("commands.deop.failed", new Object[] { paramArrayOfString[0] });
      }
      
      localMinecraftServer.getPlayerList().removeOp(localGameProfile);
      a(paramICommandListener, this, "commands.deop.success", new Object[] { paramArrayOfString[0] });
      return;
    }
    
    throw new ExceptionUsage("commands.deop.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().n());
    }
    
    return null;
  }
}
