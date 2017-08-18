package net.minecraft.server;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;







public class CommandDebug
  extends CommandAbstract
{
  private static final Logger a = ;
  private long b;
  private int c;
  
  public String getCommand()
  {
    return "debug";
  }
  
  public int a()
  {
    return 3;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.debug.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      if (paramArrayOfString[0].equals("start")) {
        a(paramICommandListener, this, "commands.debug.start", new Object[0]);
        
        MinecraftServer.getServer().am();
        this.b = MinecraftServer.ar();
        this.c = MinecraftServer.getServer().al();
        return; }
      if (paramArrayOfString[0].equals("stop")) {
        if (!MinecraftServer.getServer().methodProfiler.a) {
          throw new CommandException("commands.debug.notStarted", new Object[0]);
        }
        
        long l1 = MinecraftServer.ar();
        int i = MinecraftServer.getServer().al();
        
        long l2 = l1 - this.b;
        int j = i - this.c;
        
        a(l2, j);
        
        MinecraftServer.getServer().methodProfiler.a = false;
        a(paramICommandListener, this, "commands.debug.stop", new Object[] { Float.valueOf((float)l2 / 1000.0F), Integer.valueOf(j) });
        return;
      }
    }
    
    throw new ExceptionUsage("commands.debug.usage", new Object[0]);
  }
  
  private void a(long paramLong, int paramInt) {
    File localFile = new File(MinecraftServer.getServer().d("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
    
    localFile.getParentFile().mkdirs();
    try
    {
      FileWriter localFileWriter = new FileWriter(localFile);
      localFileWriter.write(b(paramLong, paramInt));
      localFileWriter.close();
    } catch (Throwable localThrowable) {
      a.error("Could not save profiler results to " + localFile, localThrowable);
    }
  }
  
  private String b(long paramLong, int paramInt) {
    StringBuilder localStringBuilder = new StringBuilder();
    
    localStringBuilder.append("---- Minecraft Profiler Results ----\n");
    localStringBuilder.append("// ");
    localStringBuilder.append(d());
    localStringBuilder.append("\n\n");
    
    localStringBuilder.append("Time span: ").append(paramLong).append(" ms\n");
    localStringBuilder.append("Tick span: ").append(paramInt).append(" ticks\n");
    localStringBuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(paramInt / ((float)paramLong / 1000.0F)) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
    

    localStringBuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
    
    a(0, "root", localStringBuilder);
    
    localStringBuilder.append("--- END PROFILE DUMP ---\n\n");
    
    return localStringBuilder.toString();
  }
  
  private void a(int paramInt, String paramString, StringBuilder paramStringBuilder) {
    List localList = MinecraftServer.getServer().methodProfiler.b(paramString);
    if ((localList == null) || (localList.size() < 3)) { return;
    }
    for (int i = 1; i < localList.size(); i++) {
      ProfilerInfo localProfilerInfo = (ProfilerInfo)localList.get(i);
      
      paramStringBuilder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(paramInt) }));
      for (int j = 0; j < paramInt; j++)
        paramStringBuilder.append(" ");
      paramStringBuilder.append(localProfilerInfo.c);
      paramStringBuilder.append(" - ");
      paramStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(localProfilerInfo.a) }));
      paramStringBuilder.append("%/");
      paramStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(localProfilerInfo.b) }));
      paramStringBuilder.append("%\n");
      
      if (!localProfilerInfo.c.equals("unspecified")) {
        try {
          a(paramInt + 1, paramString + "." + localProfilerInfo.c, paramStringBuilder);
        } catch (Exception localException) {
          paramStringBuilder.append("[[ EXCEPTION " + localException + " ]]");
        }
      }
    }
  }
  
  private static String d()
  {
    String[] arrayOfString = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
    



    try
    {
      return arrayOfString[((int)(System.nanoTime() % arrayOfString.length))];
    } catch (Throwable localThrowable) {}
    return "Witty comment unavailable :(";
  }
  

  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) { return a(paramArrayOfString, new String[] { "start", "stop" });
    }
    return null;
  }
}
