package net.minecraft.server;

import java.util.Random;


public abstract class WorldGenerator
{
  private final boolean a;
  
  public WorldGenerator()
  {
    this.a = false;
  }
  
  public WorldGenerator(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  

  public abstract boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
  

  public void a(double paramDouble1, double paramDouble2, double paramDouble3) {}
  

  protected void setType(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    setTypeAndData(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, 0);
  }
  
  protected void setTypeAndData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
    if (this.a) {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, paramBlock, paramInt4, 3);
    } else {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, paramBlock, paramInt4, 2);
    }
  }
}
