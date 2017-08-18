package net.minecraft.server;

public class ChunkCoordIntPair
{
  public final int x;
  public final int z;
  
  public ChunkCoordIntPair(int paramInt1, int paramInt2) {
    this.x = paramInt1;
    this.z = paramInt2;
  }
  
  public static long a(int paramInt1, int paramInt2) {
    return paramInt1 & 0xFFFFFFFF | (paramInt2 & 0xFFFFFFFF) << 32;
  }
  




  public int hashCode()
  {
    int i = 1664525 * this.x + 1013904223;
    int j = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
    return i ^ j;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    
    if ((paramObject instanceof ChunkCoordIntPair)) {
      ChunkCoordIntPair localChunkCoordIntPair = (ChunkCoordIntPair)paramObject;
      
      return (this.x == localChunkCoordIntPair.x) && (this.z == localChunkCoordIntPair.z);
    }
    
    return false;
  }
  









  public int a()
  {
    return (this.x << 4) + 8;
  }
  
  public int b() {
    return (this.z << 4) + 8;
  }
  
  public ChunkPosition a(int paramInt) {
    return new ChunkPosition(a(), paramInt, b());
  }
  
  public String toString() {
    return "[" + this.x + ", " + this.z + "]";
  }
}
