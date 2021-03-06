package net.minecraft.server;

import java.util.List;
import org.spigotmc.SpigotConfig;

public class IntCache
{
  private static int a = 256;
  private static List b = new java.util.ArrayList();
  private static List c = new java.util.ArrayList();
  private static List d = new java.util.ArrayList();
  private static List e = new java.util.ArrayList();
  

  public static synchronized int[] a(int i)
  {
    if (i <= 256) {
      if (b.isEmpty()) {
        int[] aint = new int['Ā'];
        if (c.size() < SpigotConfig.intCacheLimit) c.add(aint);
        return aint;
      }
      int[] aint = (int[])b.remove(b.size() - 1);
      if (c.size() < SpigotConfig.intCacheLimit) c.add(aint);
      return aint;
    }
    if (i > a) {
      a = i;
      d.clear();
      e.clear();
      int[] aint = new int[a];
      if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
      return aint; }
    if (d.isEmpty()) {
      int[] aint = new int[a];
      if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
      return aint;
    }
    int[] aint = (int[])d.remove(d.size() - 1);
    if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
    return aint;
  }
  
  public static synchronized void a()
  {
    if (!d.isEmpty()) {
      d.remove(d.size() - 1);
    }
    
    if (!b.isEmpty()) {
      b.remove(b.size() - 1);
    }
    
    d.addAll(e);
    b.addAll(c);
    e.clear();
    c.clear();
  }
  
  public static synchronized String b() {
    return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
  }
}
