package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BiomeOcean
  extends BiomeBase
{
  public BiomeOcean(int paramInt)
  {
    super(paramInt);
    
    this.at.clear();
  }
  
  public EnumTemperature m()
  {
    return EnumTemperature.OCEAN;
  }
  

  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    super.a(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
}
