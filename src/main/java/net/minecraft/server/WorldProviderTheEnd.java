package net.minecraft.server;






public class WorldProviderTheEnd
  extends WorldProvider
{
  public void b()
  {
    this.e = new WorldChunkManagerHell(BiomeBase.SKY, 0.0F);
    this.dimension = 1;
    this.g = true;
  }
  
  public IChunkProvider getChunkProvider()
  {
    return new ChunkProviderTheEnd(this.b, this.b.getSeed());
  }
  
  public float a(long paramLong, float paramFloat)
  {
    return 0.0F;
  }
  



























  public boolean e()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  





  public boolean canSpawn(int paramInt1, int paramInt2)
  {
    return this.b.b(paramInt1, paramInt2).getMaterial().isSolid();
  }
  
  public ChunkCoordinates h()
  {
    return new ChunkCoordinates(100, 50, 0);
  }
  
  public int getSeaLevel()
  {
    return 50;
  }
  





  public String getName()
  {
    return "The End";
  }
}
