package net.minecraft.server;





























































































































































































public class ChunkCoordinatesPortal
  extends ChunkCoordinates
{
  public long d;
  



























































































































































































  public ChunkCoordinatesPortal(PortalTravelAgent paramPortalTravelAgent, int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    super(paramInt1, paramInt2, paramInt3);
    this.d = paramLong;
  }
}