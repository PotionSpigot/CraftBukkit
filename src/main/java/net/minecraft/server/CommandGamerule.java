package net.minecraft.server;

import java.util.List;






public class CommandGamerule
  extends CommandAbstract
{
  public String getCommand()
  {
    return "gamerule";
  }
  
  public int a()
  {
    return 2;
  }
  



  public String c(ICommandListener paramICommandListener) { return "commands.gamerule.usage"; }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) { Object localObject1;
    Object localObject2;
    Object localObject3;
    if (paramArrayOfString.length == 2) {
      localObject1 = paramArrayOfString[0];
      localObject2 = paramArrayOfString[1];
      
      localObject3 = d();
      
      if (((GameRules)localObject3).contains((String)localObject1)) {
        ((GameRules)localObject3).set((String)localObject1, (String)localObject2);
        a(paramICommandListener, this, "commands.gamerule.success", new Object[0]);
      } else {
        a(paramICommandListener, this, "commands.gamerule.norule", new Object[] { localObject1 });
      }
      
      return; }
    if (paramArrayOfString.length == 1) {
      localObject1 = paramArrayOfString[0];
      localObject2 = d();
      
      if (((GameRules)localObject2).contains((String)localObject1)) {
        localObject3 = ((GameRules)localObject2).get((String)localObject1);
        paramICommandListener.sendMessage(new ChatComponentText((String)localObject1).a(" = ").a((String)localObject3));
      } else {
        a(paramICommandListener, this, "commands.gamerule.norule", new Object[] { localObject1 });
      }
      
      return; }
    if (paramArrayOfString.length == 0) {
      localObject1 = d();
      paramICommandListener.sendMessage(new ChatComponentText(a(((GameRules)localObject1).getGameRules())));
      return;
    }
    
    throw new ExceptionUsage("commands.gamerule.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1)
      return a(paramArrayOfString, d().getGameRules());
    if (paramArrayOfString.length == 2) {
      return a(paramArrayOfString, new String[] { "true", "false" });
    }
    
    return null;
  }
  
  private GameRules d() {
    return MinecraftServer.getServer().getWorldServer(0).getGameRules();
  }
}
