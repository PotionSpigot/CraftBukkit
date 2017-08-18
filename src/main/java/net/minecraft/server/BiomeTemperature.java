package net.minecraft.server;










public class BiomeTemperature
{
  public float a;
  








  public float b;
  









  public BiomeTemperature(float paramFloat1, float paramFloat2)
  {
    this.a = paramFloat1;
    this.b = paramFloat2;
  }
  
  public BiomeTemperature a() {
    return new BiomeTemperature(this.a * 0.8F, this.b * 0.6F);
  }
}
