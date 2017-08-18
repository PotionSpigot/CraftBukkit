package net.minecraft.server;

public class ChatComponentText extends ChatBaseComponent {
  private final String b;
  
  public ChatComponentText(String paramString) {
    this.b = paramString;
  }
  
  public String g() {
    return this.b;
  }
  
  public String e()
  {
    return this.b;
  }
  
  public ChatComponentText h()
  {
    ChatComponentText localChatComponentText = new ChatComponentText(this.b);
    localChatComponentText.setChatModifier(getChatModifier().clone());
    for (IChatBaseComponent localIChatBaseComponent : a()) {
      localChatComponentText.addSibling(localIChatBaseComponent.f());
    }
    return localChatComponentText;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) { return true;
    }
    if ((paramObject instanceof ChatComponentText)) {
      ChatComponentText localChatComponentText = (ChatComponentText)paramObject;
      return (this.b.equals(localChatComponentText.g())) && (super.equals(paramObject));
    }
    
    return false;
  }
  
  public String toString()
  {
    return "TextComponent{text='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
  }
}
