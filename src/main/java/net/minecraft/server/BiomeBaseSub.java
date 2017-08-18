package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

public class BiomeBaseSub extends BiomeBase
{
  protected BiomeBase aD;
  
  public BiomeBaseSub(int paramInt, BiomeBase paramBiomeBase)
  {
    super(paramInt);
    this.aD = paramBiomeBase;
    a(paramBiomeBase.ag, true);
    
    this.af = (paramBiomeBase.af + " M");
    
    this.ai = paramBiomeBase.ai;
    this.ak = paramBiomeBase.ak;
    this.al = paramBiomeBase.al;
    this.am = paramBiomeBase.am;
    this.an = paramBiomeBase.an;
    this.temperature = paramBiomeBase.temperature;
    this.humidity = paramBiomeBase.humidity;
    this.aq = paramBiomeBase.aq;
    this.aw = paramBiomeBase.aw;
    this.ax = paramBiomeBase.ax;
    
    this.at = new ArrayList(paramBiomeBase.at);
    this.as = new ArrayList(paramBiomeBase.as);
    this.av = new ArrayList(paramBiomeBase.av);
    this.au = new ArrayList(paramBiomeBase.au);
    
    this.temperature = paramBiomeBase.temperature;
    this.humidity = paramBiomeBase.humidity;
    
    this.am = (paramBiomeBase.am + 0.1F);
    this.an = (paramBiomeBase.an + 0.2F);
  }
  

  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    this.aD.ar.a(paramWorld, paramRandom, this, paramInt1, paramInt2);
  }
  
  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    this.aD.a(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
  
  public float g()
  {
    return this.aD.g();
  }
  
  public WorldGenTreeAbstract a(Random paramRandom)
  {
    return this.aD.a(paramRandom);
  }

  public Class l()
  {
    return this.aD.l();
  }
  
  public boolean a(BiomeBase paramBiomeBase)
  {
    return this.aD.a(paramBiomeBase);
  }
  
  public EnumTemperature m()
  {
    return this.aD.m();
  }
}
