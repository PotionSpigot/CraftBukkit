package net.minecraft.server;






public class CommandNetstat
  extends CommandAbstract
{
  public String getCommand()
  {
    return "netstat";
  }
  
  public int a()
  {
    return 0;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.players.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramICommandListener instanceof EntityHuman)) {
      paramICommandListener.sendMessage(new ChatComponentText("Command is not available for players"));
      return;
    }
    
    if ((paramArrayOfString.length > 0) && (paramArrayOfString[0].length() > 1)) {
      if ("hottest-read".equals(paramArrayOfString[0])) {
        paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.e().toString()));
      } else if ("hottest-write".equals(paramArrayOfString[0])) {
        paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.g().toString()));
      } else if ("most-read".equals(paramArrayOfString[0])) {
        paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.f().toString()));
      } else if ("most-write".equals(paramArrayOfString[0])) {
        paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.h().toString())); } else { PacketStatistics localPacketStatistics;
        if ("packet-read".equals(paramArrayOfString[0])) {
          if ((paramArrayOfString.length > 1) && (paramArrayOfString[1].length() > 0)) {
            try {
              int i = Integer.parseInt(paramArrayOfString[1].trim());
              localPacketStatistics = NetworkManager.h.a(i);
              a(paramICommandListener, i, localPacketStatistics);
            } catch (Exception localException1) {
              paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramArrayOfString[1] + " not found!"));
            }
          } else {
            paramICommandListener.sendMessage(new ChatComponentText("Packet id is missing"));
          }
        } else if ("packet-write".equals(paramArrayOfString[0])) {
          if ((paramArrayOfString.length > 1) && (paramArrayOfString[1].length() > 0)) {
            try {
              int j = Integer.parseInt(paramArrayOfString[1].trim());
              localPacketStatistics = NetworkManager.h.b(j);
              a(paramICommandListener, j, localPacketStatistics);
            } catch (Exception localException2) {
              paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramArrayOfString[1] + " not found!"));
            }
          } else {
            paramICommandListener.sendMessage(new ChatComponentText("Packet id is missing"));
          }
        } else if ("read-count".equals(paramArrayOfString[0])) {
          paramICommandListener.sendMessage(new ChatComponentText("total-read-count" + String.valueOf(NetworkManager.h.c())));
        } else if ("write-count".equals(paramArrayOfString[0])) {
          paramICommandListener.sendMessage(new ChatComponentText("total-write-count" + String.valueOf(NetworkManager.h.d())));
        } else
          paramICommandListener.sendMessage(new ChatComponentText("Unrecognized: " + paramArrayOfString[0]));
      }
    } else {
      String str = "reads: " + NetworkManager.h.a();
      str = str + ", writes: " + NetworkManager.h.b();
      paramICommandListener.sendMessage(new ChatComponentText(str));
    }
  }
  
  private void a(ICommandListener paramICommandListener, int paramInt, PacketStatistics paramPacketStatistics) {
    if (paramPacketStatistics != null) {
      paramICommandListener.sendMessage(new ChatComponentText(paramPacketStatistics.toString()));
    } else {
      paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramInt + " not found!"));
    }
  }
}
