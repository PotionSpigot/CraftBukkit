package net.minecraft.server;

import java.util.concurrent.Callable;
























































































































































































































































































































































































































































class CrashReportLevelGameMode
  implements Callable
{
  CrashReportLevelGameMode(WorldData paramWorldData) {}
  
  public String a()
  {
    return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldData.o(this.a).b(), Integer.valueOf(WorldData.o(this.a).getId()), Boolean.valueOf(WorldData.p(this.a)), Boolean.valueOf(WorldData.q(this.a)) });
  }
}