package net.minecraft.server;

import java.util.List;
import java.util.Random;





public class CommandWeather
  extends CommandAbstract
{
  public String getCommand()
  {
    return "weather";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.weather.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if ((paramArrayOfString.length < 1) || (paramArrayOfString.length > 2)) {
      throw new ExceptionUsage("commands.weather.usage", new Object[0]);
    }
    
    int i = (300 + new Random().nextInt(600)) * 20;
    if (paramArrayOfString.length >= 2) {
      i = a(paramICommandListener, paramArrayOfString[1], 1, 1000000) * 20;
    }
    
    WorldServer localWorldServer = MinecraftServer.getServer().worldServer[0];
    WorldData localWorldData = localWorldServer.getWorldData();
    
    if ("clear".equalsIgnoreCase(paramArrayOfString[0])) {
      localWorldData.setWeatherDuration(0);
      localWorldData.setThunderDuration(0);
      localWorldData.setStorm(false);
      localWorldData.setThundering(false);
      a(paramICommandListener, this, "commands.weather.clear", new Object[0]);
    } else if ("rain".equalsIgnoreCase(paramArrayOfString[0])) {
      localWorldData.setWeatherDuration(i);
      localWorldData.setStorm(true);
      localWorldData.setThundering(false);
      a(paramICommandListener, this, "commands.weather.rain", new Object[0]);
    } else if ("thunder".equalsIgnoreCase(paramArrayOfString[0])) {
      localWorldData.setWeatherDuration(i);
      localWorldData.setThunderDuration(i);
      localWorldData.setStorm(true);
      localWorldData.setThundering(true);
      a(paramICommandListener, this, "commands.weather.thunder", new Object[0]);
    } else {
      throw new ExceptionUsage("commands.weather.usage", new Object[0]);
    }
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, new String[] { "clear", "rain", "thunder" });
    }
    
    return null;
  }
}
