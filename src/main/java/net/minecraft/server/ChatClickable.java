package net.minecraft.server;


public class ChatClickable
{
  private final EnumClickAction a;
  
  private final String b;
  
  public ChatClickable(EnumClickAction paramEnumClickAction, String paramString)
  {
    this.a = paramEnumClickAction;
    this.b = paramString;
  }
  
  public EnumClickAction a() {
    return this.a;
  }
  
  public String b() {
    return this.b;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) return true;
    if ((paramObject == null) || (getClass() != paramObject.getClass())) { return false;
    }
    ChatClickable localChatClickable = (ChatClickable)paramObject;
    
    if (this.a != localChatClickable.a) return false;
    if (this.b != null ? !this.b.equals(localChatClickable.b) : localChatClickable.b != null) { return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
  }
  



  public int hashCode()
  {
    int i = this.a.hashCode();
    i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
    return i;
  }
}
