package net.minecraft.server;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class WorldGenMineshaft
  extends StructureGenerator
{
  private double e = 0.004D;
  

  public WorldGenMineshaft() {}
  
  public String a()
  {
    return "Mineshaft";
  }
  
  public WorldGenMineshaft(Map paramMap) {
    for (Entry localEntry : paramMap.entrySet()) {
      if (((String)localEntry.getKey()).equals("chance")) {
        this.e = MathHelper.a((String)localEntry.getValue(), this.e);
      }
    }
  }
  
  protected boolean a(int paramInt1, int paramInt2)
  {
    return (this.b.nextDouble() < this.e) && (this.b.nextInt(80) < Math.max(Math.abs(paramInt1), Math.abs(paramInt2)));
  }
  
  protected StructureStart b(int paramInt1, int paramInt2)
  {
    return new WorldGenMineshaftStart(this.c, this.b, paramInt1, paramInt2);
  }
}
