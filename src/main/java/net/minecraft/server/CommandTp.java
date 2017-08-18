package net.minecraft.server;

import java.util.List;




public class CommandTp
  extends CommandAbstract
{
  public String getCommand()
  {
    return "tp";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.tp.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1)
    {
      EntityPlayer localEntityPlayer1;
      if ((paramArrayOfString.length == 2) || (paramArrayOfString.length == 4)) {
        localEntityPlayer1 = d(paramICommandListener, paramArrayOfString[0]);
        if (localEntityPlayer1 == null) throw new ExceptionPlayerNotFound();
      } else {
        localEntityPlayer1 = b(paramICommandListener);
      }
      
      if ((paramArrayOfString.length == 3) || (paramArrayOfString.length == 4)) {
        if (localEntityPlayer1.world != null) {
          int i = paramArrayOfString.length - 3;
          
          double d1 = a(paramICommandListener, localEntityPlayer1.locX, paramArrayOfString[(i++)]);
          double d2 = a(paramICommandListener, localEntityPlayer1.locY, paramArrayOfString[(i++)], 0, 0);
          double d3 = a(paramICommandListener, localEntityPlayer1.locZ, paramArrayOfString[(i++)]);
          
          localEntityPlayer1.mount(null);
          localEntityPlayer1.enderTeleportTo(d1, d2, d3);
          a(paramICommandListener, this, "commands.tp.success.coordinates", new Object[] { localEntityPlayer1.getName(), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3) });
        }
      } else if ((paramArrayOfString.length == 1) || (paramArrayOfString.length == 2)) {
        EntityPlayer localEntityPlayer2 = d(paramICommandListener, paramArrayOfString[(paramArrayOfString.length - 1)]);
        if (localEntityPlayer2 == null) throw new ExceptionPlayerNotFound();
        if (localEntityPlayer2.world != localEntityPlayer1.world) {
          a(paramICommandListener, this, "commands.tp.notSameDimension", new Object[0]);
          return;
        }
        
        localEntityPlayer1.mount(null);
        localEntityPlayer1.playerConnection.a(localEntityPlayer2.locX, localEntityPlayer2.locY, localEntityPlayer2.locZ, localEntityPlayer2.yaw, localEntityPlayer2.pitch);
        a(paramICommandListener, this, "commands.tp.success", new Object[] { localEntityPlayer1.getName(), localEntityPlayer2.getName() });
      }
      
      return;
    }
    
    throw new ExceptionUsage("commands.tp.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length == 1) || (paramArrayOfString.length == 2)) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
