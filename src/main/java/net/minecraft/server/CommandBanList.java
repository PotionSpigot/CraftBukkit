package net.minecraft.server;

import java.util.List;




public class CommandBanList
  extends CommandAbstract
{
  public String getCommand()
  {
    return "banlist";
  }
  
  public int a()
  {
    return 3;
  }
  

  public boolean canUse(ICommandListener paramICommandListener)
  {
    return ((MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled()) || (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled())) && (super.canUse(paramICommandListener));
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.banlist.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length >= 1) && (paramArrayOfString[0].equalsIgnoreCase("ips"))) {
      paramICommandListener.sendMessage(new ChatMessage("commands.banlist.ips", new Object[] { Integer.valueOf(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().length) }));
      paramICommandListener.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries())));
    } else {
      paramICommandListener.sendMessage(new ChatMessage("commands.banlist.players", new Object[] { Integer.valueOf(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries().length) }));
      paramICommandListener.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries())));
    }
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "players", "ips" });
    }
    
    return null;
  }
}
