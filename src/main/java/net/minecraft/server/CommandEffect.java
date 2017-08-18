package net.minecraft.server;

import java.util.Collection;
import java.util.List;










public class CommandEffect
  extends CommandAbstract
{
  public String getCommand()
  {
    return "effect";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.effect.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 2) {
      EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[0]);
      
      if (paramArrayOfString[1].equals("clear")) {
        if (localEntityPlayer.getEffects().isEmpty()) {
          throw new CommandException("commands.effect.failure.notActive.all", new Object[] { localEntityPlayer.getName() });
        }
        localEntityPlayer.removeAllEffects();
        a(paramICommandListener, this, "commands.effect.success.removed.all", new Object[] { localEntityPlayer.getName() });
      }
      else {
        int i = a(paramICommandListener, paramArrayOfString[1], 1);
        int j = 600;
        int k = 30;
        int m = 0;
        
        if ((i < 0) || (i >= MobEffectList.byId.length) || (MobEffectList.byId[i] == null)) {
          throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
        }
        
        if (paramArrayOfString.length >= 3) {
          k = a(paramICommandListener, paramArrayOfString[2], 0, 1000000);
          if (MobEffectList.byId[i].isInstant()) {
            j = k;
          } else {
            j = k * 20;
          }
        } else if (MobEffectList.byId[i].isInstant()) {
          j = 1;
        }
        
        if (paramArrayOfString.length >= 4) {
          m = a(paramICommandListener, paramArrayOfString[3], 0, 255);
        }
        
        if (k == 0) {
          if (localEntityPlayer.hasEffect(i)) {
            localEntityPlayer.removeEffect(i);
            a(paramICommandListener, this, "commands.effect.success.removed", new Object[] { new ChatMessage(MobEffectList.byId[i].a(), new Object[0]), localEntityPlayer.getName() });
          } else {
            throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatMessage(MobEffectList.byId[i].a(), new Object[0]), localEntityPlayer.getName() });
          }
        } else {
          MobEffect localMobEffect = new MobEffect(i, j, m);
          localEntityPlayer.addEffect(localMobEffect);
          a(paramICommandListener, this, "commands.effect.success", new Object[] { new ChatMessage(localMobEffect.f(), new Object[0]), Integer.valueOf(i), Integer.valueOf(m), localEntityPlayer.getName(), Integer.valueOf(k) });
        }
      }
      
      return;
    }
    
    throw new ExceptionUsage("commands.effect.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, d());
    }
    
    return null;
  }
  
  protected String[] d() {
    return MinecraftServer.getServer().getPlayers();
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
