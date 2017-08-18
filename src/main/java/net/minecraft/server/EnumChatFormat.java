package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;




















public enum EnumChatFormat
{
  private static final Map w;
  private static final Map x;
  private static final Pattern y;
  private final char z;
  private final boolean A;
  private final String B;
  
  private EnumChatFormat(char paramChar)
  {
    this(paramChar, false);
  }
  
  private EnumChatFormat(char paramChar, boolean paramBoolean) {
    this.z = paramChar;
    this.A = paramBoolean;
    
    this.B = ("§" + paramChar);
  }
  
  static
  {
    w = new HashMap();
    x = new HashMap();
    y = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
    
















    for (EnumChatFormat localEnumChatFormat : values()) {
      w.put(Character.valueOf(localEnumChatFormat.getChar()), localEnumChatFormat);
      x.put(localEnumChatFormat.d(), localEnumChatFormat);
    }
  }
  
  public char getChar() {
    return this.z;
  }
  
  public boolean isFormat() {
    return this.A;
  }
  
  public boolean c() {
    return (!this.A) && (this != RESET);
  }
  
  public String d() {
    return name().toLowerCase();
  }
  
  public String toString()
  {
    return this.B;
  }
  







  public static EnumChatFormat b(String paramString)
  {
    if (paramString == null) return null;
    return (EnumChatFormat)x.get(paramString.toLowerCase());
  }
  
  public static Collection a(boolean paramBoolean1, boolean paramBoolean2) {
    ArrayList localArrayList = new ArrayList();
    
    for (EnumChatFormat localEnumChatFormat : values()) {
      if (((!localEnumChatFormat.c()) || (paramBoolean1)) && (
        (!localEnumChatFormat.isFormat()) || (paramBoolean2))) {
        localArrayList.add(localEnumChatFormat.d());
      }
    }
    return localArrayList;
  }
}
