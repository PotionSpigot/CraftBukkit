package net.minecraft.server;






public class CommandPlaySound
  extends CommandAbstract
{
  public String getCommand()
  {
    return "playsound";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.playsound.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 2) {
      throw new ExceptionUsage(c(paramICommandListener), new Object[0]);
    }
    
    int i = 0;
    String str = paramArrayOfString[(i++)];
    EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[(i++)]);
    double d1 = localEntityPlayer.getChunkCoordinates().x;
    double d2 = localEntityPlayer.getChunkCoordinates().y;
    double d3 = localEntityPlayer.getChunkCoordinates().z;
    double d4 = 1.0D;
    double d5 = 1.0D;
    double d6 = 0.0D;
    
    if (paramArrayOfString.length > i) d1 = a(paramICommandListener, d1, paramArrayOfString[(i++)]);
    if (paramArrayOfString.length > i) d2 = a(paramICommandListener, d2, paramArrayOfString[(i++)], 0, 0);
    if (paramArrayOfString.length > i) { d3 = a(paramICommandListener, d3, paramArrayOfString[(i++)]);
    }
    if (paramArrayOfString.length > i) d4 = a(paramICommandListener, paramArrayOfString[(i++)], 0.0D, 3.4028234663852886E38D);
    if (paramArrayOfString.length > i) d5 = a(paramICommandListener, paramArrayOfString[(i++)], 0.0D, 2.0D);
    if (paramArrayOfString.length > i) { d6 = a(paramICommandListener, paramArrayOfString[(i++)], 0.0D, 1.0D);
    }
    double d7 = d4 > 1.0D ? d4 * 16.0D : 16.0D;
    double d8 = localEntityPlayer.f(d1, d2, d3);
    
    if (d8 > d7) {
      if (d6 > 0.0D) {
        double d9 = d1 - localEntityPlayer.locX;
        double d10 = d2 - localEntityPlayer.locY;
        double d11 = d3 - localEntityPlayer.locZ;
        double d12 = Math.sqrt(d9 * d9 + d10 * d10 + d11 * d11);
        double d13 = localEntityPlayer.locX;
        double d14 = localEntityPlayer.locY;
        double d15 = localEntityPlayer.locZ;
        
        if (d12 > 0.0D) {
          d13 += d9 / d12 * 2.0D;
          d14 += d10 / d12 * 2.0D;
          d15 += d11 / d12 * 2.0D;
        }
        
        localEntityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(str, d13, d14, d15, (float)d6, (float)d5));
      } else {
        throw new CommandException("commands.playsound.playerTooFar", new Object[] { localEntityPlayer.getName() });
      }
    } else {
      localEntityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(str, d1, d2, d3, (float)d4, (float)d5));
    }
    
    a(paramICommandListener, this, "commands.playsound.success", new Object[] { str, localEntityPlayer.getName() });
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 1;
  }
}
