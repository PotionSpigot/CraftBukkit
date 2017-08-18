package net.minecraft.server;



public class LocaleI18n
{
  private static LocaleLanguage a = ;
  private static LocaleLanguage b = new LocaleLanguage();
  
  public static String get(String paramString)
  {
    return a.a(paramString);
  }
  
  public static String get(String paramString, Object... paramVarArgs)
  {
    return a.a(paramString, paramVarArgs);
  }
  
  public static String b(String paramString)
  {
    return b.a(paramString);
  }
  





  public static boolean c(String paramString)
  {
    return a.b(paramString);
  }
  
  public static long a() {
    return a.c();
  }
}
