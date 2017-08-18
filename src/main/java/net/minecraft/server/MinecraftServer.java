package net.minecraft.server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.GameProfileRepository;
import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.util.com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.handler.codec.base64.Base64;
import net.minecraft.util.org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.WorldTimingsHandler;
import org.bukkit.craftbukkit.v1_7_R4.util.Waitable;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.WatchdogThread;

public abstract class MinecraftServer implements ICommandListener, Runnable, IMojangStatistics
{
  private static final Logger i = ;
  private static final File a = new File("usercache.json");
  private static MinecraftServer j;
  public Convertable convertable;
  private final MojangStatisticsGenerator l = new MojangStatisticsGenerator("server", this, ar());
  public File universe;
  private final List n = new ArrayList();
  private final ICommandHandler o;
  public final MethodProfiler methodProfiler = new MethodProfiler();
  private ServerConnection p;
  private final ServerPing q = new ServerPing();
  private final Random r = new Random();
  private String serverIp;
  private int t = -1;
  public WorldServer[] worldServer;
  private PlayerList u;
  private boolean isRunning = true;
  private boolean isStopped;
  private int ticks;
  protected final Proxy d;
  public String e;
  public int f;
  private boolean onlineMode;
  private boolean spawnAnimals;
  private boolean spawnNPCs;
  private boolean pvpMode;
  private boolean allowFlight;
  private String motd;
  private int E;
  private int F = 0;
  public final long[] g = new long[100];
  public long[][] h;
  private KeyPair G;
  private String H;
  private String I;
  private boolean demoMode;
  private boolean L;
  private boolean M;
  private String N = "";
  private boolean O;
  private long P;
  private String Q;
  private boolean R;
  private boolean S;
  private final YggdrasilAuthenticationService T;
  private final MinecraftSessionService U;
  private long V = 0L;
  
  private final GameProfileRepository W;
  
  private final UserCache X;
  public List<WorldServer> worlds = new ArrayList();
  public CraftServer server;
  public OptionSet options;
  public org.bukkit.command.ConsoleCommandSender console;
  public org.bukkit.command.RemoteConsoleCommandSender remoteConsole;
  public ConsoleReader reader;
  public static int currentTick = 0;
  public final Thread primaryThread;
  public Queue<Runnable> processQueue = new java.util.concurrent.ConcurrentLinkedQueue();
  public int autosavePeriod;
  private static final int TPS = 20;
  private static final long SEC_IN_NANO = 1000000000L;
  
  public MinecraftServer(OptionSet options, Proxy proxy) { net.minecraft.util.io.netty.util.ResourceLeakDetector.setEnabled(false);
    this.X = new UserCache(this, a);
    j = this;
    this.d = proxy;
    

    this.o = new CommandDispatcher();
    
    this.T = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
    this.U = this.T.createMinecraftSessionService();
    this.W = this.T.createProfileRepository();
    
    this.options = options;
    
    if (System.console() == null) {
      System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
      org.bukkit.craftbukkit.Main.useJline = false;
    }
    try
    {
      this.reader = new ConsoleReader(System.in, System.out);
      this.reader.setExpandEvents(false);
    }
    catch (Throwable e) {
      try {
        System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
        System.setProperty("user.language", "en");
        org.bukkit.craftbukkit.Main.useJline = false;
        this.reader = new ConsoleReader(System.in, System.out);
        this.reader.setExpandEvents(false);
      } catch (IOException ex) {
        i.warn((String)null, ex);
      }
    }
    Runtime.getRuntime().addShutdownHook(new org.bukkit.craftbukkit.v1_7_R4.util.ServerShutdownThread(this));
    
    this.primaryThread = new ThreadServerApplication(this, "Server thread");
  }
  
  public abstract PropertyManager getPropertyManager();
  
  protected abstract boolean init() throws java.net.UnknownHostException;
  
  protected void a(String s)
  {
    if (getConvertable().isConvertable(s)) {
      i.info("Converting map!");
      b("menu.convertingLevel");
      getConvertable().convert(s, new ConvertProgressUpdater(this));
    }
  }
  
  protected synchronized void b(String s) {
    this.Q = s;
  }
  
  protected void a(String s, String s1, long i, WorldType worldtype, String s2) {
    a(s);
    b("menu.loadingLevel");
    this.worldServer = new WorldServer[3];
    
















    int worldCount = 3;
    
    for (int j = 0; j < worldCount; j++)
    {
      int dimension = 0;
      
      if (j == 1) {
        if (getAllowNether()) {
          dimension = -1;

        }
        

      }
      else if (j == 2) {
        if (this.server.getAllowEnd()) {
          dimension = 1;
        }
        
      }
      else
      {
        String worldType = World.Environment.getEnvironment(dimension).toString().toLowerCase();
        String name = s + "_" + worldType;
        
        ChunkGenerator gen = this.server.getGenerator(name);
        WorldSettings worldsettings = new WorldSettings(i, getGamemode(), getGenerateStructures(), isHardcore(), worldtype);
        worldsettings.a(s2);
        WorldServer world;
        if (j == 0) {
          IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), s1, true);
          WorldServer world; WorldServer world; if (R()) {
            world = new DemoWorldServer(this, idatamanager, s1, dimension, this.methodProfiler);
          }
          else {
            world = new WorldServer(this, idatamanager, s1, dimension, worldsettings, this.methodProfiler, World.Environment.getEnvironment(dimension), gen);
          }
          this.server.scoreboardManager = new org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager(this, world.getScoreboard());
        } else {
          String dim = "DIM" + dimension;
          
          File newWorld = new File(new File(name), dim);
          File oldWorld = new File(new File(s), dim);
          
          if ((!newWorld.isDirectory()) && (oldWorld.isDirectory())) {
            i.info("---- Migration of old " + worldType + " folder required ----");
            i.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
            i.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
            i.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
            
            if (newWorld.exists()) {
              i.warn("A file or folder already exists at " + newWorld + "!");
              i.info("---- Migration of old " + worldType + " folder failed ----");
            } else if (newWorld.getParentFile().mkdirs()) {
              if (oldWorld.renameTo(newWorld)) {
                i.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);
                try
                {
                  com.google.common.io.Files.copy(new File(new File(s), "level.dat"), new File(new File(name), "level.dat"));
                } catch (IOException exception) {
                  i.warn("Unable to migrate world data.");
                }
                i.info("---- Migration of old " + worldType + " folder complete ----");
              } else {
                i.warn("Could not move folder " + oldWorld + " to " + newWorld + "!");
                i.info("---- Migration of old " + worldType + " folder failed ----");
              }
            } else {
              i.warn("Could not create path for " + newWorld + "!");
              i.info("---- Migration of old " + worldType + " folder failed ----");
            }
          }
          
          IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), name, true);
          
          world = new SecondaryWorldServer(this, idatamanager, name, dimension, worldsettings, (WorldServer)this.worlds.get(0), this.methodProfiler, World.Environment.getEnvironment(dimension), gen);
        }
        
        if (gen != null) {
          world.getWorld().getPopulators().addAll(gen.getDefaultPopulators(world.getWorld()));
        }
        
        this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldInitEvent(world.getWorld()));
        
        world.addIWorldAccess(new WorldManager(this, world));
        if (!N()) {
          world.getWorldData().setGameType(getGamemode());
        }
        
        this.worlds.add(world);
        this.u.setPlayerFileData((WorldServer[])this.worlds.toArray(new WorldServer[this.worlds.size()]));
      }
    }
    
    a(getDifficulty());
    g();
  }
  
  protected void g() {
    boolean flag = true;
    boolean flag1 = true;
    boolean flag2 = true;
    boolean flag3 = true;
    int i = 0;
    
    b("menu.generatingTerrain");
    byte b0 = 0;
    

    for (int m = 0; m < this.worlds.size(); m++) {
      WorldServer worldserver = (WorldServer)this.worlds.get(m);
      i.info("Preparing start region for level " + m + " (Seed: " + worldserver.getSeed() + ")");
      if (worldserver.getWorld().getKeepSpawnInMemory())
      {


        ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
        long j = ar();
        i = 0;
        
        for (int k = 65344; (k <= 192) && (isRunning()); k += 16) {
          for (int l = 65344; (l <= 192) && (isRunning()); l += 16) {
            long i1 = ar();
            
            if (i1 - j > 1000L) {
              a_("Preparing spawn area", i * 100 / 625);
              j = i1;
            }
            
            i++;
            worldserver.chunkProviderServer.getChunkAt(chunkcoordinates.x + k >> 4, chunkcoordinates.z + l >> 4);
          }
        }
      }
    }
    for (WorldServer world : this.worlds) {
      this.server.getPluginManager().callEvent(new WorldLoadEvent(world.getWorld()));
    }
    
    n();
  }
  
  public abstract boolean getGenerateStructures();
  
  public abstract EnumGamemode getGamemode();
  
  public abstract EnumDifficulty getDifficulty();
  
  public abstract boolean isHardcore();
  
  public abstract int l();
  
  public abstract boolean m();
  
  protected void a_(String s, int i) {
    this.e = s;
    this.f = i;
    
    i.info(s + ": " + i + "%");
  }
  
  protected void n() {
    this.e = null;
    this.f = 0;
    
    this.server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.POSTWORLD);
  }
  
  protected void saveChunks(boolean flag) throws ExceptionWorldConflict {
    if (!this.M)
    {

      int i = this.worlds.size();
      
      for (int j = 0; j < i; j++) {
        WorldServer worldserver = (WorldServer)this.worlds.get(j);
        
        if (worldserver != null) {
          if (!flag) {
            i.info("Saving chunks for level '" + worldserver.getWorldData().getName() + "'/" + worldserver.worldProvider.getName());
          }
          
          worldserver.save(true, (IProgressUpdate)null);
          worldserver.saveLevel();
          
          WorldSaveEvent event = new WorldSaveEvent(worldserver.getWorld());
          this.server.getPluginManager().callEvent(event);
        }
      }
    }
  }
  
  public void stop() throws ExceptionWorldConflict
  {
    if (!this.M) {
      i.info("Stopping server");
      this.server.getPluginManager().callEvent(new org.github.paperspigot.event.server.ServerShutdownEvent(this.server));
      
      if (this.server != null) {
        this.server.disablePlugins();
      }
      

      if (ai() != null) {
        ai().b();
      }
      
      if (this.u != null) {
        i.info("Saving players");
        this.u.savePlayers();
        this.u.u();
      }
      
      if (this.worldServer != null) {
        i.info("Saving worlds");
        saveChunks(false);
      }
      








      if (this.l.d()) {
        this.l.e();
      }
      
      if (org.spigotmc.SpigotConfig.saveUserCacheOnStopOnly)
      {
        i.info("Saving usercache.json");
        this.X.c();
      }
    }
  }
  
  public String getServerIp()
  {
    return this.serverIp;
  }
  
  public void c(String s) {
    this.serverIp = s;
  }
  
  public boolean isRunning() {
    return this.isRunning;
  }
  
  public void safeShutdown() {
    this.isRunning = false;
  }
  

  private static final long TICK_TIME = 50000000L;
  
  private static final long MAX_CATCHUP_BUFFER = 60000000000L;
  
  private static final int SAMPLE_INTERVAL = 20;
  public final RollingAverage tps1 = new RollingAverage(60);
  public final RollingAverage tps5 = new RollingAverage(300);
  public final RollingAverage tps15 = new RollingAverage(900);
  public double[] recentTps = new double[3];
  
  public static class RollingAverage {
    private final int size;
    private long time;
    private double total;
    private int index = 0;
    private final double[] samples;
    private final long[] times;
    
    RollingAverage(int size) {
      this.size = size;
      this.time = (size * 1000000000L);
      this.total = (20000000000L * size);
      this.samples = new double[size];
      this.times = new long[size];
      for (int i = 0; i < size; i++) {
        this.samples[i] = 20.0D;
        this.times[i] = 1000000000L;
      }
    }
    
    public void add(double x, long t) {
      this.time -= this.times[this.index];
      this.total -= this.samples[this.index] * this.times[this.index];
      this.samples[this.index] = x;
      this.times[this.index] = t;
      this.time += t;
      this.total += x * t;
      if (++this.index == this.size) {
        this.index = 0;
      }
    }
    
    public double getAverage() {
      return this.total / this.time;
    }
  }
  
  public void run()
  {
    try {
      if (init()) {
        long i = ar();
        long j = 0L;
        
        this.q.setMOTD(new ChatComponentText(this.motd));
        this.q.setServerInfo(new ServerPingServerData("1.7.10", 5));
        a(this.q);
        


        Arrays.fill(this.recentTps, 20.0D);
        
        long start = System.nanoTime();long lastTick = start - 50000000L;long catchupTime = 0L;long tickSection = start;
        
        while (this.isRunning) {
          long curTime = System.nanoTime();
          
          long wait = 50000000L - (curTime - lastTick);
          if (wait > 0L) {
            if (catchupTime < 2000000.0D) {
              wait += Math.abs(catchupTime);
            }
            if (wait < catchupTime) {
              catchupTime -= wait;
              wait = 0L;
            } else if (catchupTime > 2000000.0D) {
              wait -= catchupTime;
              catchupTime -= catchupTime;
            }
          }
          if (wait > 0L) {
            Thread.sleep(wait / 1000000L);
            wait = 50000000L - (curTime - lastTick);
          }
          
          catchupTime = Math.min(60000000000L, catchupTime - wait);
          

          if (++currentTick % 20 == 0)
          {

            long diff = curTime - tickSection;
            double currentTps = 1.0E9D / diff * 20.0D;
            this.tps1.add(currentTps, diff);
            this.tps5.add(currentTps, diff);
            this.tps15.add(currentTps, diff);
            
            this.recentTps[0] = this.tps1.getAverage();
            this.recentTps[1] = this.tps5.getAverage();
            this.recentTps[2] = this.tps15.getAverage();
            tickSection = curTime;
          }
          
          lastTick = curTime;
          
          u();
          this.O = true;
        }
      }
      else {
        a((CrashReport)null);
      }
    } catch (Throwable throwable) {
      i.error("Encountered an unexpected exception", throwable);
      
      if (throwable.getCause() != null)
      {
        i.error("\tCause of unexpected exception was", throwable.getCause());
      }
      
      CrashReport crashreport = null;
      
      if ((throwable instanceof ReportedException)) {
        crashreport = b(((ReportedException)throwable).a());
      } else {
        crashreport = b(new CrashReport("Exception in server tick loop", throwable));
      }
      
      File file1 = new File(new File(s(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new java.util.Date()) + "-server.txt");
      
      if (crashreport.a(file1)) {
        i.error("This crash report has been saved to: " + file1.getAbsolutePath());
      } else {
        i.error("We were unable to save this crash report to disk.");
      }
      
      a(crashreport);
    } finally {
      try {
        WatchdogThread.doStop();
        stop();
        this.isStopped = true;
      } catch (Throwable throwable1) {
        i.error("Exception stopping the server", throwable1);
      }
      finally {
        try {
          this.reader.getTerminal().restore();
        }
        catch (Exception localException8) {}
        
        t();
      }
    }
  }
  
  private void a(ServerPing serverping) {
    File file1 = d("server-icon.png");
    
    if (file1.isFile()) {
      ByteBuf bytebuf = net.minecraft.util.io.netty.buffer.Unpooled.buffer();
      try
      {
        BufferedImage bufferedimage = ImageIO.read(file1);
        
        Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
        Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
        ImageIO.write(bufferedimage, "PNG", new net.minecraft.util.io.netty.buffer.ByteBufOutputStream(bytebuf));
        ByteBuf bytebuf1 = Base64.encode(bytebuf);
        
        serverping.setFavicon("data:image/png;base64," + bytebuf1.toString(net.minecraft.util.com.google.common.base.Charsets.UTF_8));
      } catch (Exception exception) {
        i.error("Couldn't load server icon", exception);
      } finally {
        bytebuf.release();
      }
    }
  }
  
  protected File s() {
    return new File(".");
  }
  
  protected void a(CrashReport crashreport) {}
  
  protected void t() {}
  
  protected void u() throws ExceptionWorldConflict {
    SpigotTimings.serverTickTimer.startTiming();
    long i = System.nanoTime();
    
    this.ticks += 1;
    if (this.R) {
      this.R = false;
      this.methodProfiler.a = true;
      this.methodProfiler.a();
    }
    
    this.methodProfiler.a("root");
    v();
    GameProfile[] agameprofile; if (i - this.V >= 5000000000L) {
      this.V = i;
      this.q.setPlayerSample(new ServerPingPlayerSample(D(), C()));
      agameprofile = new GameProfile[Math.min(C(), 12)];
      int j = MathHelper.nextInt(this.r, 0, C() - agameprofile.length);
      
      for (int k = 0; k < agameprofile.length; k++) {
        agameprofile[k] = ((EntityPlayer)this.u.players.get(j + k)).getProfile();
      }
      
      java.util.Collections.shuffle(Arrays.asList(agameprofile));
      this.q.b().a(agameprofile);
    }
    
    if ((this.autosavePeriod > 0) && (this.ticks % this.autosavePeriod == 0)) {
      SpigotTimings.worldSaveTimer.startTiming();
      this.methodProfiler.a("save");
      this.u.savePlayers();
      



      this.server.playerCommandState = true;
      for (World world : this.worlds) {
        world.getWorld().save(true);
      }
      this.server.playerCommandState = false;
      

      this.methodProfiler.b();
      SpigotTimings.worldSaveTimer.stopTiming();
    }
    
    this.methodProfiler.a("tallying");
    this.g[(this.ticks % 100)] = (System.nanoTime() - i);
    this.methodProfiler.b();
    this.methodProfiler.a("snooper");
    if ((getSnooperEnabled()) && (!this.l.d()) && (this.ticks > 100)) {
      this.l.a();
    }
    
    if ((getSnooperEnabled()) && (this.ticks % 6000 == 0)) {
      this.l.b();
    }
    
    this.methodProfiler.b();
    this.methodProfiler.b();
    WatchdogThread.tick();
    SpigotTimings.serverTickTimer.stopTiming();
    CustomTimingsHandler.tick();
  }
  
  public void v() {
    this.methodProfiler.a("levels");
    
    SpigotTimings.schedulerTimer.startTiming();
    
    this.server.getScheduler().mainThreadHeartbeat(this.ticks);
    SpigotTimings.schedulerTimer.stopTiming();
    

    SpigotTimings.processQueueTimer.startTiming();
    while (!this.processQueue.isEmpty()) {
      ((Runnable)this.processQueue.remove()).run();
    }
    SpigotTimings.processQueueTimer.stopTiming();
    
    SpigotTimings.chunkIOTickTimer.startTiming();
    org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor.tick();
    SpigotTimings.chunkIOTickTimer.stopTiming();
    
    SpigotTimings.timeUpdateTimer.startTiming();
    
    if (this.ticks % 20 == 0) {
      for (int i = 0; i < getPlayerList().players.size(); i++) {
        EntityPlayer entityplayer = (EntityPlayer)getPlayerList().players.get(i);
        entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime(), entityplayer.world.getGameRules().getBoolean("doDaylightCycle")));
      }
    }
    SpigotTimings.timeUpdateTimer.stopTiming();
    


    for (int i = 0; i < this.worlds.size(); i++) {
      long j = System.nanoTime();
      

      WorldServer worldserver = (WorldServer)this.worlds.get(i);
      
      this.methodProfiler.a(worldserver.getWorldData().getName());
      this.methodProfiler.a("pools");
      this.methodProfiler.b();
      







      this.methodProfiler.a("tick");
      

      try
      {
        worldserver.timings.doTick.startTiming();
        worldserver.doTick();
        worldserver.timings.doTick.stopTiming();
      }
      catch (Throwable throwable) {
        try {
          crashreport = CrashReport.a(throwable, "Exception ticking world");
        } catch (Throwable t) { CrashReport crashreport;
          throw new RuntimeException("Error generating crash report", t);
        }
        CrashReport crashreport;
        worldserver.a(crashreport);
        throw new ReportedException(crashreport);
      }
      try
      {
        worldserver.timings.tickEntities.startTiming();
        worldserver.tickEntities();
        worldserver.timings.tickEntities.stopTiming();
      }
      catch (Throwable throwable1) {
        try {
          crashreport = CrashReport.a(throwable1, "Exception ticking world entities");
        } catch (Throwable t) { CrashReport crashreport;
          throw new RuntimeException("Error generating crash report", t);
        }
        CrashReport crashreport;
        worldserver.a(crashreport);
        throw new ReportedException(crashreport);
      }
      
      this.methodProfiler.b();
      this.methodProfiler.a("tracker");
      worldserver.timings.tracker.startTiming();
      worldserver.getTracker().updatePlayers();
      worldserver.timings.tracker.stopTiming();
      this.methodProfiler.b();
      this.methodProfiler.b();
      worldserver.explosionDensityCache.clear();
    }
    



    this.methodProfiler.c("connection");
    SpigotTimings.connectionTimer.startTiming();
    ai().c();
    SpigotTimings.connectionTimer.stopTiming();
    this.methodProfiler.c("players");
    SpigotTimings.playerListTimer.startTiming();
    this.u.tick();
    SpigotTimings.playerListTimer.stopTiming();
    this.methodProfiler.c("tickables");
    
    SpigotTimings.tickablesTimer.startTiming();
    for (i = 0; i < this.n.size(); i++) {
      ((IUpdatePlayerListBox)this.n.get(i)).a();
    }
    SpigotTimings.tickablesTimer.stopTiming();
    
    this.methodProfiler.b();
  }
  
  public boolean getAllowNether() {
    return true;
  }
  
  public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
    this.n.add(iupdateplayerlistbox);
  }
  
  public static void main(OptionSet options) {
    DispenserRegistry.b();
    org.spigotmc.ProtocolInjector.inject();
    








































































    try
    {
      DedicatedServer dedicatedserver = new DedicatedServer(options);
      
      if (options.has("port")) {
        int port = ((Integer)options.valueOf("port")).intValue();
        if (port > 0) {
          dedicatedserver.setPort(port);
        }
      }
      
      if (options.has("universe")) {
        dedicatedserver.universe = ((File)options.valueOf("universe"));
      }
      
      if (options.has("world")) {
        dedicatedserver.k((String)options.valueOf("world"));
      }
      
      dedicatedserver.primaryThread.start();
    }
    catch (Exception exception)
    {
      i.fatal("Failed to start the minecraft server", exception);
    }
  }
  

  public void x() {}
  
  public File d(String s)
  {
    return new File(s(), s);
  }
  
  public void info(String s) {
    i.info(s);
  }
  
  public void warning(String s) {
    i.warn(s);
  }
  
  public WorldServer getWorldServer(int i)
  {
    for (WorldServer world : this.worlds) {
      if (world.dimension == i) {
        return world;
      }
    }
    
    return (WorldServer)this.worlds.get(0);
  }
  
  public String y()
  {
    return this.serverIp;
  }
  
  public int z() {
    return this.t;
  }
  
  public String A() {
    return this.motd;
  }
  
  public String getVersion() {
    return "1.7.10";
  }
  
  public int C() {
    return this.u.getPlayerCount();
  }
  
  public int D() {
    return this.u.getMaxPlayers();
  }
  
  public String[] getPlayers() {
    return this.u.f();
  }
  
  public GameProfile[] F() {
    return this.u.g();
  }
  
  public String getPlugins()
  {
    StringBuilder result = new StringBuilder();
    Plugin[] plugins = this.server.getPluginManager().getPlugins();
    
    result.append(this.server.getName());
    result.append(" on Bukkit ");
    result.append(this.server.getBukkitVersion());
    
    if ((plugins.length > 0) && (this.server.getQueryPlugins())) {
      result.append(": ");
      
      for (int i = 0; i < plugins.length; i++) {
        if (i > 0) {
          result.append("; ");
        }
        
        result.append(plugins[i].getDescription().getName());
        result.append(" ");
        result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
      }
    }
    
    return result.toString();
  }
  

  public String g(final String s)
  {
    Waitable<String> waitable = new Waitable()
    {
      protected String evaluate() {
        RemoteControlCommandListener.instance.e();
        
        RemoteServerCommandEvent event = new RemoteServerCommandEvent(MinecraftServer.this.remoteConsole, s);
        MinecraftServer.this.server.getPluginManager().callEvent(event);
        
        ServerCommand servercommand = new ServerCommand(event.getCommand(), RemoteControlCommandListener.instance);
        MinecraftServer.this.server.dispatchServerCommand(MinecraftServer.this.remoteConsole, servercommand);
        
        return RemoteControlCommandListener.instance.f();
      } };
    this.processQueue.add(waitable);
    try {
      return (String)waitable.get();
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception processing rcon command " + s, e.getCause());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted processing rcon command " + s, e);
    }
  }
  
  public boolean isDebugging()
  {
    return getPropertyManager().getBoolean("debug", false);
  }
  
  public void h(String s) {
    i.error(s);
  }
  
  public void i(String s) {
    if (isDebugging()) {
      i.info(s);
    }
  }
  
  public String getServerModName() {
    return "PaperSpigot";
  }
  
  public CrashReport b(CrashReport crashreport) {
    crashreport.g().a("Profiler Position", new CrashReportProfilerPosition(this));
    if ((this.worlds != null) && (this.worlds.size() > 0) && (this.worlds.get(0) != null)) {
      crashreport.g().a("Vec3 Pool Size", new CrashReportVec3DPoolSize(this));
    }
    
    if (this.u != null) {
      crashreport.g().a("Player Count", new CrashReportPlayerCount(this));
    }
    
    return crashreport;
  }
  








































  public List a(ICommandListener icommandlistener, String s)
  {
    return this.server.tabComplete(icommandlistener, s);
  }
  
  public static MinecraftServer getServer()
  {
    return j;
  }
  
  public String getName() {
    return "Server";
  }
  
  public void sendMessage(IChatBaseComponent ichatbasecomponent) {
    i.info(ichatbasecomponent.c());
  }
  
  public boolean a(int i, String s) {
    return true;
  }
  
  public ICommandHandler getCommandHandler() {
    return this.o;
  }
  
  public KeyPair K() {
    return this.G;
  }
  
  public int L() {
    return this.t;
  }
  
  public void setPort(int i) {
    this.t = i;
  }
  
  public String M() {
    return this.H;
  }
  
  public void j(String s) {
    this.H = s;
  }
  
  public boolean N() {
    return this.H != null;
  }
  
  public String O() {
    return this.I;
  }
  
  public void k(String s) {
    this.I = s;
  }
  
  public void a(KeyPair keypair) {
    this.G = keypair;
  }
  
  public void a(EnumDifficulty enumdifficulty)
  {
    for (int j = 0; j < this.worlds.size(); j++) {
      WorldServer worldserver = (WorldServer)this.worlds.get(j);
      

      if (worldserver != null) {
        if (worldserver.getWorldData().isHardcore()) {
          worldserver.difficulty = EnumDifficulty.HARD;
          worldserver.setSpawnFlags(true, true);
        } else if (N()) {
          worldserver.difficulty = enumdifficulty;
          worldserver.setSpawnFlags(worldserver.difficulty != EnumDifficulty.PEACEFUL, true);
        } else {
          worldserver.difficulty = enumdifficulty;
          worldserver.setSpawnFlags(getSpawnMonsters(), this.spawnAnimals);
        }
      }
    }
  }
  
  protected boolean getSpawnMonsters() {
    return true;
  }
  
  public boolean R() {
    return this.demoMode;
  }
  
  public void b(boolean flag) {
    this.demoMode = flag;
  }
  
  public void c(boolean flag) {
    this.L = flag;
  }
  
  public Convertable getConvertable() {
    return this.convertable;
  }
  
  public void U() {
    this.M = true;
    getConvertable().d();
    

    for (int i = 0; i < this.worlds.size(); i++) {
      WorldServer worldserver = (WorldServer)this.worlds.get(i);
      

      if (worldserver != null) {
        worldserver.saveLevel();
      }
    }
    
    getConvertable().e(((WorldServer)this.worlds.get(0)).getDataManager().g());
    safeShutdown();
  }
  
  public String getResourcePack() {
    return this.N;
  }
  
  public void setTexturePack(String s) {
    this.N = s;
  }
  
  public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
    mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(false));
    mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(0));
    mojangstatisticsgenerator.a("players_current", Integer.valueOf(C()));
    mojangstatisticsgenerator.a("players_max", Integer.valueOf(D()));
    mojangstatisticsgenerator.a("players_seen", Integer.valueOf(this.u.getSeenPlayers().length));
    mojangstatisticsgenerator.a("uses_auth", Boolean.valueOf(this.onlineMode));
    mojangstatisticsgenerator.a("gui_state", ak() ? "enabled" : "disabled");
    mojangstatisticsgenerator.a("run_time", Long.valueOf((ar() - mojangstatisticsgenerator.g()) / 60L * 1000L));
    mojangstatisticsgenerator.a("avg_tick_ms", Integer.valueOf((int)(MathHelper.a(this.g) * 1.0E-6D)));
    int i = 0;
    

    for (int j = 0; j < this.worlds.size(); j++) {
      WorldServer worldserver = (WorldServer)this.worlds.get(j);
      if (this.worldServer != null)
      {
        WorldData worlddata = worldserver.getWorldData();
        
        mojangstatisticsgenerator.a("world[" + i + "][dimension]", Integer.valueOf(worldserver.worldProvider.dimension));
        mojangstatisticsgenerator.a("world[" + i + "][mode]", worlddata.getGameType());
        mojangstatisticsgenerator.a("world[" + i + "][difficulty]", worldserver.difficulty);
        mojangstatisticsgenerator.a("world[" + i + "][hardcore]", Boolean.valueOf(worlddata.isHardcore()));
        mojangstatisticsgenerator.a("world[" + i + "][generator_name]", worlddata.getType().name());
        mojangstatisticsgenerator.a("world[" + i + "][generator_version]", Integer.valueOf(worlddata.getType().getVersion()));
        mojangstatisticsgenerator.a("world[" + i + "][height]", Integer.valueOf(this.E));
        mojangstatisticsgenerator.a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.L().getLoadedChunks()));
        i++;
      }
    }
    
    mojangstatisticsgenerator.a("worlds", Integer.valueOf(i));
  }
  
  public void b(MojangStatisticsGenerator mojangstatisticsgenerator) {
    mojangstatisticsgenerator.b("singleplayer", Boolean.valueOf(N()));
    mojangstatisticsgenerator.b("server_brand", getServerModName());
    mojangstatisticsgenerator.b("gui_supported", java.awt.GraphicsEnvironment.isHeadless() ? "headless" : "supported");
    mojangstatisticsgenerator.b("dedicated", Boolean.valueOf(X()));
  }
  
  public boolean getSnooperEnabled() {
    return true;
  }
  
  public abstract boolean X();
  
  public boolean getOnlineMode() {
    return this.server.getOnlineMode();
  }
  
  public void setOnlineMode(boolean flag) {
    this.onlineMode = flag;
  }
  
  public boolean getSpawnAnimals() {
    return this.spawnAnimals;
  }
  
  public void setSpawnAnimals(boolean flag) {
    this.spawnAnimals = flag;
  }
  
  public boolean getSpawnNPCs() {
    return this.spawnNPCs;
  }
  
  public void setSpawnNPCs(boolean flag) {
    this.spawnNPCs = flag;
  }
  
  public boolean getPvP() {
    return this.pvpMode;
  }
  
  public void setPvP(boolean flag) {
    this.pvpMode = flag;
  }
  
  public boolean getAllowFlight() {
    return this.allowFlight;
  }
  
  public void setAllowFlight(boolean flag) {
    this.allowFlight = flag;
  }
  
  public abstract boolean getEnableCommandBlock();
  
  public String getMotd() {
    return this.motd;
  }
  
  public void setMotd(String s) {
    this.motd = s;
  }
  
  public int getMaxBuildHeight() {
    return this.E;
  }
  
  public void c(int i) {
    this.E = i;
  }
  
  public boolean isStopped() {
    return this.isStopped;
  }
  
  public PlayerList getPlayerList() {
    return this.u;
  }
  
  public void a(PlayerList playerlist) {
    this.u = playerlist;
  }
  
  public void a(EnumGamemode enumgamemode)
  {
    for (int i = 0; i < this.worlds.size(); i++) {
      ((WorldServer)getServer().worlds.get(i)).getWorldData().setGameType(enumgamemode);
    }
  }
  


  public ServerConnection getServerConnection()
  {
    return this.p;
  }
  
  public ServerConnection ai() {
    return this.p == null ? (this.p = new ServerConnection(this)) : this.p;
  }
  
  public boolean ak() {
    return false;
  }
  
  public abstract String a(EnumGamemode paramEnumGamemode, boolean paramBoolean);
  
  public int al() {
    return this.ticks;
  }
  
  public void am() {
    this.R = true;
  }
  
  public ChunkCoordinates getChunkCoordinates() {
    return new ChunkCoordinates(0, 0, 0);
  }
  
  public World getWorld() {
    return (World)this.worlds.get(0);
  }
  
  public int getSpawnProtection() {
    return 16;
  }
  
  public boolean a(World world, int i, int j, int k, EntityHuman entityhuman) {
    return false;
  }
  
  public void setForceGamemode(boolean flag) {
    this.S = flag;
  }
  
  public boolean getForceGamemode() {
    return this.S;
  }
  
  public Proxy aq() {
    return this.d;
  }
  
  public static long ar() {
    return System.currentTimeMillis();
  }
  
  public int getIdleTimeout() {
    return this.F;
  }
  
  public void setIdleTimeout(int i) {
    this.F = i;
  }
  
  public IChatBaseComponent getScoreboardDisplayName() {
    return new ChatComponentText(getName());
  }
  
  public boolean at() {
    return true;
  }
  
  public MinecraftSessionService av() {
    return this.U;
  }
  
  public GameProfileRepository getGameProfileRepository() {
    return this.W;
  }
  
  public UserCache getUserCache() {
    return this.X;
  }
  
  public ServerPing ay() {
    return this.q;
  }
  
  public void az() {
    this.V = 0L;
  }
  
  public static Logger getLogger() {
    return i;
  }
  
  public static PlayerList a(MinecraftServer minecraftserver) {
    return minecraftserver.u;
  }
}
