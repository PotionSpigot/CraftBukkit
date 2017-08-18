package net.minecraft.server;









public class CommandSetWorldSpawn
  extends CommandAbstract
{
  public String getCommand()
  {
    return "setworldspawn";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.setworldspawn.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 3) {
      if (paramICommandListener.getWorld() != null) {
        int i = 0;
        int j = a(paramICommandListener, paramArrayOfString[(i++)], -30000000, 30000000);
        int k = a(paramICommandListener, paramArrayOfString[(i++)], 0, 256);
        int m = a(paramICommandListener, paramArrayOfString[(i++)], -30000000, 30000000);
        
        paramICommandListener.getWorld().x(j, k, m);
        a(paramICommandListener, this, "commands.setworldspawn.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m) });
      } else {
        throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
      }
    } else if (paramArrayOfString.length == 0) {
      ChunkCoordinates localChunkCoordinates = b(paramICommandListener).getChunkCoordinates();
      paramICommandListener.getWorld().x(localChunkCoordinates.x, localChunkCoordinates.y, localChunkCoordinates.z);
      a(paramICommandListener, this, "commands.setworldspawn.success", new Object[] { Integer.valueOf(localChunkCoordinates.x), Integer.valueOf(localChunkCoordinates.y), Integer.valueOf(localChunkCoordinates.z) });
    } else {
      throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
    }
  }
}
