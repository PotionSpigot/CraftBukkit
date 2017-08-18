package net.minecraft.server;

import net.minecraft.util.com.google.common.base.Function;




















































































final class ChatFunction2
  implements Function
{
  public IChatBaseComponent a(IChatBaseComponent paramIChatBaseComponent)
  {
    IChatBaseComponent localIChatBaseComponent = paramIChatBaseComponent.f();
    localIChatBaseComponent.setChatModifier(localIChatBaseComponent.getChatModifier().m());
    return localIChatBaseComponent;
  }
}
