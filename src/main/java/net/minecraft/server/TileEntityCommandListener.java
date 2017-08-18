package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.command.CraftBlockCommandSender;

public class TileEntityCommandListener extends CommandBlockListenerAbstract {
  final TileEntityCommand a;
  
  TileEntityCommandListener(TileEntityCommand tileentitycommand) {
    this.a = tileentitycommand;
    this.sender = new CraftBlockCommandSender(this);
  }
  
  public ChunkCoordinates getChunkCoordinates() {
    return new ChunkCoordinates(this.a.x, this.a.y, this.a.z);
  }
  
  public World getWorld() {
    return this.a.getWorld();
  }
  
  public void setCommand(String s) {
    super.setCommand(s);
    this.a.update();
  }
  
  public void e() {
    this.a.getWorld().notify(this.a.x, this.a.y, this.a.z);
  }
}
