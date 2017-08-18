package net.minecraft.server;







public class SecondaryWorldData
  extends WorldData
{
  private final WorldData a;
  





  public SecondaryWorldData(WorldData paramWorldData)
  {
    this.a = paramWorldData;
  }
  
  public NBTTagCompound a()
  {
    return this.a.a();
  }
  
  public NBTTagCompound a(NBTTagCompound paramNBTTagCompound)
  {
    return this.a.a(paramNBTTagCompound);
  }
  
  public long getSeed()
  {
    return this.a.getSeed();
  }
  
  public int c()
  {
    return this.a.c();
  }
  
  public int d()
  {
    return this.a.d();
  }
  
  public int e()
  {
    return this.a.e();
  }
  
  public long getTime()
  {
    return this.a.getTime();
  }
  
  public long getDayTime()
  {
    return this.a.getDayTime();
  }
  





  public NBTTagCompound i()
  {
    return this.a.i();
  }
  
  public int j()
  {
    return this.a.j();
  }
  
  public String getName()
  {
    return this.a.getName();
  }
  
  public int l()
  {
    return this.a.l();
  }
  





  public boolean isThundering()
  {
    return this.a.isThundering();
  }
  
  public int getThunderDuration()
  {
    return this.a.getThunderDuration();
  }
  
  public boolean hasStorm()
  {
    return this.a.hasStorm();
  }
  
  public int getWeatherDuration()
  {
    return this.a.getWeatherDuration();
  }
  
  public EnumGamemode getGameType()
  {
    return this.a.getGameType();
  }
  





  public void setTime(long paramLong) {}
  





  public void setDayTime(long paramLong) {}
  





  public void setSpawn(int paramInt1, int paramInt2, int paramInt3) {}
  





  public void setName(String paramString) {}
  





  public void e(int paramInt) {}
  





  public void setThundering(boolean paramBoolean) {}
  




  public void setThunderDuration(int paramInt) {}
  




  public void setStorm(boolean paramBoolean) {}
  




  public void setWeatherDuration(int paramInt) {}
  




  public boolean shouldGenerateMapFeatures()
  {
    return this.a.shouldGenerateMapFeatures();
  }
  



  public boolean isHardcore()
  {
    return this.a.isHardcore();
  }
  
  public WorldType getType()
  {
    return this.a.getType();
  }
  

  public void setType(WorldType paramWorldType) {}
  

  public boolean allowCommands()
  {
    return this.a.allowCommands();
  }
  




  public boolean isInitialized()
  {
    return this.a.isInitialized();
  }
  

  public void d(boolean paramBoolean) {}
  

  public GameRules getGameRules()
  {
    return this.a.getGameRules();
  }
}
