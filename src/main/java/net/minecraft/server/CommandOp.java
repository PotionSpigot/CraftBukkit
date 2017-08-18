package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.com.mojang.authlib.GameProfile;




public class CommandOp
  extends CommandAbstract
{
  public String getCommand()
  {
    return "op";
  }
  
  public int a()
  {
    return 3;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.op.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length == 1) && (paramArrayOfString[0].length() > 0)) {
      MinecraftServer localMinecraftServer = MinecraftServer.getServer();
      GameProfile localGameProfile = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
      if (localGameProfile == null) {
        throw new CommandException("commands.op.failed", new Object[] { paramArrayOfString[0] });
      }
      
      localMinecraftServer.getPlayerList().addOp(localGameProfile);
      a(paramICommandListener, this, "commands.op.success", new Object[] { paramArrayOfString[0] });
      return;
    }
    
    throw new ExceptionUsage("commands.op.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      String str = paramArrayOfString[(paramArrayOfString.length - 1)];
      ArrayList localArrayList = new ArrayList();
      
      for (GameProfile localGameProfile : MinecraftServer.getServer().F()) {
        if ((!MinecraftServer.getServer().getPlayerList().isOp(localGameProfile)) && (a(str, localGameProfile.getName()))) {
          localArrayList.add(localGameProfile.getName());
        }
      }
      
      return localArrayList;
    }
    
    return null;
  }
}
