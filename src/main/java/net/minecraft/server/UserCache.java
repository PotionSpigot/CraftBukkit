package net.minecraft.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.util.com.google.common.base.Charsets;
import net.minecraft.util.com.google.common.collect.Iterators;
import net.minecraft.util.com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.com.google.common.io.Files;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.mojang.authlib.Agent;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.GameProfileRepository;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.spigotmc.SpigotConfig;

public class UserCache
{
  public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
  private final Map c = Maps.newHashMap();
  private final Map d = Maps.newHashMap();
  private final LinkedList e = Lists.newLinkedList();
  private final MinecraftServer f;
  protected final Gson b;
  private final File g;
  private static final ParameterizedType h = new UserCacheEntryType();
  
  public UserCache(MinecraftServer minecraftserver, File file1) {
    this.f = minecraftserver;
    this.g = file1;
    GsonBuilder gsonbuilder = new GsonBuilder();
    
    gsonbuilder.registerTypeHierarchyAdapter(UserCacheEntry.class, new BanEntrySerializer(this, (GameProfileLookup)null));
    this.b = gsonbuilder.create();
    b();
  }
  
  private static GameProfile a(MinecraftServer minecraftserver, String s) {
    GameProfile[] agameprofile = new GameProfile[1];
    GameProfileLookup gameprofilelookup = new GameProfileLookup(agameprofile);
    
    minecraftserver.getGameProfileRepository().findProfilesByNames(new String[] { s }, Agent.MINECRAFT, gameprofilelookup);
    if ((!minecraftserver.getOnlineMode()) && (agameprofile[0] == null)) {
      UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
      GameProfile gameprofile = new GameProfile(uuid, s);
      
      gameprofilelookup.onProfileLookupSucceeded(gameprofile);
    }
    
    return agameprofile[0];
  }
  
  public void a(GameProfile gameprofile) {
    a(gameprofile, (Date)null);
  }
  
  private void a(GameProfile gameprofile, Date date) {
    UUID uuid = gameprofile.getId();
    
    if (date == null) {
      Calendar calendar = Calendar.getInstance();
      
      calendar.setTime(new Date());
      calendar.add(2, 1);
      date = calendar.getTime();
    }
    
    String s = gameprofile.getName().toLowerCase(Locale.ROOT);
    UserCacheEntry usercacheentry = new UserCacheEntry(this, gameprofile, date, (GameProfileLookup)null);
    LinkedList linkedlist = this.e;
    
    synchronized (this.e) {
      if (this.d.containsKey(uuid)) {
        UserCacheEntry usercacheentry1 = (UserCacheEntry)this.d.get(uuid);
        
        this.c.remove(usercacheentry1.a().getName().toLowerCase(Locale.ROOT));
        this.c.put(gameprofile.getName().toLowerCase(Locale.ROOT), usercacheentry);
        this.e.remove(gameprofile);
      } else {
        this.d.put(uuid, usercacheentry);
        this.c.put(s, usercacheentry);
      }
      
      this.e.addFirst(gameprofile);
    }
  }
  
  public GameProfile getProfile(String s) {
    String s1 = s.toLowerCase(Locale.ROOT);
    UserCacheEntry usercacheentry = (UserCacheEntry)this.c.get(s1);
    
    if ((usercacheentry != null) && (new Date().getTime() >= UserCacheEntry.a(usercacheentry).getTime())) {
      this.d.remove(usercacheentry.a().getId());
      this.c.remove(usercacheentry.a().getName().toLowerCase(Locale.ROOT));
      LinkedList linkedlist = this.e;
      
      synchronized (this.e) {
        this.e.remove(usercacheentry.a());
      }
      
      usercacheentry = null;
    }
    


    if (usercacheentry != null) {
      GameProfile gameprofile = usercacheentry.a();
      LinkedList linkedlist1 = this.e;
      
      synchronized (this.e) {
        this.e.remove(gameprofile);
        this.e.addFirst(gameprofile);
      }
    } else {
      GameProfile gameprofile = a(this.f, s);
      if (gameprofile != null) {
        a(gameprofile);
        usercacheentry = (UserCacheEntry)this.c.get(s1);
      }
    }
    
    if (!SpigotConfig.saveUserCacheOnStopOnly) c();
    return usercacheentry == null ? null : usercacheentry.a();
  }
  
  public String[] a() {
    ArrayList arraylist = Lists.newArrayList(this.c.keySet());
    
    return (String[])arraylist.toArray(new String[arraylist.size()]);
  }
  
  public GameProfile a(UUID uuid) {
    UserCacheEntry usercacheentry = (UserCacheEntry)this.d.get(uuid);
    
    return usercacheentry == null ? null : usercacheentry.a();
  }
  
  private UserCacheEntry b(UUID uuid) {
    UserCacheEntry usercacheentry = (UserCacheEntry)this.d.get(uuid);
    
    if (usercacheentry != null) {
      GameProfile gameprofile = usercacheentry.a();
      LinkedList linkedlist = this.e;
      
      synchronized (this.e) {
        this.e.remove(gameprofile);
        this.e.addFirst(gameprofile);
      }
    }
    
    return usercacheentry;
  }
  
  /* Error */
  public void b()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: getfield 47	net/minecraft/server/v1_7_R4/UserCache:g	Ljava/io/File;
    //   8: getstatic 272	net/minecraft/util/com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
    //   11: invokestatic 278	net/minecraft/util/com/google/common/io/Files:newReader	(Ljava/io/File;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;
    //   14: astore_2
    //   15: aload_0
    //   16: getfield 69	net/minecraft/server/v1_7_R4/UserCache:b	Lnet/minecraft/util/com/google/gson/Gson;
    //   19: aload_2
    //   20: getstatic 280	net/minecraft/server/v1_7_R4/UserCache:h	Ljava/lang/reflect/ParameterizedType;
    //   23: invokevirtual 286	net/minecraft/util/com/google/gson/Gson:fromJson	(Ljava/io/Reader;Ljava/lang/reflect/Type;)Ljava/lang/Object;
    //   26: checkcast 288	java/util/List
    //   29: astore_1
    //   30: aload_2
    //   31: invokestatic 294	net/minecraft/util/org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
    //   34: goto +48 -> 82
    //   37: astore_3
    //   38: aload_2
    //   39: invokestatic 294	net/minecraft/util/org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
    //   42: goto +39 -> 81
    //   45: astore_3
    //   46: getstatic 301	net/minecraft/server/v1_7_R4/JsonList:a	Lorg/apache/logging/log4j/Logger;
    //   49: ldc_w 303
    //   52: invokeinterface 309 2 0
    //   57: aload_0
    //   58: getfield 47	net/minecraft/server/v1_7_R4/UserCache:g	Ljava/io/File;
    //   61: invokevirtual 314	java/io/File:delete	()Z
    //   64: pop
    //   65: aload_2
    //   66: invokestatic 294	net/minecraft/util/org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
    //   69: goto +12 -> 81
    //   72: astore 4
    //   74: aload_2
    //   75: invokestatic 294	net/minecraft/util/org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
    //   78: aload 4
    //   80: athrow
    //   81: return
    //   82: aload_1
    //   83: ifnull +112 -> 195
    //   86: aload_0
    //   87: getfield 33	net/minecraft/server/v1_7_R4/UserCache:c	Ljava/util/Map;
    //   90: invokeinterface 317 1 0
    //   95: aload_0
    //   96: getfield 35	net/minecraft/server/v1_7_R4/UserCache:d	Ljava/util/Map;
    //   99: invokeinterface 317 1 0
    //   104: aload_0
    //   105: getfield 43	net/minecraft/server/v1_7_R4/UserCache:e	Ljava/util/LinkedList;
    //   108: astore_3
    //   109: aload_0
    //   110: getfield 43	net/minecraft/server/v1_7_R4/UserCache:e	Ljava/util/LinkedList;
    //   113: dup
    //   114: astore 4
    //   116: monitorenter
    //   117: aload_0
    //   118: getfield 43	net/minecraft/server/v1_7_R4/UserCache:e	Ljava/util/LinkedList;
    //   121: invokevirtual 318	java/util/LinkedList:clear	()V
    //   124: aload 4
    //   126: monitorexit
    //   127: goto +11 -> 138
    //   130: astore 5
    //   132: aload 4
    //   134: monitorexit
    //   135: aload 5
    //   137: athrow
    //   138: aload_1
    //   139: invokestatic 322	net/minecraft/util/com/google/common/collect/Lists:reverse	(Ljava/util/List;)Ljava/util/List;
    //   142: astore_1
    //   143: aload_1
    //   144: invokeinterface 326 1 0
    //   149: astore 4
    //   151: aload 4
    //   153: invokeinterface 331 1 0
    //   158: ifeq +37 -> 195
    //   161: aload 4
    //   163: invokeinterface 335 1 0
    //   168: checkcast 52	net/minecraft/server/v1_7_R4/UserCacheEntry
    //   171: astore 5
    //   173: aload 5
    //   175: ifnull +17 -> 192
    //   178: aload_0
    //   179: aload 5
    //   181: invokevirtual 190	net/minecraft/server/v1_7_R4/UserCacheEntry:a	()Lnet/minecraft/util/com/mojang/authlib/GameProfile;
    //   184: aload 5
    //   186: invokevirtual 337	net/minecraft/server/v1_7_R4/UserCacheEntry:b	()Ljava/util/Date;
    //   189: invokespecial 137	net/minecraft/server/v1_7_R4/UserCache:a	(Lnet/minecraft/util/com/mojang/authlib/GameProfile;Ljava/util/Date;)V
    //   192: goto -41 -> 151
    //   195: return
    // Line number table:
    //   Java source line #169	-> byte code offset #0
    //   Java source line #170	-> byte code offset #2
    //   Java source line #174	-> byte code offset #4
    //   Java source line #175	-> byte code offset #15
    //   Java source line #185	-> byte code offset #30
    //   Java source line #177	-> byte code offset #37
    //   Java source line #185	-> byte code offset #38
    //   Java source line #186	-> byte code offset #42
    //   Java source line #180	-> byte code offset #45
    //   Java source line #181	-> byte code offset #46
    //   Java source line #182	-> byte code offset #57
    //   Java source line #185	-> byte code offset #65
    //   Java source line #186	-> byte code offset #69
    //   Java source line #185	-> byte code offset #72
    //   Java source line #188	-> byte code offset #81
    //   Java source line #191	-> byte code offset #82
    //   Java source line #192	-> byte code offset #86
    //   Java source line #193	-> byte code offset #95
    //   Java source line #194	-> byte code offset #104
    //   Java source line #196	-> byte code offset #109
    //   Java source line #197	-> byte code offset #117
    //   Java source line #198	-> byte code offset #124
    //   Java source line #200	-> byte code offset #138
    //   Java source line #201	-> byte code offset #143
    //   Java source line #203	-> byte code offset #151
    //   Java source line #204	-> byte code offset #161
    //   Java source line #206	-> byte code offset #173
    //   Java source line #207	-> byte code offset #178
    //   Java source line #209	-> byte code offset #192
    //   Java source line #211	-> byte code offset #195
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	196	0	this	UserCache
    //   1	143	1	list	List
    //   3	72	2	bufferedreader	java.io.BufferedReader
    //   37	1	3	localFileNotFoundException	FileNotFoundException
    //   45	2	3	ex	net.minecraft.util.com.google.gson.JsonSyntaxException
    //   108	2	3	linkedlist	LinkedList
    //   72	7	4	localObject1	Object
    //   114	19	4	Ljava/lang/Object;	Object
    //   149	13	4	iterator	Iterator
    //   130	6	5	localObject2	Object
    //   171	14	5	usercacheentry	UserCacheEntry
    // Exception table:
    //   from	to	target	type
    //   4	30	37	java/io/FileNotFoundException
    //   4	30	45	net/minecraft/util/com/google/gson/JsonSyntaxException
    //   4	30	72	finally
    //   45	65	72	finally
    //   72	74	72	finally
    //   117	127	130	finally
    //   130	135	130	finally
  }
  
  public void c()
  {
    String s = this.b.toJson(a(SpigotConfig.userCacheCap));
    BufferedWriter bufferedwriter = null;
    try
    {
      bufferedwriter = Files.newWriter(this.g, Charsets.UTF_8);
      bufferedwriter.write(s);
      return;

    }
    catch (FileNotFoundException filenotfoundexception) {}catch (IOException localIOException) {}finally
    {

      IOUtils.closeQuietly(bufferedwriter);
    }
  }
  
  private List a(int i) {
    ArrayList arraylist = Lists.newArrayList();
    LinkedList linkedlist = this.e;
    
    ArrayList arraylist1;
    synchronized (this.e) {
      arraylist1 = Lists.newArrayList(Iterators.limit(this.e.iterator(), i));
    }
    ArrayList arraylist1;
    Iterator iterator = arraylist1.iterator();
    
    while (iterator.hasNext()) {
      GameProfile gameprofile = (GameProfile)iterator.next();
      UserCacheEntry usercacheentry = b(gameprofile.getId());
      
      if (usercacheentry != null) {
        arraylist.add(usercacheentry);
      }
    }
    
    return arraylist;
  }
}
