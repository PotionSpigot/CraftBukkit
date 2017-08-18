package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.util.com.google.common.primitives.Doubles;













public abstract class CommandAbstract
  implements ICommand
{
  private static ICommandDispatcher a;
  
  public int a()
  {
    return 4;
  }
  
  public List b()
  {
    return null;
  }
  
  public boolean canUse(ICommandListener paramICommandListener)
  {
    return paramICommandListener.a(a(), getCommand());
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    return null;
  }
  
  public static int a(ICommandListener paramICommandListener, String paramString) {
    try {
      return Integer.parseInt(paramString);
    } catch (NumberFormatException localNumberFormatException) {
      throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
    }
  }
  
  public static int a(ICommandListener paramICommandListener, String paramString, int paramInt) {
    return a(paramICommandListener, paramString, paramInt, Integer.MAX_VALUE);
  }
  
  public static int a(ICommandListener paramICommandListener, String paramString, int paramInt1, int paramInt2) {
    int i = a(paramICommandListener, paramString);
    
    if (i < paramInt1)
      throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt1) });
    if (i > paramInt2) {
      throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt2) });
    }
    
    return i;
  }
  
  public static double b(ICommandListener paramICommandListener, String paramString) {
    try {
      double d = Double.parseDouble(paramString);
      if (!Doubles.isFinite(d)) throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
      return d;
    } catch (NumberFormatException localNumberFormatException) {
      throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
    }
  }
  
  public static double a(ICommandListener paramICommandListener, String paramString, double paramDouble) {
    return a(paramICommandListener, paramString, paramDouble, Double.MAX_VALUE);
  }
  
  public static double a(ICommandListener paramICommandListener, String paramString, double paramDouble1, double paramDouble2) {
    double d = b(paramICommandListener, paramString);
    
    if (d < paramDouble1)
      throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble1) });
    if (d > paramDouble2) {
      throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble2) });
    }
    
    return d;
  }
  
  public static boolean c(ICommandListener paramICommandListener, String paramString) {
    if ((paramString.equals("true")) || (paramString.equals("1")))
      return true;
    if ((paramString.equals("false")) || (paramString.equals("0"))) {
      return false;
    }
    throw new CommandException("commands.generic.boolean.invalid", new Object[] { paramString });
  }
  
  public static EntityPlayer b(ICommandListener paramICommandListener)
  {
    if ((paramICommandListener instanceof EntityPlayer)) {
      return (EntityPlayer)paramICommandListener;
    }
    throw new ExceptionPlayerNotFound("You must specify which player you wish to perform this action on.", new Object[0]);
  }
  
  public static EntityPlayer d(ICommandListener paramICommandListener, String paramString)
  {
    EntityPlayer localEntityPlayer = PlayerSelector.getPlayer(paramICommandListener, paramString);
    if (localEntityPlayer != null) { return localEntityPlayer;
    }
    localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramString);
    
    if (localEntityPlayer == null) {
      throw new ExceptionPlayerNotFound();
    }
    return localEntityPlayer;
  }
  
  public static String e(ICommandListener paramICommandListener, String paramString)
  {
    EntityPlayer localEntityPlayer = PlayerSelector.getPlayer(paramICommandListener, paramString);
    
    if (localEntityPlayer != null)
      return localEntityPlayer.getName();
    if (PlayerSelector.isPattern(paramString)) {
      throw new ExceptionPlayerNotFound();
    }
    
    return paramString;
  }
  
  public static IChatBaseComponent a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    return a(paramICommandListener, paramArrayOfString, paramInt, false);
  }
  
  public static IChatBaseComponent a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
    ChatComponentText localChatComponentText = new ChatComponentText("");
    
    for (int i = paramInt; i < paramArrayOfString.length; i++) {
      if (i > paramInt) localChatComponentText.a(" ");
      Object localObject = new ChatComponentText(paramArrayOfString[i]);
      
      if (paramBoolean) {
        IChatBaseComponent localIChatBaseComponent = PlayerSelector.getPlayerNames(paramICommandListener, paramArrayOfString[i]);
        
        if (localIChatBaseComponent != null) {
          localObject = localIChatBaseComponent;
        } else if (PlayerSelector.isPattern(paramArrayOfString[i])) {
          throw new ExceptionPlayerNotFound();
        }
      }
      
      localChatComponentText.addSibling((IChatBaseComponent)localObject);
    }
    
    return localChatComponentText;
  }
  
  public static String b(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (int i = paramInt; i < paramArrayOfString.length; i++) {
      if (i > paramInt) localStringBuilder.append(" ");
      String str = paramArrayOfString[i];
      
      localStringBuilder.append(str);
    }
    
    return localStringBuilder.toString();
  }
  
  public static double a(ICommandListener paramICommandListener, double paramDouble, String paramString) {
    return a(paramICommandListener, paramDouble, paramString, -30000000, 30000000);
  }
  
  public static double a(ICommandListener paramICommandListener, double paramDouble, String paramString, int paramInt1, int paramInt2) {
    boolean bool1 = paramString.startsWith("~");
    if ((bool1) && (Double.isNaN(paramDouble))) throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { Double.valueOf(paramDouble) });
    double d = bool1 ? paramDouble : 0.0D;
    
    if ((!bool1) || (paramString.length() > 1)) {
      boolean bool2 = paramString.contains(".");
      if (bool1) { paramString = paramString.substring(1);
      }
      d += b(paramICommandListener, paramString);
      
      if ((!bool2) && (!bool1)) {
        d += 0.5D;
      }
    }
    
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      if (d < paramInt1)
        throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt1) });
      if (d > paramInt2) {
        throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt2) });
      }
    }
    
    return d;
  }
  
  public static Item f(ICommandListener paramICommandListener, String paramString) {
    Object localObject = (Item)Item.REGISTRY.get(paramString);
    
    if (localObject == null) {
      try {
        Item localItem = Item.getById(Integer.parseInt(paramString));
        
        if (localItem != null) {
          ChatMessage localChatMessage = new ChatMessage("commands.generic.deprecatedId", new Object[] { Item.REGISTRY.c(localItem) });
          localChatMessage.getChatModifier().setColor(EnumChatFormat.GRAY);
          paramICommandListener.sendMessage(localChatMessage);
        }
        
        localObject = localItem;
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    
    if (localObject == null) {
      throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] { paramString });
    }
    
    return (Item)localObject;
  }
  
  public static Block g(ICommandListener paramICommandListener, String paramString) {
    if (Block.REGISTRY.b(paramString)) {
      return (Block)Block.REGISTRY.get(paramString);
    }
    try
    {
      int i = Integer.parseInt(paramString);
      
      if (Block.REGISTRY.b(i)) {
        Block localBlock = Block.getById(i);
        
        ChatMessage localChatMessage = new ChatMessage("commands.generic.deprecatedId", new Object[] { Block.REGISTRY.c(localBlock) });
        localChatMessage.getChatModifier().setColor(EnumChatFormat.GRAY);
        paramICommandListener.sendMessage(localChatMessage);
        
        return localBlock;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    
    throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] { paramString });
  }
  
  public static String a(Object[] paramArrayOfObject) {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      String str = paramArrayOfObject[i].toString();
      
      if (i > 0) {
        if (i == paramArrayOfObject.length - 1) {
          localStringBuilder.append(" and ");
        } else {
          localStringBuilder.append(", ");
        }
      }
      
      localStringBuilder.append(str);
    }
    
    return localStringBuilder.toString();
  }
  
  public static IChatBaseComponent a(IChatBaseComponent[] paramArrayOfIChatBaseComponent) {
    ChatComponentText localChatComponentText = new ChatComponentText("");
    
    for (int i = 0; i < paramArrayOfIChatBaseComponent.length; i++) {
      if (i > 0) {
        if (i == paramArrayOfIChatBaseComponent.length - 1) {
          localChatComponentText.a(" and ");
        } else if (i > 0) {
          localChatComponentText.a(", ");
        }
      }
      
      localChatComponentText.addSibling(paramArrayOfIChatBaseComponent[i]);
    }
    
    return localChatComponentText;
  }
  
  public static String a(Collection paramCollection) {
    return a(paramCollection.toArray(new String[paramCollection.size()]));
  }
  
  public static boolean a(String paramString1, String paramString2) {
    return paramString2.regionMatches(true, 0, paramString1, 0, paramString1.length());
  }
  
  public static List a(String[] paramArrayOfString1, String... paramVarArgs) {
    String str1 = paramArrayOfString1[(paramArrayOfString1.length - 1)];
    ArrayList localArrayList = new ArrayList();
    
    for (String str2 : paramVarArgs) {
      if (a(str1, str2)) {
        localArrayList.add(str2);
      }
    }
    
    return localArrayList;
  }
  
  public static List a(String[] paramArrayOfString, Iterable paramIterable) {
    String str1 = paramArrayOfString[(paramArrayOfString.length - 1)];
    ArrayList localArrayList = new ArrayList();
    
    for (String str2 : paramIterable) {
      if (a(str1, str2)) {
        localArrayList.add(str2);
      }
    }
    
    return localArrayList;
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return false;
  }
  
  public static void a(ICommandListener paramICommandListener, ICommand paramICommand, String paramString, Object... paramVarArgs) {
    a(paramICommandListener, paramICommand, 0, paramString, paramVarArgs);
  }
  
  public static void a(ICommandListener paramICommandListener, ICommand paramICommand, int paramInt, String paramString, Object... paramVarArgs) {
    if (a != null) {
      a.a(paramICommandListener, paramICommand, paramInt, paramString, paramVarArgs);
    }
  }
  
  public static void a(ICommandDispatcher paramICommandDispatcher) {
    a = paramICommandDispatcher;
  }
  
  public int a(ICommand paramICommand)
  {
    return getCommand().compareTo(paramICommand.getCommand());
  }
}
