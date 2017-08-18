package net.minecraft.server;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.com.google.common.collect.Iterators;
import net.minecraft.util.com.google.common.collect.Lists;

public class ChatMessage
  extends ChatBaseComponent
{
  private final String d;
  private final Object[] e;
  private final Object f = new Object();
  private long g = -1L;
  
  List b = Lists.newArrayList();
  

  public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
  
  public ChatMessage(String paramString, Object... paramVarArgs) {
    this.d = paramString;
    this.e = paramVarArgs;
    
    for (Object localObject : paramVarArgs) {
      if ((localObject instanceof IChatBaseComponent)) {
        ((IChatBaseComponent)localObject).getChatModifier().a(getChatModifier());
      }
    }
  }
  
  synchronized void g()
  {
    synchronized (this.f) {
      long l = LocaleI18n.a();
      if (l == this.g) {
        return;
      }
      this.g = l;
      this.b.clear();
    }
    try
    {
      b(LocaleI18n.get(this.d));
    } catch (ChatMessageException localChatMessageException1) {
      this.b.clear();
      try {
        b(LocaleI18n.b(this.d));
      } catch (ChatMessageException localChatMessageException2) {
        throw localChatMessageException1;
      }
    }
  }
  
  protected void b(String paramString) {
    int i = 0;
    Matcher localMatcher = c.matcher(paramString);
    
    int j = 0;
    int k = 0;
    try
    {
      while (localMatcher.find(k)) {
        int m = localMatcher.start();
        int n = localMatcher.end();
        

        if (m > k) {
          localObject1 = new ChatComponentText(String.format(paramString.substring(k, m), new Object[0]));
          ((ChatComponentText)localObject1).getChatModifier().a(getChatModifier());
          this.b.add(localObject1);
        }
        
        Object localObject1 = localMatcher.group(2);
        String str = paramString.substring(m, n);
        
        Object localObject2;
        if (("%".equals(localObject1)) && ("%%".equals(str))) {
          localObject2 = new ChatComponentText("%");
          ((ChatComponentText)localObject2).getChatModifier().a(getChatModifier());
          this.b.add(localObject2);
        } else if ("s".equals(localObject1)) {
          localObject2 = localMatcher.group(1);
          int i1 = localObject2 != null ? Integer.parseInt((String)localObject2) - 1 : j++;
          this.b.add(a(i1));
        } else {
          throw new ChatMessageException(this, "Unsupported format: '" + str + "'");
        }
        
        k = n;
      }
      

      if (k < paramString.length()) {
        ChatComponentText localChatComponentText = new ChatComponentText(String.format(paramString.substring(k), new Object[0]));
        localChatComponentText.getChatModifier().a(getChatModifier());
        this.b.add(localChatComponentText);
      }
    } catch (IllegalFormatException localIllegalFormatException) {
      throw new ChatMessageException(this, localIllegalFormatException);
    }
  }
  
  private IChatBaseComponent a(int paramInt) {
    if (paramInt >= this.e.length) {
      throw new ChatMessageException(this, paramInt);
    }
    
    Object localObject1 = this.e[paramInt];
    
    Object localObject2;
    if ((localObject1 instanceof IChatBaseComponent)) {
      localObject2 = (IChatBaseComponent)localObject1;
    } else {
      localObject2 = new ChatComponentText(localObject1 == null ? "null" : localObject1.toString());
      ((IChatBaseComponent)localObject2).getChatModifier().a(getChatModifier());
    }
    
    return (IChatBaseComponent)localObject2;
  }
  
  public IChatBaseComponent setChatModifier(ChatModifier paramChatModifier)
  {
    super.setChatModifier(paramChatModifier);
    
    for (Object localObject2 : this.e) {
      if ((localObject2 instanceof IChatBaseComponent)) {
        ((IChatBaseComponent)localObject2).getChatModifier().a(getChatModifier());
      }
    }
    
    if (this.g > -1L) {
      for (??? = this.b.iterator(); ((Iterator)???).hasNext();) { IChatBaseComponent localIChatBaseComponent = (IChatBaseComponent)((Iterator)???).next();
        localIChatBaseComponent.getChatModifier().a(paramChatModifier);
      }
    }
    
    return this;
  }
  
  public Iterator iterator()
  {
    g();
    
    return Iterators.concat(a(this.b), a(this.a));
  }
  
  public String e()
  {
    g();
    
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (IChatBaseComponent localIChatBaseComponent : this.b) {
      localStringBuilder.append(localIChatBaseComponent.e());
    }
    
    return localStringBuilder.toString();
  }
  
  public ChatMessage h()
  {
    Object[] arrayOfObject = new Object[this.e.length];
    
    for (int i = 0; i < this.e.length; i++) {
      if ((this.e[i] instanceof IChatBaseComponent)) {
        arrayOfObject[i] = ((IChatBaseComponent)this.e[i]).f();
      } else {
        arrayOfObject[i] = this.e[i];
      }
    }
    
    ChatMessage localChatMessage = new ChatMessage(this.d, arrayOfObject);
    localChatMessage.setChatModifier(getChatModifier().clone());
    for (IChatBaseComponent localIChatBaseComponent : a()) {
      localChatMessage.addSibling(localIChatBaseComponent.f());
    }
    return localChatMessage;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) { return true;
    }
    if ((paramObject instanceof ChatMessage)) {
      ChatMessage localChatMessage = (ChatMessage)paramObject;
      return (Arrays.equals(this.e, localChatMessage.e)) && (this.d.equals(localChatMessage.d)) && (super.equals(paramObject));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    int i = super.hashCode();
    i = 31 * i + this.d.hashCode();
    i = 31 * i + Arrays.hashCode(this.e);
    return i;
  }
  
  public String toString()
  {
    return "TranslatableComponent{key='" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
  }
  




  public String i()
  {
    return this.d;
  }
  
  public Object[] j() {
    return this.e;
  }
}
