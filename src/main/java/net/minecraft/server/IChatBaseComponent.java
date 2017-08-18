package net.minecraft.server;

import java.util.List;

public abstract interface IChatBaseComponent
  extends Iterable
{
  public abstract IChatBaseComponent setChatModifier(ChatModifier paramChatModifier);
  
  public abstract ChatModifier getChatModifier();
  
  public abstract IChatBaseComponent a(String paramString);
  
  public abstract IChatBaseComponent addSibling(IChatBaseComponent paramIChatBaseComponent);
  
  public abstract String e();
  
  public abstract String c();
  
  public abstract List a();
  
  public abstract IChatBaseComponent f();
}
