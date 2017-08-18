package net.minecraft.server;

public class TileEntityLightDetector
  extends TileEntity
{
  public void h()
  {
    if ((this.world != null) && (!this.world.isStatic)) {
      this.h = q();
      if ((this.h instanceof BlockDaylightDetector)) {
        ((BlockDaylightDetector)this.h).e(this.world, this.x, this.y, this.z);
      }
    }
  }
}
