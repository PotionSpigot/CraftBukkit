package net.minecraft.server;

import java.util.List;






public class CommandDifficulty
  extends CommandAbstract
{
  public String getCommand()
  {
    return "difficulty";
  }
  
  public int a()
  {
    return 2;
  }
  

  public String c(ICommandListener paramICommandListener)
  {
    return "commands.difficulty.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      EnumDifficulty localEnumDifficulty = h(paramICommandListener, paramArrayOfString[0]);
      
      MinecraftServer.getServer().a(localEnumDifficulty);
      
      a(paramICommandListener, this, "commands.difficulty.success", new Object[] { new ChatMessage(localEnumDifficulty.b(), new Object[0]) });
      
      return;
    }
    
    throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
  }
  
  protected EnumDifficulty h(ICommandListener paramICommandListener, String paramString) {
    if ((paramString.equalsIgnoreCase("peaceful")) || (paramString.equalsIgnoreCase("p")))
      return EnumDifficulty.PEACEFUL;
    if ((paramString.equalsIgnoreCase("easy")) || (paramString.equalsIgnoreCase("e")))
      return EnumDifficulty.EASY;
    if ((paramString.equalsIgnoreCase("normal")) || (paramString.equalsIgnoreCase("n")))
      return EnumDifficulty.NORMAL;
    if ((paramString.equalsIgnoreCase("hard")) || (paramString.equalsIgnoreCase("h"))) {
      return EnumDifficulty.HARD;
    }
    return EnumDifficulty.getById(a(paramICommandListener, paramString, 0, 3));
  }
  

  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "peaceful", "easy", "normal", "hard" });
    }
    
    return null;
  }
}
