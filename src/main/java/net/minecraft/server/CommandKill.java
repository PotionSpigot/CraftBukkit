package net.minecraft.server;





public class CommandKill
  extends CommandAbstract
{
  public String getCommand()
  {
    return "kill";
  }
  
  public int a()
  {
    return 0;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.kill.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    EntityPlayer localEntityPlayer = b(paramICommandListener);
    
    localEntityPlayer.damageEntity(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
    
    paramICommandListener.sendMessage(new ChatMessage("commands.kill.success", new Object[0]));
  }
}
