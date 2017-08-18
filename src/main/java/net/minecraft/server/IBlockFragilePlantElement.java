package net.minecraft.server;

import java.util.Random;

public abstract interface IBlockFragilePlantElement
{
  public abstract boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
  
  public abstract boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
}
