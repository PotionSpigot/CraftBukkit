package net.minecraft.server;

import java.util.List;




public class CommandTime
  extends CommandAbstract
{
  public String getCommand()
  {
    return "time";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.time.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 1) { int i;
      if (paramArrayOfString[0].equals("set"))
      {

        if (paramArrayOfString[1].equals("day")) {
          i = 1000;
        } else if (paramArrayOfString[1].equals("night")) {
          i = 13000;
        } else {
          i = a(paramICommandListener, paramArrayOfString[1], 0);
        }
        
        a(paramICommandListener, i);
        a(paramICommandListener, this, "commands.time.set", new Object[] { Integer.valueOf(i) });
        return; }
      if (paramArrayOfString[0].equals("add")) {
        i = a(paramICommandListener, paramArrayOfString[1], 0);
        b(paramICommandListener, i);
        
        a(paramICommandListener, this, "commands.time.added", new Object[] { Integer.valueOf(i) });
        return;
      }
    }
    
    throw new ExceptionUsage("commands.time.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1)
      return a(paramArrayOfString, new String[] { "set", "add" });
    if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("set"))) {
      return a(paramArrayOfString, new String[] { "day", "night" });
    }
    
    return null;
  }
  
  protected void a(ICommandListener paramICommandListener, int paramInt) {
    for (int i = 0; i < MinecraftServer.getServer().worldServer.length; i++) {
      MinecraftServer.getServer().worldServer[i].setDayTime(paramInt);
    }
  }
  
  protected void b(ICommandListener paramICommandListener, int paramInt) {
    for (int i = 0; i < MinecraftServer.getServer().worldServer.length; i++) {
      WorldServer localWorldServer = MinecraftServer.getServer().worldServer[i];
      localWorldServer.setDayTime(localWorldServer.getDayTime() + paramInt);
    }
  }
}
