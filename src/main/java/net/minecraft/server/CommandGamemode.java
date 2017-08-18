package net.minecraft.server;

import java.util.List;








public class CommandGamemode
  extends CommandAbstract
{
  public String getCommand()
  {
    return "gamemode";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.gamemode.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      EnumGamemode localEnumGamemode = h(paramICommandListener, paramArrayOfString[0]);
      EntityPlayer localEntityPlayer = paramArrayOfString.length >= 2 ? d(paramICommandListener, paramArrayOfString[1]) : b(paramICommandListener);
      
      localEntityPlayer.a(localEnumGamemode);
      localEntityPlayer.fallDistance = 0.0F;
      
      ChatMessage localChatMessage = new ChatMessage("gameMode." + localEnumGamemode.b(), new Object[0]);
      
      if (localEntityPlayer != paramICommandListener) {
        a(paramICommandListener, this, 1, "commands.gamemode.success.other", new Object[] { localEntityPlayer.getName(), localChatMessage });
      } else {
        a(paramICommandListener, this, 1, "commands.gamemode.success.self", new Object[] { localChatMessage });
      }
      
      return;
    }
    
    throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
  }
  
  protected EnumGamemode h(ICommandListener paramICommandListener, String paramString) {
    if ((paramString.equalsIgnoreCase(EnumGamemode.SURVIVAL.b())) || (paramString.equalsIgnoreCase("s")))
      return EnumGamemode.SURVIVAL;
    if ((paramString.equalsIgnoreCase(EnumGamemode.CREATIVE.b())) || (paramString.equalsIgnoreCase("c")))
      return EnumGamemode.CREATIVE;
    if ((paramString.equalsIgnoreCase(EnumGamemode.ADVENTURE.b())) || (paramString.equalsIgnoreCase("a"))) {
      return EnumGamemode.ADVENTURE;
    }
    return WorldSettings.a(a(paramICommandListener, paramString, 0, EnumGamemode.values().length - 2));
  }
  

  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1)
      return a(paramArrayOfString, new String[] { "survival", "creative", "adventure" });
    if (paramArrayOfString.length == 2) {
      return a(paramArrayOfString, d());
    }
    
    return null;
  }
  
  protected String[] d() {
    return MinecraftServer.getServer().getPlayers();
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 1;
  }
}
