package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.Maps;



















































public enum EnumClickAction
{
  private static final Map f;
  private final boolean g;
  private final String h;
  
  private EnumClickAction(String paramString1, boolean paramBoolean)
  {
    this.h = paramString1;
    this.g = paramBoolean;
  }
  
  public boolean a() {
    return this.g;
  }
  
  public String b() {
    return this.h;
  }
  
  static
  {
    f = Maps.newHashMap();
    
















    for (EnumClickAction localEnumClickAction : values()) {
      f.put(localEnumClickAction.b(), localEnumClickAction);
    }
  }
  
  public static EnumClickAction a(String paramString) {
    return (EnumClickAction)f.get(paramString);
  }
}
