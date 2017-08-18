package net.minecraft.server;

import java.util.concurrent.Callable;























































































































































































class CrashReportTileEntityData
  implements Callable
{
  CrashReportTileEntityData(TileEntity paramTileEntity) {}
  
  public String a()
  {
    int i = this.a.world.getData(this.a.x, this.a.y, this.a.z);
    if (i < 0) return "Unknown? (Got " + i + ")";
    String str = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
    

    return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), str });
  }
}
