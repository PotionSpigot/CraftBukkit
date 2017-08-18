package net.minecraft.server;

public abstract interface IMinecraftServer
{
  public abstract int a(String paramString, int paramInt);
  
  public abstract String a(String paramString1, String paramString2);
  
  public abstract void a(String paramString, Object paramObject);
  
  public abstract void a();
  
  public abstract String b();
  
  public abstract String y();
  
  public abstract int z();
  
  public abstract String A();
  
  public abstract String getVersion();
  
  public abstract int C();
  
  public abstract int D();
  
  public abstract String[] getPlayers();
  
  public abstract String O();
  
  public abstract String getPlugins();
  
  public abstract String g(String paramString);
  
  public abstract boolean isDebugging();
  
  public abstract void info(String paramString);
  
  public abstract void warning(String paramString);
  
  public abstract void h(String paramString);
  
  public abstract void i(String paramString);
}
