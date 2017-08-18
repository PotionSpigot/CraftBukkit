package net.minecraft.server;


public class ChatHoverable
{
  private final EnumHoverAction a;
  
  private final IChatBaseComponent b;
  
  public ChatHoverable(EnumHoverAction paramEnumHoverAction, IChatBaseComponent paramIChatBaseComponent)
  {
    this.a = paramEnumHoverAction;
    this.b = paramIChatBaseComponent;
  }
  
  public EnumHoverAction a() {
    return this.a;
  }
  
  public IChatBaseComponent b() {
    return this.b;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) return true;
    if ((paramObject == null) || (getClass() != paramObject.getClass())) { return false;
    }
    ChatHoverable localChatHoverable = (ChatHoverable)paramObject;
    
    if (this.a != localChatHoverable.a) return false;
    if (this.b != null ? !this.b.equals(localChatHoverable.b) : localChatHoverable.b != null) { return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "HoverEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
  }
  



  public int hashCode()
  {
    int i = this.a.hashCode();
    i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
    return i;
  }
}
