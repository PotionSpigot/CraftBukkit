package net.minecraft.server;






















class GameRuleValue
{
  private String a;
  




















  private boolean b;
  




















  private int c;
  




















  private double d;
  





















  public GameRuleValue(String paramString)
  {
    a(paramString);
  }
  
  public void a(String paramString) {
    this.a = paramString;
    this.b = Boolean.parseBoolean(paramString);
    try {
      this.c = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException1) {}
    try {
      this.d = Double.parseDouble(paramString);
    }
    catch (NumberFormatException localNumberFormatException2) {}
  }
  
  public String a() {
    return this.a;
  }
  
  public boolean b() {
    return this.b;
  }
}
