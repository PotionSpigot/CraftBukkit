package net.minecraft.server;

public abstract interface IMojangStatistics
{
  public abstract void a(MojangStatisticsGenerator paramMojangStatisticsGenerator);
  
  public abstract void b(MojangStatisticsGenerator paramMojangStatisticsGenerator);
  
  public abstract boolean getSnooperEnabled();
}
