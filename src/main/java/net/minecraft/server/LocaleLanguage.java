package net.minecraft.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.com.google.common.base.Splitter;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.org.apache.commons.io.Charsets;
import net.minecraft.util.org.apache.commons.io.IOUtils;

public class LocaleLanguage
{
  private static final Pattern a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
  private static final Splitter b = Splitter.on('=').limit(2);
  
  private static LocaleLanguage c = new LocaleLanguage();
  
  private final Map d = Maps.newHashMap();
  private long e;
  
  public LocaleLanguage() {
    try {
      InputStream localInputStream = LocaleLanguage.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
      for (String str1 : IOUtils.readLines(localInputStream, Charsets.UTF_8))
      {
        if ((!str1.isEmpty()) && (str1.charAt(0) != '#'))
        {
          String[] arrayOfString = (String[])net.minecraft.util.com.google.common.collect.Iterables.toArray(b.split(str1), String.class);
          

          if ((arrayOfString != null) && (arrayOfString.length == 2))
          {



            String str2 = arrayOfString[0];
            String str3 = a.matcher(arrayOfString[1]).replaceAll("%$1s");
            
            this.d.put(str2, str3);
          } } }
      this.e = System.currentTimeMillis();
    }
    catch (IOException localIOException) {}
  }
  

  static LocaleLanguage a()
  {
    return c;
  }
  































  public synchronized String a(String paramString)
  {
    return c(paramString);
  }
  
  public synchronized String a(String paramString, Object... paramVarArgs) {
    String str = c(paramString);
    try {
      return String.format(str, paramVarArgs);
    } catch (IllegalFormatException localIllegalFormatException) {}
    return "Format error: " + str;
  }
  
  private String c(String paramString)
  {
    String str = (String)this.d.get(paramString);
    return str == null ? paramString : str;
  }
  
  public synchronized boolean b(String paramString) {
    return this.d.containsKey(paramString);
  }
  
  public long c() {
    return this.e;
  }
}
