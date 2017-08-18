package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.Maps;

















































public enum EnumHoverAction
{
  private static final Map d;
  private final boolean e;
  private final String f;
  
  private EnumHoverAction(String paramString1, boolean paramBoolean)
  {
    this.f = paramString1;
    this.e = paramBoolean;
  }
  
  public boolean a() {
    return this.e;
  }
  
  public String b() {
    return this.f;
  }
  
  static
  {
    d = Maps.newHashMap();
    
















    for (EnumHoverAction localEnumHoverAction : values()) {
      d.put(localEnumHoverAction.b(), localEnumHoverAction);
    }
  }
  
  public static EnumHoverAction a(String paramString) {
    return (EnumHoverAction)d.get(paramString);
  }
}
