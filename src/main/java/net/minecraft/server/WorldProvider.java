package net.minecraft.server;











public abstract class WorldProvider
{
  public static final float[] a = { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
  
  public World b;
  
  public WorldType type;
  public String d;
  public WorldChunkManager e;
  public boolean f;
  public boolean g;
  public float[] h = new float[16];
  public int dimension;
  
  public final void a(World paramWorld) {
    this.b = paramWorld;
    this.type = paramWorld.getWorldData().getType();
    this.d = paramWorld.getWorldData().getGeneratorOptions();
    b();
    a();
  }
  
  protected void a() {
    float f1 = 0.0F;
    for (int i = 0; i <= 15; i++) {
      float f2 = 1.0F - i / 15.0F;
      this.h[i] = ((1.0F - f2) / (f2 * 3.0F + 1.0F) * (1.0F - f1) + f1);
    }
  }
  
  protected void b() {
    if (this.b.getWorldData().getType() == WorldType.FLAT) {
      WorldGenFlatInfo localWorldGenFlatInfo = WorldGenFlatInfo.a(this.b.getWorldData().getGeneratorOptions());
      this.e = new WorldChunkManagerHell(BiomeBase.getBiome(localWorldGenFlatInfo.a()), 0.5F);
    } else {
      this.e = new WorldChunkManager(this.b);
    }
  }
  
  public IChunkProvider getChunkProvider() {
    if (this.type == WorldType.FLAT)
    {
      return new ChunkProviderFlat(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.d);
    }
    return new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures());
  }
  
  public boolean canSpawn(int paramInt1, int paramInt2)
  {
    return this.b.b(paramInt1, paramInt2) == Blocks.GRASS;
  }
  
  public float a(long paramLong, float paramFloat) {
    int i = (int)(paramLong % 24000L);
    float f1 = (i + paramFloat) / 24000.0F - 0.25F;
    if (f1 < 0.0F) f1 += 1.0F;
    if (f1 > 1.0F) f1 -= 1.0F;
    float f2 = f1;
    f1 = 1.0F - (float)((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
    f1 = f2 + (f1 - f2) / 3.0F;
    return f1;
  }
  
  public int a(long paramLong)
  {
    return (int)(paramLong / 24000L % 8L + 8L) % 8;
  }
  
  public boolean d() {
    return true;
  }
  


  private float[] j = new float[4];
  

































  public boolean e()
  {
    return true;
  }
  
  public static WorldProvider byDimension(int paramInt) {
    if (paramInt == -1) return new WorldProviderHell();
    if (paramInt == 0) return new WorldProviderNormal();
    if (paramInt == 1) return new WorldProviderTheEnd();
    return null;
  }
  







  public ChunkCoordinates h()
  {
    return null;
  }
  
  public int getSeaLevel() {
    if (this.type == WorldType.FLAT) {
      return 4;
    }
    return 64;
  }
  
  public abstract String getName();
}
