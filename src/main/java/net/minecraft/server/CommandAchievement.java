package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.com.google.common.collect.Lists;





public class CommandAchievement
  extends CommandAbstract
{
  public String getCommand()
  {
    return "achievement";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.achievement.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 2) {
      Statistic localStatistic = StatisticList.getStatistic(paramArrayOfString[1]);
      

      if ((localStatistic == null) && (!paramArrayOfString[1].equals("*"))) {
        throw new CommandException("commands.achievement.unknownAchievement", new Object[] { paramArrayOfString[1] });
      }
      EntityPlayer localEntityPlayer;
      if (paramArrayOfString.length >= 3) {
        localEntityPlayer = d(paramICommandListener, paramArrayOfString[2]);
      } else {
        localEntityPlayer = b(paramICommandListener);
      }
      
      if (paramArrayOfString[0].equalsIgnoreCase("give")) { Object localObject1;
        Object localObject2; if (localStatistic == null) {
          for (localObject1 = AchievementList.e.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Achievement)((Iterator)localObject1).next();
            localEntityPlayer.a((Statistic)localObject2);
          }
          
          a(paramICommandListener, this, "commands.achievement.give.success.all", new Object[] { localEntityPlayer.getName() });
        } else {
          if ((localStatistic instanceof Achievement)) {
            localObject1 = (Achievement)localStatistic;
            localObject2 = Lists.newArrayList();
            
            while ((((Achievement)localObject1).c != null) && (!localEntityPlayer.getStatisticManager().hasAchievement(((Achievement)localObject1).c))) {
              ((List)localObject2).add(((Achievement)localObject1).c);
              localObject1 = ((Achievement)localObject1).c;
            }
            
            for (Achievement localAchievement : Lists.reverse((List)localObject2)) {
              localEntityPlayer.a(localAchievement);
            }
          }
          
          localEntityPlayer.a(localStatistic);
          
          a(paramICommandListener, this, "commands.achievement.give.success.one", new Object[] { localEntityPlayer.getName(), localStatistic.j() });
        }
        
        return;
      }
    }
    
    throw new ExceptionUsage("commands.achievement.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "give" });
    }
    
    if (paramArrayOfString.length == 2) {
      ArrayList localArrayList = Lists.newArrayList();
      
      for (Statistic localStatistic : StatisticList.stats) {
        localArrayList.add(localStatistic.name);
      }
      
      return a(paramArrayOfString, localArrayList);
    }
    
    if (paramArrayOfString.length == 3) {
      return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
    }
    
    return null;
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 2;
  }
}
