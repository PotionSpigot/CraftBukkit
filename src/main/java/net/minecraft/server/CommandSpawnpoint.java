package net.minecraft.server;

import java.util.List;







public class CommandSpawnpoint
  extends CommandAbstract
{
  public String getCommand()
  {
    return "spawnpoint";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.spawnpoint.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    EntityPlayer localEntityPlayer = paramArrayOfString.length == 0 ? b(paramICommandListener) : d(paramICommandListener, paramArrayOfString[0]);
    
    if (paramArrayOfString.length == 4) {
      if (localEntityPlayer.world != null) {
        int i = 1;
        int j = 30000000;
        int k = a(paramICommandListener, paramArrayOfString[(i++)], -j, j);
        int m = a(paramICommandListener, paramArrayOfString[(i++)], 0, 256);
        int n = a(paramICommandListener, paramArrayOfString[(i++)], -j, j);
        
        localEntityPlayer.setRespawnPosition(new ChunkCoordinates(k, m, n), true);
        a(paramICommandListener, this, "commands.spawnpoint.success", new Object[] { localEntityPlayer.getName(), Integer.valueOf(k), Integer.valueOf(m), Integer.valueOf(n) });
      }
    } else if (paramArrayOfString.length <= 1) {
      ChunkCoordinates localChunkCoordinates = localEntityPlayer.getChunkCoordinates();
      localEntityPlayer.setRespawnPosition(localChunkCoordinates, true);
      a(paramICommandListener, this, "commands.spawnpoint.success", new Object[] { localEntityPlayer.getName(), Integer.valueOf(localChunkCoordinates.x), Integer.valueOf(localChunkCoordinates.y), Integer.valueOf(localChunkCoordinates.z) });
    } else {
      throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
    }
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
