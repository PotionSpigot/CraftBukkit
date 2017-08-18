package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.com.google.common.collect.Iterators;
import net.minecraft.util.com.google.common.collect.Lists;


public abstract class ChatBaseComponent
  implements IChatBaseComponent
{
  protected List a = Lists.newArrayList();
  private ChatModifier b;
  
  public IChatBaseComponent addSibling(IChatBaseComponent paramIChatBaseComponent)
  {
    paramIChatBaseComponent.getChatModifier().a(getChatModifier());
    this.a.add(paramIChatBaseComponent);
    return this;
  }
  
  public List a()
  {
    return this.a;
  }
  
  public IChatBaseComponent a(String paramString)
  {
    return addSibling(new ChatComponentText(paramString));
  }
  
  public IChatBaseComponent setChatModifier(ChatModifier paramChatModifier)
  {
    this.b = paramChatModifier;
    
    for (IChatBaseComponent localIChatBaseComponent : this.a) {
      localIChatBaseComponent.getChatModifier().a(getChatModifier());
    }
    
    return this;
  }
  
  public ChatModifier getChatModifier()
  {
    if (this.b == null) {
      this.b = new ChatModifier();
      for (IChatBaseComponent localIChatBaseComponent : this.a) {
        localIChatBaseComponent.getChatModifier().a(this.b);
      }
    }
    return this.b;
  }
  
  public Iterator iterator()
  {
    return Iterators.concat(Iterators.forArray(new ChatBaseComponent[] { this }), a(this.a));
  }
  
  public final String c()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (IChatBaseComponent localIChatBaseComponent : this) {
      localStringBuilder.append(localIChatBaseComponent.e());
    }
    
    return localStringBuilder.toString();
  }
  












  public static Iterator a(Iterable paramIterable)
  {
    Iterator localIterator = Iterators.concat(Iterators.transform(paramIterable.iterator(), new ChatFunction1()));
    




    localIterator = Iterators.transform(localIterator, new ChatFunction2());
    






    return localIterator;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) { return true;
    }
    if ((paramObject instanceof ChatBaseComponent)) {
      ChatBaseComponent localChatBaseComponent = (ChatBaseComponent)paramObject;
      return (this.a.equals(localChatBaseComponent.a)) && (getChatModifier().equals(localChatBaseComponent.getChatModifier()));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return 31 * this.b.hashCode() + this.a.hashCode();
  }
  
  public String toString()
  {
    return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
  }
}
