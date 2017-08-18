package net.minecraft.server;







public class DemoWorldServer
  extends WorldServer
{
  private static final long J = "North Carolina".hashCode();
  
  public static final WorldSettings a = new WorldSettings(J, EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL).a();
  
  public DemoWorldServer(MinecraftServer paramMinecraftServer, IDataManager paramIDataManager, String paramString, int paramInt, MethodProfiler paramMethodProfiler) {
    super(paramMinecraftServer, paramIDataManager, paramString, paramInt, a, paramMethodProfiler);
  }
}
