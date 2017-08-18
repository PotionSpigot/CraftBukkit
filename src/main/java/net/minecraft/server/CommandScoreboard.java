package net.minecraft.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;








public class CommandScoreboard
  extends CommandAbstract
{
  public String getCommand()
  {
    return "scoreboard";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.scoreboard.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1) {
      if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
        if (paramArrayOfString.length == 1)
          throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
        if (paramArrayOfString[1].equalsIgnoreCase("list")) {
          d(paramICommandListener);
        } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
          if (paramArrayOfString.length >= 4) {
            c(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
          if (paramArrayOfString.length == 3) {
            h(paramICommandListener, paramArrayOfString[2]);
          } else {
            throw new ExceptionUsage("commands.scoreboard.objectives.remove.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
          if ((paramArrayOfString.length == 3) || (paramArrayOfString.length == 4)) {
            k(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
          }
        } else {
          throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
        }
        
        return; }
      if (paramArrayOfString[0].equalsIgnoreCase("players")) {
        if (paramArrayOfString.length == 1)
          throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
        if (paramArrayOfString[1].equalsIgnoreCase("list")) {
          if (paramArrayOfString.length <= 3) {
            l(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.players.list.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
          if (paramArrayOfString.length == 5) {
            m(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.players.add.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
          if (paramArrayOfString.length == 5) {
            m(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.players.remove.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("set")) {
          if (paramArrayOfString.length == 5) {
            m(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.players.set.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("reset")) {
          if (paramArrayOfString.length == 3) {
            n(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.players.reset.usage", new Object[0]);
          }
        } else {
          throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
        }
        
        return; }
      if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
        if (paramArrayOfString.length == 1)
          throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
        if (paramArrayOfString[1].equalsIgnoreCase("list")) {
          if (paramArrayOfString.length <= 3) {
            g(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.list.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
          if (paramArrayOfString.length >= 3) {
            d(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
          if (paramArrayOfString.length == 3) {
            f(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.remove.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("empty")) {
          if (paramArrayOfString.length == 3) {
            j(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.empty.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("join")) {
          if ((paramArrayOfString.length >= 4) || ((paramArrayOfString.length == 3) && ((paramICommandListener instanceof EntityHuman)))) {
            h(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.join.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("leave")) {
          if ((paramArrayOfString.length >= 3) || ((paramICommandListener instanceof EntityHuman))) {
            i(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.leave.usage", new Object[0]);
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
          if ((paramArrayOfString.length == 4) || (paramArrayOfString.length == 5)) {
            e(paramICommandListener, paramArrayOfString, 2);
          } else {
            throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
          }
        } else {
          throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
        }
        
        return;
      }
    }
    
    throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
  }
  
  protected Scoreboard d() {
    return MinecraftServer.getServer().getWorldServer(0).getScoreboard();
  }
  
  protected ScoreboardObjective a(String paramString, boolean paramBoolean) {
    Scoreboard localScoreboard = d();
    ScoreboardObjective localScoreboardObjective = localScoreboard.getObjective(paramString);
    
    if (localScoreboardObjective == null)
      throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { paramString });
    if ((paramBoolean) && (localScoreboardObjective.getCriteria().isReadOnly())) {
      throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { paramString });
    }
    
    return localScoreboardObjective;
  }
  
  protected ScoreboardTeam a(String paramString) {
    Scoreboard localScoreboard = d();
    ScoreboardTeam localScoreboardTeam = localScoreboard.getTeam(paramString);
    
    if (localScoreboardTeam == null) {
      throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { paramString });
    }
    
    return localScoreboardTeam;
  }
  
  protected void c(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    String str1 = paramArrayOfString[(paramInt++)];
    String str2 = paramArrayOfString[(paramInt++)];
    Scoreboard localScoreboard = d();
    IScoreboardCriteria localIScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(str2);
    
    if (localIScoreboardCriteria == null) {
      throw new ExceptionUsage("commands.scoreboard.objectives.add.wrongType", new Object[] { str2 });
    }
    if (localScoreboard.getObjective(str1) != null) {
      throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { str1 });
    }
    if (str1.length() > 16) {
      throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.tooLong", new Object[] { str1, Integer.valueOf(16) });
    }
    if (str1.length() == 0) {
      throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
    }
    
    if (paramArrayOfString.length > paramInt) {
      String str3 = a(paramICommandListener, paramArrayOfString, paramInt).c();
      
      if (str3.length() > 32) {
        throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.displayTooLong", new Object[] { str3, Integer.valueOf(32) });
      }
      if (str3.length() > 0) {
        localScoreboard.registerObjective(str1, localIScoreboardCriteria).setDisplayName(str3);
      } else {
        localScoreboard.registerObjective(str1, localIScoreboardCriteria);
      }
    } else {
      localScoreboard.registerObjective(str1, localIScoreboardCriteria);
    }
    
    a(paramICommandListener, this, "commands.scoreboard.objectives.add.success", new Object[] { str1 });
  }
  
  protected void d(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    String str1 = paramArrayOfString[(paramInt++)];
    Scoreboard localScoreboard = d();
    
    if (localScoreboard.getTeam(str1) != null) {
      throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { str1 });
    }
    if (str1.length() > 16) {
      throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.tooLong", new Object[] { str1, Integer.valueOf(16) });
    }
    if (str1.length() == 0) {
      throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
    }
    
    if (paramArrayOfString.length > paramInt) {
      String str2 = a(paramICommandListener, paramArrayOfString, paramInt).c();
      
      if (str2.length() > 32) {
        throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.displayTooLong", new Object[] { str2, Integer.valueOf(32) });
      }
      if (str2.length() > 0) {
        localScoreboard.createTeam(str1).setDisplayName(str2);
      } else {
        localScoreboard.createTeam(str1);
      }
    } else {
      localScoreboard.createTeam(str1);
    }
    
    a(paramICommandListener, this, "commands.scoreboard.teams.add.success", new Object[] { str1 });
  }
  
  protected void e(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    ScoreboardTeam localScoreboardTeam = a(paramArrayOfString[(paramInt++)]);
    if (localScoreboardTeam == null) return;
    String str1 = paramArrayOfString[(paramInt++)].toLowerCase();
    
    if ((!str1.equalsIgnoreCase("color")) && (!str1.equalsIgnoreCase("friendlyfire")) && (!str1.equalsIgnoreCase("seeFriendlyInvisibles"))) {
      throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
    }
    
    if (paramArrayOfString.length == 4) {
      if (str1.equalsIgnoreCase("color"))
        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) });
      if ((str1.equalsIgnoreCase("friendlyfire")) || (str1.equalsIgnoreCase("seeFriendlyInvisibles"))) {
        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
      }
      
      throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
    }
    
    String str2 = paramArrayOfString[(paramInt++)];
    
    if (str1.equalsIgnoreCase("color")) {
      EnumChatFormat localEnumChatFormat = EnumChatFormat.b(str2);
      if ((localEnumChatFormat == null) || (localEnumChatFormat.isFormat())) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) });
      localScoreboardTeam.setPrefix(localEnumChatFormat.toString());
      localScoreboardTeam.setSuffix(EnumChatFormat.RESET.toString());
    } else if (str1.equalsIgnoreCase("friendlyfire")) {
      if ((!str2.equalsIgnoreCase("true")) && (!str2.equalsIgnoreCase("false"))) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
      localScoreboardTeam.setAllowFriendlyFire(str2.equalsIgnoreCase("true"));
    } else if (str1.equalsIgnoreCase("seeFriendlyInvisibles")) {
      if ((!str2.equalsIgnoreCase("true")) && (!str2.equalsIgnoreCase("false"))) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
      localScoreboardTeam.setCanSeeFriendlyInvisibles(str2.equalsIgnoreCase("true"));
    }
    
    a(paramICommandListener, this, "commands.scoreboard.teams.option.success", new Object[] { str1, localScoreboardTeam.getName(), str2 });
  }
  
  protected void f(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    ScoreboardTeam localScoreboardTeam = a(paramArrayOfString[(paramInt++)]);
    if (localScoreboardTeam == null) { return;
    }
    localScoreboard.removeTeam(localScoreboardTeam);
    
    a(paramICommandListener, this, "commands.scoreboard.teams.remove.success", new Object[] { localScoreboardTeam.getName() });
  }
  
  protected void g(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    Object localObject1;
    Object localObject2; Object localObject3; if (paramArrayOfString.length > paramInt) {
      localObject1 = a(paramArrayOfString[(paramInt++)]);
      if (localObject1 == null) { return;
      }
      localObject2 = ((ScoreboardTeam)localObject1).getPlayerNameSet();
      
      if (((Collection)localObject2).size() > 0) {
        localObject3 = new ChatMessage("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(((Collection)localObject2).size()), ((ScoreboardTeam)localObject1).getName() });
        ((ChatMessage)localObject3).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
        paramICommandListener.sendMessage((IChatBaseComponent)localObject3);
        paramICommandListener.sendMessage(new ChatComponentText(a(((Collection)localObject2).toArray())));
      } else {
        throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { ((ScoreboardTeam)localObject1).getName() });
      }
    } else {
      localObject1 = localScoreboard.getTeams();
      
      if (((Collection)localObject1).size() > 0) {
        localObject2 = new ChatMessage("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(((Collection)localObject1).size()) });
        ((ChatMessage)localObject2).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
        paramICommandListener.sendMessage((IChatBaseComponent)localObject2);
        
        for (localObject3 = ((Collection)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { ScoreboardTeam localScoreboardTeam = (ScoreboardTeam)((Iterator)localObject3).next();
          paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.teams.list.entry", new Object[] { localScoreboardTeam.getName(), localScoreboardTeam.getDisplayName(), Integer.valueOf(localScoreboardTeam.getPlayerNameSet().size()) }));
        }
      } else {
        throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
      }
    }
  }
  
  protected void h(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    String str1 = paramArrayOfString[(paramInt++)];
    HashSet localHashSet1 = new HashSet();
    HashSet localHashSet2 = new HashSet();
    String str2;
    if (((paramICommandListener instanceof EntityHuman)) && (paramInt == paramArrayOfString.length)) {
      str2 = b(paramICommandListener).getName();
      
      if (localScoreboard.addPlayerToTeam(str2, str1)) {
        localHashSet1.add(str2);
      } else {
        localHashSet2.add(str2);
      }
    } else {
      while (paramInt < paramArrayOfString.length) {
        str2 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
        
        if (localScoreboard.addPlayerToTeam(str2, str1)) {
          localHashSet1.add(str2);
        } else {
          localHashSet2.add(str2);
        }
      }
    }
    
    if (!localHashSet1.isEmpty()) a(paramICommandListener, this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(localHashSet1.size()), str1, a(localHashSet1.toArray(new String[0])) });
    if (!localHashSet2.isEmpty()) throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(localHashSet2.size()), str1, a(localHashSet2.toArray(new String[0])) });
  }
  
  protected void i(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    HashSet localHashSet1 = new HashSet();
    HashSet localHashSet2 = new HashSet();
    String str;
    if (((paramICommandListener instanceof EntityHuman)) && (paramInt == paramArrayOfString.length)) {
      str = b(paramICommandListener).getName();
      
      if (localScoreboard.removePlayerFromTeam(str)) {
        localHashSet1.add(str);
      } else {
        localHashSet2.add(str);
      }
    } else {
      while (paramInt < paramArrayOfString.length) {
        str = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
        
        if (localScoreboard.removePlayerFromTeam(str)) {
          localHashSet1.add(str);
        } else {
          localHashSet2.add(str);
        }
      }
    }
    
    if (!localHashSet1.isEmpty()) a(paramICommandListener, this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(localHashSet1.size()), a(localHashSet1.toArray(new String[0])) });
    if (!localHashSet2.isEmpty()) throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(localHashSet2.size()), a(localHashSet2.toArray(new String[0])) });
  }
  
  protected void j(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    ScoreboardTeam localScoreboardTeam = a(paramArrayOfString[(paramInt++)]);
    if (localScoreboardTeam == null) { return;
    }
    ArrayList localArrayList = new ArrayList(localScoreboardTeam.getPlayerNameSet());
    
    if (localArrayList.isEmpty()) {
      throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { localScoreboardTeam.getName() });
    }
    
    for (String str : localArrayList) {
      localScoreboard.removePlayerFromTeam(str, localScoreboardTeam);
    }
    
    a(paramICommandListener, this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(localArrayList.size()), localScoreboardTeam.getName() });
  }
  
  protected void h(ICommandListener paramICommandListener, String paramString) {
    Scoreboard localScoreboard = d();
    ScoreboardObjective localScoreboardObjective = a(paramString, false);
    
    localScoreboard.unregisterObjective(localScoreboardObjective);
    
    a(paramICommandListener, this, "commands.scoreboard.objectives.remove.success", new Object[] { paramString });
  }
  
  protected void d(ICommandListener paramICommandListener) {
    Scoreboard localScoreboard = d();
    Collection localCollection = localScoreboard.getObjectives();
    
    if (localCollection.size() > 0) {
      ChatMessage localChatMessage = new ChatMessage("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(localCollection.size()) });
      localChatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
      paramICommandListener.sendMessage(localChatMessage);
      
      for (ScoreboardObjective localScoreboardObjective : localCollection) {
        paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.entry", new Object[] { localScoreboardObjective.getName(), localScoreboardObjective.getDisplayName(), localScoreboardObjective.getCriteria().getName() }));
      }
    } else {
      throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
    }
  }
  
  protected void k(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    String str = paramArrayOfString[(paramInt++)];
    int i = Scoreboard.getSlotForName(str);
    ScoreboardObjective localScoreboardObjective = null;
    
    if (paramArrayOfString.length == 4) {
      localScoreboardObjective = a(paramArrayOfString[(paramInt++)], false);
    }
    
    if (i < 0) {
      throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { str });
    }
    
    localScoreboard.setDisplaySlot(i, localScoreboardObjective);
    
    if (localScoreboardObjective != null) {
      a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getSlotName(i), localScoreboardObjective.getName() });
    } else {
      a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getSlotName(i) });
    }
  }
  
  protected void l(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    Object localObject1;
    Object localObject2; if (paramArrayOfString.length > paramInt) {
      localObject1 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
      localObject2 = localScoreboard.getPlayerObjectives((String)localObject1);
      
      if (((Map)localObject2).size() > 0) {
        ChatMessage localChatMessage = new ChatMessage("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(((Map)localObject2).size()), localObject1 });
        localChatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
        paramICommandListener.sendMessage(localChatMessage);
        
        for (ScoreboardScore localScoreboardScore : ((Map)localObject2).values()) {
          paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(localScoreboardScore.getScore()), localScoreboardScore.getObjective().getDisplayName(), localScoreboardScore.getObjective().getName() }));
        }
      } else {
        throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { localObject1 });
      }
    } else {
      localObject1 = localScoreboard.getPlayers();
      
      if (((Collection)localObject1).size() > 0) {
        localObject2 = new ChatMessage("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(((Collection)localObject1).size()) });
        ((ChatMessage)localObject2).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
        paramICommandListener.sendMessage((IChatBaseComponent)localObject2);
        paramICommandListener.sendMessage(new ChatComponentText(a(((Collection)localObject1).toArray())));
      } else {
        throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
      }
    }
  }
  
  protected void m(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    String str1 = paramArrayOfString[(paramInt - 1)];
    String str2 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
    ScoreboardObjective localScoreboardObjective = a(paramArrayOfString[(paramInt++)], true);
    int i = str1.equalsIgnoreCase("set") ? a(paramICommandListener, paramArrayOfString[(paramInt++)]) : a(paramICommandListener, paramArrayOfString[(paramInt++)], 1);
    Scoreboard localScoreboard = d();
    ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(str2, localScoreboardObjective);
    
    if (str1.equalsIgnoreCase("set")) {
      localScoreboardScore.setScore(i);
    } else if (str1.equalsIgnoreCase("add")) {
      localScoreboardScore.addScore(i);
    } else {
      localScoreboardScore.removeScore(i);
    }
    
    a(paramICommandListener, this, "commands.scoreboard.players.set.success", new Object[] { localScoreboardObjective.getName(), str2, Integer.valueOf(localScoreboardScore.getScore()) });
  }
  
  protected void n(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    Scoreboard localScoreboard = d();
    String str = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
    
    localScoreboard.resetPlayerScores(str);
    
    a(paramICommandListener, this, "commands.scoreboard.players.reset.success", new Object[] { str });
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "objectives", "players", "teams" });
    }
    
    if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
      if (paramArrayOfString.length == 2) {
        return a(paramArrayOfString, new String[] { "list", "add", "remove", "setdisplay" });
      }
      
      if (paramArrayOfString[1].equalsIgnoreCase("add")) {
        if (paramArrayOfString.length == 4) {
          Set localSet = IScoreboardCriteria.criteria.keySet();
          return a(paramArrayOfString, localSet);
        }
      } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
        if (paramArrayOfString.length == 3) {
          return a(paramArrayOfString, a(false));
        }
      } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
        if (paramArrayOfString.length == 3)
          return a(paramArrayOfString, new String[] { "list", "sidebar", "belowName" });
        if (paramArrayOfString.length == 4) {
          return a(paramArrayOfString, a(false));
        }
      }
    } else if (paramArrayOfString[0].equalsIgnoreCase("players")) {
      if (paramArrayOfString.length == 2) {
        return a(paramArrayOfString, new String[] { "set", "add", "remove", "reset", "list" });
      }
      
      if ((paramArrayOfString[1].equalsIgnoreCase("set")) || (paramArrayOfString[1].equalsIgnoreCase("add")) || (paramArrayOfString[1].equalsIgnoreCase("remove"))) {
        if (paramArrayOfString.length == 3)
          return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
        if (paramArrayOfString.length == 4) {
          return a(paramArrayOfString, a(true));
        }
      } else if (((paramArrayOfString[1].equalsIgnoreCase("reset")) || (paramArrayOfString[1].equalsIgnoreCase("list"))) && 
        (paramArrayOfString.length == 3)) {
        return a(paramArrayOfString, d().getPlayers());
      }
    }
    else if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
      if (paramArrayOfString.length == 2) {
        return a(paramArrayOfString, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
      }
      
      if (paramArrayOfString[1].equalsIgnoreCase("join")) {
        if (paramArrayOfString.length == 3)
          return a(paramArrayOfString, d().getTeamNames());
        if (paramArrayOfString.length >= 4)
          return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
      } else {
        if (paramArrayOfString[1].equalsIgnoreCase("leave"))
          return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
        if ((paramArrayOfString[1].equalsIgnoreCase("empty")) || (paramArrayOfString[1].equalsIgnoreCase("list")) || (paramArrayOfString[1].equalsIgnoreCase("remove"))) {
          if (paramArrayOfString.length == 3) {
            return a(paramArrayOfString, d().getTeamNames());
          }
        } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
          if (paramArrayOfString.length == 3)
            return a(paramArrayOfString, d().getTeamNames());
          if (paramArrayOfString.length == 4)
            return a(paramArrayOfString, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles" });
          if (paramArrayOfString.length == 5) {
            if (paramArrayOfString[3].equalsIgnoreCase("color"))
              return a(paramArrayOfString, EnumChatFormat.a(true, false));
            if ((paramArrayOfString[3].equalsIgnoreCase("friendlyfire")) || (paramArrayOfString[3].equalsIgnoreCase("seeFriendlyInvisibles"))) {
              return a(paramArrayOfString, new String[] { "true", "false" });
            }
          }
        }
      }
    }
    return null;
  }
  
  protected List a(boolean paramBoolean) {
    Collection localCollection = d().getObjectives();
    ArrayList localArrayList = new ArrayList();
    ScoreboardObjective localScoreboardObjective;
    for (Iterator localIterator = localCollection.iterator(); localIterator.hasNext(); 
        localArrayList.add(localScoreboardObjective.getName()))
    {
      localScoreboardObjective = (ScoreboardObjective)localIterator.next();
      if ((paramBoolean) && (localScoreboardObjective.getCriteria().isReadOnly())) {}
    }
    
    return localArrayList;
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    if (paramArrayOfString[0].equalsIgnoreCase("players"))
      return paramInt == 2;
    if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
      return (paramInt == 2) || (paramInt == 3);
    }
    
    return false;
  }
}
