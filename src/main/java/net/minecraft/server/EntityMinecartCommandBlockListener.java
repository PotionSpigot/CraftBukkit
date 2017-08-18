package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftMinecartCommand;

public class EntityMinecartCommandBlockListener extends CommandBlockListenerAbstract {
  final EntityMinecartCommandBlock a;
  
  EntityMinecartCommandBlockListener(EntityMinecartCommandBlock entityminecartcommandblock) {
    this.a = entityminecartcommandblock;
    this.sender = ((CraftMinecartCommand)entityminecartcommandblock.getBukkitEntity());
  }
  
  public void e() {
    this.a.getDataWatcher().watch(23, getCommand());
    this.a.getDataWatcher().watch(24, ChatSerializer.a(h()));
  }
  
  public ChunkCoordinates getChunkCoordinates() {
    return new ChunkCoordinates(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY + 0.5D), MathHelper.floor(this.a.locZ));
  }
  
  public World getWorld() {
    return this.a.world;
  }
}
