package net.minecraft.server;

import java.util.Comparator;

public class PlayerDistanceComparator
  implements Comparator
{
  private final ChunkCoordinates a;
  
  public PlayerDistanceComparator(ChunkCoordinates paramChunkCoordinates)
  {
    this.a = paramChunkCoordinates;
  }
  
  public int a(EntityPlayer paramEntityPlayer1, EntityPlayer paramEntityPlayer2)
  {
    double d1 = paramEntityPlayer1.e(this.a.x, this.a.y, this.a.z);
    double d2 = paramEntityPlayer2.e(this.a.x, this.a.y, this.a.z);
    
    if (d1 < d2)
      return -1;
    if (d1 > d2) {
      return 1;
    }
    return 0;
  }
}
