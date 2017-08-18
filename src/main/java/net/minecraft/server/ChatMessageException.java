package net.minecraft.server;

public class ChatMessageException extends IllegalArgumentException {
  public ChatMessageException(ChatMessage paramChatMessage, String paramString) {
    super(String.format("Error parsing: %s: %s", new Object[] { paramChatMessage, paramString }));
  }
  
  public ChatMessageException(ChatMessage paramChatMessage, int paramInt) {
    super(String.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(paramInt), paramChatMessage }));
  }
  
  public ChatMessageException(ChatMessage paramChatMessage, Throwable paramThrowable) {
    super(String.format("Error while parsing: %s", new Object[] { paramChatMessage }), paramThrowable);
  }
}
