package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandHandler
{
  private static final Logger a = ;
  private final Map b = new HashMap();
  private final Set c = new HashSet();
  
  public int a(ICommandListener paramICommandListener, String paramString)
  {
    paramString = paramString.trim();
    if (paramString.startsWith("/")) { paramString = paramString.substring(1);
    }
    String[] arrayOfString = paramString.split(" ");
    String str = arrayOfString[0];
    
    arrayOfString = a(arrayOfString);
    
    ICommand localICommand = (ICommand)this.b.get(str);
    int i = a(localICommand, arrayOfString);
    int j = 0;
    try
    {
      if (localICommand == null) {
        throw new ExceptionUnknownCommand();
      }
      if (localICommand.canUse(paramICommandListener)) {
        if (i > -1)
        {
          EntityPlayer[] arrayOfEntityPlayer1 = PlayerSelector.getPlayers(paramICommandListener, arrayOfString[i]);
          localObject = arrayOfString[i];
          
          for (EntityPlayer localEntityPlayer : arrayOfEntityPlayer1) {
            arrayOfString[i] = localEntityPlayer.getName();
            try
            {
              localICommand.execute(paramICommandListener, arrayOfString);
              j++;
            } catch (CommandException localCommandException3) {
              ChatMessage localChatMessage2 = new ChatMessage(localCommandException3.getMessage(), localCommandException3.getArgs());
              localChatMessage2.getChatModifier().setColor(EnumChatFormat.RED);
              paramICommandListener.sendMessage(localChatMessage2);
            }
          }
          
          arrayOfString[i] = localObject;
        } else {
          try {
            localICommand.execute(paramICommandListener, arrayOfString);
            j++;
          } catch (CommandException localCommandException1) {
            localObject = new ChatMessage(localCommandException1.getMessage(), localCommandException1.getArgs());
            ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
            paramICommandListener.sendMessage((IChatBaseComponent)localObject);
          }
        }
      } else {
        ChatMessage localChatMessage1 = new ChatMessage("commands.generic.permission", new Object[0]);
        localChatMessage1.getChatModifier().setColor(EnumChatFormat.RED);
        paramICommandListener.sendMessage(localChatMessage1);
      }
    }
    catch (ExceptionUsage localExceptionUsage) {
      localObject = new ChatMessage("commands.generic.usage", new Object[] { new ChatMessage(localExceptionUsage.getMessage(), localExceptionUsage.getArgs()) });
      ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
      paramICommandListener.sendMessage((IChatBaseComponent)localObject);
    } catch (CommandException localCommandException2) {
      localObject = new ChatMessage(localCommandException2.getMessage(), localCommandException2.getArgs());
      ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
      paramICommandListener.sendMessage((IChatBaseComponent)localObject);
    } catch (Throwable localThrowable) {
      Object localObject = new ChatMessage("commands.generic.exception", new Object[0]);
      ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
      paramICommandListener.sendMessage((IChatBaseComponent)localObject);
      a.error("Couldn't process command: '" + paramString + "'", localThrowable);
    }
    
    return j;
  }
  
  public ICommand a(ICommand paramICommand) {
    List localList = paramICommand.b();
    
    this.b.put(paramICommand.getCommand(), paramICommand);
    this.c.add(paramICommand);
    
    if (localList != null) {
      for (String str : localList) {
        ICommand localICommand = (ICommand)this.b.get(str);
        
        if ((localICommand == null) || (!localICommand.getCommand().equals(str))) {
          this.b.put(str, paramICommand);
        }
      }
    }
    
    return paramICommand;
  }
  
  private static String[] a(String[] paramArrayOfString) {
    String[] arrayOfString = new String[paramArrayOfString.length - 1];
    
    for (int i = 1; i < paramArrayOfString.length; i++) {
      arrayOfString[(i - 1)] = paramArrayOfString[i];
    }
    
    return arrayOfString;
  }
  
  public List b(ICommandListener paramICommandListener, String paramString)
  {
    String[] arrayOfString = paramString.split(" ", -1);
    String str = arrayOfString[0];
    Object localObject;
    if (arrayOfString.length == 1)
    {
      localObject = new ArrayList();
      
      for (Entry localEntry : this.b.entrySet()) {
        if ((CommandAbstract.a(str, (String)localEntry.getKey())) && (((ICommand)localEntry.getValue()).canUse(paramICommandListener))) {
          ((List)localObject).add(localEntry.getKey());
        }
      }
      
      return (List)localObject; }
    if (arrayOfString.length > 1)
    {

      localObject = (ICommand)this.b.get(str);
      
      if (localObject != null) {
        return ((ICommand)localObject).tabComplete(paramICommandListener, a(arrayOfString));
      }
    }
    
    return null;
  }
  
  public List a(ICommandListener paramICommandListener)
  {
    ArrayList localArrayList = new ArrayList();
    
    for (ICommand localICommand : this.c) {
      if (localICommand.canUse(paramICommandListener)) {
        localArrayList.add(localICommand);
      }
    }
    
    return localArrayList;
  }
  
  public Map a()
  {
    return this.b;
  }
  
  private int a(ICommand paramICommand, String[] paramArrayOfString) {
    if (paramICommand == null) {
      return -1;
    }
    
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if ((paramICommand.isListStart(paramArrayOfString, i)) && (PlayerSelector.isList(paramArrayOfString[i]))) {
        return i;
      }
    }
    
    return -1;
  }
}
