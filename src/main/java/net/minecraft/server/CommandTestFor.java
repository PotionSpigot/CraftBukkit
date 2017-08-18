package net.minecraft.server;






public class CommandTestFor
  extends CommandAbstract
{
  public String getCommand()
  {
    return "testfor";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.testfor.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length != 1) throw new ExceptionUsage("commands.testfor.usage", new Object[0]);
    if (!(paramICommandListener instanceof CommandBlockListenerAbstract)) throw new CommandException("commands.testfor.failed", new Object[0]);
    d(paramICommandListener, paramArrayOfString[0]);
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
