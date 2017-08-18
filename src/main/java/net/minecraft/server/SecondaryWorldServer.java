package net.minecraft.server;

import org.bukkit.World.Environment;

public class SecondaryWorldServer extends WorldServer {
  public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, WorldServer worldserver, MethodProfiler methodprofiler, World.Environment env, org.bukkit.generator.ChunkGenerator gen) { super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, env, gen);
    
    this.worldMaps = worldserver.worldMaps;
    this.scoreboard = worldserver.getScoreboard();
  }
}
