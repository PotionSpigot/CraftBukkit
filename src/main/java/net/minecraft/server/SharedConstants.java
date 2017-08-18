package net.minecraft.server;




























public class SharedConstants
{
  public static boolean isAllowedChatCharacter(char paramChar)
  {
    return (paramChar != '§') && (paramChar >= ' ') && (paramChar != '');
  }
  
  public static final char[] allowedCharacters = { '/', '\n', '\r', '\t', '\000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
  





  public static String a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (char c : paramString.toCharArray()) {
      if (isAllowedChatCharacter(c)) {
        localStringBuilder.append(c);
      }
    }
    
    return localStringBuilder.toString();
  }
}
