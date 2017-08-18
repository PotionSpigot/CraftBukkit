package net.minecraft.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Logger;

public class HttpUtilities
{
  private static final AtomicInteger a = new AtomicInteger(0);
  private static final Logger b = org.apache.logging.log4j.LogManager.getLogger();
  

  public static String a(Map paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (Entry localEntry : paramMap.entrySet()) {
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append('&');
      }
      try
      {
        localStringBuilder.append(java.net.URLEncoder.encode((String)localEntry.getKey(), "UTF-8"));
      } catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
        localUnsupportedEncodingException1.printStackTrace();
      }
      
      if (localEntry.getValue() != null) {
        localStringBuilder.append('=');
        try {
          localStringBuilder.append(java.net.URLEncoder.encode(localEntry.getValue().toString(), "UTF-8"));
        } catch (UnsupportedEncodingException localUnsupportedEncodingException2) {
          localUnsupportedEncodingException2.printStackTrace();
        }
      }
    }
    
    return localStringBuilder.toString();
  }
  
  public static String a(URL paramURL, Map paramMap, boolean paramBoolean) {
    return a(paramURL, a(paramMap), paramBoolean);
  }
  
  private static String a(URL paramURL, String paramString, boolean paramBoolean) {
    try {
      Proxy localProxy = MinecraftServer.getServer() == null ? null : MinecraftServer.getServer().aq();
      if (localProxy == null) localProxy = Proxy.NO_PROXY;
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection(localProxy);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      
      localHttpURLConnection.setRequestProperty("Content-Length", "" + paramString.getBytes().length);
      localHttpURLConnection.setRequestProperty("Content-Language", "en-US");
      
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.setDoOutput(true);
      

      DataOutputStream localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
      localDataOutputStream.writeBytes(paramString);
      localDataOutputStream.flush();
      localDataOutputStream.close();
      

      BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(localHttpURLConnection.getInputStream()));
      
      StringBuffer localStringBuffer = new StringBuffer();
      String str;
      while ((str = localBufferedReader.readLine()) != null) {
        localStringBuffer.append(str);
        localStringBuffer.append('\r');
      }
      
      localBufferedReader.close();
      return localStringBuffer.toString();
    } catch (Exception localException) {
      if (!paramBoolean)
        b.error("Could not post to " + paramURL, localException);
    }
    return "";
  }
}
