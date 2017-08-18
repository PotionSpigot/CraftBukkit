package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.LoggerOutputStream;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
import org.bukkit.craftbukkit.v1_7_R4.util.TerminalConsoleWriterThread;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.PluginManager;
import org.github.paperspigot.PaperSpigotConfig;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.SpigotConfig;

public class DedicatedServer extends MinecraftServer implements IMinecraftServer
{
  private static final org.apache.logging.log4j.Logger i = ;
  private final List j = java.util.Collections.synchronizedList(new ArrayList());
  private RemoteStatusListener k;
  private RemoteControlListener l;
  public PropertyManager propertyManager;
  private EULA n;
  private boolean generateStructures;
  private EnumGamemode p;
  private boolean q;
  
  public DedicatedServer(OptionSet options)
  {
    super(options, java.net.Proxy.NO_PROXY);
    

    new ThreadSleepForever(this, "Server Infinisleeper");
  }
  
  protected boolean init() throws java.net.UnknownHostException {
    ThreadCommandReader threadcommandreader = new ThreadCommandReader(this, "Server console handler");
    
    threadcommandreader.setDaemon(true);
    threadcommandreader.start();
    

    java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
    global.setUseParentHandlers(false);
    for (java.util.logging.Handler handler : global.getHandlers()) {
      global.removeHandler(handler);
    }
    global.addHandler(new org.bukkit.craftbukkit.v1_7_R4.util.ForwardLogHandler());
    
    org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
    for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
      if ((appender instanceof ConsoleAppender)) {
        logger.removeAppender(appender);
      }
    }
    
    new Thread(new TerminalConsoleWriterThread(System.out, this.reader)).start();
    
    System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
    System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
    

    i.info("Starting minecraft server version 1.7.10");
    if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
      i.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
    }
    
    i.info("Loading properties");
    this.propertyManager = new PropertyManager(this.options);
    
    File EULALock = new File(".eula-lock");
    
    boolean eulaAgreed = Boolean.getBoolean("com.mojang.eula.agree");
    if (eulaAgreed)
    {
      System.err.println("You have used the Spigot command line EULA agreement flag.");
      System.err.println("By using this setting you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula).");
      System.err.println("If you do not agree to the above EULA please stop your server and remove this flag immediately.");
    }
    else {
      if (!EULALock.exists()) {
        System.err.println("WARNING: By using this server you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula)");
        System.err.println("If you do not agree to the above EULA please stop this server and remove it from your system immediately.");
        System.err.println("The server will start in 10 seconds, you will only see this message and have to wait this one time.");
        try {
          EULALock.createNewFile();
        } catch (IOException e1) {
          System.err.println("Unable to create EULA lock file");
          e1.printStackTrace();
        }
        try {
          Thread.sleep(TimeUnit.SECONDS.toMillis(10L));
        }
        catch (InterruptedException localInterruptedException) {}
      }
      
      if (N()) {
        c("127.0.0.1");
      } else {
        setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
        c(this.propertyManager.getString("server-ip", ""));
      }
      
      setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
      setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
      setPvP(this.propertyManager.getBoolean("pvp", true));
      setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
      setTexturePack(this.propertyManager.getString("resource-pack", ""));
      setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
      setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
      setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
      if (this.propertyManager.getInt("difficulty", 1) < 0) {
        this.propertyManager.setProperty("difficulty", Integer.valueOf(0));
      } else if (this.propertyManager.getInt("difficulty", 1) > 3) {
        this.propertyManager.setProperty("difficulty", Integer.valueOf(3));
      }
      
      this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
      int gamemode = this.propertyManager.getInt("gamemode", EnumGamemode.SURVIVAL.getId());
      
      this.p = WorldSettings.a(gamemode);
      i.info("Default game type: " + this.p);
      InetAddress inetaddress = null;
      
      if (getServerIp().length() > 0) {
        inetaddress = InetAddress.getByName(getServerIp());
      }
      
      if (L() < 0) {
        setPort(this.propertyManager.getInt("server-port", 25565));
      }
      
      a(new DedicatedPlayerList(this));
      SpigotConfig.init();
      SpigotConfig.registerCommands();
      

      PaperSpigotConfig.init();
      PaperSpigotConfig.registerCommands();
      

      i.info("Generating keypair");
      a(MinecraftEncryption.b());
      i.info("Starting Minecraft server on " + (getServerIp().length() == 0 ? "*" : getServerIp()) + ":" + L());
      
      if (!SpigotConfig.lateBind) {
        try {
          ai().a(inetaddress, L());
        } catch (Throwable ioexception) {
          i.warn("**** FAILED TO BIND TO PORT!");
          i.warn("The exception was: {}", new Object[] { ioexception.toString() });
          i.warn("Perhaps a server is already running on that port?");
          return false;
        }
      }
      


      this.server.loadPlugins();
      this.server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.STARTUP);
      

      if (!getOnlineMode()) {
        i.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
        i.warn("The server will make no attempt to authenticate usernames. Beware.");
        i.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
        i.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }
      
      if (aE()) {
        getUserCache().c();
      }
      
      if (!NameReferencingFileConverter.a(this.propertyManager)) {
        return false;
      }
      
      this.convertable = new WorldLoaderServer(this.server.getWorldContainer());
      long j = System.nanoTime();
      
      if (O() == null) {
        k(this.propertyManager.getString("level-name", "world"));
      }
      
      String s = this.propertyManager.getString("level-seed", "");
      String s1 = this.propertyManager.getString("level-type", "DEFAULT");
      String s2 = this.propertyManager.getString("generator-settings", "");
      long k = new Random().nextLong();
      
      if (s.length() > 0) {
        try {
          long l = Long.parseLong(s);
          
          if (l != 0L) {
            k = l;
          }
        } catch (NumberFormatException numberformatexception) {
          k = s.hashCode();
        }
      }
      
      WorldType worldtype = WorldType.getType(s1);
      
      if (worldtype == null) {
        worldtype = WorldType.NORMAL;
      }
      
      at();
      getEnableCommandBlock();
      l();
      getSnooperEnabled();
      c(this.propertyManager.getInt("max-build-height", 256));
      c((getMaxBuildHeight() + 8) / 16 * 16);
      c(MathHelper.a(getMaxBuildHeight(), 64, 256));
      this.propertyManager.setProperty("max-build-height", Integer.valueOf(getMaxBuildHeight()));
      i.info("Preparing level \"" + O() + "\"");
      a(O(), O(), k, worldtype, s2);
      long i1 = System.nanoTime() - j;
      String s3 = String.format("%.3fs", new Object[] { Double.valueOf(i1 / 1.0E9D) });
      
      i.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
      if (this.propertyManager.getBoolean("enable-query", false)) {
        i.info("Starting GS4 status listener");
        this.k = new RemoteStatusListener(this);
        this.k.a();
      }
      
      if (this.propertyManager.getBoolean("enable-rcon", false)) {
        i.info("Starting remote control listener");
        this.l = new RemoteControlListener(this);
        this.l.a();
        this.remoteConsole = new org.bukkit.craftbukkit.v1_7_R4.command.CraftRemoteConsoleCommandSender();
      }
      

      if (this.server.getBukkitSpawnRadius() > -1) {
        i.info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
        this.propertyManager.properties.remove("spawn-protection");
        this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
        this.server.removeBukkitSpawnRadius();
        this.propertyManager.savePropertiesFile();
      }
      

      if (SpigotConfig.lateBind) {
        try {
          ai().a(inetaddress, L());
        } catch (Throwable ioexception) {
          i.warn("**** FAILED TO BIND TO PORT!");
          i.warn("The exception was: {}", new Object[] { ioexception.toString() });
          i.warn("Perhaps a server is already running on that port?");
          return false;
        }
      }
      return true;
    }
    
    return true;
  }
  
  public PropertyManager getPropertyManager()
  {
    return this.propertyManager;
  }
  
  public boolean getGenerateStructures()
  {
    return this.generateStructures;
  }
  
  public EnumGamemode getGamemode() {
    return this.p;
  }
  
  public EnumDifficulty getDifficulty() {
    return EnumDifficulty.getById(this.propertyManager.getInt("difficulty", 1));
  }
  
  public boolean isHardcore() {
    return this.propertyManager.getBoolean("hardcore", false);
  }
  
  protected void a(CrashReport crashreport) {}
  
  public CrashReport b(CrashReport crashreport) {
    crashreport = super.b(crashreport);
    crashreport.g().a("Is Modded", new CrashReportModded(this));
    crashreport.g().a("Type", new CrashReportType(this));
    return crashreport;
  }
  
  protected void t() {
    System.exit(0);
  }
  
  public void v() {
    super.v();
    aB();
  }
  
  public boolean getAllowNether() {
    return this.propertyManager.getBoolean("allow-nether", true);
  }
  
  public boolean getSpawnMonsters() {
    return this.propertyManager.getBoolean("spawn-monsters", true);
  }
  
  public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
    mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(aC().getHasWhitelist()));
    mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(aC().getWhitelisted().length));
    super.a(mojangstatisticsgenerator);
  }
  
  public boolean getSnooperEnabled() {
    return this.propertyManager.getBoolean("snooper-enabled", true);
  }
  
  public void issueCommand(String s, ICommandListener icommandlistener) {
    this.j.add(new ServerCommand(s, icommandlistener));
  }
  
  public void aB() {
    SpigotTimings.serverCommandTimer.startTiming();
    while (!this.j.isEmpty()) {
      ServerCommand servercommand = (ServerCommand)this.j.remove(0);
      

      ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.command);
      this.server.getPluginManager().callEvent(event);
      servercommand = new ServerCommand(event.getCommand(), servercommand.source);
      

      this.server.dispatchServerCommand(this.console, servercommand);
    }
    
    SpigotTimings.serverCommandTimer.stopTiming();
  }
  
  public boolean X() {
    return true;
  }
  
  public DedicatedPlayerList aC() {
    return (DedicatedPlayerList)super.getPlayerList();
  }
  
  public int a(String s, int i) {
    return this.propertyManager.getInt(s, i);
  }
  
  public String a(String s, String s1) {
    return this.propertyManager.getString(s, s1);
  }
  
  public boolean a(String s, boolean flag) {
    return this.propertyManager.getBoolean(s, flag);
  }
  
  public void a(String s, Object object) {
    this.propertyManager.setProperty(s, object);
  }
  
  public void a() {
    this.propertyManager.savePropertiesFile();
  }
  
  public String b() {
    File file1 = this.propertyManager.c();
    
    return file1 != null ? file1.getAbsolutePath() : "No settings file";
  }
  
  public void aD() {
    ServerGUI.a(this);
    this.q = true;
  }
  
  public boolean ak() {
    return this.q;
  }
  
  public String a(EnumGamemode enumgamemode, boolean flag) {
    return "";
  }
  
  public boolean getEnableCommandBlock() {
    return this.propertyManager.getBoolean("enable-command-block", false);
  }
  
  public int getSpawnProtection() {
    return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
  }
  
  public boolean a(World world, int i, int j, int k, EntityHuman entityhuman) {
    if (world.worldProvider.dimension != 0)
      return false;
    if (aC().getOPs().isEmpty())
      return false;
    if (aC().isOp(entityhuman.getProfile()))
      return false;
    if (getSpawnProtection() <= 0) {
      return false;
    }
    ChunkCoordinates chunkcoordinates = world.getSpawn();
    int l = MathHelper.a(i - chunkcoordinates.x);
    int i1 = MathHelper.a(k - chunkcoordinates.z);
    int j1 = Math.max(l, i1);
    
    return j1 <= getSpawnProtection();
  }
  
  public int l()
  {
    return this.propertyManager.getInt("op-permission-level", 4);
  }
  
  public void setIdleTimeout(int i) {
    super.setIdleTimeout(i);
    this.propertyManager.setProperty("player-idle-timeout", Integer.valueOf(i));
    a();
  }
  
  public boolean m() {
    return this.propertyManager.getBoolean("broadcast-rcon-to-ops", true);
  }
  
  public boolean at() {
    return this.propertyManager.getBoolean("announce-player-achievements", true);
  }
  
  protected boolean aE() {
    this.server.getLogger().info("**** Beginning UUID conversion, this may take A LONG time ****");
    boolean flag = false;
    


    for (int i = 0; (!flag) && (i <= 2); i++) {
      if (i > 0)
      {
        i.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
        aG();
      }
      
      flag = NameReferencingFileConverter.a(this);
    }
    
    boolean flag1 = false;
    
    for (i = 0; (!flag1) && (i <= 2); i++) {
      if (i > 0)
      {
        i.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
        aG();
      }
      
      flag1 = NameReferencingFileConverter.b(this);
    }
    
    boolean flag2 = false;
    
    for (i = 0; (!flag2) && (i <= 2); i++) {
      if (i > 0)
      {
        i.warn("Encountered a problem while converting the op list, retrying in a few seconds");
        aG();
      }
      
      flag2 = NameReferencingFileConverter.c(this);
    }
    
    boolean flag3 = false;
    
    for (i = 0; (!flag3) && (i <= 2); i++) {
      if (i > 0)
      {
        i.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
        aG();
      }
      
      flag3 = NameReferencingFileConverter.d(this);
    }
    
    boolean flag4 = false;
    
    for (i = 0; (!flag4) && (i <= 2); i++) {
      if (i > 0)
      {
        i.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
        aG();
      }
      
      flag4 = NameReferencingFileConverter.a(this, this.propertyManager);
    }
    
    return (flag) || (flag1) || (flag2) || (flag3) || (flag4);
  }
  
  private void aG() {
    try {
      Thread.sleep(5000L);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public PlayerList getPlayerList()
  {
    return aC();
  }
  
  static org.apache.logging.log4j.Logger aF() {
    return i;
  }
}
